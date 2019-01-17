package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;

import java.util.HashMap;

public class SetOptilifeCodiceToBase extends AbstractService<HashMap<String, String>, Boolean> {

    public SetOptilifeCodiceToBase() {
    }

    public SetOptilifeCodiceToBase(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected Boolean doStuff(HashMap<String, String>... hashMaps) {
        HashMap<String, String> param = hashMaps[0];
        OfferDAOImpl offerDAO = new OfferDAOImpl();
        Integer offerId = Integer.valueOf(param.get("offerId"));
        Offer offer = offerDAO.getOfferById(offerId);
        if (offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR) {
            offer.setCodiceFiscale(param.get("fiscale"));
        } else {
            offer.setPiva(param.get("fiscale"));
        }
        offerDAO.update(offer);
        return true;
    }
}
