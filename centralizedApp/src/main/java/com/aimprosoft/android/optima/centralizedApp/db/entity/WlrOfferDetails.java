package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "WlrOfferDetails")
public class WlrOfferDetails extends BaseEntity {

    public static final String WLR_OFFER_DETT_ID = "wlrOfferDettId";
    public static final String WLR_OFFER_ID = "wlrOfferId";
    public static final String WLR_NAME = "wlrName";
    public static final String IS_EXISTING_CLIENT_OFFER = "isExistingClientOffer";
    public static final String OWN_LOCAL_BUNDLE_ID = "ownLocalBundleId";
    public static final String OWN_MOBILE_BUNDLE_ID = "ownMobileBundleId";
    public static final String LINE_ID = "lineId";
    public static final String CARRIER_ID = "carrierId";
    public static final String NETWORK_ID = "networkId";
    public static final String NUM_LINES = "numLines";
    public static final String IS_MODIFIED = "isModified";
    public static final String SERVICE_ADDICTION_NUMBER = "serviceAddictionNumber";
    public static final String SERVICES_ID = "servicesId";
    public static final String COST = "cost";
    public static final String COST_IVA = "costIVA";
    public static final String EXISTING_CLIENT_COST_VERSION = "existingClientCostVersion";
    public static final String SERVICE_COST_VERSION = "serviceCostVersion";
    public static final String ADDITIONAL_NUMBER_COST_VERSION = "additionalNumberCostVersion";
    public static final String RETE = "rete";
    public static final String PARENT = "parent";

    public WlrOfferDetails() {
        parent = true;
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = WLR_OFFER_DETT_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int wlrOfferDettId;

    @DatabaseField(columnName = WLR_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int wlrOfferId;

    @DatabaseField(columnName = WLR_NAME, canBeNull = false, dataType = DataType.STRING, defaultValue = " ")
    private String wlrName;

    @DatabaseField(columnName = IS_EXISTING_CLIENT_OFFER, canBeNull = false, dataType = DataType.BOOLEAN, defaultValue = "false")
    private boolean isExistingClientOffer;

    @DatabaseField(columnName = OWN_LOCAL_BUNDLE_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer ownLocalBundleId;

    @DatabaseField(columnName = OWN_MOBILE_BUNDLE_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer ownMobileBundleId;

    @DatabaseField(columnName = LINE_ID, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int lineId;

    @DatabaseField(columnName = NETWORK_ID, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int networkId;

    @DatabaseField(columnName = CARRIER_ID, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int carrierId;

    @DatabaseField(columnName = NUM_LINES, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int numLines;

    @DatabaseField(columnName = SERVICES_ID, canBeNull = true, dataType = DataType.STRING, defaultValue = "")
    private String servicesId;

    @DatabaseField(columnName = SERVICE_ADDICTION_NUMBER, canBeNull = true, dataType = DataType.INTEGER_OBJ, defaultValue = "0")
    private Integer serviceAddictionNumber;

    @DatabaseField(columnName = COST, canBeNull = true, dataType = DataType.DOUBLE, defaultValue = "0")
    private double cost;

    @DatabaseField(columnName = COST_IVA, canBeNull = true, dataType = DataType.DOUBLE, defaultValue = "0")
    private double costIVA;

    @DatabaseField(columnName = EXISTING_CLIENT_COST_VERSION, canBeNull = false, dataType = DataType.STRING, defaultValue = "0")
    private String existingClientCostVersion;

    @DatabaseField(columnName = SERVICE_COST_VERSION, canBeNull = false, dataType = DataType.STRING, defaultValue = "0")
    private String serviceCostVersion;

    @DatabaseField(columnName = ADDITIONAL_NUMBER_COST_VERSION, canBeNull = false, dataType = DataType.STRING, defaultValue = "0")
    private String additionalNumberCostVersion;

    @DatabaseField(columnName = IS_MODIFIED, canBeNull = false, dataType = DataType.BOOLEAN)
    private boolean isModified;

    @DatabaseField(columnName = RETE, canBeNull = true, dataType = DataType.STRING, defaultValue = "WLR")
    private String rete;

    @DatabaseField(columnName = PARENT, canBeNull = false, dataType = DataType.BOOLEAN, defaultValue = "true")
    private boolean parent;

    public boolean isParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public String getRete() {
        return rete;
    }

    public void setRete(String rete) {
        this.rete = rete;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean isModified) {
        this.isModified = isModified;
    }

    public String getExistingClientCostVersion() {
        return existingClientCostVersion;
    }

    public void setExistingClientCostVersion(String existingClientCostVersion) {
        this.existingClientCostVersion = existingClientCostVersion;
    }

    public String getServiceCostVersion() {
        return serviceCostVersion;
    }

    public void setServiceCostVersion(String serviceCostVersion) {
        this.serviceCostVersion = serviceCostVersion;
    }

    public String getAdditionalNumberCostVersion() {
        return additionalNumberCostVersion;
    }

    public void setAdditionalNumberCostVersion(String additionalNumberCostVersion) {
        this.additionalNumberCostVersion = additionalNumberCostVersion;
    }

    public String getWlrName() {
        return wlrName;
    }

    public void setWlrName(String wlrName) {
        this.wlrName = wlrName;
    }

    public boolean isExistingClientOffer() {
        return isExistingClientOffer;
    }

    public void setExistingClientOffer(boolean existingClientOffer) {
        isExistingClientOffer = existingClientOffer;
    }

    public Integer getOwnLocalBundleId() {
        return ownLocalBundleId;
    }

    public void setOwnLocalBundleId(Integer ownLocalBundleId) {
        this.ownLocalBundleId = ownLocalBundleId;
    }

    public Integer getOwnMobileBundleId() {
        return ownMobileBundleId;
    }

    public void setOwnMobileBundleId(Integer ownMobileBundleId) {
        this.ownMobileBundleId = ownMobileBundleId;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    public Integer getServiceAddictionNumber() {
        return serviceAddictionNumber;
    }

    public void setServiceAddictionNumber(Integer serviceAddictionNumber) {
        this.serviceAddictionNumber = serviceAddictionNumber;
    }

    public String getServicesId() {
        return servicesId;
    }

    public void setServicesId(String servicesId) {
        this.servicesId = servicesId;
    }

    public double getCostIVA() {
        return costIVA;
    }

    public void setCostIVA(double costIVA) {
        this.costIVA = costIVA;
    }

    public int getWlrOfferDettId() {
        return wlrOfferDettId;
    }

    public void setWlrOfferDettId(int wlrOfferDett) {
        this.wlrOfferDettId = wlrOfferDett;
    }

    public int getWlrOfferId() {
        return wlrOfferId;
    }

    public void setWlrOfferId(int wlrOfferId) {
        this.wlrOfferId = wlrOfferId;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public int getNumLines() {
        return numLines;
    }

    public void setNumLines(int numLines) {
        this.numLines = numLines;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
