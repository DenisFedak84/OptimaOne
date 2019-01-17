package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ClientGasOffers")
public class ClientGasOffers extends BaseEntity {

    public static final String CLIENT_GAS_OFFER_ID = "clientGasOfferId";
    public static final String NUM_PDR = "numPdr";
    public static final String STATE = "state";
    public static final String COST = "cost";
    public static final String COSTIVA = "costIva";
    public static final String RELAX = "relax";
    public static final String CLIENT_CODE_SIN = "clientCodeSin";
    public static final String CLIENT_ID = "clientId";


    public ClientGasOffers() {
    }

    public ClientGasOffers(int clientGasOfferId, int numPdr, String state, double cost, double costIva,
                           int relax, int clientCodeSin, int clientId) {
        this.clientGasOfferId = clientGasOfferId;
        this.numPdr = numPdr;
        this.state = state;
        this.cost = cost;
        this.costIva = costIva;
        this.relax = relax;
        this.clientCodeSin = clientCodeSin;
        this.clientId = clientId;
    }

    @DatabaseField(id = true, columnName = CLIENT_GAS_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int clientGasOfferId;
    @DatabaseField(columnName = NUM_PDR, canBeNull = false, dataType = DataType.INTEGER)
    private int numPdr;
    @DatabaseField(columnName = STATE, canBeNull = false, dataType = DataType.STRING)
    private String state;
    @DatabaseField(columnName = COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double cost;
    @DatabaseField(columnName = COSTIVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double costIva;
    @DatabaseField(columnName = RELAX, canBeNull = false, dataType = DataType.INTEGER)
    private int relax;
    @DatabaseField(columnName = CLIENT_CODE_SIN, canBeNull = false, dataType = DataType.INTEGER)
    private int clientCodeSin;
    @DatabaseField(columnName = CLIENT_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int clientId;

    public int getClientCodeSin() {
        return clientCodeSin;
    }

    public void setClientCodeSin(int clientCodeSin) {
        this.clientCodeSin = clientCodeSin;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientGasOfferId() {
        return clientGasOfferId;
    }

    public void setClientGasOfferId(int clientGasOfferId) {
        this.clientGasOfferId = clientGasOfferId;
    }

    public int getNumPdr() {
        return numPdr;
    }

    public void setNumPdr(int numPdr) {
        this.numPdr = numPdr;
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
