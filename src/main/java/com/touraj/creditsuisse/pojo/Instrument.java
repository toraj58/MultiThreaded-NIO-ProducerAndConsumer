package com.touraj.creditsuisse.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by toraj on 03/24/2017.
 */
public class Instrument implements Comparable<Instrument> {

    String name;
    String date;
    double value;

    public Instrument(String name, String date, double value) {
        this.name = name;
        this.date = date;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int compareTo(Instrument inst) {

        Date dt1 = null;
        Date dt2 = null;
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
            dt1 = format1.parse(this.date);
            dt2 = format1.parse(inst.getDate());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //[Toura] :: Sorting Descending :: Lastest Dates First
        return -dt1.compareTo(dt2);
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", value=" + value +
                '}';
    }

}
