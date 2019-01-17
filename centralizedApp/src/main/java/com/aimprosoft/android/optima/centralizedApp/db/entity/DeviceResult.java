package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DeviceResult")
public class DeviceResult extends BaseEntity {

    public static final String DEVICE_OFFER_ID = "deviceOfferId";
    public static final String RESULT_OFFER_ID = "resultOfferId";
    public static final String TIPO_DEVICE = "tipoDevice";
    public static final String CANONE_DEVICE = "canoneDevice";
    public static final String CANONE_DEVICE_CON_IVA = "canoneDeviceConIVA";
    public static final String DEVICE_NUMBER = "deviceNumber";

    public DeviceResult() {
    }

    @DatabaseField(generatedId = true, columnName = DEVICE_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int deviceOfferId;

    @DatabaseField(columnName = RESULT_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int resultOfferId;

    @DatabaseField(columnName = TIPO_DEVICE, canBeNull = true, dataType = DataType.STRING)
    private String tipoDevice;

    @DatabaseField(columnName = DEVICE_NUMBER, canBeNull = true, dataType = DataType.STRING)
    private String deviceNumber;

    @DatabaseField(columnName = CANONE_DEVICE, canBeNull = true, dataType = DataType.DOUBLE)
    private double canoneDevice;

    @DatabaseField(columnName = CANONE_DEVICE_CON_IVA, canBeNull = true, dataType = DataType.DOUBLE)
    private double canoneDeviceConIVA;

    public int getResultOfferId() {
        return resultOfferId;
    }

    public void setResultOfferId(int resultOfferId) {
        this.resultOfferId = resultOfferId;
    }

    public int getDeviceOfferId() {
        return deviceOfferId;
    }

    public void setDeviceOfferId(int deviceOfferId) {
        this.deviceOfferId = deviceOfferId;
    }

    public String getTipoDevice() {
        return tipoDevice;
    }

    public void setTipoDevice(String tipoDevice) {
        this.tipoDevice = tipoDevice;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public double getCanoneDevice() {
        return canoneDevice;
    }

    public void setCanoneDevice(double canoneDevice) {
        this.canoneDevice = canoneDevice;
    }

    public double getCanoneDeviceConIVA() {
        return canoneDeviceConIVA;
    }

    public void setCanoneDeviceConIVA(double canoneDeviceConIVA) {
        this.canoneDeviceConIVA = canoneDeviceConIVA;
    }
}
