package com.touraj.creditsuisse.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by toraj on 03/22/2017.
 */
public class Utility {


    private static String filePath = "c:/swiss/example_input.txt";

    public static int getDayofWeek(String date) {

        int dayOfWeek = 0;
        try {
            Calendar c = Calendar.getInstance();

            SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
            Date dt1 = null;

            dt1 = format1.parse(date);

            c.setTime(dt1);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayOfWeek;
    }

    public static int convertMonthtoInt(String month) {

        List<String> validMonthes = Arrays.asList("JAN","FEB", "MAR",
                "APR", "MAY" ,"JUN",
                "JUL", "AUG", "SEP",
                "OCT", "NOV", "DEC");

        int idx = validMonthes.indexOf(month.toUpperCase());

        if (idx != -1) {
            //[Touraj]  :: Letting Months to start from 1
            ++idx;
        }

        return idx;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        Utility.filePath = filePath;
    }
}
