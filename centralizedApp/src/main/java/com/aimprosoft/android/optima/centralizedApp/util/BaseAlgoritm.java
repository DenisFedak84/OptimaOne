package com.aimprosoft.android.optima.centralizedApp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseAlgoritm {
    protected static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public int getMonthNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    //    public int getMonthNumber(String date) {
//        try {
//            Date currentDate = simpleDateFormat.parse(date);
//            return currentDate.getMonth() + 1;
//        } catch (ParseException e) {
//            Log.e(e.getMessage(), "can't parse date", e);
//        }
//        return -1;
//    }
    public String convertDatetoString(Date dateStr) {
        return new SimpleDateFormat("dd/MM/yyyy").format(dateStr);
    }

}

