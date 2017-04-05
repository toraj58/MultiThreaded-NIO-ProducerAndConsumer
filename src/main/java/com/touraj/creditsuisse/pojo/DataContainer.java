package com.touraj.creditsuisse.pojo;

import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by toraj on 03/22/2017.
 */
public class DataContainer {

    private final static Lock lock = new ReentrantLock();

    static TreeSet<Instrument> sortedSetOfInstrumentsWithOnly10 = new TreeSet<>();

    static double sumInstrument1= 0;
    static double sumInstrument2= 0;
    static double maxInstrument3= 0;

    static long countInstrument1;
    static long countInstrument2;

    public static double getSumInstrument1() {
        return sumInstrument1;
    }

    public static void setSumInstrument1(double si1) {
        sumInstrument1 = si1;
    }

    public static double getSumInstrument2() {
        return sumInstrument2;
    }

    public static void setSumInstrument2(double si2) {
        sumInstrument2 = si2;
    }

    public static long getCountInstrument1() {
        return countInstrument1;
    }

    public static void setCountInstrument1(long ci1) {
        countInstrument1 = ci1;
    }

    public static long getCountInstrument2() {
        return countInstrument2;
    }

    public static double getMaxInstrument3() {
        return maxInstrument3;
    }

    public static void setMaxInstrument3(double maxInstrument3) {
        DataContainer.maxInstrument3 = maxInstrument3;
    }

    public static void setCountInstrument2(long ci2) {
        countInstrument2 = ci2;
    }

    public static void updateSumInstrument1(double value)
    {
        try {
            lock.lock();

            sumInstrument1 += value;
            ++countInstrument1;

        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {

        lock.unlock();
        }
    }

    public static synchronized void updateSumInstrument2(double val)
    {
        try {
            //[touraj] :: preparing Sum and Count For Calculation Algorithm 2 for Instrument2

            sumInstrument2 += val;
            ++countInstrument2;

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static double getMeanforInstrument1()
    {
        return sumInstrument1 / countInstrument1;
    }

    public static double getMeanforInstrument2()
    {
        return sumInstrument2 / countInstrument2;
    }

    public static synchronized void updateMaxInstrument3(double newValue)
    {
        //[Touraj] :: updating Max of Instrument 3
        if (newValue > maxInstrument3) {
            setMaxInstrument3(newValue);
        }


    }

    public static synchronized void addToSortedSetOfInstrumentsWithOnly10(Instrument inst) {

        sortedSetOfInstrumentsWithOnly10.add(inst);

//        [Touraj] :: I hold only top 10 Instruments sorted base on date Desc

        if (sortedSetOfInstrumentsWithOnly10.size() > 10) {
            sortedSetOfInstrumentsWithOnly10.remove(sortedSetOfInstrumentsWithOnly10.last());
        }

    }

    public static synchronized void printOutSortedSetOfInstrumentsWithOnly10() {

        for (Instrument instrument : sortedSetOfInstrumentsWithOnly10) {

            System.out.println(instrument.toString());

        }

    }

    public static synchronized double getSumOfSortedSetOfInstrumentsWithOnly10()
    {
        double sum = 0;

        for (Instrument instrument : sortedSetOfInstrumentsWithOnly10) {

            sum+=instrument.getValue();

        }

        return sum;
    }

}
