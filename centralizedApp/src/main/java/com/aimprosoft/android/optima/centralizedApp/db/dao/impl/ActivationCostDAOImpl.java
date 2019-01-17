package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ActivationCost;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;

import java.util.ArrayList;
import java.util.List;

public class ActivationCostDAOImpl extends AbstractDAOImpl<ActivationCost> {
    public ActivationCostDAOImpl() {
        super(ActivationCost.class);
    }

    public List<Object[]> getActivationCost(String lineOfId) {

        String query = "SELECT SUM(ActivationCost.activationCost) AS Expr1 FROM ActivationCost WHERE" +
                " (idService IN (" + lineOfId + "6));";


        try {
            GenericRawResults<Object[]> genericRawResults = getBaseDAO().queryRaw(query, new DataType[]{DataType.DOUBLE});
            return genericRawResults.getResults();
        } catch (Throwable e) {
            Log.e(TAG, "can't make query", e);
        }
        return new ArrayList<>();
    }

    public double getActivationCostByServiceId(int serviceId) {
        try {
            List<ActivationCost> activationCosts = getBaseDAO().queryForEq(ActivationCost.ID_SERVICE, serviceId);
            if (activationCosts.size() != 0) {
                return activationCosts.get(0).getActivationCost();
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return 0.00;
    }

    public ActivationCost getActivationCostByServiceDesc(String serviceDesc) {
        try {
            ActivationCost activationCost = getBaseDAO().queryBuilder().where()
                    .eq(ActivationCost.NAME_SERVICE, serviceDesc).queryForFirst();
            if (activationCost != null) {
                return activationCost;
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return null;
    }
}
