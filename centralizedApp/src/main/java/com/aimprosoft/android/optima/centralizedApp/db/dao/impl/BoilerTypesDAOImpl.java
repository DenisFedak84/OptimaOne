package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.BoilerTypes;

import java.util.List;

public class BoilerTypesDAOImpl extends AbstractDAOImpl<BoilerTypes> {
    public BoilerTypesDAOImpl() {
        super(BoilerTypes.class);
    }

    public String getBoilerTypesDescById(int id) {
        try {
            List<BoilerTypes> list = getBaseDAO().queryBuilder().where().eq(BoilerTypes.ID_TYPE, id).query();
            if (list.size() != 0) {
                return list.get(0).getDescType();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return "";
    }
}
