package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Tariffa")
public class Tariffa extends BaseEntity {

    public static final String TARIFFA_ID = "tariffaId";
    public static final String TARIFFA_DESC = "tariffaDesc";

    public Tariffa() {
    }

    public Tariffa(int tariffaId, String tariffaDesc) {
        this.tariffaId = tariffaId;
        this.tariffaDesc = tariffaDesc;
    }

    @DatabaseField(id = true, columnName = TARIFFA_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int tariffaId;
    @DatabaseField(columnName = TARIFFA_DESC, canBeNull = false, dataType = DataType.STRING)
    private String tariffaDesc;

    public int getTariffaId() {
        return tariffaId;
    }

    public void setTariffaId(int tariffaId) {
        this.tariffaId = tariffaId;
    }

    public String getTariffaDesc() {
        return tariffaDesc;
    }

    public void setTariffaDesc(String tariffaDesc) {
        this.tariffaDesc = tariffaDesc;
    }

    @Override
    public String toString() {
        return tariffaDesc;
    }
}
