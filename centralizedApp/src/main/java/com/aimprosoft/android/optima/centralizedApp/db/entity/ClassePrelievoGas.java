package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ClassePrelievoGas")
public class ClassePrelievoGas extends BaseEntity {

    public static final String ID_CLASSE_PRELIEVO = "idClassePrelievo";
    public static final String CLASSE_PRELIEVO = "classePrelievo";

    public ClassePrelievoGas() {
    }

    public ClassePrelievoGas(int idClassePrelievo, String classePrelievo) {
        this.idClassePrelievo = idClassePrelievo;
        this.classePrelievo = classePrelievo;
    }

    @DatabaseField(columnName = ID_CLASSE_PRELIEVO, canBeNull = false, dataType = DataType.INTEGER)
    private int idClassePrelievo;
    @DatabaseField(columnName = CLASSE_PRELIEVO, canBeNull = false, dataType = DataType.STRING)
    private String classePrelievo;

    public String getClassePrelievo() {
        return classePrelievo;
    }

    public void setClassePrelievo(String classePrelievo) {
        this.classePrelievo = classePrelievo;
    }

    public int getIdClassePrelievo() {
        return idClassePrelievo;
    }

    public void setIdClassePrelievo(int idClassePrelievo) {
        this.idClassePrelievo = idClassePrelievo;
    }

    @Override
    public String toString() {
        return getClassePrelievo();
    }
}
