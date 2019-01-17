package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Fonte;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Tariffa;

import java.util.List;

public class FonteDAOImpl extends AbstractDAOImpl<Fonte> {
    public FonteDAOImpl() {
        super(Fonte.class);
    }

    public Fonte getFonteById(int fonteId) {
        try {
            List<Fonte> list = getBaseDAO().queryBuilder().where().eq(Fonte.FONTE_ID, fonteId).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row");
        }
        return new Fonte();
    }

    public Fonte getFonteByDesc(String fonteDesc) {
        try {
            List<Fonte> list = getBaseDAO().queryBuilder().where().eq(Fonte.FONTE_DESC, fonteDesc).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row");
        }
        return new Fonte();
    }
}
