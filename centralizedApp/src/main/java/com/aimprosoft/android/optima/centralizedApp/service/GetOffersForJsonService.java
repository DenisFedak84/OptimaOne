package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.AssicurazioneOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDeviceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.AssicurazioneOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferDevice;
import com.aimprosoft.android.optima.centralizedApp.util.OffersUtils;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.AbstractJarUtil;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.CentralizedCostUtil;
import com.optima.pricing.domain.Pricing;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GetOffersForJsonService extends AbstractService<Offer, Map<String, Object>> {

    private BaseActivity activity;
    public static final String ACTIVATION_AMOUNT = "activation_amount";
    public static final String MONTHLY_AMOUNT = "monthly_amount";
    public static final String SERVICES = "services";
    //    public static final String DETAILS = "details";
    public static final String SERVICE_ID = "service_id";
    public static final String BRANCH = "branch";
    public static final String REMOTE_SERVICE_STATUS = "remote_service_status";
    public static final String SUM_ATTIVA = "sum_attiva";
    public final static String COUNT_MESE = "count_mese";
    public final static String COUNT_POD = "count_pod";
    public static final String SUBSCRIBER = "subscriber";
    public static final String YEARLY_UNITS = "yearly_units";
    public static final String PERCENTAGE = "percentage";
    public static final String LINES_NUMBER = "linesNumber";
    public static final String CARRIER_ID = "carrier_id";
    public static final String CARRIER_DESC = "carrier_desc";
    public static final String NETWORK_ID = "network_id";
    public static final String NETWORK_DESC = "network_desc";
    public static final String VOCE_SERVICES = "voce_services";
    public static final String VOCE_SERVICE_ID = "voce_service_id";
    public static final String VOCE_SERVICE_DESC = "voce_service_desc";
    public static final String VOCE_SERVICE_COST = "voce_service_cost";
    public static final String ADSL_SERVICES = "adsl_services";
    public static final String ADSL_SERVICE_ID = "adsl_service_id";
    public static final String ADSL_SERVICE_DESC = "adsl_service_desc";
    public static final String ADSL_SERVICE_COST = "adsl_service_cost";
    public static final String NUMERAZIONE_AGGIUNTIVA = "numerazione_aggiuntiva";
    public static final String LINE_TYPE = "line_type";
    public static final String LINE_DESC = "line_desc";
    public static final String LINE_COST = "line_cost";
    public static final String YEARLY_SMC = "yearly_smc";
    public static final String INTERVAL_YEARLY_SMC = "interval_yearly_smc";
    public static final String YEARLY_CONSUMPTION = "yearly_consumption";
    public static final String FISCAL_CLASS = "fiscal_class";
    public static final String ROUTER = "Router";
    public static final String PERIODO_DA = "periodoDa";
    public static final String PERIODO_A = "periodoA";
    public static final String USER_TYPE = "user_type";
    public static final String DAY_CLASS = "day_class";
    public static final String GAS_INTEVALS = "gas_intevals";
    public static final String ENERGY_METER = "energy_meter";
    public static final String TIPO_CONTATORE = "tipo_contatore";
    public static final String TENSIONE = "tensione";
    public static final String ANNUO_KWH = "annuo_kwh";
    public static final String ANNUO_KWH_2 = "annuo_kwh_2";
    public static final String ANNUO_KWH_3 = "annuo_kwh_3";
    public static final String INTERVAL_KWH = "interval_kwh";
    public static final String INTERVAL_KWH_2 = "interval_kwh_2";
    public static final String INTERVAL_KWH_3 = "interval_kwh_3";
    public static final String ENERGY_INTERVALS = "energy_intervals";
    public static final String APP_VERSION = "app_version";
    public static final String APP_NAME = "app_name";
    public static final String COST_VERSION = "cost_version";
    public static final String BUNDLE_COST_VERSION = "bundle_cost_version";
    public static final String SERVICES_COST_VERSION = "services_cost_version";
    public static final String TIPO_MISURATORE = "tipo_misuratore";
    public static final String POTENZA_E_OPZIONE_TARIFFARIA = "potenza_e_opzione_tariffaria";
    //    public static final String ADDITIONAL_NUMBER_COST_VERSION = "additional_number_cost_version";
    //    public static final String EXISTING_CLIENT_COST_VERSION = "existing_client_cost_version";
    //    public static final String TIPO_USO = "tipo_uso";
    //    public static final String DATA_VERSIONE_TARIFFA = "data_versione_tariffa";
    //    public static final String TIPO_TARIFFA = "tipo_tariffa";
    //    public static final String TIPO_USO_E_TIPO_PDR = "tipo_uso_e_tipo_pdr";
    public static final String CATEGORIA_DI_UTILIZZO = "categoria_di_utilizzo";
    public static final String CLASSE_PRELIEVO = "tipo_uso_e_tipo_pdr";
    public static final String REGIME_FISCALE = "regime_fiscale";
    public static final String INPUT_DATA = "input_data";
    public static final String COF_OFFERTA = "cof_offerta";
    public static final String CPS = "cps";
    public static final String WLR = "wlr";
    public static final String VOIP = "voip";
    public static final String LINEE_COUNT = "linee_count";
    public static final String CPS_LINE_NUMBER = "cps_line_number";
    public static final String VOCE_LINEE_COUNT = "voce_linee_count";
    public static final String VOIP_LINEE_COUNT = "voip_linee_count";
    public static final String LOCAL_YEARLY_UNITS = "local_yearly_units";
    public static final String MOBILE_YEARLY_UNITS = "mobile_yearly_units";
    public static final String ENERGY_POD_COUNT = "energy_pod_count";
    public static final String GAS_POD_COUNT = "gas_pod_count";
    public static final String ADSL_POD_COUNT = "adsl_pod_count";
    public static final String FIBRA = "fibra";
    public static final String TARGA = "targa";
    public static final String COSTO = "costo";
    public static final String DATA_INIZIO_POLIZZA = "data_inizio_polizza";
    public static final String SIM_COUNT = "sim_count";

    public GetOffersForJsonService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
        this.activity = (BaseActivity) activity;
    }

    @Override
    protected Map<String, Object> doStuff(Offer... offers) {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            Map<String, Object> jsonMap = new HashMap<>();
            Offer offer = offers[0];
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.UK);
            decimalFormatSymbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#.##", decimalFormatSymbols);

            jsonMap.put(ACTIVATION_AMOUNT, AbstractJarUtil.getFormattedActivationCost(offer));
            jsonMap.put(APP_VERSION, activity.getString(R.string.app_version));
            jsonMap.put(APP_NAME, activity.getString(R.string.app_name));
            jsonMap.put(MONTHLY_AMOUNT, decimalFormat.format(offer.getOfferCost()));
            resultMap.put(COF_OFFERTA, jsonMap);
            Pricing pricing = new CentralizedCostUtil().buildPricing(offer);
            resultMap.put(INPUT_DATA, buildJarInputParams(offer, pricing));
            List<Map<String, Object>> serviceList = buildServicesJson(offer, pricing);
            jsonMap.put(SERVICES, serviceList);
            return resultMap;
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.toString());
        }
        return new HashMap<>();
    }

    private Map<String, Object> buildJarInputParams(Offer offer, Pricing pricing) {
        return new BuildJarInputParamsJsonUtil().getJsonMap(pricing, offer);
    }

    private List<Map<String, Object>> buildServicesJson(Offer offer, Pricing pricing) {
        OffersUtils offersUtils = new OffersUtils(offer);
        List<Map<String, Object>> data = new ArrayList<>();
        if (offer.getGasOfferId() != null) {
            data.addAll(offersUtils.processGasOffer());
        }
        if (offer.getEnergyOfferId() != null) {
            data.addAll(offersUtils.processEnergyOffer());
        }
        if (offer.getInternetOfferId() != null) {
            data.addAll(offersUtils.processInternetOffer());
        }
        if (offer.getTlcOfferId() != null || offer.getWlrOfferId() != null)
            data.addAll(offersUtils.processVoceOffer(pricing));
        if(offer.getMobileOfferId()!=null){
            data.addAll(offersUtils.processMobileOffer());
        }

        AssicurazioneOfferDAOImpl assicurazioneOfferDAO = new AssicurazioneOfferDAOImpl();
        List<AssicurazioneOffer> assicurazioneOffers = assicurazioneOfferDAO.getAssicurazioneByOfferId(offer.getOfferId());
            if (!assicurazioneOffers.isEmpty()) {
                data.addAll(offersUtils.processAssicurazione(assicurazioneOffers));
        }

        addDeviceService(offer, data, offersUtils);
        return data;
    }

    private void addDeviceService(Offer offer, List<Map<String, Object>> data, OffersUtils offersUtils) {
        List<OfferDevice> offerDeviceList = new OfferDeviceDAOImpl().getDevicesByOfferId(offer.getOfferId());
        if (!offerDeviceList.isEmpty()) {
            data.addAll(offersUtils.processDeviceOffer(offerDeviceList));
        }
    }
}
