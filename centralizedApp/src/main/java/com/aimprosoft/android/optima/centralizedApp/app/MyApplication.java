package com.aimprosoft.android.optima.centralizedApp.app;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.db.DatabaseHelper;
import com.aimprosoft.android.optima.centralizedApp.db.HelperFactory;
import com.aimprosoft.android.optima.centralizedApp.util.Cache;
import com.aimprosoft.android.optima.centralizedApp.util.SDCardHelper;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MyApplication extends Application {
    private static Context context;
    private static Integer remoteDbVersion;
    private static AlarmManager alarmManager;
    private static boolean isAlarmRegistered = false;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getBaseContext();
        HelperFactory.setHelper(context, DatabaseHelper.class);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        upgradeJarDb();
        initFabric();
    }

    private void initFabric() {
        if (!getResources().getBoolean(R.bool.is_test_version)) {
            final Fabric fabric = new Fabric.Builder(this)
                    .kits(new Crashlytics())
                    .build();
            Fabric.with(fabric);
        }
    }

//    public static void setUpLogoutReceiver() {
//      if(!isAlarmRegistered){
//          Intent intent = new Intent(context, LogoutTimeReceiver.class);
//          PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
//          long logoutTime = LogoutUtils.getMidnightTime(Calendar.getInstance().getTime()).getTime();
//          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//              AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(logoutTime, pi);
//              alarmManager.setAlarmClock(alarmClockInfo, pi);
//          } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//              alarmManager.setExact(AlarmManager.RTC_WAKEUP, logoutTime, pi);
//          } else {
//              alarmManager.set(AlarmManager.RTC_WAKEUP, logoutTime, pi);
//          }
//          isAlarmRegistered = true;
//      }
//    }

    private void upgradeJarDb() {
//        SDCardHelper.getInstance().onUpgradeJarDb(SDCardHelper.SFORAMENTO_DB_NAME);
//        SDCardHelper.getInstance().onUpgradeJarDb(SDCardHelper.CONSUMPTION_DB_NAME);
//        SDCardHelper.getInstance().onUpgradeJarDb(SDCardHelper.POTENZA_STIMATA_DB_NAME);
        SDCardHelper.getInstance().onUpgradeJarDb(SDCardHelper.CPI2_DB_NAME);
    }

    public static boolean isAlarmRegistered() {
        return isAlarmRegistered;
    }

    public static void setIsAlarmRegistered(boolean isAlarmRegistered) {
        MyApplication.isAlarmRegistered = isAlarmRegistered;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static <T> T get(String key, Class<T> t) {
        try {
            return t.cast(new Cache(context).get(key));
        } catch (Throwable e) {
            Log.w("CacheManager", "cannot cast class", e);
            return null;
        }
    }

    public static void set(String key, Object val) {
        new Cache(context).save(key, val);
    }

    public static void remove(String key) {
        new Cache(context).delete(key);
    }

    public static void removeAll() {
        new Cache(context).delete(null);
    }

    public static Context getContext() {
        return context;
    }

    public static Integer getRemoteDbVersion() {
        return remoteDbVersion;
    }

    public static void setRemoteDbVersion(Integer remoteDbVersion) {
        MyApplication.remoteDbVersion = remoteDbVersion;
    }
}
