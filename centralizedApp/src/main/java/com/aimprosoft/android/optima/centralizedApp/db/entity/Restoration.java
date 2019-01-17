package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Restoration")
public class Restoration extends BaseEntity {

    public static final String ID_RESTORATION = "idRestoration";
    public static final String DESC_RESTORATION = "descRestoration";

    public Restoration(int idRestoration, String descRestoration) {
        this.idRestoration = idRestoration;
        this.descRestoration = descRestoration;
    }

    public Restoration() {
    }

    @DatabaseField(columnName = ID_RESTORATION, canBeNull = false, dataType = DataType.INTEGER)
    private int idRestoration;
    @DatabaseField(columnName = DESC_RESTORATION, canBeNull = false, dataType = DataType.STRING)
    private String descRestoration;

    public int getIdRestoration() {
        return idRestoration;
    }

    public void setIdRestoration(int idRestoration) {
        this.idRestoration = idRestoration;
    }

    public String getDescRestoration() {
        return descRestoration;
    }

    public void setDescRestoration(String descRestoration) {
        this.descRestoration = descRestoration;
    }

    @Override
    public String toString() {
        return descRestoration;
    }
}
