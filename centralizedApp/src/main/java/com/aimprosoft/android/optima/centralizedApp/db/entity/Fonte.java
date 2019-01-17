package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Fonte")
public class Fonte extends BaseEntity{

    public static final String FONTE_ID = "fonteId";
    public static final String FONTE_DESC = "fonteDesc";

    public Fonte() {
    }

    @DatabaseField(columnName = FONTE_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int fonteId;
    @DatabaseField(columnName = FONTE_DESC, canBeNull = false, dataType = DataType.STRING)
    private String fonteDesc;

    public Fonte(int fonteId, String fonteDesc) {
        this.fonteId = fonteId;
        this.fonteDesc = fonteDesc;
    }

    public int getFonteId() {
        return fonteId;
    }

    public void setFonteId(int fonteId) {
        this.fonteId = fonteId;
    }

    public String getFonteDesc() {
        return fonteDesc;
    }

    public void setFonteDesc(String fonteDesc) {
        this.fonteDesc = fonteDesc;
    }
}
