package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "YearOfReference")
public class YearOfReference extends BaseEntity {

    public static final String YEAR_OF_REFERENCE_ID = "yearOfReferenceId";
    public static final String YEAR_OF_REFERENCE_VALUE = "yearOfReferenceValue";
    public static final String ENERGY_CLASS = "energyClass";
    public static final String CODE_APPLIANCE = "codeAppliance";
    public static final String COEFFICIENT = "coefficient";

    public YearOfReference() {
    }

    public YearOfReference(int yearOfReferenceId, int yearOfReferenceValue, int energyClass, String codeAppliance, double coefficient) {
        this.yearOfReferenceId = yearOfReferenceId;
        this.yearOfReferenceValue = yearOfReferenceValue;
        this.energyClass = energyClass;
        this.codeAppliance = codeAppliance;
        this.coefficient = coefficient;
    }

    @DatabaseField(columnName = YEAR_OF_REFERENCE_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int yearOfReferenceId;

    @DatabaseField(columnName = YEAR_OF_REFERENCE_VALUE, canBeNull = false, dataType = DataType.INTEGER)
    private int yearOfReferenceValue;

    @DatabaseField(columnName = ENERGY_CLASS, canBeNull = false, dataType = DataType.INTEGER)
    private int energyClass;

    @DatabaseField(columnName = CODE_APPLIANCE, canBeNull = false, dataType = DataType.STRING)
    private String codeAppliance;

    @DatabaseField(columnName = COEFFICIENT, canBeNull = false, dataType = DataType.DOUBLE)
    private double coefficient;


    public int getEnergyClass() {
        return energyClass;
    }

    public void setEnergyClass(int energyClass) {
        this.energyClass = energyClass;
    }

    public String getCodeAppliance() {
        return codeAppliance;
    }

    public void setCodeAppliance(String codeAppliance) {
        this.codeAppliance = codeAppliance;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public int getYearOfReferenceId() {
        return yearOfReferenceId;
    }

    public void setYearOfReferenceId(int yearOfReferenceId) {
        this.yearOfReferenceId = yearOfReferenceId;
    }

    public int getYearOfReferenceValue() {
        return yearOfReferenceValue;
    }

    public void setYearOfReferenceValue(int yearOfReferenceValue) {
        this.yearOfReferenceValue = yearOfReferenceValue;
    }

    public String toString() {
        return "" + yearOfReferenceValue;
    }
}
