package com.aimprosoft.android.optima.centralizedApp.service.net;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TownDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyExitResponseModel;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Town;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.util.Json;
import com.aimprosoft.android.optima.centralizedApp.util.URLs;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.HashMap;
import java.util.Map;

public class EnergyAdditionalWebServices extends AbstractUrlConnectionService<Void, Map<String, Long>> {

    private Offer offer;
    private EnergyOfferDetails energyOfferDetails;

    public final static String CODICE_FISCALE = "codiceFiscale";
    public final static String PARTITA_IVA_UTENTE = "partitaIvaUtente";
    public final static String PARTITA_IVA_DISTRIBUTORE = "partitaIvaDistributore";
    public final static String POD = "pod";
    public final static String CODICE_PRATICA_UTENTE = "codicePraticaUtente";

    private final String PARTITA_IVA_UTENTE_VALUE = "07469040633";
    private final String PARTITA_IVA_DISTRIBUTORE_VALUE = "05779711000";

    public final static String SUM_ATTIVA = "SumAttiva";
    public final static String COUNT_MESE = "CountMese";
    public final static String COUNT_POD = "CountPod";
    public final static String OFFER = "CountPod";
    public final static String ENERGY_OFFER_DETAILS = "ENERGY_OFFER_DETAILS";

    private final String POD_PARAM = "@p_sPod=";
    private final String CODICE_FISCALE_PARAM = "@p_sCF=";
    private final String CAP_PARAM = "@p_sCAP=";
    private final String NOME_PARAM = "@p_sNome=";
    private final String COGNOME_PARAM = "@p_sCognome=";
    private final String INDIRIZZO_PARAM = "@p_sIndirizzo=";
    private final String COMUNE_PARAM = "@p_sComune=";
    private final String RAGIONE_SOCIALE = "@p_sRagSociale=";
    private final String SEMICOLON = ";";

    private final int CORRECT = 1;
    private final int WRONG = 0;
    private final String ESITO = "Esito";

    public EnergyAdditionalWebServices(Activity activity, AbstractService.OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    public EnergyAdditionalWebServices(OnTaskCompleted onTaskCompleted) {
        super(onTaskCompleted);
    }

    @Override
    protected Map<String, Long> doStuff(Void... voids) {
        Map<String, Long> resultMap = new HashMap<>();

        if (offer != null && energyOfferDetails != null) {
            Context context = MyApplication.getContext();
            boolean isTestVersion = context.getResources().getBoolean(R.bool.is_test_version);
            String url = context.getString(isTestVersion ?
                    R.string.energy_exist_webservice_url_preprod : R.string.energy_exist_webservice_url_prod);
            try {
                JSONObject data = buildJsonData();
                String result;
//                if (isTestVersion) {
                result = callHttpUrlConnectionPostForResult(url, data.toString());
//                } else {
//                    result = callHttpsUrlConnectionPostForResult(url, data.toString(), new SocketFactoryHelper().buildSslSocketFactory(MyApplication.getContext(), Constants.CERTIFICATE_NAME));
//                }
                processResponse(result);
            } catch (Exception e) {
                Log.e(this.getClass().getName(), "error duaring executing service", e);
            }
        }
           /* Map<String, BaseEntity> entityMap = maps[0];
            Offer offer = (Offer) entityMap.get(OFFER);
            EnergyOfferDetails energyOfferDetails = (EnergyOfferDetails) entityMap.get(ENERGY_OFFER_DETAILS);
            String result = callHttpUrlConnectionGetForResult(buildUrl(offer, energyOfferDetails));
            String innerJson = new JSONDeserializer<String>().deserialize(result);
            ArrayList<HashMap<String, Long>> resultList = new JSONDeserializer<ArrayList<HashMap<String, Long>>>().deserialize(innerJson);
            resultMap = resultList.get(0);*/
        return resultMap;
    }

    private void processResponse(String result) {
        String message = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            JSONObject messaggio = XML.toJSONObject(jsonObject.getString("messaggio"));
            EnergyExitResponseModel responseModel = Json.outJSON(messaggio.getJSONObject("Prestazione").toString(), EnergyExitResponseModel.class);
            if (responseModel.getEsito() != CORRECT || responseModel.getAmmissibilita().getVerifica_amm() != CORRECT) {
                if (responseModel.getAmmissibilita().getVerifica_amm() == WRONG &&
                        TextUtils.isEmpty(responseModel.getAmmissibilita().getMotivazione())) {
                    message = responseModel.getAmmissibilita().getMotivazione();
                } else {
                    message = responseModel.getDettaglio_Esito();
                }
            }
        } catch (Exception e) {
            message = processError(jsonObject);
        }
        energyOfferDetails.setWebServiceResult(message);
    }

    private String processError(JSONObject jsonObject) {
        String message = null;
        try {
            if (jsonObject != null && jsonObject.has("dettaglioErrore")) {
                message = jsonObject.getString("dettaglioErrore");
            }
        } catch (JSONException e) {
            // ignore this error
        }
        return message;
    }

    private String getEsito(JSONObject prestazione) throws JSONException {
        String esito = null;
        if (prestazione.has(ESITO)) {
            esito = prestazione.getString(ESITO);
        }
        return esito;
    }

    private String buildUrl(Offer offer, EnergyOfferDetails energyOfferDetails) {
        StringBuilder url = new StringBuilder();
        boolean isBusinessConf = offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR;
        Town town = new TownDAOImpl().getTownById(offer.getTownId());
        url.append(isBusinessConf ? URLs.B2B_YEARLY_CONSUMPTION_SERVICE_PROD_URL : URLs.CONSUMER_YEARLY_CONSUMPTION_SERVICE_PROD_URL)
                .append(POD_PARAM).append(energyOfferDetails.getPod()).append(SEMICOLON)
                .append(CODICE_FISCALE_PARAM).append(offer.getCodiceFiscale()).append(SEMICOLON)
                .append(CAP_PARAM).append(offer.getCap()).append(SEMICOLON)
                .append(NOME_PARAM).append(offer.getNome()).append(SEMICOLON)
                .append(COGNOME_PARAM).append(offer.getSurname()).append(SEMICOLON)
                .append(INDIRIZZO_PARAM).append(offer.getIndirizzoDiFornitura()).append(SEMICOLON)
                .append(COMUNE_PARAM).append(town != null ? town.getTownDesc() : Constants.EMPTY_STRING).append(SEMICOLON)
                .append(RAGIONE_SOCIALE).append(offer.getName());
        return url.toString().replace(" ", "%20");
    }

    private JSONObject buildJsonData() {
        JSONObject data = null;
        try {
            data = new JSONObject();
            data.put(CODICE_FISCALE, offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ? offer.getPiva() : offer.getCodiceFiscale());
            data.put(PARTITA_IVA_UTENTE, PARTITA_IVA_UTENTE_VALUE);
//            data.put(PARTITA_IVA_DISTRIBUTORE, PARTITA_IVA_DISTRIBUTORE_VALUE);
            data.put(POD, energyOfferDetails.getPod());
            String codiceUtente = "ONE" + System.currentTimeMillis();
            data.put(CODICE_PRATICA_UTENTE, codiceUtente.substring(0, codiceUtente.length() - 3));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public EnergyOfferDetails getEnergyOfferDetails() {
        return energyOfferDetails;
    }

    public void setEnergyOfferDetails(EnergyOfferDetails energyOfferDetails) {
        this.energyOfferDetails = energyOfferDetails;
    }
}
