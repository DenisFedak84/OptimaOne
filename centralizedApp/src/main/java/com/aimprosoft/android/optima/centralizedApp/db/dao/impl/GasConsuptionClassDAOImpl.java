package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.GasConsuptionClass;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;

import java.util.ArrayList;
import java.util.List;

public class GasConsuptionClassDAOImpl extends AbstractDAOImpl<GasConsuptionClass> {
    public GasConsuptionClassDAOImpl() {
        super(GasConsuptionClass.class);
    }

    public List<Object[]> offerCostComputation(int yearlyConsumption, int townId, int fiscaleId) {

        String query = "SELECT montlyCost, yearlyConsumption, montlyCostIVA, version FROM GasConsuptionClass " +
                "INNER JOIN Towns ON Towns.tariffZone=GasConsuptionClass.tariffZone WHERE " +
                "GasConsuptionClass.fiscalClass ="+ fiscaleId +" AND Towns.townId =" + townId + " " +
                "AND (GasConsuptionClass.yearlyRangeFrom <= " + yearlyConsumption + ") " +
                "AND (GasConsuptionClass.yearlyRangeTo >= " + yearlyConsumption + ");";

        GenericRawResults<Object[]> result;
        try {
            result = getBaseDAO().queryRaw(query, new DataType[]{DataType.BIG_DECIMAL, DataType.INTEGER, DataType.BIG_DECIMAL, DataType.STRING});
            return result.getResults();
        } catch (Throwable e) {
            Log.e(TAG, "can't make query", e);
        }
        return new ArrayList<>();
    }

    public List<Object[]> offerCostComputationIfNeedMax(int townId, int fiscaleId){

        String query = "SELECT montlyCost, yearlyConsumption, montlyCostIVA, version FROM GasConsuptionClass " +
                "INNER JOIN Towns ON Towns.tariffZone=GasConsuptionClass.tariffZone WHERE " +
                "GasConsuptionClass.fiscalClass="+ fiscaleId +" AND Towns.townId =" + townId + " " +
                "ORDER BY GasConsuptionClass.yearlyRangeFrom DESC LIMIT 1;";

        GenericRawResults<Object[]> result;
        try {
            result = getBaseDAO().queryRaw(query, new DataType[]{DataType.BIG_DECIMAL, DataType.INTEGER, DataType.BIG_DECIMAL, DataType.STRING});
            return result.getResults();
        } catch (Throwable e) {
            Log.e(TAG, "can't make query", e);
        }
        return new ArrayList<>();
    }
}
