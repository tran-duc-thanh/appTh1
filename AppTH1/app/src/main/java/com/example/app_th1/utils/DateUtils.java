package com.example.app_th1.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    public static final String DD_MM_YYYY = "dd/MM/yyyy";

    public static boolean isValidate(String dateStr) {
        try {
            DateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
            sdf.setLenient(false);
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidate(String dateStr, String format) {
        try {
            DateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static int getYear () {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth () {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getDay () {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour () {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute () {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static int getSecond () {
        return Calendar.getInstance().get(Calendar.SECOND);
    }
}
