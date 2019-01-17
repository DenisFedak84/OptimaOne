package com.aimprosoft.android.optima.centralizedApp.service.net;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.util.SDCUtils.SDCEnergyReportImpl;
import com.aimprosoft.android.optima.centralizedApp.util.SDCUtils.SDCGasReportImpl;
import com.aimprosoft.android.optima.centralizedApp.util.URLs;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flexjson.JSONSerializer;

public class SendSDCDataService extends AbstractUrlConnectionService<Map<String, Object>, Integer> {

    public static final String OFFER = "offer";
    public static final String EMAILS = "emails";
    private final String ENERGY = "energy";
    private final String GAS = "gas";
    private final String CF_PIVA = "cf_piva";
    private final String RAGSOC_COGNOME = "ragsoc_cognome";
    private final String JSON_PARAM_NAME = "?json=";

    private Exception exception;


    public SendSDCDataService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected Integer doStuff(Map... maps) {
        if (maps.length > 0 && maps[0].containsKey(OFFER) && maps[0].containsKey(EMAILS)) {
            Map map = maps[0];
            Offer offer = (Offer) map.get(OFFER);
            List<String> emails = (List<String>) map.get(EMAILS);
            boolean isTestVersion = MyApplication.getContext().getResources().getBoolean(R.bool.is_test_version);
            if (isTestVersion) {
                callHttpUrlConnectionGet(buildURL(offer, emails));
            } else {
                callHttpsUrlConnectionGet(buildURL(offer, emails), new SocketFactoryHelper().buildSslSocketFactory(MyApplication.getContext(), Constants.CERTIFICATE_NAME));
            }
        }
        return getResponseCode();
    }

    private String buildURL(Offer offer, List<String> emails) {
        Map<String, Object> paramsMap = new HashMap<>();
        StringBuilder url = new StringBuilder();
        try {
            paramsMap.put(ENERGY, new SDCEnergyReportImpl().getFullSDCDataMap(offer, emails));
            paramsMap.put(GAS, new SDCGasReportImpl().getFullSDCDataMap(offer, emails));
            paramsMap.put(CF_PIVA, offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ?
                    offer.getPiva() : offer.getCodiceFiscale());
            paramsMap.put(RAGSOC_COGNOME, offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR
                    ? URLEncoder.encode(offer.getName().trim(), "UTF-8") : URLEncoder.encode((offer.getNome() + " " + offer.getSurname()).trim(), "UTF-8"));
            String json = new JSONSerializer().deepSerialize(paramsMap);
            boolean isTestVersion = MyApplication.getContext().getResources().getBoolean(R.bool.is_test_version);
            url.append(isTestVersion ?
                    URLs.SEND_SDC_TO_WEB_SERVER_URL_TEST : URLs.SEND_SDC_JSON_TO_WEB_SERVER_URL)
                    .append(JSON_PARAM_NAME)
                    .append(isTestVersion ? URLEncoder.encode(json, "UTF-8") : json);
        } catch (UnsupportedEncodingException e) {
            Log.e(getClass().getSimpleName(), "error while encoding url", e);
            exception = e;
        }
        return url.toString();
    }

    public boolean hasError() {
        return exception != null;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
