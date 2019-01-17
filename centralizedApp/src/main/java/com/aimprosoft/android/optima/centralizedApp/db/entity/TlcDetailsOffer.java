package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TlcDetailsOffer")
public class TlcDetailsOffer extends BaseEntity {

    public static final String TLC_OFFER_DETAILS_ID = "tlcOfferDetailsId";
    public static final String TLC_OFFER_ID = "tlcOfferId";
    public static final String TLC_NAME = "tlcName";
    public static final String IS_EXISTING_CLIENT_OFFER = "isExistingClientOffer";
    public static final String IS_MODIFIED = "isModified";
    public static final String NUM_LINES = "numLines";
    public static final String RETE = "rete";
    public static final String MOBILE_BUNDLE_ID = "mobileBundleId";
    public static final String LOCAL_BUNDLE_ID = "localBundleId";
    public static final String OFFER_COST = "offerCost";
    public static final String OFFER_COSTIVA = "offerCostIva";
    public static final String COST_VERSION = "costVersion";
    public static final String PARENT = "parent";

    public TlcDetailsOffer() {
    }

    public TlcDetailsOffer(int tlcOfferDetailsId, int tlcOfferId, String tlcName,
                           boolean isExistingClientOffer, int numLines, Integer mobileBundleId,
                           Integer localBundleId, double offerCost, double offerCostIva,
                           String costVersion, boolean isModified, String rete) {
        this.tlcOfferDetailsId = tlcOfferDetailsId;
        this.tlcOfferId = tlcOfferId;
        this.tlcName = tlcName;
        this.isExistingClientOffer = isExistingClientOffer;
        this.numLines = numLines;
        this.mobileBundleId = mobileBundleId;
        this.localBundleId = localBundleId;
        this.offerCost = offerCost;
        this.offerCostIva = offerCostIva;
        this.costVersion = costVersion;
        this.isModified = isModified;
        this.rete = rete;
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = TLC_OFFER_DETAILS_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int tlcOfferDetailsId;
    @DatabaseField(columnName = TLC_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int tlcOfferId;
    @DatabaseField(columnName = TLC_NAME, canBeNull = false, dataType = DataType.STRING, defaultValue = " ")
    private String tlcName;
    @DatabaseField(columnName = IS_EXISTING_CLIENT_OFFER, canBeNull = false, dataType = DataType.BOOLEAN, defaultValue = "false")
    private boolean isExistingClientOffer;
    @DatabaseField(columnName = NUM_LINES, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "1")
    private int numLines;
    @DatabaseField(columnName = MOBILE_BUNDLE_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer mobileBundleId;
    @DatabaseField(columnName = LOCAL_BUNDLE_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer localBundleId;
    @DatabaseField(columnName = OFFER_COST, canBeNull = false, dataType = DataType.DOUBLE, defaultValue = "0.0")
    private double offerCost;
    @DatabaseField(columnName = OFFER_COSTIVA, canBeNull = false, dataType = DataType.DOUBLE, defaultValue = "0.0")
    private double offerCostIva;
    @DatabaseField(columnName = COST_VERSION, canBeNull = false, dataType = DataType.STRING, defaultValue = "0")
    private String costVersion;
    @DatabaseField(columnName = IS_MODIFIED, canBeNull = false, dataType = DataType.BOOLEAN)
    private boolean isModified;
    @DatabaseField(columnName = RETE, canBeNull = true, dataType = DataType.STRING, defaultValue = "CPS")
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

    public String getCostVersion() {
        return costVersion;
    }

    public void setCostVersion(String costVersion) {
        this.costVersion = costVersion;
    }

    public String getTlcName() {
        return tlcName;
    }

    public void setTlcName(String tlcName) {
        this.tlcName = tlcName;
    }

    public boolean isExistingClientOffer() {
        return isExistingClientOffer;
    }

    public void setExistingClientOffer(boolean existingClientOffer) {
        isExistingClientOffer = existingClientOffer;
    }

    public int getTlcOfferDetailsId() {
        return tlcOfferDetailsId;
    }

    public void setTlcOfferDetailsId(int tlcOfferDetailsId) {
        this.tlcOfferDetailsId = tlcOfferDetailsId;
    }

    public int getTlcOfferId() {
        return tlcOfferId;
    }

    public void setTlcOfferId(int tlcOfferId) {
        this.tlcOfferId = tlcOfferId;
    }

    public int getNumLines() {
        return numLines;
    }

    public void setNumLines(int numLines) {
        this.numLines = numLines;
    }

    public Integer getMobileBundleId() {
        return mobileBundleId;
    }

    public void setMobileBundleId(Integer mobileBundleId) {
        this.mobileBundleId = mobileBundleId;
    }

    public Integer getLocalBundleId() {
        return localBundleId;
    }

    public void setLocalBundleId(Integer localBundleId) {
        this.localBundleId = localBundleId;
    }

    public double getOfferCost() {
        return offerCost;
    }

    public void setOfferCost(double offerCost) {
        this.offerCost = offerCost;
    }

    public double getOfferCostIva() {
        return offerCostIva;
    }

    public void setOfferCostIva(double offerCostIva) {
        this.offerCostIva = offerCostIva;
    }
}
