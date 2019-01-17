package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "InternetOffer")
public class InternetOffer extends BaseEntity {

    public static final String INTERNET_OFFER_ID = "internetOfferId";
//    public static final String BUNDLE_ID = "bundleId";
    public static final String COST = "cost";
    public static final String COST_IVA = "costIVA";

    public InternetOffer() {
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true,
            columnName = INTERNET_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int internetOfferId;

//    @DatabaseField(columnName = BUNDLE_ID, canBeNull = false, dataType = DataType.INTEGER, )
//    private int bundleId;

    @DatabaseField(columnName = COST, canBeNull = false, dataType = DataType.DOUBLE, defaultValue = "0")
    private double cost;

    @DatabaseField(columnName = COST_IVA, canBeNull = false, dataType = DataType.DOUBLE, defaultValue = "0")
    private double costIVA;

    public int getInternetOfferId() {
        return internetOfferId;
    }

    public void setInternetOfferId(int internetOfferId) {
        this.internetOfferId = internetOfferId;
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

