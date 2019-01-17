package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CategorieUsoDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.CategorieUso;

import java.util.List;

public class CategoriaDusoService extends AbstractService<String, List<CategorieUso>> {

    public CategoriaDusoService() {
    }

    public CategoriaDusoService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List<CategorieUso> doStuff(String... strings) {
        String system = strings[0];
        List<CategorieUso> categorieUsoList = new CategorieUsoDAOImpl().getCategoriaUsoBySystem(system);
        categorieUsoList.add(0, new CategorieUso(0, "", "", ""));
        return categorieUsoList;
    }
}
