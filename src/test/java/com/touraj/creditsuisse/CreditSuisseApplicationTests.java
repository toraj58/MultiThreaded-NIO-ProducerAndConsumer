package com.touraj.creditsuisse;

import com.touraj.creditsuisse.pojo.Instrument;
import com.touraj.creditsuisse.repository.Instrument_Price_ModifierRepository;
import com.touraj.creditsuisse.util.Utility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.TreeSet;

@RunWith(SpringJUnit4ClassRunner.class)
public class CreditSuisseApplicationTests {

	@Mock
	private static Instrument_Price_ModifierRepository repo;

	@Test
	public void testTreeSetWithOnlymost10Elements() {
		{

			TreeSet<Instrument> treeSetofInst = new TreeSet<>();

			Instrument ins1 = new Instrument("ins1", "08-Nov-2015", 10);
			Instrument ins2 = new Instrument("ins2", "12-Jan-2015", 100);
			Instrument ins3 = new Instrument("ins3", "10-Feb-2015", 12);

			Instrument ins4 = new Instrument("ins4", "10-Feb-2016", 12000);
			Instrument ins5 = new Instrument("ins5", "01-Jan-2017", 120);
			Instrument ins6 = new Instrument("ins6", "10-Feb-2014", 1200);

			Instrument ins7 = new Instrument("ins7", "10-Feb-2018", 12000);
			Instrument ins8 = new Instrument("ins8", "01-Jan-2018", 120);
			Instrument ins9 = new Instrument("ins9", "10-Feb-2020", 1200);

			Instrument ins10 = new Instrument("ins10", "10-Feb-2010", 12000);
			Instrument ins11 = new Instrument("ins11", "01-Jan-2013", 120);

			treeSetofInst.add(ins1);
			treeSetofInst.add(ins2);
			treeSetofInst.add(ins3);
			treeSetofInst.add(ins4);
			treeSetofInst.add(ins5);
			treeSetofInst.add(ins6);
			treeSetofInst.add(ins7);
			treeSetofInst.add(ins8);
			treeSetofInst.add(ins9);
			treeSetofInst.add(ins10);
			treeSetofInst.add(ins11);

			if (treeSetofInst.size() > 10) {
				treeSetofInst.remove(treeSetofInst.last());
			}

			Assert.assertEquals(treeSetofInst.size(), 10);

		}
	}

	@Test
	public void convertMonthtoIntTest() {

		int idx = Utility.convertMonthtoInt("MAY");
		System.out.println("idx of month :: " + idx);
		org.junit.Assert.assertNotEquals(-1, idx);

	}

	@Test
	public void convertMonthtoInt_invalidMonthTest() {

		int idx = Utility.convertMonthtoInt("QWE");
		System.out.println("idx of month :: " + idx);
		org.junit.Assert.assertEquals(-1, idx);

	}

	@Test
	public void getDayOfWeekTest()
	{

		int dayOfWeek = Utility.getDayofWeek("26-Mar-2017");
		System.out.println("Day of week for 26-Mar-2017 is : " + dayOfWeek);
		Assert.assertEquals(1 , dayOfWeek);

	}

}
