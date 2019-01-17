package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "MobileOffer")
public class MobileOffer extends BaseEntity {

    public static final String MOBILE_OFFER_ID = "mobileOfferId";
    public static final String OFFER_COST = "offerCost";
    public static final String OFFER_COST_IVA = "offerCostIva";
    public static final String OFFER_COST_CON_PROMO = "offerCostConPromo";
    public static final String PROMO_COST = "promoCost";
    public static final String OFFER_COST_EXTRA = "offerCostExtra";

    public MobileOffer() {
    }

    @DatabaseField(generatedId = true, columnName = MOBILE_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int mobileOfferId;

    @DatabaseField(columnName = OFFER_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double offerCost;

    @DatabaseField(columnName = OFFER_COST_CON_PROMO, canBeNull = false, dataType = DataType.DOUBLE)
    private double offerCostConPromo;

    @DatabaseField(columnName = PROMO_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double promoCost;

    @DatabaseField(columnName = OFFER_COST_IVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double offerCostIva;

    @DatabaseField(columnName = OFFER_COST_EXTRA, canBeNull = false, dataType = DataType.DOUBLE)
    private double offerCostExtra;

    public int getMobileOfferId() {
        return mobileOfferId;
    }

    public void setMobileOfferId(int mobileOfferId) {
        this.mobileOfferId = mobileOfferId;
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

    public double getOfferCostExtra() {
        return offerCostExtra;
    }

    public void setOfferCostExtra(double offerCostExtra) {
        this.offerCostExtra = offerCostExtra;
    }

    public double getOfferCostConPromo() {
        return offerCostConPromo;
    }

    public void setOfferCostConPromo(double offerCostConPromo) {
        this.offerCostConPromo = offerCostConPromo;
    }

    public double getPromoCost() {
        return promoCost;
    }

    public void setPromoCost(double promoCost) {
        this.promoCost = promoCost;
    }
}
