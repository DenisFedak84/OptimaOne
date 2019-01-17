package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.TLCOffer;

import java.util.List;

public class TLCOfferDAOImpl extends AbstractDAOImpl<TLCOffer> {
    public TLCOfferDAOImpl() {
        super(TLCOffer.class);
    }

    public Integer getTLCOfferId(TLCOffer tlcOffer) {
        try {
            return getBaseDAO().extractId(tlcOffer);
        } catch (Throwable e) {
            Log.e(TAG, "can't extract id", e);
        }
        return null;
    }

    public TLCOffer getTLCOfferById(int id) {
        try {
            List<TLCOffer> list = getBaseDAO().queryBuilder().where().eq(TLCOffer.TLC_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new TLCOffer();
    }
}
