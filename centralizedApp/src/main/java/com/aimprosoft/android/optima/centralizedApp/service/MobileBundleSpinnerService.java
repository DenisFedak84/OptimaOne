package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.MobileBundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileBundle;

import java.util.List;

public class MobileBundleSpinnerService extends AbstractService<Void, List<MobileBundle>> {

    private int idCampagna;
    private MobileBundleDAOImpl.TipoProdotto tipoProdotto;
    private int pachettoPromo;

    public MobileBundleSpinnerService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    public MobileBundleSpinnerService(Activity activity, OnTaskCompleted onTaskCompleted,
                                      int idCampagna,
                                      MobileBundleDAOImpl.TipoProdotto tipoProdotto,
                                      int pachettoPromo) {
        super(activity, onTaskCompleted);
        this.idCampagna = idCampagna;
        this.tipoProdotto = tipoProdotto;
        this.pachettoPromo = pachettoPromo;
    }

    @Override
    protected List doStuff(Void... voids) {
        List<MobileBundle> mobileBundleList;

        if (tipoProdotto != null) {
            mobileBundleList = MobileBundleDAOImpl.getMobileBundleList(idCampagna, pachettoPromo, tipoProdotto);
        } else {
            mobileBundleList = MobileBundleDAOImpl.getMobileBundleList(idCampagna, pachettoPromo);
        }

        return mobileBundleList;
    }
}


