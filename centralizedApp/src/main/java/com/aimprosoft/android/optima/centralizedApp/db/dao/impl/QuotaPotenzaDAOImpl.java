package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.QuotaPotenza;

import java.sql.SQLException;
import java.util.List;

public class QuotaPotenzaDAOImpl extends AbstractDAOImpl<QuotaPotenza> {
    public QuotaPotenzaDAOImpl() {
        super(QuotaPotenza.class);
    }

    public QuotaPotenza getQuotaPotenzaByClassPotenza(double PS) {
        try {
            List<QuotaPotenza> quotaPotenzas = getBaseDAO().queryBuilder().where().gt(QuotaPotenza.CLASS_POTENZA_SUP, PS).and().le(QuotaPotenza.CLASS_POTENZA_INF, PS).query();
            if (quotaPotenzas.size() != 0) {
                return quotaPotenzas.get(0);
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get row", e);
        }
        return new QuotaPotenza();
    }

}
