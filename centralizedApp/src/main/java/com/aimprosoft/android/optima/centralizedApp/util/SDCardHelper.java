package com.aimprosoft.android.optima.centralizedApp.util;

import android.os.Environment;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SDCardHelper {

    private static SDCardHelper instance;

    public static String FOLDER_NAME = "ConfiguratoreUnicoData";
    public static final String SFORAMENTO_DB_NAME = "dblite.db";
    public static final String CONSUMPTION_DB_NAME = "dbliteStime.db";
    public static final String POTENZA_STIMATA_DB_NAME = "dblitePotenza.db";
    public static final String CPI2_DB_NAME = "cpi2.db";
    public static final String DATABASE_LOCATION_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + FOLDER_NAME;

    private void createDataFolderIfItNotExists() {
        File appDataFolder = new File(DATABASE_LOCATION_DIR);
        appDataFolder.mkdir();
    }

    public void onUpgradeJarDb(String dbName) {
        int jarDbVersion = LocalSharedPreferencesManager.getInstance().getJarDbVersionByName(dbName);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + FOLDER_NAME + File.separator + dbName);
        if (!file.exists() || jarDbVersion > LocalSharedPreferencesManager.getInstance().getSharedPreferencesIntValue(MyApplication.getContext(), dbName)) {
            writeFileFromAssets(file);
            LocalSharedPreferencesManager.getInstance().storeSharedPreferencesIntValue(MyApplication.getContext(), dbName, jarDbVersion);
        }
    }

    private void writeFileFromAssets(File file) {
        createDataFolderIfItNotExists();
        OutputStream out = null;
        InputStream inputStream = null;
        try {
            inputStream = MyApplication.getContext().getAssets().open(file.getName());
            out = new FileOutputStream(file);
            copyFile(inputStream, out);
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Failed to copy asset file", e);
        } finally {
            try {
                out.flush();
                out.close();
                inputStream.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Failed to copy asset file", e);
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static SDCardHelper getInstance() {
        if (instance == null)
            synchronized (SDCardHelper.class) {
                if (instance == null)
                    instance = new SDCardHelper();
            }
        return instance;
    }
}
