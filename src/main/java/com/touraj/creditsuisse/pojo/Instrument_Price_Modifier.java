package com.touraj.creditsuisse.pojo;

/**
 * Created by toraj on 03/21/2017.
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Instrument_Price_Modifier {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private Double multiplier;

    protected Instrument_Price_Modifier() {}

    public Instrument_Price_Modifier(String name, Double multiplier) {
        this.name = name;
        this.multiplier = multiplier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public String toString() {
        return String.format(
                "INSTRUMENT_PRICE_MODIFIER[id=%d, name='%s', multiplier='%s']",
                id, name, multiplier);
    }

}
