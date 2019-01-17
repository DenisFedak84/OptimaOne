package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.GroupOfPotenza;

import java.util.List;

public class GroupOfPotenzaDAOImpl extends AbstractDAOImpl<GroupOfPotenza> {
    public GroupOfPotenzaDAOImpl() {
        super(GroupOfPotenza.class);
    }

    public GroupOfPotenza getGroupOfPotenzaByValue(double potenza) {
        try {
            List<GroupOfPotenza> groupOfPotenzas = getBaseDAO().queryBuilder().where().gt(GroupOfPotenza.POTENZA_MAX, potenza).and().le(GroupOfPotenza.POTENZA_MIN, potenza).query();
            if (groupOfPotenzas.size() != 0) {
                return groupOfPotenzas.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new GroupOfPotenza();
    }
}
