package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferDevice;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfferDeviceDAOImpl extends AbstractDAOImpl<OfferDevice> {
    public OfferDeviceDAOImpl() {
        super(OfferDevice.class);
    }

    public void deleteOfferDeviceById(int id) {
        try {
            DeleteBuilder<OfferDevice, Integer> offerDeviceIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            offerDeviceIntegerDeleteBuilder.where().eq(OfferDevice.DEVICE_ID, id);
            offerDeviceIntegerDeleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(getClass().getSimpleName(), "can't delete row", e);
        }
    }

    public List<OfferDevice> getDevicesByOfferId(int offerId) {
        try {
            List<OfferDevice> list = getBaseDAO().queryBuilder().where().eq(OfferDevice.OFFER_ID, offerId).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }

    public List<OfferDevice> getSortedDevicesByOfferId(int offerId, boolean isASC) {
        try {
            List<OfferDevice> list = getBaseDAO().queryBuilder().orderBy(OfferDevice.PRIORITY, isASC).where().eq(OfferDevice.OFFER_ID, offerId).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }

    public void deleteOfferDeviceByOfferId(int offerId) {
        try {
            DeleteBuilder<OfferDevice, Integer> offerDeviceIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            offerDeviceIntegerDeleteBuilder.where().eq(OfferDevice.OFFER_ID, offerId);
            offerDeviceIntegerDeleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(TAG, "can't delete row", e);
        }
    }

}
