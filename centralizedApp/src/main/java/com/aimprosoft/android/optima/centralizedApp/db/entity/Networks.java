package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Networks")
public class Networks extends BaseEntity {

    public static final String NETWORK_ID = "networkId";
    public static final String NETWORK_DESC = "networkDesc";
    public static final String CARRIER_ID = "carrierId";
    public static final String COST = "cost";
    public static final String COSTIVA = "costIva";
    public static final String ACTIVATION_COST = "activationCost";
    public static final String RELAX = "relax";

    public Networks() {
    }

    public Networks(int networkId, String networkDesc, int carrierId, double cost, double costIva, double activationCost, int relax) {
        this.networkId = networkId;
        this.networkDesc = networkDesc;
        this.carrierId = carrierId;
        this.cost = cost;
        this.costIva = costIva;
        this.activationCost = activationCost;
        this.relax = relax;
    }

    @DatabaseField(id = true, columnName = NETWORK_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int networkId;
    @DatabaseField(columnName = NETWORK_DESC, canBeNull = false, dataType = DataType.STRING)
    private String networkDesc;
    @DatabaseField(columnName = CARRIER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int carrierId;
    @DatabaseField(columnName = COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double cost;
    @DatabaseField(columnName = COSTIVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double costIva;
    @DatabaseField(columnName = ACTIVATION_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double activationCost;
    @DatabaseField(columnName = RELAX, canBeNull = false, dataType = DataType.INTEGER)
    private int relax;

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public String getNetworkDesc() {
        return networkDesc;
    }

    public void setNetworkDesc(String networkDesc) {
        this.networkDesc = networkDesc;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
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

    public double getActivationCost() {
        return activationCost;
    }

    public void setActivationCost(double activationCost) {
        this.activationCost = activationCost;
    }

    public int getRelax() {
        return relax;
    }

    public void setRelax(int relax) {
        this.relax = relax;
    }

    @Override
    public String toString() {
        return networkDesc;
    }
}
