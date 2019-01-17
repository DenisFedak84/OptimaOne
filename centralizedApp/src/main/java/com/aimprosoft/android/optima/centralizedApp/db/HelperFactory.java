package com.aimprosoft.android.optima.centralizedApp.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.support.ConnectionSource;

public class HelperFactory {

    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getHelper() {
        return databaseHelper;
    }

    public static void setHelper(Context context, Class aClass) {
        databaseHelper = (DatabaseHelper) OpenHelperManager.getHelper(context, aClass);
    }

    public static void releaseHelper() {
        OpenHelperManager.releaseHelper();
        databaseHelper = null;
    }

    public static ConnectionSource getConnectionSource() {
        return databaseHelper.getConnectionSource();
    }
}