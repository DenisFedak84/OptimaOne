package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.FlatType;

import java.util.List;

public class FlatTypeDAOImpl extends AbstractDAOImpl<FlatType> {
    public FlatTypeDAOImpl() {
        super(FlatType.class);
    }

    public int getFlatTypeValueById(int id) {
        try {
            List<FlatType> list = getBaseDAO().queryBuilder().where().eq(FlatType.ID_FLAT, id).query();
            if (list.size() != 0) {
                return list.get(0).getValue();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return 0;
    }
}
