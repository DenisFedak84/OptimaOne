package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.TownsCap;

import java.util.List;

public class TownsCapDAOImpl extends AbstractDAOImpl<TownsCap> {
    public TownsCapDAOImpl() {
        super(TownsCap.class);
    }

    public TownsCap getCapRelatedWithTownId(int id) {
        try {
            List<TownsCap> towns = getBaseDAO().queryBuilder().where().eq(TownsCap.TOWN_ID, id).query();
            if (towns.size() == 1) {
                return towns.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find exiting townsCap", e);
        }
        return new TownsCap();
    }
}
