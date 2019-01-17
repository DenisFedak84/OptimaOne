package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.LineTypeDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.ServicesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.AdditionalNumbersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CarriersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.LineNumbersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.NetworksDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TlcDetailsOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.LineTypes;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Networks;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Services;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TlcDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOffers;
import com.optima.pricing.domain.Pricing;

import java.math.BigInteger;
import java.util.List;

public class VoceJarUtil extends AbstractJarUtil {
    private BundleDAOImpl bundleDAO = new BundleDAOImpl();

    @Override
    void buildPricing(Pricing pricing, Offer offer) {
        pricing.setDatiVoce(buildDatiVoce(offer));
    }

    private Pricing.DatiVoce buildDatiVoce(Offer offer) {
        String localBundle = null;
        String mobileBundle = null;

        WlrOffers wlrOffer = null;
        boolean isAlreadyClientWlrPresent = false;
        List<WlrOfferDetails> alreadyClientWlrOffers = null;
        if (offer.getWlrOfferId() != null) {
            alreadyClientWlrOffers = new WlrOfferDetailsDAOImpl().getOldClientDetailsByOfferId(offer.getWlrOfferId(), true);
            wlrOffer = new WlrOffersDAOImpl().getWlrOfferById(offer.getWlrOfferId());
            localBundle = getVoceBundle(wlrOffer.getLocalBundleId());
            mobileBundle = wlrOffer.getMobileBundleId() != null && wlrOffer.getMobileBundleId() == 27 ? "Flat" : getVoceBundle(wlrOffer.getMobileBundleId());
            isAlreadyClientWlrPresent = new WlrOfferDetailsDAOImpl().isExistingOffersUsed(offer.getWlrOfferId());
        }

        TlcDetailsOffer tlcDetailsOffer = null;
        boolean isAlreadyClientCpsPresent = false;
        List<TlcDetailsOffer> alreadyClientTlsOffers = null;
        if (offer.getTlcOfferId() != null) {
            alreadyClientTlsOffers = new TlcDetailsOfferDAOImpl().getExistingClientDetailsByTlcOfferId(offer.getTlcOfferId());
            tlcDetailsOffer = new TlcDetailsOfferDAOImpl().getAllTlcDetailsByTlcOfferId(offer.getTlcOfferId()).get(0);
            localBundle = getVoceBundle(tlcDetailsOffer.getLocalBundleId());
            mobileBundle = tlcDetailsOffer.getMobileBundleId() != null && tlcDetailsOffer.getMobileBundleId() == 27 ? "Flat" : getVoceBundle(tlcDetailsOffer.getMobileBundleId());
            isAlreadyClientCpsPresent = new TlcDetailsOfferDAOImpl().isExistingOffersUsed(offer.getTlcOfferId());
        }

        boolean isAlreadyClientUsed = isAlreadyClientCpsPresent || isAlreadyClientWlrPresent;

        Pricing.DatiVoce datiVoce = new Pricing.DatiVoce();
        datiVoce.setIdCampagnaAttivazione(getIdCampagnaAttivazione(offer.getConfiguratorType(), isAlreadyClientUsed));

        List<Pricing.DatiVoce.DettaglioVoce> dettaglioVoceList = datiVoce.getDettaglioVoce();

        if (localBundle != null || mobileBundle != null) {
            Pricing.DatiVoce.DettaglioVoce dettaglioVoce = new Pricing.DatiVoce.DettaglioVoce();
            dettaglioVoce.setIdCampagna(getIdCampagna(offer.getConfiguratorType(), isAlreadyClientUsed));
            dettaglioVoce.setDataInizioValidita(getDateInizio());
            dettaglioVoce.setDataFineValidita(getDateFine());

            dettaglioVoce.setMinutiFisso(localBundle);

            dettaglioVoce.setMinutiMobile(mobileBundle);
            dettaglioVoceList.add(dettaglioVoce);
            buildLineaVoceList(offer.getWlrOfferId(), dettaglioVoce);
        }

        if (alreadyClientTlsOffers != null) {
            Pricing.DatiVoce.DettaglioVoce dettaglioVoce;
            for (TlcDetailsOffer alreadyClientTlc : alreadyClientTlsOffers) {
                dettaglioVoce = new Pricing.DatiVoce.DettaglioVoce();
                dettaglioVoce.setIdCampagna(getIdCampagna(offer.getConfiguratorType(), true));
                dettaglioVoce.setDataInizioValidita(getDateInizio());
                dettaglioVoce.setDataFineValidita(getDateFine());

                dettaglioVoce.setMinutiFisso(String.valueOf(alreadyClientTlc.getLocalBundleId()));
                dettaglioVoce.setMinutiMobile(String.valueOf(alreadyClientTlc.getMobileBundleId()));
                dettaglioVoceList.add(dettaglioVoce);
            }
        }

        if (alreadyClientWlrOffers != null) {
            Pricing.DatiVoce.DettaglioVoce dettaglioVoce;
            for (WlrOfferDetails alreadyClientWlr : alreadyClientWlrOffers) {
                dettaglioVoce = new Pricing.DatiVoce.DettaglioVoce();
                dettaglioVoce.setIdCampagna(getIdCampagna(offer.getConfiguratorType(), true));
                dettaglioVoce.setDataInizioValidita(getDateInizio());
                dettaglioVoce.setDataFineValidita(getDateFine());

                dettaglioVoce.setMinutiFisso(String.valueOf(alreadyClientWlr.getOwnLocalBundleId()));
                dettaglioVoce.setMinutiMobile(String.valueOf(alreadyClientWlr.getOwnMobileBundleId()));
                dettaglioVoceList.add(dettaglioVoce);
                buildLineaVoceList(offer.getWlrOfferId(), dettaglioVoce);
            }
        }
        return datiVoce;
    }

