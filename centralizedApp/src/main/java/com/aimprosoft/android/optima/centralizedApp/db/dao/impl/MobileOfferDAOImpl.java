package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;


import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileOffer;

import java.util.List;

public class MobileOfferDAOImpl extends AbstractDAOImpl<MobileOffer> {
    public MobileOfferDAOImpl() {
        super(MobileOffer.class);
    }

    public MobileOffer getMobileOfferById(int id) {
        try {
            List<MobileOffer> list = getBaseDAO().queryBuilder().where().eq(MobileOffer.MOBILE_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return null;
    }

    public Integer getMobileOfferId(MobileOffer mobileOffer) {
        try {
            return getBaseDAO().extractId(mobileOffer);
        } catch (Throwable e) {
            Log.e(TAG, "can't extract id", e);
        }
        return null;
    }
}
