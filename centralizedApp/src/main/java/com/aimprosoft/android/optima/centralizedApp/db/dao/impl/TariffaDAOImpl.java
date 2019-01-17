package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Tariffa;

import java.util.List;

public class TariffaDAOImpl extends AbstractDAOImpl<Tariffa> {
    public TariffaDAOImpl() {
        super(Tariffa.class);
    }

    public Tariffa getTariffaById(int tariffaId) {
        try {
            List<Tariffa> list = getBaseDAO().queryBuilder().where().eq(Tariffa.TARIFFA_ID, tariffaId).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row");
        }
        return new Tariffa();
    }

    public Tariffa getTariffaByDesc(String tariffaDesc) {
        try {
            List<Tariffa> list = getBaseDAO().queryBuilder().where().eq(Tariffa.TARIFFA_DESC, tariffaDesc).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row");
        }
        return new Tariffa();
    }
}
