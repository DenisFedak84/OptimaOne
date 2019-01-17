package com.aimprosoft.android.optima.centralizedApp.app.receiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.aimprosoft.android.optima.centralizedApp.util.AlarmUtils;
import com.aimprosoft.android.optima.centralizedApp.util.LocalSharedPreferencesManager;

public class LogoutTimeReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        boolean isUserLoggined = LocalSharedPreferencesManager.getInstance().getSharedPreferencesBooleanValue(context, LocalSharedPreferencesManager.IS_USER_LOGGINED);
//        if(isUserLoggined){
        LocalSharedPreferencesManager.getInstance().storeSharedPreferencesBooleanValue(context,
                LocalSharedPreferencesManager.IS_USER_LOGGINED,
                false);
        AlarmUtils.cancelAlarm(context, LogoutTimeReceiver.class);
//            LocalSharedPreferencesManager.getInstance().storeSharedPreferencesLongValue(context, LocalSharedPreferencesManager.LOGIN_TIMESTAMP, 0);
//
//            MyApplication.setIsAlarmRegistered(false);
//            Intent targetintent = new Intent(context, LoginActivity.class);
//            targetintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(targetintent);
//        }
    }
}
