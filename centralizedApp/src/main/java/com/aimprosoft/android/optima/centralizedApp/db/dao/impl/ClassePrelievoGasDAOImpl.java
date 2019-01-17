package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;


import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ClassePrelievoGas;

import java.sql.SQLException;
import java.util.List;

public class ClassePrelievoGasDAOImpl extends AbstractDAOImpl<ClassePrelievoGas> {
    public ClassePrelievoGasDAOImpl() {
        super(ClassePrelievoGas.class);
    }

    public String getClasseDiPrelievoDescById(int classeDiPrelievoId) {
        try {
            List<ClassePrelievoGas> classePrelievoGases = getBaseDAO().queryBuilder().where().eq(ClassePrelievoGas.ID_CLASSE_PRELIEVO, classeDiPrelievoId).query();
            if (classePrelievoGases.size() != 0) {
                ClassePrelievoGas classePrelievoGas = classePrelievoGases.get(0);
                return classePrelievoGas.getClassePrelievo();
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get row", e);
        }
        return "";
    }

    public ClassePrelievoGas getClasseDiPrelievoByDesc(String desc) {
        try {
            ClassePrelievoGas classePrelievoGas = getBaseDAO().queryBuilder().where()
                    .like(ClassePrelievoGas.CLASSE_PRELIEVO, desc + "%").queryForFirst();
            if (classePrelievoGas != null) {
                return classePrelievoGas;
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get row", e);
        }
        return new ClassePrelievoGas();
    }
}
