package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.CoeffTransport;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class CoeffTransportDAOImpl extends AbstractDAOImpl<CoeffTransport> {
    public CoeffTransportDAOImpl() {
        super(CoeffTransport.class);
    }

    public BigDecimal getCoeffTransportoByPotenza(int tariffaId) {
        try {
            List<CoeffTransport> coeffTransports = getBaseDAO().queryBuilder().where().eq(CoeffTransport.ID, tariffaId).query();
            if (coeffTransports.size() != 0) {
                return coeffTransports.get(0).getCoefficienteTariffa();
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get row", e);
        }
        return new BigDecimal(0);
    }
}
