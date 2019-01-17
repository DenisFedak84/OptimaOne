package com.aimprosoft.android.optima.centralizedApp.app.receiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.aimprosoft.android.optima.centralizedApp.util.AlarmUtils;
import com.aimprosoft.android.optima.centralizedApp.util.LogoutUtils;
import com.aimprosoft.android.optima.centralizedApp.util.LocalSharedPreferencesManager;

public class BootCompleteReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       LogoutUtils.checkUserSessionStatus();
    }
}
