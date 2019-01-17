package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.VoceResult;

import java.util.List;

public class VoceResultDAOImpl extends AbstractDAOImpl<VoceResult> {

    public VoceResultDAOImpl() {
        super(VoceResult.class);
    }

    public List<VoceResult> getVoceResultListByResultOfferId(int id) {
        try {
            List<VoceResult> voceResultList = getBaseDAO().queryBuilder().where()
                    .eq(VoceResult.RESULT_OFFER_ID, id).query();
            if (!voceResultList.isEmpty()) {
                return voceResultList;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return null;
    }
}
