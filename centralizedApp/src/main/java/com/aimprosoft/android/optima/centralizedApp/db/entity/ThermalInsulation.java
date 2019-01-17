package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ThermalInsulation")
public class ThermalInsulation extends BaseEntity {

    public static final String ID_THERMAL_INSULATION = "idThermalInsulation";
    public static final String DESC_THERMAL_INSULATION = "descThermalInsulation";

    public ThermalInsulation(int idThermalInsulation, String descThermalInsulation) {
        this.idThermalInsulation = idThermalInsulation;
        this.descThermalInsulation = descThermalInsulation;
    }

    public ThermalInsulation() {
    }

    @DatabaseField(columnName = ID_THERMAL_INSULATION, canBeNull = false, dataType = DataType.INTEGER)
    private int idThermalInsulation;
    @DatabaseField(columnName = DESC_THERMAL_INSULATION, canBeNull = false, dataType = DataType.STRING)
    private String descThermalInsulation;

    public int getIdThermalInsulation() {
        return idThermalInsulation;
    }

    public void setIdThermalInsulation(int idThermalInsulation) {
        this.idThermalInsulation = idThermalInsulation;
    }

    public String getDescThermalInsulation() {
        return descThermalInsulation;
    }

    public void setDescThermalInsulation(String descThermalInsulation) {
        this.descThermalInsulation = descThermalInsulation;
    }

    @Override
    public String toString() {
        return descThermalInsulation;
    }
}