    private String getVoceBundle(Integer bundleId) {
        return bundleId != null ? bundleDAO.getBundleById(bundleId).getCode() : null;
    }

    private void buildLineaVoceList(Integer wlrOfferId, Pricing.DatiVoce.DettaglioVoce dettaglioVoce) {
        if (wlrOfferId != null) {
            AdditionalNumbersDAOImpl additionalNumbersDAO = new AdditionalNumbersDAOImpl();
            LineNumbersDAOImpl lineNumbersDAO = new LineNumbersDAOImpl();
            CarriersDAOImpl carriersDAO = new CarriersDAOImpl();
            NetworksDAOImpl networksDAO = new NetworksDAOImpl();
            List<WlrOfferDetails> offerDetailsList = new WlrOfferDetailsDAOImpl().getWlrOfferDetailsByWlrOfferId(wlrOfferId, true);
            List<Pricing.DatiVoce.DettaglioVoce.LineaVoce> lineaVoceList = dettaglioVoce.getLineaVoce();
            for (WlrOfferDetails wlrOfferDetails : offerDetailsList) {
                if (!wlrOfferDetails.getRete().equals(Constants.CPS)) {
                    Pricing.DatiVoce.DettaglioVoce.LineaVoce lineaVoce = new Pricing.DatiVoce.DettaglioVoce.LineaVoce();
//                lineaVoce.setCli();
                    lineaVoce.setCostoLineaGiaCliente(new BigInteger("0"));
//                lineaVoce.setGiaCliente();
                    lineaVoce.setNumeroAggiuntivi(new BigInteger(String.valueOf(additionalNumbersDAO.getAdditionalNumbersById(wlrOfferDetails.getServiceAddictionNumber()).getValueAdditionalNumber())));
                    lineaVoce.setNumeroLinee(new BigInteger(String.valueOf(lineNumbersDAO.getLineNumbersById(wlrOfferDetails.getNumLines()).getLineNumberDesc())));
                    lineaVoce.setOperatore(carriersDAO.getCarrierById(wlrOfferDetails.getCarrierId()).getCode());
                    LineTypes lineTypes = LineTypeDAOImpl.getLineTypeById(wlrOfferDetails.getLineId());
                    lineaVoce.setTipoLinea(lineTypes.getCode() != null ? lineTypes.getCode() : Constants.VOIP);
                    Networks networks = networksDAO.getNetworkById(wlrOfferDetails.getNetworkId());
                    lineaVoce.setTipoRete(networks.getNetworkDesc() != null ? networks.getNetworkDesc() : Constants.VOIP);
                    List<Pricing.DatiVoce.DettaglioVoce.LineaVoce.OpzioneLinea> opzioneLineaList = lineaVoce.getOpzioneLinea();
                    Pricing.DatiVoce.DettaglioVoce.LineaVoce.OpzioneLinea opzioneLinea;
                    List<Services> servicesList = ServicesDAOImpl.getServicesByIdRange(wlrOfferDetails.getServicesId());
                    for (Services services : servicesList) {
                        opzioneLinea = new Pricing.DatiVoce.DettaglioVoce.LineaVoce.OpzioneLinea();
                        opzioneLinea.setCodiceOpzione(services.getCode());
                        opzioneLineaList.add(opzioneLinea);
                    }
                    lineaVoceList.add(lineaVoce);
                }
            }
        }
    }

    public static int getIdCampagnaAttivazione(int offerType, boolean isAlreadyClient) {
        int id;
        if (offerType == Constants.BUSINESS_CONFIGURATOR) {
            id = isAlreadyClient ? ID_CAMPAGNA_VOCE_SF_ALREADY_CLIENT : ID_CAMPAGNA_VOCE_SF_REGULAR;
        } else {
            id = isAlreadyClient ? ID_CAMPAGNA_VOCE_SF_CONSUMER_ALREADY_CLIENT : ID_CAMPAGNA_VOCE_SF_CONSUMER_REGULAR;
        }
        return id;
    }

    public static BigInteger getIdCampagna(int offerType, boolean isAlreadyClient) {
        BigInteger id;
        if (offerType == Constants.BUSINESS_CONFIGURATOR) {
            id = isAlreadyClient ? ID_CAMPAGNA_VOCE_ALREADY_CLIENT : ID_CAMPAGNA_VOCE_REGULAR;
        } else {
            id = isAlreadyClient ? ID_CAMPAGNA_VOCE_CONSUMER_ALREADY_CLIENT : ID_CAMPAGNA_VOCE_CONSUMER_REGULAR;
        }
        return id;
    }
}
