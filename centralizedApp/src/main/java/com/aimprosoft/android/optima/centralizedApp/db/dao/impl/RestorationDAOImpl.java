package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Restoration;

import java.util.List;

public class RestorationDAOImpl extends AbstractDAOImpl<Restoration> {
    public RestorationDAOImpl() {
        super(Restoration.class);
    }

    public String getRestorationDescById(int id) {
        try {
            List<Restoration> list = getBaseDAO().queryBuilder().where().eq(Restoration.ID_RESTORATION, id).query();
            if (list.size() != 0) {
                return list.get(0).getDescRestoration();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return "";
    }
}
