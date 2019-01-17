package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ClientConsuptionClass")
public class ClientConsuptionClass extends BaseEntity {

    public static final String CLIENT_CONSUMPTION_CLASS_ID = "clientConsumptionClassId";
    public static final String ENERGY_METER_ID = "energyMeterId";
    public static final String CLIENT_TYPE = "clientType";
    public static final String CONSUPTION_CLASS_TYPE = "consuptionClassType";

    public ClientConsuptionClass() {
    }

    public ClientConsuptionClass(int clientConsumptionClassId, int energyMeterId, int clientType, int consuptionClassType) {
        this.clientConsumptionClassId = clientConsumptionClassId;
        this.energyMeterId = energyMeterId;
        this.clientType = clientType;
        this.consuptionClassType = consuptionClassType;
    }

    @DatabaseField(id = true, columnName = CLIENT_CONSUMPTION_CLASS_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int clientConsumptionClassId;
    @DatabaseField(columnName = ENERGY_METER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int energyMeterId;
    @DatabaseField(columnName = CLIENT_TYPE, canBeNull = false, dataType = DataType.INTEGER)
    private int clientType;
    @DatabaseField(columnName = CONSUPTION_CLASS_TYPE, canBeNull = false, dataType = DataType.INTEGER)
    private int consuptionClassType;

    public ClientConsuptionClass(int energyMeterId) {
        this.energyMeterId = energyMeterId;
    }

    public int getClientConsumptionClassId() {
        return clientConsumptionClassId;
    }

    public void setClientConsumptionClassId(int clientConsumptionClassId) {
        this.clientConsumptionClassId = clientConsumptionClassId;
    }

    public int getEnergyMeterId() {
        return energyMeterId;
    }

    public void setEnergyMeterId(int energyMeterId) {
        this.energyMeterId = energyMeterId;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public int getConsuptionClassType() {
        return consuptionClassType;
    }

    public void setConsuptionClassType(int consuptionClassType) {
        this.consuptionClassType = consuptionClassType;
    }


}
