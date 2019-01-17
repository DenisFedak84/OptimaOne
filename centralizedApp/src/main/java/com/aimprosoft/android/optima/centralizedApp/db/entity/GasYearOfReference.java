package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "GasYearOfReference")
public class GasYearOfReference extends BaseEntity {

    public static final String ID_YEAR_OF_REFERENCE = "idYearOfReference";
    public static final String DESC_YEAR_OF_REFERENCE = "descYearOfReference";
    public static final String VALUE_YEAR_OF_REFERENCE = "valueYearOfReference";
    public static final String CLASS = "yearClass";

    public GasYearOfReference() {
    }

    public GasYearOfReference(int idYearOfReference, String descYearOfReference, int valueYearOfReference, String yearClass) {
        this.idYearOfReference = idYearOfReference;
        this.descYearOfReference = descYearOfReference;
        this.valueYearOfReference = valueYearOfReference;
        this.yearClass = yearClass;
    }

    @DatabaseField(columnName = ID_YEAR_OF_REFERENCE, canBeNull = false, dataType = DataType.INTEGER)
    private int idYearOfReference;

    @DatabaseField(columnName = DESC_YEAR_OF_REFERENCE, canBeNull = false, dataType = DataType.STRING)
    private String descYearOfReference;

    @DatabaseField(columnName = VALUE_YEAR_OF_REFERENCE, canBeNull = false, dataType = DataType.INTEGER)
    private int valueYearOfReference;

    @DatabaseField(columnName = CLASS, canBeNull = false, dataType = DataType.STRING)
    private String yearClass;

    public int getIdYearOfReference() {
        return idYearOfReference;
    }

    public void setIdYearOfReference(int idYearOfReference) {
        this.idYearOfReference = idYearOfReference;
    }

    public String getYearClass() {
        return yearClass;
    }

    public void setYearClass(String yearClass) {
        this.yearClass = yearClass;
    }

    public String getDescYearOfReference() {
        return descYearOfReference;
    }

    public void setDescYearOfReference(String descYearOfReference) {
        this.descYearOfReference = descYearOfReference;
    }

    public int getValueYearOfReference() {
        return valueYearOfReference;
    }

    public void setValueYearOfReference(int valueYearOfReference) {
        this.valueYearOfReference = valueYearOfReference;
    }

    @Override
    public String toString() {
        return descYearOfReference;
    }
}
