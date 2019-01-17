package com.aimprosoft.android.optima.centralizedApp.service.net;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TownDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.BaseEntity;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Town;
import com.aimprosoft.android.optima.centralizedApp.util.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EnergyPageExitWebService extends AbstractUrlConnectionService<Map<String, BaseEntity>, Map<String, Long>> {

    private Offer offer;
    private EnergyOfferDetails energyOfferDetails;

    public final static String CODICE_FISCALE = "codiceFiscale";
    public final static String PARTITA_IVA_UTENTE = "partitaIvaUtente";
    public final static String PARTITA_IVA_DISTRIBUTORE = "partitaIvaDistributore";
    public final static String POD = "pod";
    public final static String CODICE_PRATICA_UTENTE = "codicePraticaUtente";

    private final String PARTITA_IVA_UTENTE_VALUE = "07469040633";
    private final String PARTITA_IVA_DISTRIBUTORE_VALUE = "05779711000";

    public EnergyPageExitWebService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    public EnergyPageExitWebService(OnTaskCompleted onTaskCompleted) {
        super(onTaskCompleted);
    }

    @Override
    protected Map<String, Long> doStuff(Map<String, BaseEntity>... maps) {
        Map<String, Long> resultMap = new HashMap<>();
        if (offer != null && energyOfferDetails != null) {
                Context context = MyApplication.getContext();
                boolean isTestVersion = context.getResources().getBoolean(R.bool.is_test_version);
                String url = context.getString(isTestVersion ?
                        R.string.energy_exist_webservice_url_preprod : R.string.energy_exist_webservice_url_prod);
                String appName = context.getString(isTestVersion ? R.string.app_name_test : R.string.app_name_test);
                try {
                    JSONObject data = buildJsonData(appName);
                    String result = callHttpUrlConnectionPostForResult(url, data.toString());
                } catch (Exception e) {
                    Log.e(this.getClass().getName(), "error duaring executing service", e);
                }
        }
        return resultMap;
    }

    private JSONObject buildJsonData(String appName) {
        JSONObject data = null;
        try {
            data = new JSONObject();
            data.put(CODICE_FISCALE, offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ? offer.getPiva() : offer.getCodiceFiscale());
            data.put(PARTITA_IVA_UTENTE, PARTITA_IVA_UTENTE_VALUE);
            data.put(PARTITA_IVA_DISTRIBUTORE, PARTITA_IVA_DISTRIBUTORE_VALUE);
            data.put(POD, energyOfferDetails.getPod());
            data.put(CODICE_PRATICA_UTENTE, appName + System.currentTimeMillis());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

}
