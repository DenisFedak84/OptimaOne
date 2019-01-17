package com.aimprosoft.android.optima.centralizedApp.util.energyUtils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseEnergyUtils {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public boolean  isMonthBelongToFirstGroup(String date) {
        boolean result = false;
        try {
            calendar.setTime(simpleDateFormat.parse(date));
            int monthNumber = calendar.get(Calendar.MONTH) + 1;
            result = (monthNumber > 4 && monthNumber < 9);
        } catch (ParseException e) {
            Log.e(e.getMessage(), "can't parse date", e);
        }
        return result;
    }

    public String convertDatetoString(Date dateStr) {
        return new SimpleDateFormat("dd/MM/yyyy").format(dateStr);
    }

    public int getMonthNumber(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

}
