package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ClassePrelievoGas;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasProfiliUtilizzo;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GasProfiliUtilizzoDAOImpl extends AbstractDAOImpl<GasProfiliUtilizzo> {
    public GasProfiliUtilizzoDAOImpl() {
        super(GasProfiliUtilizzo.class);
    }

    public List getClassePrelievoGasFilteredList(int townId, int categoriaUso) {
        try {
            String query = "SELECT DISTINCT ClassePrelievoGas.idClassePrelievo " +
                    "FROM GasProfiliUtilizzo INNER JOIN " +
                    "Towns ON GasProfiliUtilizzo.zonaClimatica = Towns.climaticZone INNER JOIN " +
                    "ClassePrelievoGas ON GasProfiliUtilizzo.classePrelievo = ClassePrelievoGas.idClassePrelievo INNER JOIN " +
                    "CategorieUso ON GasProfiliUtilizzo.categoriaUso = CategorieUso.codiceCategoria " +
                    "WHERE (Towns.townId = " + townId + ") AND (CategorieUso.idCategoriaUso = " + categoriaUso + ");";

            GenericRawResults<Object[]> result = getBaseDAO().queryRaw(query, new DataType[]{DataType.INTEGER});
            List<Object[]> objectList = result.getResults();
            List<Object> idList = new ArrayList<>();
            for (Object[] ob : objectList) {
                idList.add(ob[0]);
            }
            Where<ClassePrelievoGas, Integer> wh = new ClassePrelievoGasDAOImpl().getBaseDAO().queryBuilder().
                    where().in(ClassePrelievoGas.ID_CLASSE_PRELIEVO, idList);
            List classePrelievoGases = wh.query();
            classePrelievoGases.add(0, "");
            return classePrelievoGases;


        } catch (SQLException e) {
            Log.e(TAG, "can't make query", e);
        }
        return null;
    }
}
