package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.util.ArrayList;
import java.util.List;

public class GasDetailOffersDAOImpl extends AbstractDAOImpl<GasDetailOffers> {

    public GasDetailOffersDAOImpl() {
        super(GasDetailOffers.class);
    }

    public int deleteGasDetailslById(int id) {
        try {
            DeleteBuilder<GasDetailOffers, Integer> offerDetailsIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            offerDetailsIntegerDeleteBuilder.where().eq(GasDetailOffers.GAS_DETAILS_OFFER_ID, id);
            return offerDetailsIntegerDeleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return 0;
    }

    public int deleteGasDetailslByGasOfferId(int id) {
        try {
            DeleteBuilder<GasDetailOffers, Integer> offerDetailsIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            offerDetailsIntegerDeleteBuilder.where().eq(GasDetailOffers.GAS_OFFER_ID, id);
            return offerDetailsIntegerDeleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return 0;
    }

    public List<GasDetailOffers> getGasOfferDetailsByGasOfferId(int id) {
        try {
            List<GasDetailOffers> list = getBaseDAO().queryBuilder().where().eq(GasDetailOffers.GAS_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }


    public boolean isExistingOffersUsed(int mainOfferId) {
        boolean result = false;
        try {
            List<GasDetailOffers> list = getBaseDAO().queryBuilder().where().eq(GasDetailOffers.GAS_OFFER_ID, mainOfferId).and().eq(GasDetailOffers.IS_EXISTING_CLIENT_OFFER, true).query();
            if (list.size() != 0) result = true;
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return result;
    }

    public GasDetailOffers getGasOfferDetailsById(int id) {
        try {
            List<GasDetailOffers> list = getBaseDAO().queryBuilder().where().eq(GasDetailOffers.GAS_DETAILS_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new GasDetailOffers();
    }

    public List<GasDetailOffers> getNewClientDetailsByOfferId(int id) {
        try {
            List<GasDetailOffers> list = getBaseDAO().queryBuilder().where().eq(GasDetailOffers.GAS_OFFER_ID, id)
                    .and().eq(GasDetailOffers.IS_EXISTING_CLIENT_OFFER, false).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }
}
