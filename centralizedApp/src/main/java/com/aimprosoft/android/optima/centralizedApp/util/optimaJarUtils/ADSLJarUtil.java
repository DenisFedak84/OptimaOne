package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils;

import android.text.TextUtils;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.ServicesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Services;
import com.optima.pricing.domain.Pricing;

import java.math.BigInteger;
import java.util.List;

public class ADSLJarUtil extends AbstractJarUtil {
    @Override
    void buildPricing(Pricing pricing, Offer offer) {
        pricing.setDatiAdsl(buildDatiADSL(offer));
    }

    private Pricing.DatiAdsl buildDatiADSL(Offer offer) {
        Pricing.DatiAdsl datiAdsl = new Pricing.DatiAdsl();
        InternetDetailOffersDAOImpl internetDetailOffersDAO = new InternetDetailOffersDAOImpl();
        datiAdsl.setIdCampagnaAttivazione(getIdCampagnaAttivazione(offer.getConfiguratorType(),
                internetDetailOffersDAO.isExistingOffersUsed(offer.getInternetOfferId())));

        List<Pricing.DatiAdsl.DettaglioAdsl> dettaglioAdslList = datiAdsl.getDettaglioAdsl();

        BundleDAOImpl bundleDAO = new BundleDAOImpl();
        List<InternetDetailOffers> internetDetailOfferses = new InternetDetailOffersDAOImpl().getInternetDetailsOfferByInternetOfferId(offer.getInternetOfferId());
        for (InternetDetailOffers internetDetailOffer : internetDetailOfferses) {
            Pricing.DatiAdsl.DettaglioAdsl dettaglioAdsl = new Pricing.DatiAdsl.DettaglioAdsl();
            dettaglioAdsl.setDataInizioValidita(getDateInizio());
            dettaglioAdsl.setDataFineValidita(getDateFine());
//            dettaglioAdsl.setGiaCliente("");
            dettaglioAdsl.setIdCampagna(getIdCampagna(offer.getConfiguratorType(), internetDetailOffer.isExistingClientOffer()));
            List<Services> servicesList = ServicesDAOImpl.getServicesByIdRange(internetDetailOffer.getServiceId());
            List<Pricing.DatiAdsl.DettaglioAdsl.OpzioniLinea> opzioniLineaList = dettaglioAdsl.getOpzioniLinea();
            Pricing.DatiAdsl.DettaglioAdsl.OpzioniLinea opzioniLinea;
            for (Services services : servicesList) {
                opzioniLinea = new Pricing.DatiAdsl.DettaglioAdsl.OpzioniLinea();
                opzioniLinea.setCodiceOpzione(services.getCode());
                opzioniLineaList.add(opzioniLinea);
            }
            String code = internetDetailOffer.isExistingClientOffer() ? String.valueOf(internetDetailOffer.getBundleId())
                    : bundleDAO.getBundleById(internetDetailOffer.getBundleId()).getCode();
            dettaglioAdsl.setTipoAdsl(code);
            dettaglioAdslList.add(dettaglioAdsl);
        }
        return datiAdsl;
    }

    public static int getIdCampagnaAttivazione(int offerType, boolean isAlreadyClient) {
        int id;
        if (offerType == Constants.BUSINESS_CONFIGURATOR) {
            id = isAlreadyClient ? ID_CAMPAGNA_ADSL_SF_ALREADY_CLIENT : ID_CAMPAGNA_ADSL_SF_REGULAR;
        } else {
            id = isAlreadyClient ? ID_CAMPAGNA_ADSL_SF_CONSUMER_ALREADY_CLIENT : ID_CAMPAGNA_ADSL_SF_CONSUMER_REGULAR;
        }
        return id;
    }

    public static BigInteger getIdCampagna(int offerType, boolean isAlreadyClient) {
        BigInteger id;
        if (offerType == Constants.BUSINESS_CONFIGURATOR) {
            id = isAlreadyClient ? ID_CAMPAGNA_ADSL_ALREADY_CLIENT : ID_CAMPAGNA_ADSL_REGULAR;
        } else {
            id = isAlreadyClient ? ID_CAMPAGNA_ADSL_CONSUMER_ALREADY_CLIENT : ID_CAMPAGNA_ADSL_CONSUMER_REGULAR;
        }
        return id;
    }
}
