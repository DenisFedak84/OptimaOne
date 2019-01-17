package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ADSLResult;

import java.util.List;

public class ADSLResultDAOImpl extends AbstractDAOImpl<ADSLResult> {

    public ADSLResultDAOImpl() {
        super(ADSLResult.class);
    }

    public List<ADSLResult> getADSLResultListByResultOfferId(int id) {
        try {
            List<ADSLResult> adslList = getBaseDAO().queryBuilder().where()
                    .eq(ADSLResult.RESULT_OFFER_ID, id).query();
            if (!adslList.isEmpty()) {
                return adslList;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return null;
    }
}
