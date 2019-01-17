package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public class ConsumptionClassType extends BaseEntity {

    public static final String CONSUMPTION_CLASS_TYPE_ID = "consumptionClassTypeId";
    public static final String CONSUMPTION_CLASS_TYPE_DESC = "consumptionClassTypeDesc";

    public ConsumptionClassType(String consumptionClassTypeDesc) {
        this.consumptionClassTypeDesc = consumptionClassTypeDesc;
    }

    public ConsumptionClassType() {
    }

    public ConsumptionClassType(int consumptionClassTypeId, String consumptionClassTypeDesc) {
        this.consumptionClassTypeId = consumptionClassTypeId;
        this.consumptionClassTypeDesc = consumptionClassTypeDesc;
    }

    @DatabaseField(columnName = CONSUMPTION_CLASS_TYPE_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int consumptionClassTypeId;

    @DatabaseField(columnName = CONSUMPTION_CLASS_TYPE_DESC, canBeNull = false, dataType = DataType.STRING)
    private String consumptionClassTypeDesc;

    public int getConsumptionClassTypeId() {
        return consumptionClassTypeId;
    }

    public void setConsumptionClassTypeId(int consumptionClassTypeId) {
        this.consumptionClassTypeId = consumptionClassTypeId;
    }

    public String getConsumptionClassTypeDesc() {
        return consumptionClassTypeDesc;
    }

    public void setConsumptionClassTypeDesc(String consumptionClassTypeDesc) {
        this.consumptionClassTypeDesc = consumptionClassTypeDesc;
    }
}
