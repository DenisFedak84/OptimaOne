package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.CategorieUso;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieUsoDAOImpl extends AbstractDAOImpl<CategorieUso> {
    public CategorieUsoDAOImpl() {
        super(CategorieUso.class);
    }

    public String getCodiceCategoriaById(int categoriaId) {
        try {
            List<CategorieUso> categories = getBaseDAO().queryBuilder().where().eq(CategorieUso.ID_CATEGORIA_USO, categoriaId).query();
            if (categories.size() != 0) {
                CategorieUso categorieUso = categories.get(0);
                return categorieUso.getCodiceCategoria();
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get codice categoria", e);
        }
        return "";
    }


    public String getCategorieUsoDescById(int id) {
        try {
            List<CategorieUso> list = getBaseDAO().queryBuilder().where().eq(CategorieUso.ID_CATEGORIA_USO, id).query();
            if (list.size() != 0) {
                return list.get(0).getDescCategoriaUso();

            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return "";
    }

    public List<CategorieUso> getCategoriaUsoBySystem(String system) {
        try {
            List<CategorieUso> list = getBaseDAO().queryForEq(CategorieUso.SYSTEM, system);
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ArrayList<>();
    }

    public CategorieUso getCategorieUsoDescAndSystemById(String desc, String system) {
        try {
            CategorieUso categorieUso = getBaseDAO().queryBuilder().where()
                    .eq(CategorieUso.DESC_CATEGORIA_USO, desc).and()
                    .eq(CategorieUso.SYSTEM, system).queryForFirst();
            if (categorieUso != null) {
                return categorieUso;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new CategorieUso();
    }
}
