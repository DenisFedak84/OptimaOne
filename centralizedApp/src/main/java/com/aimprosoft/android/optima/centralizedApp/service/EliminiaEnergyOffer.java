package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;

public class EliminiaEnergyOffer extends AbstractService<EnergyOffer, Boolean> {

    public EliminiaEnergyOffer() {
    }

    public EliminiaEnergyOffer(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected Boolean doStuff(EnergyOffer... energyOffers) {
        EnergyOffer energyOffer = energyOffers[0];
        new EnergyOfferDetailsDAOImpl().deleteEnergyDetailslByEnergyOfferId(energyOffer.getEnergyOfferId());
        return new EnergyOfferDAOImpl().delete(energyOffer);
    }
}
