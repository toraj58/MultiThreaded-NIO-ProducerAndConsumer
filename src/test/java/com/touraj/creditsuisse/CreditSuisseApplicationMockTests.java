package com.touraj.creditsuisse;

import com.touraj.creditsuisse.pojo.Instrument_Price_Modifier;
import com.touraj.creditsuisse.repository.Instrument_Price_ModifierRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CreditSuisseApplicationMockTests {

	@Mock
	private static Instrument_Price_ModifierRepository repo;

	@Test
	public void	getDataFromRepositoryTest() {

		List<Instrument_Price_Modifier> listOFInsts = new ArrayList<>();

		Instrument_Price_Modifier inst1 = new Instrument_Price_Modifier("INSTRUMENT1", 1d);
		Instrument_Price_Modifier inst2 = new Instrument_Price_Modifier("INSTRUMENT2", 2d);
		Instrument_Price_Modifier inst3 = new Instrument_Price_Modifier("INSTRUMENT3", 3d);
		Instrument_Price_Modifier inst4 = new Instrument_Price_Modifier("INSTRUMENT4", 4d);

		listOFInsts.add(inst1);
		listOFInsts.add(inst2);
		listOFInsts.add(inst3);
		listOFInsts.add(inst4);

		Mockito.when(repo.findAll()).thenReturn(listOFInsts);

		for (Instrument_Price_Modifier instrument_price_modifier : repo.findAll()) {
			System.out.println(instrument_price_modifier.toString());

		}

		Assert.assertTrue(repo.findAll().iterator().hasNext());


	}

	@Test
	public void	getDataFromRepositoryByNameTest() {

		List<Instrument_Price_Modifier> listOFInsts = new ArrayList<>();

		Instrument_Price_Modifier inst1 = new Instrument_Price_Modifier("INSTRUMENT1", 1d);
		Instrument_Price_Modifier inst2 = new Instrument_Price_Modifier("INSTRUMENT1", 2d);

		listOFInsts.add(inst1);
		listOFInsts.add(inst2);

		Mockito.when(repo.findByName("INSTRUMENT1")).thenReturn(listOFInsts);

		List<Instrument_Price_Modifier> res = repo.findByName("INSTRUMENT1");

		for (Instrument_Price_Modifier instrument_price_modifier : res) {
			System.out.println(instrument_price_modifier.toString());

		}

		Assert.assertEquals(res.size(), 2);

	}
}
