package com.aimprosoft.android.optima.centralizedApp.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aimprosoft.android.optima.centralizedApp.util.LogoutUtils;

public class DeviceDateChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_DATE_CHANGED) || intent.getAction().equals(Intent.ACTION_TIME_CHANGED)) {
            LogoutUtils.checkUserSessionStatus();
        }
    }
}
