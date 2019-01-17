package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.GasCurveConsumo;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GasCurveConsumoDAOImpl extends AbstractDAOImpl<GasCurveConsumo> {
    public GasCurveConsumoDAOImpl() {
        super(GasCurveConsumo.class);
    }

    public BigDecimal sumPercValue(int townId, String codeZone, String periodo_da, String periodo_a, int classePrelievo) {

        String query = "SELECT SUM(perc) AS percYearlyConsumption" +
                " FROM GasCurveConsumo INNER JOIN" +
                " GasProfiliUtilizzo ON GasCurveConsumo.codice = GasProfiliUtilizzo.profiloUtilizzo INNER JOIN" +
                " Towns ON GasProfiliUtilizzo.zonaClimatica = Towns.climaticZone" +
                " WHERE (Towns.townId =" + townId + ") AND (GasCurveConsumo.data BETWEEN '" + periodo_da + "' AND '" + periodo_a + "')" +
                " AND (GasProfiliUtilizzo.categoriaUso = '" + codeZone + "') AND (GasProfiliUtilizzo.classePrelievo =" + classePrelievo + ");";
        try {
            GenericRawResults<Object[]> result = getBaseDAO().queryRaw(query, new DataType[]{DataType.BIG_DECIMAL});
            List<Object[]> objectsList = result.getResults();
            if (objectsList.size() != 0) {
                Object[] objects = objectsList.get(0);
                return (BigDecimal) objects[0];
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't make query", e);
        }
        return null;
    }


    public List<Object[]> getListEstimatedConsumption(int townId, int yearlyConsumption, String codeZone, int classePrelievo) {
        List<Object[]> resultList = new ArrayList<>();

        String query = "SELECT ((SUM(perc)/100) * " + yearlyConsumption + "), strftime('%Y-%m', datetime(data/1000, 'unixepoch', 'localtime'))" +
                " AS converted_date FROM GasCurveConsumo" +
                " INNER JOIN" +
                " GasProfiliUtilizzo ON GasCurveConsumo.codice = GasProfiliUtilizzo.profiloUtilizzo INNER JOIN" +
                " Towns ON GasProfiliUtilizzo.zonaClimatica = Towns.climaticZone" +
                " WHERE (Towns.townId =" + townId + ")" +
                " AND GasProfiliUtilizzo.categoriaUso = '" + codeZone + "' AND (GasProfiliUtilizzo.classePrelievo =" + classePrelievo + ")" +
                " GROUP BY converted_date ORDER BY converted_date LIMIT 12";
        try {
            GenericRawResults<Object[]> result = getBaseDAO().queryRaw(query, new DataType[]{DataType.BIG_DECIMAL, DataType.STRING});
            List<Object[]> objectsList = result.getResults();
            if (objectsList.size() != 0) {
                resultList = objectsList;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't make query", e);
        }
        return resultList;
    }
}

