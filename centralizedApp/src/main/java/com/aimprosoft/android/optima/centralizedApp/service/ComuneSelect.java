package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TownDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Town;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComuneSelect extends AbstractService<Void, List<Town>> {

    public ComuneSelect() {
    }

    public ComuneSelect(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List<Town> doStuff(Void... params) {
        try {
            TownDAOImpl townDAO = new TownDAOImpl();
//            List list = townDAO.getAllRows();
            List list = townDAO.getTownsList();
            Collections.sort(list, townComparator);
            return list;
        } catch (Exception ignored) {
        }
        return new ArrayList<>();
    }

    static Comparator<Town> townComparator = new Comparator<Town>() {

        public int compare(Town o1, Town o2) {
            return o1.getTownDesc().compareTo(o2.getTownDesc());
        }
    };
}
