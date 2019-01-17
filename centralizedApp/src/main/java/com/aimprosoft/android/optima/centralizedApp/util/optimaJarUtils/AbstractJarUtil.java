package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyMeterDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Bundle;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Services;
import com.aimprosoft.android.optima.centralizedApp.db.entity.SimpleBundleModel;
import com.aimprosoft.android.optima.centralizedApp.util.SDCardHelper;
import com.optima.pricing.db.DbManager;
import com.optima.pricing.domain.Pricing;
import com.optima.pricing.pricing.DB_TYPE;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractJarUtil {

    private static SQLiteDatabase sqLiteDatabase;

    public static final BigInteger ID_CAMPAGNA_ENERGIA = new BigInteger("2103");
    public static final BigInteger ID_CAMPAGNA_GAS = new BigInteger("2105");
    public static final BigInteger ID_CAMPAGNA_MOBILE = new BigInteger("1085");
    public static final BigInteger ID_CAMPAGNA_VOCE_REGULAR = new BigInteger("1055");
    //    public static final BigInteger ID_CAMPAGNA_VOCE_REGULAR = new BigInteger("1058");//Agenzie
    public static final BigInteger ID_CAMPAGNA_VOCE_ALREADY_CLIENT = new BigInteger("9");
    public static final BigInteger ID_CAMPAGNA_ADSL_REGULAR = new BigInteger("1053");
    //    public static final BigInteger ID_CAMPAGNA_ADSL_REGULAR = new BigInteger("1059");//Agenzie
    public static final BigInteger ID_CAMPAGNA_ADSL_ALREADY_CLIENT = new BigInteger("11");

    public static final int ID_CAMPAGNA_ENERGIA_SF = 2103;
    public static final int ID_CAMPAGNA_GAS_SF = 2105;
    public static final int ID_CAMPAGNA_MOBILE_SF = 1085;
    public static final int ID_CAMPAGNA_VOCE_SF_REGULAR = 1055;
    //    public static final int ID_CAMPAGNA_VOCE_SF_REGULAR = 1058;//Agenzie
    public static final int ID_CAMPAGNA_VOCE_SF_ALREADY_CLIENT = 9;
    public static final int ID_CAMPAGNA_ADSL_SF_REGULAR = 1053;
    //    public static final int ID_CAMPAGNA_ADSL_SF_REGULAR = 1059;//Agenzie
    public static final int ID_CAMPAGNA_ADSL_SF_ALREADY_CLIENT = 11;

    public static final BigInteger ID_CAMPAGNA_MOBILE_DEFAULT = new BigInteger("1061");
    public static final int ID_CAMPAGNA_MOBILE_DEFAULT_SF = 1061;

    public static final BigInteger ID_CAMPAGNA_ENERGIA_CONSUMER = new BigInteger("2102");
    public static final BigInteger ID_CAMPAGNA_GAS_CONSUMER = new BigInteger("2104");
    public static final BigInteger ID_CAMPAGNA_MOBILE_CONSUMER = new BigInteger("1086");
    public static final BigInteger ID_CAMPAGNA_VOCE_CONSUMER_REGULAR = new BigInteger("1056");
    //    public static final BigInteger ID_CAMPAGNA_VOCE_CONSUMER_REGULAR = new BigInteger("1057");//Agenzie
    public static final BigInteger ID_CAMPAGNA_VOCE_CONSUMER_ALREADY_CLIENT = new BigInteger("10");
    public static final BigInteger ID_CAMPAGNA_ADSL_CONSUMER_REGULAR = new BigInteger("1054");
    //    public static final BigInteger ID_CAMPAGNA_ADSL_CONSUMER_REGULAR = new BigInteger("1060");//Agenzie
    public static final BigInteger ID_CAMPAGNA_ADSL_CONSUMER_ALREADY_CLIENT = new BigInteger("12");

    public static final int ID_CAMPAGNA_ENERGIA_SF_CONSUMER = 2102;
    public static final int ID_CAMPAGNA_GAS_SF_CONSUMER = 2104;
    public static final int ID_CAMPAGNA_MOBILE_SF_CONSUMER = 1086;
    public static final int ID_CAMPAGNA_VOCE_SF_CONSUMER_REGULAR = 1056;
    //    public static final int ID_CAMPAGNA_VOCE_SF_CONSUMER_REGULAR = 1057;//Agenzie
    public static final int ID_CAMPAGNA_VOCE_SF_CONSUMER_ALREADY_CLIENT = 10;
    public static final int ID_CAMPAGNA_ADSL_SF_CONSUMER_REGULAR = 1054;
    //    public static final int ID_CAMPAGNA_ADSL_SF_CONSUMER_REGULAR = 1060;//Agenzie
    public static final int ID_CAMPAGNA_ADSL_SF_CONSUMER_ALREADY_CLIENT = 12;

    private final static String TARIFF_TRANSP_BT = "BT";
    private final static String TARIFF_TRANSP_MT = "MT";
    private final static String TARIFF_TRANSP_D2 = "D2";
    private final static String TARIFF_TRANSP_D3 = "D3";
    private final static String TARIFF_TRANSP_D3_NORES = "D3NORES";

    private final static String TARIFF_TRANSP_D2_DESCRIPTION = "TD Residente";
    private final static String TARIFF_TRANSP_D3_DESCRIPTION = "TD Residente (Seleziona D3 sul Contratto)";
    private final static String TARIFF_TRANSP_D3_NO_RES_DESCRIPTION = "TD NON Residente";

    public static final int COST_JAR = 11;
    public static final int ESTIMATION_JAR = 22;
    public static final int POTENZA_STIMATA_JAR = 33;

    public static final float POTENZA_FOR_6PLUS_CASE = 10;
    public static final float COSTO_GESTIONE_INTEGRATA = 3.9f;
    public static final float COSTO_GESTIONE_INTEGRATA_IVA_INCLUSA = 4.8f;

    public final static String urlLiteConfiguratore = "jdbc:sqlite:" + SDCardHelper.DATABASE_LOCATION_DIR + File.separator + "cpi2.db";
    public final static String urlLiteStima = "jdbc:sqlite:" + SDCardHelper.DATABASE_LOCATION_DIR + File.separator + "cpi2.db";
    public final static String urllitePotenza = "jdbc:sqlite:" + SDCardHelper.DATABASE_LOCATION_DIR + File.separator + "cpi2.db";

    public static String SQLITE_DRIVER = "org.sqldroid.SQLDroidDriver";
    public static DateFormat formatterStandartDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    public static DateFormat formatterFullDateWithSlash = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    public static DateFormat formatterFullDateWithSlashAndTimeDetails = new SimpleDateFormat("yyyy-MM-dd 00:00:00", Locale.US);
    public static Calendar calendar = Calendar.getInstance();

    private static EnergyMeterDAOImpl energyMeterDAO = new EnergyMeterDAOImpl();

    abstract void buildPricing(Pricing pricing, Offer offer);

    public String getDateInizio() {
        calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, +1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return formatterFullDateWithSlash.format(calendar.getTime());
    }

    public String getDateFine() {
        calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, +1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return formatterFullDateWithSlash.format(calendar.getTime());
    }

    public String getDataStipula() {
        calendar = Calendar.getInstance();
        return formatterFullDateWithSlash.format(calendar.getTime());
    }

    public static int getMonth(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static String getMonth(String date) {
        return date.substring(date.length() - 2, date.length());
    }

    public static Map<String, Object> getConfigMap(int jarType) {
        Map<String, Object> configurationMapLite = new HashMap<String, Object>();
        configurationMapLite.put(DbManager.DBTYPE, getDbType(jarType));
        configurationMapLite.put(DbManager.URL, getDbUrl(jarType));
        configurationMapLite.put(DbManager.DRIVER, AbstractJarUtil.SQLITE_DRIVER);
        return configurationMapLite;
    }

    private static Enum getDbType(int jarType) {
        Enum result = null;
        switch (jarType) {
            case COST_JAR:
                result = DB_TYPE.SQLLITE;
                break;
            case ESTIMATION_JAR:
                result = com.optima.consumi.DB_TYPE.SQLLITE;
                break;
            case POTENZA_STIMATA_JAR:
                result = com.optima.potenza.DB_TYPE.SQLLITE;
                break;
        }
        return result;
    }

    private static String getDbUrl(int jarType) {
        String result = null;
        switch (jarType) {
            case COST_JAR:
                result = urlLiteConfiguratore;
                break;
            case ESTIMATION_JAR:
                result = urlLiteStima;
                break;
            case POTENZA_STIMATA_JAR:
                result = urllitePotenza;
                break;
        }
        return result;
    }

    public static int defineLineaSoloDati(List<Services> lineaSoloDatiList, Bundle adslBundle) {
        return adslBundle.getCode().equals(Constants.FIBRA_CODE) ?
                lineaSoloDatiList.get(1).getServiceId() :
                lineaSoloDatiList.get(2).getServiceId();
    }

    public static float getPotenzaValue(Offer offer, EnergyOfferDetails energyOfferDetails) {
        return energyOfferDetails.isExistingClientOffer() ?
                energyOfferDetails.getPotenzaReal() :
                getFormattedPotenza(getNewClientPotenza(offer, energyOfferDetails));
    }

    private static String getNewClientPotenza(Offer offer, EnergyOfferDetails energyOfferDetails) {
        return String.valueOf((energyOfferDetails.getTensione() != 1 && offer.getConfiguratorType() != Constants.CONSUMER_CONFIGURATOR)
                ? energyOfferDetails.getPotenzaStimata()
                : energyMeterDAO.getEnergyMeterDescById(energyOfferDetails.isQuestionarioUsing()
                ? energyOfferDetails.getEnergyMeter2() : energyOfferDetails.getEnergyMeter()));
    }

    private static Float getFormattedPotenza(String energyMeterDesc) {
        Float resultPotenza;
        if (energyMeterDesc.equals(Constants.SPECIAL_POTENZA_VALUE)) {
            resultPotenza = POTENZA_FOR_6PLUS_CASE;
        } else {
            resultPotenza = parseFloatPotenza(energyMeterDesc);
        }
        Log.v("asd", "ZZZZZZZZZ POTENZA " + resultPotenza + "       " + energyMeterDesc);
        return resultPotenza;
    }

    private static float parseFloatPotenza(String potenza) {
        float resultPotenza;
        try {
            resultPotenza = Float.valueOf((potenza.contains("KW") ? potenza
                    .replaceAll(" KW", "").split(" - ")[1] : potenza)
                    .replaceAll(",", ".")
                    .replaceAll("\\+", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            resultPotenza = 0f;
        }
        return resultPotenza;
    }

    public static String getTariffTransporto(Offer offer, EnergyOfferDetails energyOfferDetails) {
        String coeffTransporto;
        if (energyOfferDetails.getTensione() != 0 || energyOfferDetails.isQuestionarioUsing()) {
            coeffTransporto = offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ?
                    energyOfferDetails.getTensione() != 3 ?
                            getSpecificAlgoritmPotenza(offer, energyOfferDetails) : getMTType(energyOfferDetails) :
                    getConsumerTensione(offer, energyOfferDetails);
        } else {
            coeffTransporto = "";
        }
        return coeffTransporto;
    }

    public static String getTariffTransportoDescription(Offer offer, EnergyOfferDetails energyOfferDetails) {
        String rawTariffTransporto = getTariffTransporto(offer, energyOfferDetails);
        String tariffTransportoDesc;
        switch (rawTariffTransporto) {
            case TARIFF_TRANSP_D2:
                tariffTransportoDesc = TARIFF_TRANSP_D2_DESCRIPTION;
                break;
            case TARIFF_TRANSP_D3:
                tariffTransportoDesc = TARIFF_TRANSP_D2_DESCRIPTION;
                break;
            case TARIFF_TRANSP_D3_NORES:
                tariffTransportoDesc = TARIFF_TRANSP_D3_NO_RES_DESCRIPTION;
                break;
            default:
                tariffTransportoDesc = rawTariffTransporto;
        }
        return tariffTransportoDesc;
    }


    private static String getSpecificAlgoritmPotenza(Offer offer, EnergyOfferDetails energyOfferDetails) {
        return energyOfferDetails.getTensione() != 2 ? getBTATariffTransp(getPotenzaValue(offer, energyOfferDetails)) : Constants.BTA6_STRING;
    }

    private static String getConsumerTensione(Offer offer, EnergyOfferDetails energyOfferDetails) {
        String tensione;
        if (energyOfferDetails.getTipologiaContratto() == Constants.DOMESTICO) {
            if (offer.getClientTypeId() == Constants.RESIDENZIALE) {
                tensione = getPotenzaValue(offer, energyOfferDetails) <= 3 ? TARIFF_TRANSP_D2 :
                        TARIFF_TRANSP_D3;
            } else {
                tensione = TARIFF_TRANSP_D3_NORES;
            }
        } else {
            tensione = new EnergyMeterDAOImpl().getEnergiMeterById(energyOfferDetails.getEnergyMeter()).getTariffOption();
        }
        return tensione;
    }

    private static String getMTType(EnergyOfferDetails energyOfferDetails) {
        return "MTA" + (energyOfferDetails.getTariffaId() - 1);
    }

    private static String getBTATariffTransp(float potenza) {
        Cursor cursor = executeConfiguratoreEngineCursor(MyApplication.getContext().getString(R.string.configuratore_engine_get_bta_tarifftransporto_code, potenza));
        String BTATariffTransp;
        if (cursor != null) {
            cursor.moveToFirst();
            BTATariffTransp = cursor.getString(0);
        } else {
            BTATariffTransp = Constants.EMPTY_STRING;
        }
        closeDbConnection();
        return BTATariffTransp;
    }

    public static String getTariffTranspId(String tariffOptions) {
        Cursor cursor = executeConfiguratoreEngineCursor(MyApplication.getContext().getString(R.string.configuratore_engine_get_bta_tarifftransporto_id, tariffOptions));
        String tariffTransporto;
        if (cursor != null) {
            cursor.moveToFirst();
            tariffTransporto = cursor.getString(0);
        } else {
            tariffTransporto = Constants.EMPTY_STRING;
        }
        closeDbConnection();
        return tariffTransporto;
    }


    public static SimpleBundleModel getBundleInfoFromJarDB(Integer bundleId, int idCampagnaAttivazione, int tipoBundle) {
        SimpleBundleModel simpleBundleModel = null;
        if (bundleId != null) {
            String bundleDesc = new BundleDAOImpl().getBundleDescByBundleId(bundleId);
            Cursor cursor = executeConfiguratoreEngineCursor(MyApplication.getContext()
                    .getString(R.string.configuratore_engine_get_bundle_by_code, bundleDesc, idCampagnaAttivazione, tipoBundle));
            if (cursor != null) {
                cursor.moveToFirst();
                simpleBundleModel = new SimpleBundleModel(cursor.getString(1), cursor.getDouble(3), cursor.getDouble(4));
            }
            closeDbConnection();
        }
        return simpleBundleModel;
    }


    private static Cursor executeConfiguratoreEngineCursor(String query) {
        Cursor cursor = null;
        try {
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                sqLiteDatabase = SQLiteDatabase.openDatabase(SDCardHelper.DATABASE_LOCATION_DIR + File.separator + SDCardHelper.CPI2_DB_NAME, null, 1);
            }
            cursor = sqLiteDatabase.rawQuery(query, null);
        } catch (Exception e) {
            Log.e("AbstractJarUtil", "couldn't open database", e);
        }
        return cursor;
    }

    private static void closeDbConnection() {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
            sqLiteDatabase = null;
        }
    }

    public static String changeSDCDateFormat(String date, DateFormat formatFrom, DateFormat formatTo) {
        String resultDate;
        try {
            Date currentDate = formatFrom.parse(date);
            resultDate = formatTo.format(currentDate);
        } catch (ParseException e) {
            Log.e("AbstractJarUtil", "can't parse date", e);
            resultDate = "";
        }
        return resultDate;
    }

    public static boolean checkForSpecialPotenza(Offer offer, EnergyOfferDetails energyOfferDetails) {
        return offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR && (new EnergyMeterDAOImpl().getEnergyMeterDescById(energyOfferDetails.isQuestionarioUsing()
                ? energyOfferDetails.getEnergyMeter2() : energyOfferDetails.getEnergyMeter())).equals(Constants.SPECIAL_POTENZA_VALUE);
    }

    public static double getFormattedActivationCost(Offer offer) {
        double activationCost = 0.0;
        if (offer.getOfferCostExtra() != null) {
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.UK);
            decimalFormatSymbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat(offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR ?
                    "#.0" : "#.00", decimalFormatSymbols);
            decimalFormat.setRoundingMode(RoundingMode.FLOOR);
            if (offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR) {
                activationCost = Double.valueOf(decimalFormat.format(offer.getOfferCostExtra().doubleValue() * 4 / 1.22));
            } else {
                activationCost = Double.valueOf(decimalFormat.format(offer.getOfferCostExtra().multiply(new BigDecimal(4))));
            }
        }
        return activationCost;
    }

    public static int getMobileIdCampagna(Offer offer) {
        int idCampagna;
        if (offer != null) {
            idCampagna = offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR
                    ? ID_CAMPAGNA_MOBILE_SF : ID_CAMPAGNA_MOBILE_SF_CONSUMER;
        } else {
            idCampagna = 0;
        }
        return idCampagna;
    }
}