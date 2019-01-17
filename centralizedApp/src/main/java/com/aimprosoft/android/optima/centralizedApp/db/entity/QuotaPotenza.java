package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "QuotaPotenza")
public class QuotaPotenza extends BaseEntity {
    public static final String ID = "id";
    public static final String CLASS_POTENZA_INF = "classPotenzaInf";
    public static final String CLASS_POTENZA_SUP = "classPotenzaSup";
    public static final String QPSR = "qpsr";
    public static final String QPOG = "qpog";

    public QuotaPotenza() {
    }

    public QuotaPotenza(int id, double classPotenzaInf, double classPotenzaSup, double qpsr, double qpog) {
        this.id = id;
        this.classPotenzaInf = classPotenzaInf;
        this.classPotenzaSup = classPotenzaSup;
        this.qpsr = qpsr;
        this.qpog = qpog;
    }

    @DatabaseField(id = true, columnName = ID, canBeNull = false, dataType = DataType.INTEGER)
    private int id;
    @DatabaseField(columnName = CLASS_POTENZA_INF, canBeNull = false, dataType = DataType.DOUBLE)
    private double classPotenzaInf;
    @DatabaseField(columnName = CLASS_POTENZA_SUP, canBeNull = false, dataType = DataType.DOUBLE)
    private double classPotenzaSup;
    @DatabaseField(columnName = QPSR, canBeNull = false, dataType = DataType.DOUBLE)
    private double qpsr;
    @DatabaseField(columnName = QPOG, canBeNull = false, dataType = DataType.DOUBLE)
    private double qpog;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getClassPotenzaInf() {
        return classPotenzaInf;
    }

    public void setClassPotenzaInf(double classPotenzaInf) {
        this.classPotenzaInf = classPotenzaInf;
    }

    public double getClassPotenzaSup() {
        return classPotenzaSup;
    }

    public void setClassPotenzaSup(double classPotenzaSup) {
        this.classPotenzaSup = classPotenzaSup;
    }

    public double getQpsr() {
        return qpsr;
    }

    public void setQpsr(double qpsr) {
        this.qpsr = qpsr;
    }

    public double getQpog() {
        return qpog;
    }

    public void setQpog(double qpog) {
        this.qpog = qpog;
    }
}
