package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.HouseHolder;

import java.util.List;

public class HouseHolderDAOImpl extends AbstractDAOImpl<HouseHolder> {
    public HouseHolderDAOImpl() {
        super(HouseHolder.class);
    }

    public String getHouseHolderDescById(int id) {
        try {
            List<HouseHolder> list = getBaseDAO().queryBuilder().where().eq(HouseHolder.HOUSE_HOLDER_ID, id).query();
            if (list.size() != 0) {
                HouseHolder houseHolder =  list.get(0);
                return houseHolder.getHouseHolderDesc();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return null;
    }

}
