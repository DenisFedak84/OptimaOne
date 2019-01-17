package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Eng_tRipartizioniConsumi;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;

import java.sql.SQLException;
import java.util.List;

public class Eng_tRipartizioniConsumiDAOImpl extends AbstractDAOImpl<Eng_tRipartizioniConsumi> {
    public Eng_tRipartizioniConsumiDAOImpl() {
        super(Eng_tRipartizioniConsumi.class);
    }


    public Double getMonthSumValue(String monthPart, String cluster) {
        try {
            String query = "SELECT " + monthPart.substring(0, monthPart.length() - 2) + "FROM Eng_tRipartizioniConsumi WHERE rc_desc='" + cluster + "' group by rc_id;";

            GenericRawResults<Object[]> result = getBaseDAO().queryRaw(query, new DataType[]{DataType.DOUBLE});
            List<Object[]> objectList = result.getResults();
            if (objectList.size() != 0) {
                Object[] objects = objectList.get(0);
                return (Double) objects[0];
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't make query", e);
        }
        return 1.00;
    }

    public Double getMonthSumTotalValueAccrodingFascia(String cluster, int fascia) {
        try {
            String query = "SELECT (rc_perc_mese1 + rc_perc_mese2 + rc_perc_mese3 + rc_perc_mese4 + rc_perc_mese5 + rc_perc_mese6 + rc_perc_mese7 + rc_perc_mese8 + rc_perc_mese9" +
                    " + rc_perc_mese10 + rc_perc_mese11 + rc_perc_mese12) as valuePerFascia FROM Eng_tRipartizioniConsumi WHERE rc_desc='" + cluster + "' AND rc_fascia=" + fascia + "  group by rc_id;";

            GenericRawResults<Object[]> result = getBaseDAO().queryRaw(query, new DataType[]{DataType.DOUBLE});
            List<Object[]> objectList = result.getResults();
            if (objectList.size() != 0) {
                Object[] objects = objectList.get(0);
                return (Double) objects[0];
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't make query", e);
        }
        return 1.00;
    }

    public Double getMonthSumTotalValue(String cluster) {
        try {
            String query = "SELECT SUM(rc_perc_mese1 + rc_perc_mese2 + rc_perc_mese3 + rc_perc_mese4 + rc_perc_mese5 + rc_perc_mese6 + rc_perc_mese7 + rc_perc_mese8 + rc_perc_mese9" +
                    " + rc_perc_mese10 + rc_perc_mese11 + rc_perc_mese12) as valuePerFascia FROM Eng_tRipartizioniConsumi WHERE rc_desc='" + cluster + "' group by rc_id;";

            GenericRawResults<Object[]> result = getBaseDAO().queryRaw(query, new DataType[]{DataType.DOUBLE});
            List<Object[]> objectList = result.getResults();
            if (objectList.size() != 0) {
                Object[] objects = objectList.get(0);
                return (Double) objects[0];
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't make query", e);
        }
        return 0.00;
    }


    public Double getInsertedMonthPerc(String queryPart, String cluster) {
        try {
            String query = "SELECT sum(" + queryPart + ") as valuePerFascia FROM Eng_tRipartizioniConsumi WHERE rc_desc='" + cluster + "';";

            GenericRawResults<Object[]> result = getBaseDAO().queryRaw(query, new DataType[]{DataType.DOUBLE});
            List<Object[]> objectList = result.getResults();
            if (objectList.size() != 0) {
                Object[] objects = objectList.get(0);
                return (Double) objects[0];
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't make query", e);
        }
        return 0.00;
    }
}
