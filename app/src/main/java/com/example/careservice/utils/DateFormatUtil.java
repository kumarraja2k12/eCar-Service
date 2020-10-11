package com.example.careservice.utils;

import android.text.format.DateUtils;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {

    public static String formatDate(String dateInMillisecondsString) {
        long dateInMilliseconds = Long.parseLong(dateInMillisecondsString);
        Date date = new Date(dateInMilliseconds);
        if(DateUtils.isToday(dateInMilliseconds)) {
            SimpleDateFormat todayDateFormat = new SimpleDateFormat("hh:mm:ss a");
            return todayDateFormat.format(date);
        } else if (isWithinWeek(date)) {
            SimpleDateFormat todayDateFormat = new SimpleDateFormat("EEE hh:mm");
            return todayDateFormat.format(date);
        } else {
            SimpleDateFormat todayDateFormat = new SimpleDateFormat("MMM hh:mm");
            return todayDateFormat.format(date);
        }
    }

    private static boolean isWithinWeek(Date date) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat wdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date today = new Date();
            Date startOfToday = wdf.parse(sdf.format(today) + " 00:00:00");

            Calendar cal = Calendar.getInstance();
            //Displaying current date in the desired format
            System.out.println("Current Date: "+sdf.format(cal.getTime()));

            //Number of Days to add
            cal.setTime(startOfToday);
            cal.add(Calendar.DAY_OF_MONTH, -6);

            //Date after adding the days to the current date
            Date fromDate = cal.getTime();
            Date toDate = startOfToday;

            return fromDate.compareTo(date) * date.compareTo(toDate) >= 0;

        }catch(ParseException ex){
            ex.printStackTrace();
        }
        return false;
    }
}
