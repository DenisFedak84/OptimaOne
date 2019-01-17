package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetDetailOffers;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.util.ArrayList;
import java.util.List;

public class InternetDetailOffersDAOImpl extends AbstractDAOImpl<InternetDetailOffers> {
    public InternetDetailOffersDAOImpl() {
        super(InternetDetailOffers.class);
    }

    public int deleteInternetDetailslById(int id) {
        try {
            DeleteBuilder<InternetDetailOffers, Integer> offerDetailsIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            offerDetailsIntegerDeleteBuilder.where().eq(InternetDetailOffers.INTERNET_DETAIL_OFFER_ID, id);
            return offerDetailsIntegerDeleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return 0;
    }

    public int deleteInternetDetailslByInternetOfferId(int id) {
        try {
            DeleteBuilder<InternetDetailOffers, Integer> offerDetailsIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            offerDetailsIntegerDeleteBuilder.where().eq(InternetDetailOffers.INTERNET_OFFER_ID, id);
            return offerDetailsIntegerDeleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return 0;
    }

    public boolean isExistingOffersUsed(int mainOfferId) {
        boolean result = false;
        try {
            List<InternetDetailOffers> list = getBaseDAO().queryBuilder().where().eq(InternetDetailOffers.INTERNET_OFFER_ID, mainOfferId).and().eq(InternetDetailOffers.IS_EXISTING_CLIENT_OFFER, true).query();
            if (list.size() != 0) result = true;
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return result;
    }

    public List<InternetDetailOffers> getInternetDetailsOfferByInternetOfferId(int id) {
        try {
            List<InternetDetailOffers> list = getBaseDAO().queryBuilder().where().eq(InternetDetailOffers.INTERNET_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }

    public InternetDetailOffers getInternetDetailsOfferById(int id) {
        try {
            List<InternetDetailOffers> list = getBaseDAO().queryBuilder().where().eq(InternetDetailOffers.INTERNET_DETAIL_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new InternetDetailOffers();
    }

    public List<InternetDetailOffers> getNewClientDetailsByOfferId(int id) {
        try {
            List<InternetDetailOffers> list = getBaseDAO().queryBuilder().where().eq(InternetDetailOffers.INTERNET_OFFER_ID, id)
                    .and().eq(InternetDetailOffers.IS_EXISTING_CLIENT_OFFER, false).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }
}
