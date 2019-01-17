package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "InternetDetailOffers")
public class InternetDetailOffers extends BaseEntity {

    public static final String INTERNET_DETAIL_OFFER_ID = "internetDetailOfferId";
    public static final String INTERNET_OFFER_ID = "internetOfferId";
    public static final String IS_EXISTING_CLIENT_OFFER = "isExistingClientOffer";
    public static final String ADSL = "adsl";
    public static final String BUNDLE_ID = "bundleId";
    public static final String ROUTER = "router";
    public static final String SERVICES_ID = "serviceId";
    public static final String COST = "cost";
    public static final String COSTIVA = "costIVA";
    public static final String BUNDLE_COST_VERSION = "bundleCostVersion";
    public static final String SERVICE_COST_VERSION = "serviceCostVersion";
    public static final String PERCENTAGE = "percentage";

    public InternetDetailOffers() {
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = INTERNET_DETAIL_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int internetDetailOfferId;
    @DatabaseField(columnName = INTERNET_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int internetOfferId;
    @DatabaseField(columnName = IS_EXISTING_CLIENT_OFFER, canBeNull = false, dataType = DataType.BOOLEAN, defaultValue = "false")
    private boolean isExistingClientOffer;
    @DatabaseField(columnName = BUNDLE_ID, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int bundleId;
    @DatabaseField(columnName = ADSL, canBeNull = false, dataType = DataType.STRING, defaultValue = " ")
    private String adsl;
    @DatabaseField(columnName = ROUTER, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int router;
    @DatabaseField(columnName = SERVICES_ID, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String serviceId;
    @DatabaseField(columnName = COST, canBeNull = true, dataType = DataType.DOUBLE, defaultValue = "0")
    private double cost;
    @DatabaseField (columnName = COSTIVA, canBeNull = true, dataType = DataType.DOUBLE, defaultValue = "0")
    private double costIVA;
    @DatabaseField(columnName = SERVICE_COST_VERSION, canBeNull = false, dataType = DataType.STRING, defaultValue = "0")
    private String serviceCostVersion;
    @DatabaseField(columnName = BUNDLE_COST_VERSION, canBeNull = false, dataType = DataType.STRING, defaultValue = "0")
    private String bundleCostVersion;
    @DatabaseField(columnName = PERCENTAGE, canBeNull = true, dataType = DataType.FLOAT, defaultValue = "0")
    private float percentage;

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public String getServiceCostVersion() {
        return serviceCostVersion;
    }

    public void setServiceCostVersion(String serviceCostVersion) {
        this.serviceCostVersion = serviceCostVersion;
    }

    public String getBundleCostVersion() {
        return bundleCostVersion;
    }

    public void setBundleCostVersion(String bundleCostVersion) {
        this.bundleCostVersion = bundleCostVersion;
    }

    public String getAdsl() {
        return adsl;
    }

    public void setAdsl(String adsl) {
        this.adsl = adsl;
    }

    public boolean isExistingClientOffer() {
        return isExistingClientOffer;
    }

    public void setExistingClientOffer(boolean existingClientOffer) {
        isExistingClientOffer = existingClientOffer;
    }

    public static String getServicesId() {
        return SERVICES_ID;
    }

    public static String getCostiva() {
        return COSTIVA;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getInternetDetailOfferId() {
        return internetDetailOfferId;
    }

    public void setInternetDetailOfferId(int internetDetailOfferId) {
        this.internetDetailOfferId = internetDetailOfferId;
    }

    public int getInternetOfferId() {
        return internetOfferId;
    }

    public void setInternetOfferId(int internetOfferId) {
        this.internetOfferId = internetOfferId;
    }

    public int getBundleId() {
        return bundleId;
    }

    public void setBundleId(int bundleId) {
        this.bundleId = bundleId;
    }

    public int getRouter() {
        return router;
    }

    public void setRouter(int router) {
        this.router = router;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCostIVA() {
        return costIVA;
    }

    public void setCostIVA(double costIVA) {
        this.costIVA = costIVA;
    }
}
