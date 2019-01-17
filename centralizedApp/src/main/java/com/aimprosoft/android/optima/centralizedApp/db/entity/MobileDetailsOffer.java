package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public class MobileDetailsOffer extends BaseEntity {

    public static final String MOBILE_DETAIL_OFFER_ID = "mobileDetailOfferId";
    public static final String MOBILE_OFFER_ID = "mobileOfferId";
    public static final String OFFERTA_ID = "offertaId";
    public static final String BUNDLE_ID = "bundleId";
    public static final String SIM = "sim";
    public static final String SCONTO = "sconto";
    public static final String COST = "cost";
    public static final String COSTIVA = "costIVA";
    public static final String COST_EXTRA = "costExtra";

    public MobileDetailsOffer() {
    }

    public MobileDetailsOffer(int mobileOfferId, int offertaId, int sconto, int bundleId, String sim, double cost, double costIVA, double costExtra) {
        this.mobileOfferId = mobileOfferId;
        this.offertaId = offertaId;
        this.sconto = sconto;
        this.bundleId = bundleId;
        this.sim = sim;
        this.cost = cost;
        this.costIVA = costIVA;
        this.costExtra = costExtra;
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = MOBILE_DETAIL_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int mobileDetailOfferId;
    @DatabaseField(columnName = MOBILE_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int mobileOfferId;
    @DatabaseField(columnName = OFFERTA_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int offertaId;
    @DatabaseField(columnName = SCONTO, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int sconto;
    @DatabaseField(columnName = BUNDLE_ID, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int bundleId;
    @DatabaseField(columnName = SIM, canBeNull = false, dataType = DataType.STRING, defaultValue = " ")
    private String sim;
    @DatabaseField(columnName = COST, canBeNull = true, dataType = DataType.DOUBLE, defaultValue = "0")
    private double cost;
    @DatabaseField(columnName = COSTIVA, canBeNull = true, dataType = DataType.DOUBLE, defaultValue = "0")
    private double costIVA;
    @DatabaseField(columnName = COST_EXTRA, canBeNull = true, dataType = DataType.DOUBLE, defaultValue = "0")
    private double costExtra;


    public int getMobileDetailOfferId() {
        return mobileDetailOfferId;
    }

    public void setMobileDetailOfferId(int mobileDetailOfferId) {
        this.mobileDetailOfferId = mobileDetailOfferId;
    }

    public int getMobileOfferId() {
        return mobileOfferId;
    }

    public void setMobileOfferId(int mobileOfferId) {
        this.mobileOfferId = mobileOfferId;
    }

    public int getOffertaId() {
        return offertaId;
    }

    public void setOffertaId(int offertaId) {
        this.offertaId = offertaId;
    }

    public int getBundleId() {
        return bundleId;
    }

    public void setBundleId(int bundleId) {
        this.bundleId = bundleId;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
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

    public double getCostExtra() {
        return costExtra;
    }

    public void setCostExtra(double costExtra) {
        this.costExtra = costExtra;
    }

    public int getSconto() {
        return sconto;
    }

    public void setSconto(int sconto) {
        this.sconto = sconto;
    }
}
