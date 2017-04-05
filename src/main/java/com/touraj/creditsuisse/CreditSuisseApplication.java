package com.touraj.creditsuisse;

import com.touraj.creditsuisse.io.FileProcessor;
import com.touraj.creditsuisse.io.FileReaderNIO;
import com.touraj.creditsuisse.pojo.DataContainer;
import com.touraj.creditsuisse.pojo.Instrument_Price_Modifier;
import com.touraj.creditsuisse.repository.Instrument_Price_ModifierRepository;
import com.touraj.creditsuisse.util.Utility;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.lang.System.exit;

@SpringBootApplication
public class CreditSuisseApplication implements CommandLineRunner {

	@Autowired
	Instrument_Price_ModifierRepository repository;

	private static final Logger log = LoggerFactory.getLogger(CreditSuisseApplication.class);

	private static final int FILEPROCESSOR_COUNT = 11;

	private MutableBoolean isFileReadCompleted = new MutableBoolean(false);
	private final static BlockingQueue<String> linesBLQueue = new ArrayBlockingQueue<String>(30);

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(CreditSuisseApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		System.out.println("***** Credit Suisse Application By Touraj Ebrahimi ******");
		System.out.println("Please wait till Spring Boot Initialization finished");
		app.run(args);

	}

	@Override
	public void run(String... strings) throws Exception {

		System.out.println("Program Starting ...");
		System.out.println("Please Wait till all threads and Calculations finished ...");

		File file = new File(Utility.getFilePath());

		while (!file.exists()) {
			//[Touraj] :: if default file path not found then asking user to input valid file path

			System.out.println("File Not Found :: " + Utility.getFilePath());

			BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Please input valid file path:");
			String filePath=buffer.readLine();
			Utility.setFilePath(filePath);

			file = new File(filePath);
		}

		//[Touraj] :: Initializing H2 in memory Database with these sample data for multipliers
		repository.save(new Instrument_Price_Modifier("INSTRUMENT1", 2d));
		repository.save(new Instrument_Price_Modifier("INSTRUMENT2", 1d));
		repository.save(new Instrument_Price_Modifier("INSTRUMENT3", 1d));

		// [Touraj] pool of consumer threads to process the lines in Blocking Queue + one Producer
		ExecutorService consumerPoolFileProcessors = Executors.newFixedThreadPool(FILEPROCESSOR_COUNT);

		consumerPoolFileProcessors.submit(new FileReaderNIO(linesBLQueue, isFileReadCompleted));

		for (int i = 0; i < FILEPROCESSOR_COUNT-1; i++) {
			consumerPoolFileProcessors.submit(new FileProcessor(linesBLQueue, isFileReadCompleted, repository));
		}

		consumerPoolFileProcessors.shutdown();

		while ( !consumerPoolFileProcessors.isTerminated()) {
			//[Touraj] :: Waiting Loop unitl all Consumers and Producers in Pool are finished and
			// when Blocking Queue is empty and all data have been read and processed.
		}

		System.out.println("*************************************");
		System.out.println("******                         ******");
		System.out.println("****** Calculating Algorithms  ******");
		System.out.println("******                         ******");
		System.out.println("*************************************");

		System.out.println("Mean for Instrument1 = " + DataContainer.getMeanforInstrument1());

		System.out.println("Mean for Instrument2 (for Values from November 2014) = " + DataContainer.getMeanforInstrument2());

		System.out.println("Max of Instrument3 (Another Statistical Calculation) = " + DataContainer.getMaxInstrument3());

		System.out.println("************* Instrument4 or Another Instrument Algorithm (SUM) **************");

		System.out.println("Sum of Other Instrument (newest 10) is : " + DataContainer.getSumOfSortedSetOfInstrumentsWithOnly10());

		exit(0);
	}
}
