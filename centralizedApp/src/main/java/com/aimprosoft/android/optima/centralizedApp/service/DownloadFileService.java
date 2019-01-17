package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.service.net.AbstractUrlConnectionService;
import com.aimprosoft.android.optima.centralizedApp.util.URLs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class DownloadFileService extends AbstractUrlConnectionService<Integer, String> {

    public DownloadFileService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected String doStuff(Integer... ints) {
        File file = null;
        FileOutputStream fos;
        byte[] buffer;
        int bufferLength;
        try {
            int version = ints[0];

            String url = URLs.UPDATE_URL + Constants.APK_NAME_FOR_UPDATE + version + Constants.APK_FILE_EXTENSION;

            file = new File("sdcard/download/" + Constants.APK_NAME_FOR_UPDATE + version + Constants.APK_FILE_EXTENSION);
            if (file.exists())
                file.delete();

            fos = new FileOutputStream(file);

            InputStream inputStream = openURLForInput(new URL(url), CheckForUpdatesService.LOGIN, CheckForUpdatesService.PASS);

            buffer = new byte[1024];

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, bufferLength);
            }

            fos.close();
            inputStream.close();


        } catch (Throwable throwable) {
            Log.e(TAG, "error while executing DownloadFileService");
        }
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }


}
