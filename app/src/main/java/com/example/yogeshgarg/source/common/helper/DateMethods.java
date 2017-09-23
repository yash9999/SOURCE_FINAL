package com.example.yogeshgarg.source.common.helper;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yogeshgarg on 02/09/17.
 */

public class DateMethods {

    public static String dateConvertion(String dateFromCalender) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        String formattedDate = null;
        try {
            date = originalFormat.parse(dateFromCalender);
            formattedDate = targetFormat.format(date);
        } catch (ParseException ex) {
            // Handle Exception.
        }
        return formattedDate;
    }

}
