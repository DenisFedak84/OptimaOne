package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "OfferDevice")
public class OfferDevice extends BaseEntity {

    public static final String DEVICE_ID = "deviceId";
    public static final String OFFER_ID = "offerId";
    public static final String DEVICE_DESC = "deviceDesc";
    public static final String DEVICE_NAME = "deviceName";
    public static final String DEVICE_COST = "deviceCost";
    public static final String DEVICE_COST_IVA = "deviceCostIva";
    public static final String ACTIVATION_COST = "activationCost";
    public static final String ACTIVATION_COSTO_EXTRA = "activationCostoExtra";
    public static final String DEVICE_TYPE = "deviceType";
    public static final String PRIORITY = "priority";

    public OfferDevice() {
    }

    public OfferDevice(int deviceId, int offerId, String deviceDesc, String deviceName, double deviceCost, double deviceCostIva, double activationCost, double activationCostoExtra, String deviceType, int priority) {
        this.deviceId = deviceId;
        this.offerId = offerId;
        this.deviceDesc = deviceDesc;
        this.deviceName = deviceName;
        this.deviceCost = deviceCost;
        this.deviceCostIva = deviceCostIva;
        this.activationCost = activationCost;
        this.activationCostoExtra = activationCostoExtra;
        this.deviceType = deviceType;
        this.priority = priority;
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = DEVICE_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int deviceId;
    @DatabaseField(columnName = OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int offerId;
    @DatabaseField(columnName = DEVICE_DESC, canBeNull = false, dataType = DataType.STRING)
    private String deviceDesc;
    @DatabaseField(columnName = DEVICE_NAME, canBeNull = false, dataType = DataType.STRING, defaultValue = "")
    private String deviceName;
    @DatabaseField(columnName = DEVICE_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double deviceCost;
    @DatabaseField(columnName = DEVICE_COST_IVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double deviceCostIva;
    @DatabaseField(columnName = ACTIVATION_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double activationCost;
    @DatabaseField(columnName = ACTIVATION_COSTO_EXTRA, canBeNull = false, dataType = DataType.DOUBLE)
    private double activationCostoExtra;
    @DatabaseField(columnName = DEVICE_TYPE, canBeNull = false, dataType = DataType.STRING)
    private String deviceType;
    @DatabaseField(columnName = PRIORITY, canBeNull = false, dataType = DataType.INTEGER)
    private int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getActivationCostoExtra() {
        return activationCostoExtra;
    }

    public void setActivationCostoExtra(double activationCostoExtra) {
        this.activationCostoExtra = activationCostoExtra;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public double getActivationCost() {
        return activationCost;
    }

    public void setActivationCost(double activationCost) {
        this.activationCost = activationCost;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public double getDeviceCost() {
        return deviceCost;
    }

    public void setDeviceCost(double deviceCost) {
        this.deviceCost = deviceCost;
    }

    public double getDeviceCostIva() {
        return deviceCostIva;
    }

    public void setDeviceCostIva(double deviceCostIva) {
        this.deviceCostIva = deviceCostIva;
    }

    @Override
    public String toString() {
        return deviceDesc;
    }
}
