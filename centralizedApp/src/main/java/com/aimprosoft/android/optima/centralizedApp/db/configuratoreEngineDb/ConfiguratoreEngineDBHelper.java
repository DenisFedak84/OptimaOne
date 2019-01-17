package com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.util.SDCardHelper;

import java.io.File;

public class ConfiguratoreEngineDBHelper {

    private static SQLiteDatabase dbLite;

    private static void checkDbConnection() {
        if (dbLite == null || !dbLite.isOpen()) {
            dbLite = SQLiteDatabase.openDatabase(SDCardHelper.DATABASE_LOCATION_DIR + File.separator + "cpi2.db", null, 1);
        }
    }

    protected static Cursor executeCursor(String query) {
        Cursor cursor = null;
        try {
            checkDbConnection();
            cursor = dbLite.rawQuery(query, null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AbstractJarUtil", "couldn't open database", e);
        }
        return cursor;
    }

    protected static void closeCursor(Cursor cursor){
        if(cursor!=null){
            cursor.close();
        }
    }

}
