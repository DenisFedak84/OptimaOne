package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;


import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ConsuptionClass;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;

import java.util.ArrayList;
import java.util.List;


public class ConsuptionClassDAOImpl extends AbstractDAOImpl<ConsuptionClass> {
    public ConsuptionClassDAOImpl() {
        super(ConsuptionClass.class);
    }

    public List<Object[]> offerCostComputation(int intervalPerc, int tensioneId, int energyMeter, int yearlyConsumption) {

        String query = "SELECT " + getMontlyCostNumber(intervalPerc) +", yearlyConsumption, "+ getMontlyCostIVANumber(intervalPerc) +", version FROM ConsuptionClass " +
                "WHERE (ConsuptionClass.consumptionClassType = " + tensioneId + ") " +
                "AND (ConsuptionClass.energy_meter = " + energyMeter + ") " +
                "AND (ConsuptionClass.yearlyRangeFrom <= " + yearlyConsumption + ") " +
                "AND (ConsuptionClass.yearlyRangeTo >= " + yearlyConsumption + ");";

//        String query = "SELECT montlyCost, yearlyConsumption, montlyCostIVA FROM Cli<<<<entConsuptionClass " +
//                "JOIN ConsuptionClass " +
//                "ON ClientConsuptionClass.consuptionClassType = ConsuptionClass.consumptionClassType " +
//                "WHERE (ClientConsuptionClass.consuptionClassType = " + consumptionType + ") " +
//                "AND (ClientConsuptionClass.energyMeterId = " + energyMeter + ") " +
//                "AND (ConsuptionClass.yearlyRangeFrom <= " + yearlyConsumption + ") " +
//                "AND (ConsuptionClass.yearlyRangeTo >= " + yearlyConsumption + ");";

        GenericRawResults<Object[]> result;
        try {
            result = getBaseDAO().queryRaw(query, new DataType[]{DataType.BIG_DECIMAL,DataType.INTEGER, DataType.BIG_DECIMAL, DataType.STRING});
            return result.getResults();
        } catch (Throwable e) {
            Log.e(TAG, "can't make query", e);
        }
        return new ArrayList<>();
    }

    private String getMontlyCostNumber(int montlyCostNumber) {
        String columnName = "";
        switch (montlyCostNumber){
            case 1:
                columnName = "montlyCost";
                break;
            case 2:
                columnName = "montlyCost2";
                break;
            case 3:
                columnName = "montlyCost3";
                break;
        }
        return columnName;
    }

    private String getMontlyCostIVANumber(int montlyCostNumber) {
        String columnName = "";
        switch (montlyCostNumber){
            case 1:
                columnName = "montlyCostIVA";
                break;
            case 2:
                columnName = "montlyCostIVA2";
                break;
            case 3:
                columnName = "montlyCostIVA3";
                break;
        }
        return columnName;
    }
}
