package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.FiscalClass;

import java.util.List;

public class FiscalClassDAOImpl extends AbstractDAOImpl<FiscalClass> {

    public FiscalClassDAOImpl() {
        super(FiscalClass.class);
    }

    public FiscalClass getFiscalClassById(int id) {
        try {
            List<FiscalClass> list = getBaseDAO().queryBuilder().where().eq(FiscalClass.ID_FISCAL_CLASS, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new FiscalClass();
    }

    public int getFiscalClassIdByDesc(String desc) {
        try {
            FiscalClass fiscalClass = getBaseDAO().queryBuilder().where().like(FiscalClass.DESC_FISCAL_CLASS, desc).queryForFirst();
            if (fiscalClass != null) {
                return fiscalClass.getIdFiscalClass();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return -1;
    }

    public String getFiscalClassCodeById(int id) {
        try {
            FiscalClass fiscalClass = getBaseDAO().queryBuilder().where().eq(FiscalClass.ID_FISCAL_CLASS, id).queryForFirst();
            if (fiscalClass != null) {
                return fiscalClass.getCode();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return "";
    }
}
