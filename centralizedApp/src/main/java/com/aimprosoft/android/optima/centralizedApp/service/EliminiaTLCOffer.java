package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TLCOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TlcDetailsOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TLCOffer;

public class EliminiaTLCOffer extends AbstractService<TLCOffer, Boolean> {

    public EliminiaTLCOffer(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    public EliminiaTLCOffer() {
    }

    @Override
    protected Boolean doStuff(TLCOffer... tlcOffers) {
        TLCOffer tlcOffer = tlcOffers[0];
        new TlcDetailsOfferDAOImpl().deleteTlcOfferDetailsWitTlcOfferId(tlcOffer.getTlcOfferId());
        return new TLCOfferDAOImpl().delete(tlcOffer);
    }
}
