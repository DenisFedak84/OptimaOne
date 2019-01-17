package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "EnergyClass")
public class EnergyClass extends BaseEntity {

    public static final String ENERGY_CLASS_ID = "energyClassId";
    public static final String ENERGY_CLASS_VALUE = "energyClassValue";


    public EnergyClass() {

    }

    public EnergyClass(int energyClassId, String energyClassValue) {
        this.energyClassId = energyClassId;
        this.energyClassValue = energyClassValue;
    }

    @DatabaseField(columnName = ENERGY_CLASS_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int energyClassId;

    @DatabaseField(columnName = ENERGY_CLASS_VALUE, canBeNull = false, dataType = DataType.STRING)
    private String energyClassValue;

    public int getEnergyClassId() {
        return energyClassId;
    }

    public void setEnergyClassId(int energyClassId) {
        this.energyClassId = energyClassId;
    }

    public String getEnergyClassValue() {
        return energyClassValue;
    }

    public void setEnergyClassValue(String energyClassValue) {
        this.energyClassValue = energyClassValue;
    }

    public String toString() {
        return energyClassValue;
    }
}
