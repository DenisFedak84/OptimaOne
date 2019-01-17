package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Town;

import java.util.List;

public class TownDAOImpl extends AbstractDAOImpl<Town> {
    public TownDAOImpl() {
        super(Town.class);
    }

    public Town getTownById(int id) {
        try {
            List<Town> towns = getBaseDAO().queryBuilder().where().eq(Town.TOWN_ID, id).query();
            if (towns.size() > 0) {
                return towns.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find exiting town", e);
        }
        return null;
    }

    public Town getTownByDesc(String desc) {
        try {
            Town town = getBaseDAO().queryBuilder().where()
                    .eq(Town.TOWN_DESC, desc.toUpperCase()).queryForFirst();
            if (town!=null) {
                return town;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find exiting town", e);
        }
        return null;
    }

    public List<Town> getTownsList() {
        try {
            List<Town> towns = getBaseDAO().queryBuilder().where().not().eq(Town.TOWN_ID, 0).query();
            if (towns.size() > 0) {
                return towns;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find exiting town", e);
        }
        return null;
    }
}
