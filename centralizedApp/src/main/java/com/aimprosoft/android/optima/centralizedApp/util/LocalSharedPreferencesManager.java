package com.aimprosoft.android.optima.centralizedApp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocalSharedPreferencesManager {

    private static LocalSharedPreferencesManager instance;
    private static final int SFORAMANTO_JAR_VERSION = 57;
    private static final int CONSUMPTION_JAR_VERSION = 7;
    private static final int POTENZA_STIMATA_JAR_VERSION = 2;
    private static final int CPI2_JAR_VERSION = 59;

    public static final String ID_SOGGETTO = "ID_SOGGETTO";
    public static final String CURRENT_DB_VERSION = "current_db_version";
    public static final String LOGIN_TIMESTAMP = "login_timestamp";
    public static final String IS_USER_LOGGINED = "is_user_loggined";
    public static final String IS_APP_CHECKED_FOR_UPDATE = "is_app_checked_for_update";
    public static final String IS_ESPORTA_DIALOG_REQUIRED = "is_esporta_dialog_required";

    private String TAG = getClass().getSimpleName();

    public LocalSharedPreferencesManager() {
    }

    public static LocalSharedPreferencesManager getInstance() {
        if (instance == null)
            synchronized (LocalSharedPreferencesManager.class) {
                if (instance == null)
                    instance = new LocalSharedPreferencesManager();
            }
        return instance;
    }

    public int getSharedPreferencesIntValue(Context context, String key) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);
    }

    public void storeSharedPreferencesIntValue(Context context, String key, int value) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public long getSharedPreferencesLongValue(Context context, String key) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getLong(key, 0);
    }

    public void storeSharedPreferencesLongValue(Context context, String key, long value) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public boolean getSharedPreferencesBooleanValue(Context context, String key) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);
    }

    public void storeSharedPreferencesBooleanValue(Context context, String key, boolean value) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getSharedPreferencesStringValue(Context context, String key) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }

    public void storeSharedPreferencesStringValue(Context context, String key, String value) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public int getJarDbVersionByName(String name) {
        int version = 0;
        switch (name) {
            case SDCardHelper.SFORAMENTO_DB_NAME:
                version = SFORAMANTO_JAR_VERSION;
                break;
            case SDCardHelper.CONSUMPTION_DB_NAME:
                version = CONSUMPTION_JAR_VERSION;
                break;
            case SDCardHelper.POTENZA_STIMATA_DB_NAME:
                version = POTENZA_STIMATA_JAR_VERSION;
                break;
            case SDCardHelper.CPI2_DB_NAME:
                version = CPI2_JAR_VERSION;
                break;
        }
        return version;
    }
}
