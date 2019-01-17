package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CreateFilesOnSDCard extends AbstractService<Activity, Void> {

    public CreateFilesOnSDCard(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private void createDirectory() {
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "OptiLifeDoc");
        if(!directory.exists()) {
            directory.mkdir();
        }
    }

    @Override
    protected Void doStuff(Activity... params) {
        createDirectory();
        InputStream in = null;
        OutputStream out = null;
        try {
            Activity activity = getParam();
            AssetManager assetManager = activity.getAssets();
            String[] filelist = assetManager.list("files");
            for (String file : filelist) {
                File savedFile = new File(Environment. getExternalStorageDirectory().getAbsolutePath() + File.separator + "OptiLifeDoc" + File.separator + file);
                if (!savedFile.exists()) {
                    in = assetManager.open("files/" + file);
                    out = new FileOutputStream(Environment. getExternalStorageDirectory().getAbsolutePath() + File.separator + "OptiLifeDoc" + File.separator + file);
                    copyFile(in, out);
                }
            }
        } catch (IOException e) {
            Log.e("tag", "Failed to copy asset file", e);
        } finally {
            if(out!=null){
                try{
                    out.flush();
                    out.close();
                } catch (Exception e){
                }
            }
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        return null;
    }
}
