package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.GasResult;

import java.util.List;

public class GasResultDAOImpl extends AbstractDAOImpl<GasResult> {

    public GasResultDAOImpl() {
        super(GasResult.class);
    }

    public List<GasResult> getGasResultListByResultOfferId(int id) {
        try {
            List<GasResult> gasResultList = getBaseDAO().queryBuilder().where()
                    .eq(GasResult.RESULT_OFFER_ID, id).query();
            if (!gasResultList.isEmpty()) {
                return gasResultList;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return null;
    }
}
