package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDeviceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TLCOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TlcDetailsOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferDevice;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TLCOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOffers;

import java.util.List;

public class DeleteAllOfferInfo extends AbstractService<Integer, Boolean> {

    public DeleteAllOfferInfo() {
    }

    public DeleteAllOfferInfo(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    public DeleteAllOfferInfo(OnTaskCompleted onTaskCompleted) {
        super(onTaskCompleted);
    }

    @Override
    protected Boolean doStuff(Integer... integers) {
        int offerId = integers[0];
        OfferDAOImpl offerDAO = new OfferDAOImpl();
        Offer offer = offerDAO.getOfferById(offerId);

        if (offer.getEnergyOfferId() != null) {
            EnergyOfferDAOImpl energyOfferDAO = new EnergyOfferDAOImpl();
            EnergyOfferDateIntervalDAOImpl energyOfferDateIntervalDAO = new EnergyOfferDateIntervalDAOImpl();
            EnergyOfferDetailsDAOImpl energyOfferDetailsDAO = new EnergyOfferDetailsDAOImpl();
            EnergyOffer energyOffer = energyOfferDAO.getEnergyOfferById(offer.getEnergyOfferId());
            List<EnergyOfferDetails> energyOfferDetailses = energyOfferDetailsDAO.getEnergyOfferDetailsByEnergyOfferId(offer.getEnergyOfferId());
            for (EnergyOfferDetails energyOfferDetails : energyOfferDetailses) {
                energyOfferDateIntervalDAO.deleteEnergyDateIntervallByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
            }
            energyOfferDetailsDAO.deleteEnergyDetailslByEnergyOfferId(offer.getEnergyOfferId());
            energyOfferDAO.delete(energyOffer);
        }

        if (offer.getGasOfferId() != null) {
            GasOfferDAOImpl gasOfferDAO = new GasOfferDAOImpl();
            GasDetailOffersDAOImpl gasDetailOffersDAO = new GasDetailOffersDAOImpl();
            GasOfferDateIntervalDAOImpl gasOfferDateIntervalDAO = new GasOfferDateIntervalDAOImpl();
            GasOffer gasOffer = gasOfferDAO.getGasOfferById(offer.getGasOfferId());
            List<GasDetailOffers> gasDetailOfferses = gasDetailOffersDAO.getGasOfferDetailsByGasOfferId(offer.getGasOfferId());
            for (GasDetailOffers gasDetailOffers : gasDetailOfferses) {
                gasOfferDateIntervalDAO.deleteGasDateIntervallByDetailId(gasDetailOffers.getGasDetailsOfferId());
            }
            gasDetailOffersDAO.deleteGasDetailslByGasOfferId(offer.getGasOfferId());
            gasOfferDAO.delete(gasOffer);
        }

        if (offer.getTlcOfferId() != null) {
            TLCOfferDAOImpl tlcOfferDAO = new TLCOfferDAOImpl();
            TLCOffer tlcOffer = tlcOfferDAO.getTLCOfferById(offer.getTlcOfferId());
            new TlcDetailsOfferDAOImpl().deleteTlcOfferDetailsWitTlcOfferId(tlcOffer.getTlcOfferId());
            tlcOfferDAO.delete(tlcOffer);
        }

        if (offer.getWlrOfferId() != null) {
            WlrOffersDAOImpl wlrOffersDAO = new WlrOffersDAOImpl();
            WlrOffers wlrOffers = wlrOffersDAO.getWlrOfferById(offer.getWlrOfferId());
            new WlrOfferDetailsDAOImpl().deleteWlrOfferDetailsByMainWlrOfferId(offer.getWlrOfferId());
            wlrOffersDAO.delete(wlrOffers);
        }

        if (offer.getInternetOfferId() != null) {
            InternetOfferDAOImpl internetOfferDAO = new InternetOfferDAOImpl();
            InternetDetailOffersDAOImpl internetDetailOffersDAO = new InternetDetailOffersDAOImpl();
            internetDetailOffersDAO.deleteInternetDetailslByInternetOfferId(offer.getInternetOfferId());
            InternetOffer internetOffer = internetOfferDAO.getInternetOfferById(offer.getInternetOfferId());
            internetOfferDAO.delete(internetOffer);
        }
//
//        if (offer.getMobileOfferId() != null) {
//            MobileOfferDAOImpl mobileOfferDAO = new MobileOfferDAOImpl();
//            MobileOffer mobileOffer = mobileOfferDAO.getMobileOfferById(offer.getMobileOfferId());
//            mobileOfferDAO.delete(mobileOffer);
//        }

        new OfferDeviceDAOImpl().deleteOfferDeviceByOfferId(offer.getOfferId());

        MyApplication.removeAll();
        return new OfferDAOImpl().delete(offer);
    }
}
