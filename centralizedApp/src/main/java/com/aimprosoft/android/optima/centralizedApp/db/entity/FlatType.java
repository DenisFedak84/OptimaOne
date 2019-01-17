package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName ="FlatType")
public class FlatType extends BaseEntity {

    public static final String ID_FLAT ="idFlat";
    public static final String DESC_FLAT ="descFlat";
    public static final String VALUE = "value";

    public FlatType() {
    }

    public FlatType(int idFlat, String descFlat, int value) {
        this.idFlat = idFlat;
        this.descFlat = descFlat;
        this.value = value;
    }

    @DatabaseField(columnName = ID_FLAT, canBeNull = false, dataType = DataType.INTEGER)
    private int idFlat;

    @DatabaseField(columnName = DESC_FLAT, canBeNull = false, dataType = DataType.STRING)
    private String descFlat;

    @DatabaseField(columnName = VALUE, canBeNull = false, dataType = DataType.INTEGER)
    private int value;

    public int getIdFlat() {
        return idFlat;
    }

    public void setIdFlat(int idFlat) {
        this.idFlat = idFlat;
    }

    public String getDescFlat() {
        return descFlat;
    }

    public void setDescFlat(String descFlat) {
        this.descFlat = descFlat;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return descFlat;
    }
}
