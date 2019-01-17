package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Device")
public class Device extends BaseEntity {

    public static final String ID_DEVICE = "idDevice";
    public static final String CODE_DEVICE = "codeDevice";
    public static final String NAME_DEVICE = "nameDevice";
    public static final String DEVICE_TYPE = "deviceType";
    public static final String NUMBER_RATES = "numberRates";

    public Device() {
    }

    public Device(int idDevice, String codeDevice, String nameDevice, String deviceType, Integer numberRates) {
        this.idDevice = idDevice;
        this.codeDevice = codeDevice;
        this.nameDevice = nameDevice;
        this.deviceType = deviceType;
        this.numberRates = numberRates;
    }

    @DatabaseField(generatedId = true, columnName = ID_DEVICE, canBeNull = false, dataType = DataType.INTEGER)
    private int idDevice;
    @DatabaseField(columnName = CODE_DEVICE, canBeNull = false, dataType = DataType.STRING)
    private String codeDevice;
    @DatabaseField(columnName = NAME_DEVICE, canBeNull = false, dataType = DataType.STRING)
    private String nameDevice;
    @DatabaseField(columnName = DEVICE_TYPE, canBeNull = false, dataType = DataType.STRING)
    private String deviceType;
    @DatabaseField(columnName = NUMBER_RATES, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer numberRates;

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

    public String getCodeDevice() {
        return codeDevice;
    }

    public void setCodeDevice(String codeDevice) {
        this.codeDevice = codeDevice;
    }

    public String getNameDevice() {
        return nameDevice;
    }

    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getNumberRates() {
        return numberRates;
    }

    public void setNumberRates(Integer numberRates) {
        this.numberRates = numberRates;
    }

    @Override
    public String toString() {
        return nameDevice;
    }
}
