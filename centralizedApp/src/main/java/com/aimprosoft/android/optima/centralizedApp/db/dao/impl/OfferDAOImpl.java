package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.AutoTestExecutor;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.util.ArrayList;
import java.util.List;

public class OfferDAOImpl extends AbstractDAOImpl<Offer> {
    public OfferDAOImpl() {
        super(Offer.class);
    }

    public List<Object[]> getArchivioList() {
        String query = "SELECT o.name, t.townDesc, o.piva, o.optilifeCode, o.ni, o.offerCost, " +
                "o.creationDate, o.offerId ,o.configurator_type, o.nome, o.surname, o.codiceFiscale " +
                "FROM Offer o " +
                "JOIN Towns t ON o.townId = t.townId";

        GenericRawResults<Object[]> result;
        try {
            result = getBaseDAO().queryRaw(query, new DataType[]{DataType.STRING});
            return result.getResults();
        } catch (Throwable e) {
            Log.e(TAG, "can't make query", e);
        }
        return new ArrayList<>();
    }

    public List<Offer> getOffersByCodiceFiscale(String codiceFiscale) {
        try {
            return getBaseDAO().queryBuilder().where().like(Offer.CODICE_FISCALE, codiceFiscale).query();
        } catch (Exception e) {
            Log.e(TAG, "cant get offers by codiceFiscale");
        }
        return new ArrayList<>();
    }

    public List<Offer> getOffersByPiva(String piva) {
        try {
            return getBaseDAO().queryBuilder().where().like(Offer.PIVA, piva).query();
        } catch (Exception e) {
            Log.e(TAG, "cant get offers by piva");
        }
        return new ArrayList<>();
    }

    public Offer getOfferById(int id) {
        try {
            List<Offer> list = getBaseDAO().queryBuilder().where().eq(Offer.OFFER_ID, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return null;
    }

    public List<Object[]> getListForExcel() {
        String query = "SELECT * " +
                "FROM Offer o " +
                "JOIN GasOffer t ON o.gasOfferId = t.gasOfferId";
        GenericRawResults<Object[]> result;
        try {
            result = getBaseDAO().queryRaw(query, new DataType[]{DataType.STRING});
            return result.getResults();
        } catch (Throwable e) {
            Log.e(TAG, "can't make query", e);
        }
        return new ArrayList<>();
    }

    public List<Offer> getOffersForAutoTest() {
        try {
            return getBaseDAO().queryBuilder().where().eq(Offer.OPTILIFE_CODE, Constants.TEST_OFFER_CODE).query();
        } catch (Exception e) {
            Log.e(TAG, "cant get offers by piva");
        }
        return new ArrayList<>();
    }

    public int deleteAllOffersForAutoTest() {
        try {
            DeleteBuilder<Offer, Integer>  deleteBuilder =
                    getBaseDAO().deleteBuilder();
            deleteBuilder.where().eq(Offer.OPTILIFE_CODE, Constants.TEST_OFFER_CODE);
            return deleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return 0;
    }
}


