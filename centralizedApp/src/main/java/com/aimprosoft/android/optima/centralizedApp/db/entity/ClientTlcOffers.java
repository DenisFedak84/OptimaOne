package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ClientTlcOffers")
public class ClientTlcOffers extends BaseEntity {

    public static final String CLIENT_TLC_OFFER_ID = "clientTlcOfferId";
    public static final String NUM_LINE = "numLine";
    public static final String STATE = "state";
    public static final String COST = "cost";
    public static final String COSTIVA = "costIva";
    public static final String RELAX = "relax";
    public static final String CLIENT_SIN = "clientSin";

    public ClientTlcOffers() {
    }

    public ClientTlcOffers(int clientTlcOfferId, int numLine, String state, double cost, double costIva, int relax, Integer clientSin) {
        this.clientTlcOfferId = clientTlcOfferId;
        this.numLine = numLine;
        this.state = state;
        this.cost = cost;
        this.costIva = costIva;
        this.relax = relax;
        this.clientSin = clientSin;
    }

    @DatabaseField(id = true, columnName = CLIENT_TLC_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int clientTlcOfferId;
    @DatabaseField(columnName = NUM_LINE, canBeNull = false, dataType = DataType.INTEGER)
    private int numLine;
    @DatabaseField(columnName = STATE, canBeNull =false, dataType = DataType.STRING)
    private String state;
    @DatabaseField(columnName = COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double cost;
    @DatabaseField(columnName = COSTIVA, canBeNull = false ,dataType = DataType.DOUBLE)
    private double costIva;
    @DatabaseField(columnName = RELAX, canBeNull = false ,dataType = DataType.INTEGER)
    private int relax;
    @DatabaseField(columnName = CLIENT_SIN, canBeNull = true ,dataType = DataType.INTEGER_OBJ)
    private Integer clientSin;

    public Integer getClientSin() {
        return clientSin;
    }

    public void setClientSin(Integer clientSin) {
        this.clientSin = clientSin;
    }

    public int getClientTlcOfferId() {
        return clientTlcOfferId;
    }

    public void setClientTlcOfferId(int clientTlcOfferId) {
        this.clientTlcOfferId = clientTlcOfferId;
    }

    public int getNumLine() {
        return numLine;
    }

    public void setNumLine(int numLine) {
        this.numLine = numLine;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCostIva() {
        return costIva;
    }

    public void setCostIva(double costIva) {
        this.costIva = costIva;
    }

    public int getRelax() {
        return relax;
    }

    public void setRelax(int relax) {
        this.relax = relax;
    }
}
