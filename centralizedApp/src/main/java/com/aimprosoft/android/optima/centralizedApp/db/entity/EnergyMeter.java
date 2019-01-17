package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "EnergyMeter")
public class EnergyMeter extends BaseEntity {

    public static final String ENERGY_METER_ID = "energyMeterId";
    public static final String ENERGY_METER_VALUE = "energyMeterValue";
    public static final String TARIFF_OPTION = "tariffOption";
    public static final String CONTRACT_TYPE = "contractType";
    public static final String SYSTEM = "system";

    public EnergyMeter() {
    }

    @DatabaseField(columnName = ENERGY_METER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int energyMeterId;

    @DatabaseField(columnName = ENERGY_METER_VALUE, canBeNull = false, dataType = DataType.STRING)
    private String energyMeterValue;

    @DatabaseField(columnName = TARIFF_OPTION, canBeNull = false, dataType = DataType.STRING)
    private String tariffOption;

    @DatabaseField(columnName = CONTRACT_TYPE, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer contractType;

    @DatabaseField(columnName = SYSTEM, canBeNull = true, dataType = DataType.STRING)
    private String system;

    public int getContractType() {
        return contractType;
    }

    public void setContractType(int contractType) {
        this.contractType = contractType;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getTariffOption() {
        return tariffOption;
    }

    public void setTariffOption(String tariffOption) {
        this.tariffOption = tariffOption;
    }

    public int getEnergyMeterId() {
        return energyMeterId;
    }

    public void setEnergyMeterId(int energyMeterId) {
        this.energyMeterId = energyMeterId;
    }

    public String getEnergyMeterValue() {
        return energyMeterValue;
    }

    public void setEnergyMeterValue(String energyMeterValue) {
        this.energyMeterValue = energyMeterValue;
    }

    public String toString() {
        return energyMeterValue;
    }
}
