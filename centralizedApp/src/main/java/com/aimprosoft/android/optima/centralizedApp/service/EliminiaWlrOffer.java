package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOffers;

public class EliminiaWlrOffer extends AbstractService<WlrOffers, Boolean> {

    public EliminiaWlrOffer() {
    }

    public EliminiaWlrOffer(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected Boolean doStuff(WlrOffers... wlrOfferses) {
        WlrOffers wlrOffers = wlrOfferses[0];
        new WlrOfferDetailsDAOImpl().deleteWlrOfferDetailsByMainWlrOfferId(wlrOffers.getWlrOfferId());
        return new WlrOffersDAOImpl().delete(wlrOffers);
    }
}
