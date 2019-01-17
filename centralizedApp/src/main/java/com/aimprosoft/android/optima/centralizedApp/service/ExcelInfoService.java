package com.aimprosoft.android.optima.centralizedApp.service;

import android.os.AsyncTask;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TLCOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TownDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TLCOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Town;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelInfoService extends AsyncTask<Void, Void, Map> {

    EnergyOffer energyOffer;
    GasOffer gasOffer;
    List<GasOfferDateInterval> gasOfferDateIntervalList;
    List<EnergyOfferDateInterval> energyOfferDateIntervalList;
    TLCOffer tlcOffer;
    InternetOffer internetOffer;
    MobileOffer mobileOffer;

    Map<Integer, List> excelMap = new HashMap<>();

    OfferDAOImpl offerDAO = new OfferDAOImpl();
    EnergyOfferDAOImpl energyOfferDAO = new EnergyOfferDAOImpl();
    GasOfferDAOImpl gasOfferDAO = new GasOfferDAOImpl();
    TLCOfferDAOImpl tlcOfferDAO = new TLCOfferDAOImpl();
    InternetOfferDAOImpl internetOfferDAO = new InternetOfferDAOImpl();
    MobileOfferDAOImpl mobileOfferDAO = new MobileOfferDAOImpl();
    EnergyOfferDateIntervalDAOImpl energyOfferDateIntervalDAO = new EnergyOfferDateIntervalDAOImpl();
    GasOfferDateIntervalDAOImpl gasOfferDateIntervalDAO = new GasOfferDateIntervalDAOImpl();
    TownDAOImpl townDAO = new TownDAOImpl();

    public ExcelInfoService() {
    }

    @Override
    protected Map doInBackground(Void... voids) {

        List<Offer> offerList = null;
        try {
            offerList = offerDAO.getAllRows();
        } catch (SQLException e) {
            Log.e(e.getClass().getName(), "can't get OfferList", e);
        }


        if (offerList == null) {
            return new HashMap();
        }

        for (Offer offer : offerList) {
            List excelInfo = new ArrayList();
            Town town = townDAO.getTownById(offer.getTownId());

            energyOffer = offer.getEnergyOfferId() != null ? energyOfferDAO.getEnergyOfferById(offer.getEnergyOfferId()) : null;
//            energyOfferDateIntervalList = offer.getEnergyOfferId() != null ? energyOfferDateIntervalDAO.getOfferDateIntervalByDetailsId(energyOffer.getEnergyOfferId()) : null;

            gasOffer = offer.getGasOfferId() != null ? gasOfferDAO.getGasOfferById(offer.getGasOfferId()) : null;
//            gasOfferDateIntervalList = offer.getGasOfferId() != null ? gasOfferDateIntervalDAO.getOfferDateIntervalById(gasOffer.getGasOfferId()) : null;

            tlcOffer = offer.getTlcOfferId() != null ? tlcOfferDAO.getTLCOfferById(offer.getTlcOfferId()) : null;

            internetOffer = offer.getInternetOfferId() != null ? internetOfferDAO.getInternetOfferById(offer.getInternetOfferId()) : null;

            mobileOffer = offer.getMobileOfferId() != null ? mobileOfferDAO.getMobileOfferById(offer.getMobileOfferId()) : null;

            excelInfo.add(offer);
            excelInfo.add(town);
            excelInfo.add(energyOffer);
            excelInfo.add(energyOfferDateIntervalList);
            excelInfo.add(gasOffer);
            excelInfo.add(gasOfferDateIntervalList);
            excelInfo.add(tlcOffer);
            excelInfo.add(internetOffer);
            excelInfo.add(mobileOffer);
            excelMap.put(offer.getOfferId(), excelInfo);
        }
        return excelMap;
    }
}

