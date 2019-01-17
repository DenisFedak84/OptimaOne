package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;

import java.util.List;

public class GasOfferDAOImpl extends AbstractDAOImpl<GasOffer> {
    public GasOfferDAOImpl() {
        super(GasOffer.class);
    }

    public Integer getGasOfferId(GasOffer gasOffer) {
        try {
            return getBaseDAO().extractId(gasOffer);
        } catch (Throwable e) {
            Log.e(TAG, "can't extract id", e);
        }
        return null;
    }

    public GasOffer getGasOfferById(int id) {
        try {
            List<GasOffer> list = getBaseDAO().queryBuilder().where().eq(GasOffer.GAS_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return null;
    }
}
