package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyResult;

import java.util.List;

public class EnergyResultDAOImpl extends AbstractDAOImpl<EnergyResult> {

    public EnergyResultDAOImpl() {
        super(EnergyResult.class);
    }

    public List<EnergyResult> getEnergyResultListByResultOfferId(int id) {
        try {
            List<EnergyResult> energyResultList = getBaseDAO().queryBuilder().where()
                    .eq(EnergyResult.RESULT_OFFER_ID, id).query();
            if (energyResultList != null) {
                return energyResultList;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return null;
    }
}
