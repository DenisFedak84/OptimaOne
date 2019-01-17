package com.aimprosoft.android.optima.centralizedApp.util;

import android.content.Context;

import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.app.receiver.LogoutTimeReceiver;

import java.util.Calendar;
import java.util.Date;

public class LogoutUtils {

    public static Date getMidnightTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static boolean isLogoutNeeded(){
        long loginTimestamp = LocalSharedPreferencesManager.getInstance().getSharedPreferencesLongValue(MyApplication.getContext(), LocalSharedPreferencesManager.LOGIN_TIMESTAMP);
        Date loginDate = new Date(loginTimestamp);
        Date logoutDate = LogoutUtils.getMidnightTime(loginDate);
        Date now = new Date();
        return now.before(loginDate) || now.after(logoutDate);
    }

    public static void checkUserSessionStatus() {
        Context context = MyApplication.getContext();
        boolean isUserLoggined = LocalSharedPreferencesManager.getInstance().getSharedPreferencesBooleanValue(context,
                LocalSharedPreferencesManager.IS_USER_LOGGINED);
        if (isUserLoggined && !LogoutUtils.isLogoutNeeded()) {
            AlarmUtils.cancelAlarm(context, LogoutTimeReceiver.class);
            AlarmUtils.setupAlarm(context, LogoutTimeReceiver.class);
        } else {
            LocalSharedPreferencesManager.getInstance().storeSharedPreferencesBooleanValue(context,
                    LocalSharedPreferencesManager.IS_USER_LOGGINED, false);
        }
    }
}
