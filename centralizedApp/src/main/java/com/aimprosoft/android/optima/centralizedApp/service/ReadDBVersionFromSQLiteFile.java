package com.aimprosoft.android.optima.centralizedApp.service;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadDBVersionFromSQLiteFile extends AbstractService<Void, Integer> {

    private static final String TAG = "DBVersion";
    BaseActivity activity;
    private int offset = 60;
    private int sizeNumberVersion = 4;

    public ReadDBVersionFromSQLiteFile(BaseActivity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
        this.activity = activity;
    }

    @Override
    protected Integer doStuff(Void... voids) {

            //Copy saved users data
            String inFileName = "configuratore_unico_base.db";
            InputStream is = null;
            try {
                is = activity.getAssets().open(inFileName);
                int version = readVersion(is);
//                Toast.makeText(this, "Data base version =", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "---db version = "+version);
                return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int readVersion(InputStream is) {
        int numberVersion = 0;
        ByteArrayInputStream bis = null;
        try {
            byte[] buff = new byte[sizeNumberVersion];
            bis = new ByteArrayInputStream(getBytesFromInputStream(is));
            bis.skip(offset);
            bis.read(buff, 0, sizeNumberVersion);
            numberVersion = intFromByteArray(buff);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert is != null;
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert bis != null;
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return numberVersion;
    }

    private byte[] getBytesFromInputStream(InputStream is) {
        ByteArrayOutputStream os = null;
        byte [] bytes = new byte[0];
        try {
            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[0xFFFF];
            for (int len; (len = is.read(buffer)) != -1;)
                os.write(buffer, 0, len);
            os.flush();
            bytes = os.toByteArray();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            assert os != null;
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    private int intFromByteArray(byte[] bytes) {
        int value = 0;
        for (int i = 0; i <= sizeNumberVersion - 1; i++) {
            value = (value << 8) + (bytes[i] & 0xFF);
        }
        return value;
    }

    public BaseActivity getActivity() {
        return activity;
    }
}