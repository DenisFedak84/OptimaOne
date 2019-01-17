package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;

import java.util.List;

public class EnergyOfferDAOImpl extends AbstractDAOImpl<EnergyOffer> {
    public EnergyOfferDAOImpl() {
        super(EnergyOffer.class);
    }

    public Integer getEnergyOfferId(EnergyOffer energyOffer) {
        try {
            return getBaseDAO().extractId(energyOffer);
        } catch (Throwable e) {
            Log.e(TAG, "can't extract id", e);
        }
        return null;
    }

    public EnergyOffer getEnergyOfferById(int id) {
        try {
            List<EnergyOffer> list = getBaseDAO().queryBuilder().where().eq(EnergyOffer.ENERGY_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return null;
    }
}
