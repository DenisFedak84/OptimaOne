package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileDetailsOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileOffer;

public class EliminiaMobileOffer extends AbstractService<MobileOffer, Boolean> {

    public EliminiaMobileOffer() {
    }

    public EliminiaMobileOffer(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected Boolean doStuff(MobileOffer... mobileOffers) {
        MobileOffer mobileOffer = mobileOffers[0];
        new MobileDetailsOfferDAOImpl().deleteMobileDetailslByMobileOfferId(mobileOffer.getMobileOfferId());
        return new MobileOfferDAOImpl().delete(mobileOffer);
    }
}
