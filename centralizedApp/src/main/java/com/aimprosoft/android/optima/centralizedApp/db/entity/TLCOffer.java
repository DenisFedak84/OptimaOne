package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;

@DatabaseTable(tableName = "TLCOffer")
public class TLCOffer extends BaseEntity {

    public static final String TLC_OFFER_ID = "tlcOfferId";
    public static final String IS_EXISTING_CLIENT_OFFER = "isExistingClientOffer";
    public static final String LOCAL_BUNDLE_ID = "localBundleId";
    public static final String MOBILE_BUNDLE_ID = "mobileBundleId";
    public static final String LINES_NUMBER = "linesNumber";
    public static final String BUNDLE_COST = "bundleCost";
    public static final String BUNDLE_COST_IVA = "bundleCostIVA";
    public static final String PMC = "pmc";
    public static final String PS = "ps";
    public static final String CODICE_PMC_PS = "codicePmcPs";
    public static final String PERCENTAGE = "percentage";

    public TLCOffer(String ps, int tlcOfferId, BigDecimal bundleCost, BigDecimal bundleCostIVA, String pmc, String codicePmcPS) {
        this.ps = ps;
        this.tlcOfferId = tlcOfferId;
        this.bundleCost = bundleCost;
        this.bundleCostIVA = bundleCostIVA;
        this.pmc = pmc;
        this.codicePmcPS = codicePmcPS;
    }

    public TLCOffer() {
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = TLC_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int tlcOfferId;
//
//    @DatabaseField(columnName = LOCAL_BUNDLE_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
//    private Integer localBundleId;
//
//    @DatabaseField(columnName = MOBILE_BUNDLE_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
//    private Integer mobileBundleId;
//
//    @DatabaseField(columnName = LINES_NUMBER, canBeNull = false, dataType = DataType.INTEGER_OBJ)
//    private Integer linesNumber;

    @DatabaseField(columnName = BUNDLE_COST, canBeNull = true, dataType = DataType.BIG_DECIMAL, defaultValue = "0.0")
    private BigDecimal bundleCost;

    @DatabaseField(columnName = BUNDLE_COST_IVA, canBeNull = true, dataType = DataType.BIG_DECIMAL, defaultValue = "0.0")
    private BigDecimal bundleCostIVA;

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

    public int getTlcOfferId() {
        return tlcOfferId;
    }

    public void setTlcOfferId(int tlcOfferId) {
        this.tlcOfferId = tlcOfferId;
    }

    public BigDecimal getBundleCost() {
        return bundleCost;
    }

    public void setBundleCost(BigDecimal bundleCost) {
        this.bundleCost = bundleCost;
    }

    public BigDecimal getBundleCostIVA() {
        return bundleCostIVA;
    }

    public void setBundleCostIVA(BigDecimal bundleCostIVA) {
        this.bundleCostIVA = bundleCostIVA;
    }
}
