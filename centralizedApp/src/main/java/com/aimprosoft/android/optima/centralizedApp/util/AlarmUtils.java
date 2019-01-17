package com.aimprosoft.android.optima.centralizedApp.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class AlarmUtils {

    public static void setupAlarm(Context context, Class<? extends BroadcastReceiver> receiver) {
        AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, receiver);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        long logoutTime = LogoutUtils.getMidnightTime(Calendar.getInstance().getTime()).getTime();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(logoutTime, pi);
            alarmManager.setAlarmClock(alarmClockInfo, pi);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, logoutTime, pi);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, logoutTime, pi);
        }
    }

    public static void cancelAlarm(Context context, Class<? extends BroadcastReceiver> receiver) {
        AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, receiver);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.cancel(pi);
        pi.cancel();
    }
}
