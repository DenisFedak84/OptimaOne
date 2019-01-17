package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "GasOfferDateInterval")
public class GasOfferDateInterval extends BaseEntity {

    public static final String GAS_DETAIL_OFFER_INTERVAL_ID = "gasDetailOfferIntervalId";
    public static final String GAS_DETAIL_OFFER_ID = "gasDetailOfferId";
    public static final String DATE_FROM = "dateFrom";
    public static final String DATE_TO = "dateTo";
    public static final String SMC = "smc";

    public GasOfferDateInterval() {
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = GAS_DETAIL_OFFER_INTERVAL_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int gasDetailOfferIntervalId;

    @DatabaseField(columnName = GAS_DETAIL_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int gasDetailOfferId;

    @DatabaseField(columnName = DATE_FROM, canBeNull = true, dataType = DataType.DATE)
    private Date dateFrom;

    @DatabaseField(columnName = DATE_TO, canBeNull = true, dataType = DataType.DATE)
    private Date dateTo;

    @DatabaseField(columnName = SMC, canBeNull = false, dataType = DataType.INTEGER)
    private int smc;

    public int getGasDetailOfferIntervalId() {
        return gasDetailOfferIntervalId;
    }

    public void setGasDetailOfferIntervalId(int gasDetailOfferIntervalId) {
        this.gasDetailOfferIntervalId = gasDetailOfferIntervalId;
    }

    public int getGasDetailOfferId() {
        return gasDetailOfferId;
    }

    public void setGasDetailOfferId(int gasDetailOfferId) {
        this.gasDetailOfferId = gasDetailOfferId;
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

    public int getSmc() {
        return smc;
    }

    public void setSmc(int smc) {
        this.smc = smc;
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
