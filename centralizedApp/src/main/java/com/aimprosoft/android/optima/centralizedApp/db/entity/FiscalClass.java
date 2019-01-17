package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "FiscalClass")
public class FiscalClass extends BaseEntity {

    public static final String ID_FISCAL_CLASS = "idFiscalClass";
    public static final String DESC_FISCAL_CLASS = "descFiscalClass";
    public static final String CODE = "code";

    public FiscalClass() {
    }

    public FiscalClass(int idFiscalClass, String descFiscalClass) {
        this.idFiscalClass = idFiscalClass;
        this.descFiscalClass = descFiscalClass;
    }

    @DatabaseField(id = true, columnName = ID_FISCAL_CLASS, canBeNull = false, dataType = DataType.INTEGER)
    private int idFiscalClass;
    @DatabaseField(columnName = DESC_FISCAL_CLASS, canBeNull = false, dataType = DataType.STRING)
    private String descFiscalClass;
    @DatabaseField(columnName = CODE, canBeNull = false, dataType = DataType.STRING)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIdFiscalClass() {
        return idFiscalClass;
    }

    public void setIdFiscalClass(int idFiscalClass) {
        this.idFiscalClass = idFiscalClass;
    }

    public String getDescFiscalClass() {
        return descFiscalClass;
    }

    public void setDescFiscalClass(String descFiscalClass) {
        this.descFiscalClass = descFiscalClass;
    }
}
