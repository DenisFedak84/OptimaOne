package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ClientServices")
public class ClientServices extends BaseEntity {

    public static final String CLIENT_SERVICE_ID = "clientServiceId";
    public static final String CLIENT_ID = "clientId";
    public static final String ENERGY_SERVICE_ID = "energyServiceId";
    public static final String GAS_SERVICE_ID = "gasServiceId";
    public static final String TLC_SERVICE_ID = "tlcServiceId";
    public static final String ADSL_SERVICE_ID = "adslServiceId";

    public ClientServices() {
    }

    public ClientServices(int clientServiceId, Integer clientId, Integer energyServiceId, Integer gasServiceId,
                          Integer tlcServiceId, Integer adslServiceId) {
        this.clientServiceId = clientServiceId;
        this.clientId = clientId;
        this.energyServiceId = energyServiceId;
        this.gasServiceId = gasServiceId;
        this.tlcServiceId = tlcServiceId;
        this.adslServiceId = adslServiceId;
    }

    @DatabaseField(columnName = CLIENT_SERVICE_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int clientServiceId;
    @DatabaseField(columnName = CLIENT_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer clientId;
    @DatabaseField(columnName = ENERGY_SERVICE_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer energyServiceId;
    @DatabaseField(columnName = GAS_SERVICE_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer gasServiceId;
    @DatabaseField(columnName = TLC_SERVICE_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer tlcServiceId;
    @DatabaseField(columnName = ADSL_SERVICE_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer adslServiceId;


    public Integer getAdslServiceId() {
        return adslServiceId;
    }

    public void setAdslServiceId(Integer adslServiceId) {
        this.adslServiceId = adslServiceId;
    }

    public int getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(int clientServiceId) {
        this.clientServiceId = clientServiceId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getEnergyServiceId() {
        return energyServiceId;
    }

    public void setEnergyServiceId(Integer energyServiceId) {
        this.energyServiceId = energyServiceId;
    }

    public Integer getGasServiceId() {
        return gasServiceId;
    }

    public void setGasServiceId(Integer gasServiceId) {
        this.gasServiceId = gasServiceId;
    }

    public Integer getTlcServiceId() {
        return tlcServiceId;
    }

    public void setTlcServiceId(Integer tlcServiceId) {
        this.tlcServiceId = tlcServiceId;
    }
}
