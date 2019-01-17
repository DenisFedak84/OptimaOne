package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetOffer;

public class EliminiaInternetOffer extends AbstractService<InternetOffer, Boolean> {

    public EliminiaInternetOffer() {
    }

    public EliminiaInternetOffer(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected Boolean doStuff(InternetOffer... internetOffers) {
        InternetOffer internetOffer = internetOffers[0];
        new InternetDetailOffersDAOImpl().deleteInternetDetailslByInternetOfferId(internetOffer.getInternetOfferId());
        return new InternetOfferDAOImpl().delete(internetOffer);
    }
}
