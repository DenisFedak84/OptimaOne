package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.COnsumptionTrend;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;

import java.util.ArrayList;
import java.util.List;

public class COnsumptionTrendDAOImpl extends AbstractDAOImpl<COnsumptionTrend> {

    public COnsumptionTrendDAOImpl() {
        super(COnsumptionTrend.class);
    }

    public List<Object[]> sum(int energyMeterId, int flatType, int townId, String queryPart) {

//        String query = "SELECT SUM(COnsumptionTrend.percValue) AS percYearlyConsumption"+
//                " FROM UtilizationProfile INNER JOIN COnsumptionTrend"+
//                " ON UtilizationProfile.codeZone = COnsumptionTrend.codeZone"+
//                " INNER JOIN Towns ON UtilizationProfile.climaticZone = Towns.climaticZone"+
//                " WHERE (UtilizationProfile.energyMeter = "+energyMeterId+") AND (UtilizationProfile.flatType = "+flatType+")"+
//                " AND (Towns.townId = "+townId+") AND "+queryPart;


        String query = "SELECT SUM(COnsumptionTrend.percValue) AS percYearlyConsumption" +
                " FROM COnsumptionTrend" +

                " WHERE COnsumptionTrend.codeZone" +
                " IN (SELECT UtilizationProfile.codeZone" +
                " FROM UtilizationProfile" +
                " WHERE (UtilizationProfile.energyMeter = " + energyMeterId + ")" +
                " AND (UtilizationProfile.flatType = " + flatType + ")" +
                " AND UtilizationProfile.climaticZone" +
                " IN(SELECT Towns.climaticZone" +
                " FROM Towns" +
                " WHERE Towns.townId = " + townId + "))" +
                " AND (" + queryPart +
                " GROUP BY COnsumptionTrend.codeZone;";

        try {
            GenericRawResults<Object[]> result = getBaseDAO().queryRaw(query, new DataType[]{DataType.BIG_DECIMAL});
            return result.getResults();
        } catch (Throwable e) {
            Log.e(TAG, "can't make query", e);
        }
        return new ArrayList<>();
    }
}
