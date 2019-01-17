package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "WlrOffers")
public class WlrOffers extends BaseEntity {

    public static final String WLR_OFFER_ID = "wlrOfferId";
    //    public static final String CLIENT_TYPE = "clientType";
    public static final String LOCAL_BUNDLE_ID = "localBundleId";
    public static final String MOBILE_BUNDLE_ID = "mobileBundleId";
    public static final String BUNDLE_COST = "bundleCost";
    public static final String BUNDLE_COST_IVA = "bundleCostIva";
    public static final String OFFER_COST = "offerCost";
    public static final String OFFER_COST_IVA = "offerCostIVA";
    public static final String BUNDLE_COST_VERSION = "bundleCostVersion";
    public static final String PMC = "pmc";
    public static final String PS = "ps";
    public static final String CODICE_PMC_PS = "codicePmcPs";
    public static final String PERCENTAGE = "percentage";

    public WlrOffers() {
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = WLR_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int wlrOfferId;

//    @DatabaseField(columnName = CLIENT_TYPE, canBeNull = true, dataType = DataType.INTEGER_OBJ)
//    private Integer clientType;

    @DatabaseField(columnName = LOCAL_BUNDLE_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer localBundleId;

    @DatabaseField(columnName = MOBILE_BUNDLE_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer mobileBundleId;

    @DatabaseField(columnName = BUNDLE_COST, canBeNull = true, dataType = DataType.DOUBLE)
    private double bundleCost;

    @DatabaseField(columnName = BUNDLE_COST_IVA, canBeNull = true, dataType = DataType.DOUBLE)
    private double bundleCostIVA;

    @DatabaseField(columnName = OFFER_COST, canBeNull = true, dataType = DataType.DOUBLE, defaultValue = "0.0")
    private double offerCost;

    @DatabaseField(columnName = OFFER_COST_IVA, canBeNull = true, dataType = DataType.DOUBLE, defaultValue = "0.0")
    private double offerCostIVA;

    @DatabaseField(columnName = BUNDLE_COST_VERSION, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String bundleCostVersion;

    @DatabaseField(columnName = PMC, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String pmc;

    @DatabaseField(columnName = PS, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String ps;

    @DatabaseField(columnName = CODICE_PMC_PS, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String codicePmcPS;

    @DatabaseField(columnName = PERCENTAGE, canBeNull = true, dataType = DataType.FLOAT, defaultValue = "0")
    private float percentage;

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public String getPmc() {
        return pmc;
    }

    public void setPmc(String pmc) {
        this.pmc = pmc;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getCodicePmcPS() {
        return codicePmcPS;
    }

    public void setCodicePmcPS(String codicePmcPS) {
        this.codicePmcPS = codicePmcPS;
    }

    public String getBundleCostVersion() {
        return bundleCostVersion;
    }

    public void setBundleCostVersion(String bundleCostVersion) {
        this.bundleCostVersion = bundleCostVersion;
    }

    public int getWlrOfferId() {
        return wlrOfferId;
    }

    public void setWlrOfferId(int wlrOfferId) {
        this.wlrOfferId = wlrOfferId;
    }

    public Integer getLocalBundleId() {
        return localBundleId;
    }

    public void setLocalBundleId(Integer localBundleId) {
        this.localBundleId = localBundleId;
    }

    public Integer getMobileBundleId() {
        return mobileBundleId;
    }

    public void setMobileBundleId(Integer mobileBundleId) {
        this.mobileBundleId = mobileBundleId;
    }

    public double getBundleCost() {
        return bundleCost;
    }

    public void setBundleCost(double bundleCost) {
        this.bundleCost = bundleCost;
    }

    public double getBundleCostIVA() {
        return bundleCostIVA;
    }

    public void setBundleCostIVA(double bundleCostIVA) {
        this.bundleCostIVA = bundleCostIVA;
    }

    public double getOfferCost() {
        return offerCost;
    }

    public void setOfferCost(double offerCost) {
        this.offerCost = offerCost;
    }

    public double getOfferCostIVA() {
        return offerCostIVA;
    }

    public void setOfferCostIVA(double offerCostIVA) {
        this.offerCostIVA = offerCostIVA;
    }
}
