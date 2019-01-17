package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.GasYearOfReference;

import java.util.List;

public class GasYearOfReferenceDAOImpl extends AbstractDAOImpl<GasYearOfReference> {
    public GasYearOfReferenceDAOImpl() {
        super(GasYearOfReference.class);
    }

    public String getGasYearOfReferenceValueById(int id) {
        try {
            List<GasYearOfReference> list = getBaseDAO().queryBuilder().where().eq(GasYearOfReference.ID_YEAR_OF_REFERENCE, id).query();
            if (list.size() != 0) {
                return String.valueOf(list.get(0).getValueYearOfReference());
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return "";
    }
}
