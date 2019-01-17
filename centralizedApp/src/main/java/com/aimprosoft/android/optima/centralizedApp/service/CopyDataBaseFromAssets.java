package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.DatabaseHelper;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BaseEntityDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.BaseEntity;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferDevice;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TLCOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TlcDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOffers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CopyDataBaseFromAssets extends AbstractService<Integer, Boolean> {
    private boolean result = true;
    private static final String TAG = "DBVersion";
    Class[] classesEntity = new Class[]{
            EnergyOffer.class,
            EnergyOfferDateInterval.class,
            EnergyOfferDetails.class,
            GasOfferDateInterval.class,
            GasDetailOffers.class,
            GasOffer.class,
            MobileOffer.class,
            MobileDetailsOffer.class,
            Offer.class,
            OfferDevice.class,
            TlcDetailsOffer.class,
            TLCOffer.class,
            WlrOfferDetails.class,
            WlrOffers.class,
            InternetOffer.class,
            InternetDetailOffers.class};
    Activity activity;

    public CopyDataBaseFromAssets(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
        this.activity = activity;
    }

    @Override
    protected Boolean doStuff(Integer... ints) {
        try {
            if (ints[0] > DatabaseHelper.getUpdateDatabaseVersionLimit()) {
                //put user offers to cache
                putOffersDataToTempCashe();///
            }
            //Copy saved users data
            String inFileName = "configuratore_unico_base.db";
            InputStream is = null;
            try {
                is = activity.getAssets().open(inFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String outFileName = "/data/data/com.aimprosoft.android.optima.centralizedApp/databases/configuratore_unico_base.db";
            OutputStream output = new FileOutputStream(outFileName);
            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024 * 1024];
            int length;
            assert is != null;
            while ((length = is.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            // Close the streams
            output.flush();
            output.close();
            is.close();
            if (ints[0] > DatabaseHelper.getUpdateDatabaseVersionLimit()) {
                //get user offers from cache
                getOffersDataFromTempCashe();///
            }
        } catch (Exception e) {
            Log.w(TAG, "error while replacing db", e);
            result = false;
        }
        return result;
    }

    private void putOffersDataToTempCashe() throws SQLException {
        Map<Class, List<BaseEntity>> tempMapOffers = new HashMap<>();
        BaseEntityDAOImpl baseEntityDAOImpl;
        for (Class clasEntity : classesEntity) {
            baseEntityDAOImpl = new BaseEntityDAOImpl(clasEntity);
            List<BaseEntity> listEntityPutToCache = baseEntityDAOImpl.getAllRows();
            if (listEntityPutToCache.size() > 0) {
                tempMapOffers.put(clasEntity, listEntityPutToCache);
            }
        }
        if (!tempMapOffers.isEmpty()) {
            MyApplication.set("TempDataOffers", tempMapOffers);
        }
    }

    private void getOffersDataFromTempCashe() {
        Map<Class, List<BaseEntity>> tempMapOffers = MyApplication.get("TempDataOffers", Map.class);
        if (tempMapOffers != null) {
            BaseEntityDAOImpl baseEntityDAOImpl;
            Class[] listClas = (tempMapOffers.keySet()).toArray(new Class[tempMapOffers.size()]);
            for (Class classEntity : listClas) {
                baseEntityDAOImpl = new BaseEntityDAOImpl(classEntity);
                List<BaseEntity> listEntityFromCache = tempMapOffers.get(classEntity);
                for (BaseEntity entity : listEntityFromCache) {
                    baseEntityDAOImpl.insert(entity);
                }
            }
        }
    }
}
