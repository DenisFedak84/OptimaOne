package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOffers;

import java.util.List;

public class WlrOffersDAOImpl extends AbstractDAOImpl<WlrOffers> {
    public WlrOffersDAOImpl() {
        super(WlrOffers.class);
    }

    public WlrOffers getWlrOfferById(int id) {
        try {
            List<WlrOffers> list = getBaseDAO().queryBuilder().where().eq(WlrOffers.WLR_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new WlrOffers();
    }
}
