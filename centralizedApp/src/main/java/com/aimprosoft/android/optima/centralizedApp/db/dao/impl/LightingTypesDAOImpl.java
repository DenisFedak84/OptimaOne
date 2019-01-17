package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.LightingTypes;

import java.util.List;

public class LightingTypesDAOImpl extends AbstractDAOImpl<LightingTypes> {
    public LightingTypesDAOImpl() {
        super(LightingTypes.class);
    }

    public String getLightingTypesDescById(int id) {
        try {
            List<LightingTypes> list = getBaseDAO().queryBuilder().where().eq(LightingTypes.ID_TYPE, id).query();
            if (list.size() != 0) {
                return list.get(0).getDescType();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return "";
    }
}
