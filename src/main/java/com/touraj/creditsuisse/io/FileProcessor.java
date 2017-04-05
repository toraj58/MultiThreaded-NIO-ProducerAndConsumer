package com.touraj.creditsuisse.io;

import com.touraj.creditsuisse.pojo.DataContainer;
import com.touraj.creditsuisse.pojo.Instrument;
import com.touraj.creditsuisse.pojo.Instrument_Price_Modifier;
import com.touraj.creditsuisse.repository.Instrument_Price_ModifierRepository;
import com.touraj.creditsuisse.util.Utility;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by toraj on 03/21/2017.
 */

public class FileProcessor implements Runnable {

    Instrument_Price_ModifierRepository repository;

    private static BlockingQueue<String> linesBLQueue =null;
    MutableBoolean isFileReadCompleted;

    public FileProcessor(BlockingQueue<String> linesBLQueue, MutableBoolean isFileReadCompleted, Instrument_Price_ModifierRepository repository) {
        this.linesBLQueue = linesBLQueue;
        this.isFileReadCompleted = isFileReadCompleted;
        this.repository = repository;
    }

    @Override
    public void run() {

        try {
            while (!isFileReadCompleted.getValue() || (isFileReadCompleted.getValue() && !linesBLQueue.isEmpty())) {
//                Touraj :: [Would be better :] to use also Poison Pill Shutdown
                String lineToProcess = linesBLQueue.take();

                //My bad ! I should not used Poll because with Delay in Producer threads I may get NullPointerExption
                // Better way is using Take + poison Pill Shutdown (sending Sentinel Value to Consumer to force them shutdown)
//                String lineToProcess = linesBLQueue.poll(2000, TimeUnit.MILLISECONDS);

                //[Touraj] :: Following is a work around
                // It is not neccessary to be done when using take() + poison pill shutdown technique.
                if (lineToProcess == null) {
                    continue;
                }

                String[] tokens = lineToProcess.split(",");

                String instrumentName = tokens[0].trim();
                String date = tokens[1].trim();
                double value = Double.parseDouble(tokens[2].trim());
                int dayofWeek = Utility.getDayofWeek(date);

                double multiplier = 1d;

                // [Touraj] :: Fetching Multiplier Value from Data base :: here H2 Data base
                List<Instrument_Price_Modifier> ipm =  repository.findByName(instrumentName);

                if (ipm.size() > 0) {

                    multiplier = ipm.get(0).getMultiplier();
                }

                //[Touraj] :: Algorithm for INSTRUMENT1
                if (instrumentName.equalsIgnoreCase("INSTRUMENT1") && dayofWeek !=7 && dayofWeek !=1) {
                    //[Touraj] :: only Consider Business Dates so we discard day 7:Saturday and day 1:Sunday
                    DataContainer.updateSumInstrument1(value * multiplier);
                }

                //[Touraj] :: Algorithm for INSTRUMENT2
                if (instrumentName.equalsIgnoreCase("INSTRUMENT2") && dayofWeek !=7 && dayofWeek !=1) {
                    //[Touraj] :: only Consider Business Dates so we discard day 7:Saturday and day 1:Sunday

                    String[] dateParts = date.split("-");

                    int year = Integer.parseInt(dateParts[2]);
                    int monthNumber = Utility.convertMonthtoInt(dateParts[1]);

                    if ((year > 2014) || (year == 2014 && monthNumber >= 11)) {
                        //[Touraj] :: All year greater than 2014 are acceptable for algorithm 2 for Instrument2
                        //[Touraj] :: if year is 2014 then NOV and DEC months are acceptable for algorithm 2 and Instrument2

                        DataContainer.updateSumInstrument2(value * multiplier);
                    }
                }

                //[Touraj] :: Algorithm for INSTRUMENT3
                if (instrumentName.equalsIgnoreCase("INSTRUMENT3") && dayofWeek !=7 && dayofWeek !=1) {
                    //[Touraj] :: only Consider Business Dates so we discard day 7:Saturday and day 1:Sunday
                    DataContainer.updateMaxInstrument3(value * multiplier);
                }

                //[Touraj] :: Algorithm for Other INSTRUMENT like INSTRUMENT4
                if (!instrumentName.equalsIgnoreCase("INSTRUMENT1") && !instrumentName.equalsIgnoreCase("INSTRUMENT2")
                        && !instrumentName.equalsIgnoreCase("INSTRUMENT3") && dayofWeek !=7 && dayofWeek !=1
                        ) {

                    Instrument instNew = new Instrument(instrumentName, date, value * multiplier);
                    DataContainer.addToSortedSetOfInstrumentsWithOnly10(instNew);
                }

//                System.out.println(lineToProcess);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " consumer is done");
    }
}