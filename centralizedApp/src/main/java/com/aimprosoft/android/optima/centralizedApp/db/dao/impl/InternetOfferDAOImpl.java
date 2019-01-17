package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;


import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetOffer;

import java.util.List;

public class InternetOfferDAOImpl extends AbstractDAOImpl<InternetOffer> {
    public InternetOfferDAOImpl() {
        super(InternetOffer.class);
    }

    public InternetOffer getInternetOfferById(int id) {
        try {
            List<InternetOffer> list = getBaseDAO().queryBuilder().where().eq(InternetOffer.INTERNET_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return null;
    }

    public Integer getInternetOfferId(InternetOffer internetOffer) {
        try {
            return getBaseDAO().extractId(internetOffer);
        } catch (Throwable e) {
            Log.e(TAG, "can't extract id", e);
        }
        return null;
    }
}
