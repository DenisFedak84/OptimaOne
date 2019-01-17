package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ServiceOffer")
public class ServiceOffer extends BaseEntity {

    public static final String OFFER_ID = "offerId";
    public static final String ENERGY_OFFER_ID = "energyOfferId";
    public static final String GAS_OFFER_ID = "gasOfferId";
    public static final String TLC_OFFER_ID = "tlcOfferId";
    public static final String INTERNET_OFFER_ID = "internetOfferId";
    public static final String MOBILE_OFFER_ID = "mobileOfferId";

    public ServiceOffer() {
    }

    public ServiceOffer(int energyOfferId, int gasOfferId, int tlcOfferId, int internetOfferId, int mobileOfferId) {
        this.energyOfferId = energyOfferId;
        this.gasOfferId = gasOfferId;
        this.tlcOfferId = tlcOfferId;
        this.internetOfferId = internetOfferId;
        this.mobileOfferId = mobileOfferId;
    }

    @DatabaseField(generatedId = true, columnName = OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int offerId;

    @DatabaseField(columnName = ENERGY_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int energyOfferId;

    @DatabaseField(columnName = GAS_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int gasOfferId;

    @DatabaseField(columnName = TLC_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int tlcOfferId;

    @DatabaseField(columnName = INTERNET_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int internetOfferId;

    @DatabaseField(columnName = MOBILE_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int mobileOfferId;

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getEnergyOfferId() {
        return energyOfferId;
    }

    public void setEnergyOfferId(int energyOfferId) {
        this.energyOfferId = energyOfferId;
    }

    public int getGasOfferId() {
        return gasOfferId;
    }

    public void setGasOfferId(int gasOfferId) {
        this.gasOfferId = gasOfferId;
    }

    public int getTlcOfferId() {
        return tlcOfferId;
    }

    public void setTlcOfferId(int tlcOfferId) {
        this.tlcOfferId = tlcOfferId;
    }

    public int getInternetOfferId() {
        return internetOfferId;
    }

    public void setInternetOfferId(int internetOfferId) {
        this.internetOfferId = internetOfferId;
    }

    public int getMobileOfferId() {
        return mobileOfferId;
    }

    public void setMobileOfferId(int mobileOfferId) {
        this.mobileOfferId = mobileOfferId;
    }
}
