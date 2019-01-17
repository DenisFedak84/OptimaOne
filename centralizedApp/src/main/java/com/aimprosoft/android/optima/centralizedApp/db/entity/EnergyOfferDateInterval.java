package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "EnergyOfferDateInterval")
public class EnergyOfferDateInterval extends BaseEntity {

    public static final String ENERGY_OFFER_DATE_INTERVAL_ID = "energyOfferDateIntervalId";
    public static final String ID_ENERGY_DETAIL_OFFER = "idEnergyDetailOffer";
    public static final String DATE_FROM = "dateFrom";
    public static final String DATE_TO = "dateTo";
    public static final String KWH1 = "kwh1";
    private static final String KWH2 = "kwh2";
    private static final String KWH3 = "kwh3";
    private static final String POTENZA_IMPEGNATA = "potenzaImpegnata";

    public EnergyOfferDateInterval() {
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = ENERGY_OFFER_DATE_INTERVAL_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int energyOfferDateIntervalId;

    @DatabaseField(columnName = ID_ENERGY_DETAIL_OFFER, canBeNull = false, dataType = DataType.INTEGER)
    private int idEnergyDetailOffer;

    @DatabaseField(columnName = DATE_FROM, canBeNull = true, dataType = DataType.DATE)
    private Date dateFrom;

    @DatabaseField(columnName = DATE_TO, canBeNull = true, dataType = DataType.DATE)
    private Date dateTo;

    @DatabaseField(columnName = KWH1, canBeNull = false, dataType = DataType.INTEGER)
    private int kwh1;

    @DatabaseField(columnName = KWH2, canBeNull = false, dataType = DataType.INTEGER)
    private int kwh2;

    @DatabaseField(columnName = KWH3, canBeNull = false, dataType = DataType.INTEGER)
    private int kwh3;

    @DatabaseField(columnName = POTENZA_IMPEGNATA, canBeNull = false, dataType = DataType.INTEGER)
    private int potenzaImpegnata;

    public int getPotenzaImpegnata() {
        return potenzaImpegnata;
    }

    public void setPotenzaImpegnata(int potenzaImpegnata) {
        this.potenzaImpegnata = potenzaImpegnata;
    }

    public int getEnergyOfferDateIntervalId() {
        return energyOfferDateIntervalId;
    }

    public void setEnergyOfferDateIntervalId(int energyOfferDateIntervalId) {
        this.energyOfferDateIntervalId = energyOfferDateIntervalId;
    }

    public int getIdEnergyDetailOffer() {
        return idEnergyDetailOffer;
    }

    public void setIdEnergyDetailOffer(int idEnergyDetailOffer) {
        this.idEnergyDetailOffer = idEnergyDetailOffer;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = convertDate(dateFrom);
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = convertDate(dateTo);
    }

    public int getKwh1() {
        return kwh1;
    }

    public void setKwh1(int kwh1) {
        this.kwh1 = kwh1;
    }

    public int getKwh2() {
        return kwh2;
    }

    public void setKwh2(int kwh2) {
        this.kwh2 = kwh2;
    }

    public int getKwh3() {
        return kwh3;
    }

    public void setKwh3(int kwh3) {
        this.kwh3 = kwh3;
    }

    @Override
    public Date convertDate(String dateStr) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date parsedDate = dateFormat.parse(dateStr);
            return parsedDate;
        } catch (Throwable e) {
            return null;
        }
    }

}
