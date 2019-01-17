package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "LightingTypes")
public class LightingTypes extends BaseEntity {

    public static final String ID_TYPE = "idType";
    public static final String DESC_TYPE = "descType";

    public LightingTypes() {
    }

    public LightingTypes(int idType, String descType) {
        this.idType = idType;
        this.descType = descType;
    }

    @DatabaseField(columnName = ID_TYPE, canBeNull = false, dataType = DataType.INTEGER)
    private int idType;
    @DatabaseField(columnName =  DESC_TYPE, canBeNull = false, dataType = DataType.STRING)
    private String descType;

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getDescType() {
        return descType;
    }

    public void setDescType(String descType) {
        this.descType = descType;
    }

    @Override
    public String toString() {
        return descType;
    }
}
