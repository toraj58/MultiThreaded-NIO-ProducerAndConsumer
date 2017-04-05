package com.touraj.creditsuisse.repository;

/**
 * Created by toraj on 03/21/2017.
 */
import java.util.List;

import com.touraj.creditsuisse.pojo.Instrument_Price_Modifier;
import org.springframework.data.repository.CrudRepository;

public interface Instrument_Price_ModifierRepository extends CrudRepository<Instrument_Price_Modifier, Long> {

    List<Instrument_Price_Modifier> findByMultiplier(Double multiplier);
    List<Instrument_Price_Modifier> findByName(String name);
}