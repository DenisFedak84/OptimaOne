package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;

public class EliminiaGasOffer extends AbstractService<GasOffer, Boolean> {

    public EliminiaGasOffer() {
    }

    public EliminiaGasOffer(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected Boolean doStuff(GasOffer... gasOffers) {
        GasOffer gasOffer = gasOffers[0];
        new GasDetailOffersDAOImpl().deleteGasDetailslByGasOfferId(gasOffer.getGasOfferId());
        return new GasOfferDAOImpl().delete(gasOffer);
    }
}
