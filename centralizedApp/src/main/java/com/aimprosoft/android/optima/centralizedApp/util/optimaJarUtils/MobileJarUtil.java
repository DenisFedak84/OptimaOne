package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.MobileBundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileDetailsOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileBundle;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.optima.pricing.domain.Pricing;

import java.math.BigInteger;
import java.util.List;

public class MobileJarUtil extends AbstractJarUtil {
    @Override
    void buildPricing(Pricing pricing, Offer offer) {
        pricing.setDatiMobile(buildDatiMobile(offer));
    }

    private Pricing.DatiMobile buildDatiMobile(Offer offer) {
        Pricing.DatiMobile datiMobile = new Pricing.DatiMobile();
//        datiMobile.setIdCampagnaAttivazione(ID_CAMPAGNA_MOBILE_DEFAULT_SF);
        datiMobile.setIdCampagnaAttivazione(getIdCampagnaAttivazione(offer.getConfiguratorType()));

        List<Pricing.DatiMobile.DettaglioMobile> dettaglioMobileList = datiMobile.getDettaglioMobile();
        datiMobile.setNumserv(defineServicesCount(offer));

        List<MobileBundle> mobileBundleList = MobileBundleDAOImpl.getMobileBundleList(getIdCampagnaAttivazione(offer.getConfiguratorType()), 1);
//        List<MobileBundle> mobileBundleList = MobileBundleDAOImpl.getMobileBundleList(256, 1);
        if(mobileBundleList!=null && !mobileBundleList.isEmpty()){
            datiMobile.setPacchettoPromo(String.valueOf((int)mobileBundleList.get(0).getIdProdotto()));
        }

        List<MobileDetailsOffer> mobileDetailsOffers = new MobileDetailsOfferDAOImpl().getMobileOfferDetailsByMobileOfferId(offer.getMobileOfferId());
        for (MobileDetailsOffer mobileDetailsOffer : mobileDetailsOffers) {
            String idProdotto = String.valueOf((int)MobileBundleDAOImpl.getMobileBundle(mobileDetailsOffer.getBundleId()).getIdProdotto());
            Pricing.DatiMobile.DettaglioMobile dettaglioMobile = new Pricing.DatiMobile.DettaglioMobile();
            dettaglioMobile.setDataInizioValidita(getDateInizio());
            dettaglioMobile.setDataFineValidita(getDateFine());
//            dettaglioAdsl.setGiaCliente("");
//            dettaglioMobile.setIdCampagna(getIdCampagna(offer.getConfiguratorType()));
            dettaglioMobile.setIdCampagna(MobileBundleDAOImpl.getMobileBundle(mobileDetailsOffer.getBundleId()).getIdProdotto() == 1818 ? getIdCampagna(offer.getConfiguratorType()) : ID_CAMPAGNA_MOBILE_DEFAULT);
//            dettaglioMobile.setIdCampagna(MobileDetailsOfferDAOImpl.ID_CAMPAGNA_MOBILE_DEFAULT);
            dettaglioMobile.setTipoPacchetto(idProdotto);
            dettaglioMobile.setNumeroSim(mobileDetailsOffer.getSim());
            dettaglioMobileList.add(dettaglioMobile);
        }
        return datiMobile;
    }

    private Integer defineServicesCount(Offer offer) {
        return (offer.getEnergyOfferId() != null ? 1 : 0)
                + (offer.getGasOfferId() != null ? 1 : 0)
                + (offer.getInternetOfferId() != null && (offer.getEnergyOfferId() != null  || offer.getGasOfferId() != null )  ? 1 : 0);
    }

    public static int getIdCampagnaAttivazione(int offerType) {
        return offerType == Constants.BUSINESS_CONFIGURATOR ? ID_CAMPAGNA_MOBILE_SF : ID_CAMPAGNA_MOBILE_SF_CONSUMER;
//        return 1061;
    }

    public static BigInteger getIdCampagna(int offerType) {
        return offerType == Constants.BUSINESS_CONFIGURATOR ? ID_CAMPAGNA_MOBILE : ID_CAMPAGNA_MOBILE_CONSUMER;
//        return new BigInteger("1061");
    }
}
