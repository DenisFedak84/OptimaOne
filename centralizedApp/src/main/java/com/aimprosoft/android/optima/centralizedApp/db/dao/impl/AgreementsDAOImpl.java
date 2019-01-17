package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Agreements;

import java.util.List;

public class AgreementsDAOImpl extends AbstractDAOImpl<Agreements> {
    public AgreementsDAOImpl() {
        super(Agreements.class);
    }

    public Agreements getAgreementsById(int id) {
        try {
            List<Agreements> agreementses = getBaseDAO().queryForEq(Agreements.AGREEMENT_ID, id);
            if (agreementses.size() != 0) {
                return agreementses.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new Agreements();
    }
}
