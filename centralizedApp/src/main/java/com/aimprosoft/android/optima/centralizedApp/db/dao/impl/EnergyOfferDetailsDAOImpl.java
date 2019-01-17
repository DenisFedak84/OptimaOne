package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.util.ArrayList;
import java.util.List;

public class EnergyOfferDetailsDAOImpl extends AbstractDAOImpl<EnergyOfferDetails> {

    public EnergyOfferDetailsDAOImpl() {
        super(EnergyOfferDetails.class);
    }

    public int deleteEnergyDetailslById(int id) {
        try {
            DeleteBuilder<EnergyOfferDetails, Integer> offerDetailsIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            offerDetailsIntegerDeleteBuilder.where().eq(EnergyOfferDetails.ENERGY_DETAIL_OFFER_ID, id);
            return offerDetailsIntegerDeleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return 0;
    }

    public int deleteEnergyDetailslByEnergyOfferId(int id) {
        try {
            DeleteBuilder<EnergyOfferDetails, Integer> offerDetailsIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            offerDetailsIntegerDeleteBuilder.where().eq(EnergyOfferDetails.ENERGY_OFFER_ID, id);
            return offerDetailsIntegerDeleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return 0;
    }


    public List<EnergyOfferDetails> getEnergyOfferDetailsByEnergyOfferId(int id) {
        try {
            List<EnergyOfferDetails> list = getBaseDAO().queryBuilder().where().eq(EnergyOfferDetails.ENERGY_OFFER_ID, id).query();
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
            List<EnergyOfferDetails> list = getBaseDAO().queryBuilder().where().eq(EnergyOfferDetails.ENERGY_OFFER_ID, mainOfferId).and().eq(EnergyOfferDetails.IS_EXISTING_CLIENT_OFFER, true).query();
            if (list.size() != 0) result = true;
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return result;
    }

    public EnergyOfferDetails getEnergyOfferDetailsById(int id) {
        try {
            List<EnergyOfferDetails> list = getBaseDAO().queryBuilder().where().eq(EnergyOfferDetails.ENERGY_DETAIL_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new EnergyOfferDetails();
    }

    public List<EnergyOfferDetails> getNewClientDetailsByOfferId(int id) {
        try {
            List<EnergyOfferDetails> list = getBaseDAO().queryBuilder().where().eq(EnergyOfferDetails.ENERGY_OFFER_ID, id)
                    .and().eq(EnergyOfferDetails.IS_EXISTING_CLIENT_OFFER, false).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }
}
