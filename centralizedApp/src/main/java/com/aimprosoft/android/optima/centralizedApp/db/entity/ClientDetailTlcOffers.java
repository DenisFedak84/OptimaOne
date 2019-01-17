package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName ="ClientDetailTlcOffers")
public class ClientDetailTlcOffers extends BaseEntity {

    public static final String CLIENT_DETAIL_TLC_OFFERS_ID = "clientDetailTlcOffersId";
    public static final String CLIENT_TLC_OFFERS_ID = "clientTlcOfferId";
    public static final String LINE = "line";
    public static final String LOCAL_MINUTES = "localMinutes";
    public static final String MOBILE_MINUTES = "mobileMinutes";
    public static final String LOCAL_OFFER_COST = "localOfferCost";
    public static final String MOBILE_OFFER_COST = "mobileOfferCost";
    public static final String LOCAL_OFFER_COST_IVA = "localOfferCostIva";
    public static final String MOBILE_OFFER_COST_IVA = "mobileOfferCostIva";
    public static final String VERSION = "version";
    public static final String DISPLAY = "display";
    public static final String DATE_REF = "dateRef";
    public static final String RETE = "rete";
    public static final String TIPOLOGIA_LINEA = "tipologiaLinea";
    public static final String NUM_AGGIUNTIVI = "numAggiuntivi";
    public static final String FLAG_PRINCIPALE = "flagPrincipale";

    public ClientDetailTlcOffers() {
    }

    public ClientDetailTlcOffers(int clientDetailTlcOffersId, int clientTlcOffersId, String line,
                                 int localMinutes, int mobileMinutes, double localOfferCost,
                                 double mobileOfferCost, double localOfferCostIva,
                                 double mobileOfferCostIva, String version, String display,
                                 String dateRef, String rete, String tipologiaLinea,
                                 int numAggiuntivi, int flagPrincipale) {
        this.clientDetailTlcOffersId = clientDetailTlcOffersId;
        this.clientTlcOffersId = clientTlcOffersId;
        this.line = line;
        this.localMinutes = localMinutes;
        this.mobileMinutes = mobileMinutes;
        this.localOfferCost = localOfferCost;
        this.mobileOfferCost = mobileOfferCost;
        this.localOfferCostIva = localOfferCostIva;
        this.mobileOfferCostIva = mobileOfferCostIva;
        this.version = version;
        this.display = display;
        this.dateRef = dateRef;
        this.rete = rete;
        this.tipologiaLinea = tipologiaLinea;
        this.numAggiuntivi = numAggiuntivi;
        this.flagPrincipale = flagPrincipale;
    }

    @DatabaseField(id=true, columnName = CLIENT_DETAIL_TLC_OFFERS_ID, canBeNull = false, dataType =  DataType.INTEGER)
    private int clientDetailTlcOffersId;
    @DatabaseField(columnName = CLIENT_TLC_OFFERS_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int clientTlcOffersId;
    @DatabaseField(columnName = LINE, canBeNull = false, dataType = DataType.STRING)
    private String line;
    @DatabaseField(columnName = LOCAL_MINUTES, canBeNull = false, dataType = DataType.INTEGER)
    private int localMinutes;
    @DatabaseField(columnName = MOBILE_MINUTES, canBeNull = false, dataType = DataType.INTEGER)
    private int mobileMinutes;
    @DatabaseField(columnName = LOCAL_OFFER_COST, canBeNull = false,dataType = DataType.DOUBLE)
    private double localOfferCost;
    @DatabaseField(columnName = MOBILE_OFFER_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double mobileOfferCost;
    @DatabaseField(columnName = LOCAL_OFFER_COST_IVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double localOfferCostIva;
    @DatabaseField(columnName = MOBILE_OFFER_COST_IVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double mobileOfferCostIva;
    @DatabaseField(columnName = VERSION, canBeNull = false, dataType = DataType.STRING)
    private String version;
    @DatabaseField(columnName = DISPLAY, canBeNull = false, dataType = DataType.STRING)
    private String display;
    @DatabaseField(columnName = DATE_REF, canBeNull = false, dataType = DataType.STRING)
    private String dateRef;
    @DatabaseField(columnName = RETE, canBeNull = false, dataType = DataType.STRING)
    private String rete;
    @DatabaseField(columnName = TIPOLOGIA_LINEA, canBeNull = false, dataType = DataType.STRING)
    private String tipologiaLinea;
    @DatabaseField(columnName = NUM_AGGIUNTIVI, canBeNull = false, dataType = DataType.INTEGER)
    private int numAggiuntivi;
    @DatabaseField(columnName = FLAG_PRINCIPALE, canBeNull = false, dataType = DataType.INTEGER)
    private int flagPrincipale;

    public String getDateRef() {
        return dateRef;
    }

    public void setDateRef(String dateRef) {
        this.dateRef = dateRef;
    }

    public String getRete() {
        return rete;
    }

    public void setRete(String rete) {
        this.rete = rete;
    }

    public String getTipologiaLinea() {
        return tipologiaLinea;
    }

    public void setTipologiaLinea(String tipologiaLinea) {
        this.tipologiaLinea = tipologiaLinea;
    }

    public int getNumAggiuntivi() {
        return numAggiuntivi;
    }

    public void setNumAggiuntivi(int numAggiuntivi) {
        this.numAggiuntivi = numAggiuntivi;
    }

    public int getFlagPrincipale() {
        return flagPrincipale;
    }

    public void setFlagPrincipale(int flagPrincipale) {
        this.flagPrincipale = flagPrincipale;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public int getClientDetailTlcOffersId() {
        return clientDetailTlcOffersId;
    }

    public void setClientDetailTlcOffersId(int clientDetailTlcOffersId) {
        this.clientDetailTlcOffersId = clientDetailTlcOffersId;
    }

    public int getClientTlcOffersId() {
        return clientTlcOffersId;
    }

    public void setClientTlcOffersId(int clientTlcOffersId) {
        this.clientTlcOffersId = clientTlcOffersId;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getLocalMinutes() {
        return localMinutes;
    }

    public void setLocalMinutes(int localMinutes) {
        this.localMinutes = localMinutes;
    }

    public int getMobileMinutes() {
        return mobileMinutes;
    }

    public void setMobileMinutes(int mobileMinutes) {
        this.mobileMinutes = mobileMinutes;
    }

    public double getLocalOfferCost() {
        return localOfferCost;
    }

    public void setLocalOfferCost(double localOfferCost) {
        this.localOfferCost = localOfferCost;
    }

    public double getMobileOfferCost() {
        return mobileOfferCost;
    }

    public void setMobileOfferCost(double mobileOfferCost) {
        this.mobileOfferCost = mobileOfferCost;
    }

    public double getLocalOfferCostIva() {
        return localOfferCostIva;
    }

    public void setLocalOfferCostIva(double localOfferCostIva) {
        this.localOfferCostIva = localOfferCostIva;
    }

    public double getMobileOfferCostIva() {
        return mobileOfferCostIva;
    }

    public void setMobileOfferCostIva(double mobileOfferCostIva) {
        this.mobileOfferCostIva = mobileOfferCostIva;
    }
}
