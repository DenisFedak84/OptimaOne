package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TlcDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOfferDetails;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TlcDetailsOfferDAOImpl extends AbstractDAOImpl<TlcDetailsOffer> {
    public TlcDetailsOfferDAOImpl() {
        super(TlcDetailsOffer.class);
    }

    public void deleteTlcOfferDetailsById(int id) {
        try {
            DeleteBuilder<TlcDetailsOffer, Integer> tlcDetailsOfferIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            tlcDetailsOfferIntegerDeleteBuilder.where().eq(TlcDetailsOffer.TLC_OFFER_DETAILS_ID, id);
            tlcDetailsOfferIntegerDeleteBuilder.delete();
        } catch (SQLException e) {
        }
    }

    public boolean isExistingOffersUsed(int mainOfferId) {
        boolean result = false;
        try {
            List<TlcDetailsOffer> list = getBaseDAO().queryBuilder().where().eq(TlcDetailsOffer.TLC_OFFER_ID, mainOfferId).and().eq(TlcDetailsOffer.IS_EXISTING_CLIENT_OFFER, true).query();
            if (list.size() != 0) result = true;
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return result;
    }

    public void deleteTlcOfferDetailsWitTlcOfferId(int id) {
        try {
            DeleteBuilder<TlcDetailsOffer, Integer> tlcDetailsOfferIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            tlcDetailsOfferIntegerDeleteBuilder.where().eq(TlcDetailsOffer.TLC_OFFER_ID, id);
            tlcDetailsOfferIntegerDeleteBuilder.delete();
        } catch (SQLException e) {
        }
    }

    public TlcDetailsOffer getDetailsTlcOfferById(int id) {
        try {
            List<TlcDetailsOffer> list = getBaseDAO().queryBuilder().where().eq(TlcDetailsOffer.TLC_OFFER_DETAILS_ID, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new TlcDetailsOffer();
    }

    public List<TlcDetailsOffer> getExistingClientDetailsByTlcOfferId(int id) {
        try {
            List<TlcDetailsOffer> list = getBaseDAO().queryBuilder().where().eq(TlcDetailsOffer.TLC_OFFER_ID, id)
                    .and().eq(TlcDetailsOffer.IS_EXISTING_CLIENT_OFFER, true).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }

    public List<TlcDetailsOffer> getNewClientDetailsByTlcOfferId(int id) {
        try {
            List<TlcDetailsOffer> list = getBaseDAO().queryBuilder().where().eq(TlcDetailsOffer.TLC_OFFER_ID, id)
                    .and().eq(TlcDetailsOffer.IS_EXISTING_CLIENT_OFFER, false).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }


    public List<TlcDetailsOffer> getAllTlcDetailsByTlcOfferId(int id) {
        try {
            List<TlcDetailsOffer> list = getBaseDAO().queryBuilder().where().eq(TlcDetailsOffer.TLC_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }
}
