package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ClientAdslOffers")
public class ClientAdslOffers extends BaseEntity {

    public static final String CLIENT_ADSL_OFFER_ID = "clientAdslOfferId";
    public static final String NUM_ADSL = "numAdsl";
    public static final String STATE = "state";
    public static final String COST = "cost";
    public static final String COSTIVA = "costIVA";
    public static final String RELAX = "relax";
    public static final String CLIENT_SIN = "clientSin";

    public ClientAdslOffers() {
    }

    public ClientAdslOffers(int clientAdslOffers, int numAdsl, String state, double cost, double costIVA, int relax, int clientSin) {
        this.clientAdslOffers = clientAdslOffers;
        this.numAdsl = numAdsl;
        this.state = state;
        this.cost = cost;
        this.costIVA = costIVA;
        this.relax = relax;
        this.clientSin = clientSin;
    }

    @DatabaseField(id = true, columnName = CLIENT_ADSL_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int clientAdslOffers;
    @DatabaseField(columnName = NUM_ADSL, canBeNull = false, dataType = DataType.INTEGER)
    private int numAdsl;
    @DatabaseField(columnName = STATE, canBeNull = false ,dataType = DataType.STRING)
    private String state;
    @DatabaseField(columnName = COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double cost;
    @DatabaseField(columnName = COSTIVA, canBeNull = false , dataType = DataType.DOUBLE)
    private double costIVA;
    @DatabaseField(columnName = RELAX, canBeNull = false, dataType = DataType.INTEGER)
    private int relax;
    @DatabaseField(columnName = CLIENT_SIN, canBeNull = false, dataType = DataType.INTEGER)
    private int clientSin;

    public int getClientSin() {
        return clientSin;
    }

    public void setClientSin(int clientSin) {
        this.clientSin = clientSin;
    }

    public int getClientAdslOffers() {
        return clientAdslOffers;
    }

    public void setClientAdslOffers(int clientAdslOffers) {
        this.clientAdslOffers = clientAdslOffers;
    }

    public int getNumAdsl() {
        return numAdsl;
    }

    public void setNumAdsl(int numAdsl) {
        this.numAdsl = numAdsl;
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

    public double getCostIVA() {
        return costIVA;
    }

    public void setCostIVA(double costIVA) {
        this.costIVA = costIVA;
    }

    public int getRelax() {
        return relax;
    }

    public void setRelax(int relax) {
        this.relax = relax;
    }
}
