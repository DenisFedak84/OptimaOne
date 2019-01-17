package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "GroupOfPotenza")
public class GroupOfPotenza extends BaseEntity {

    public static final String ID_POTENZA = "ID";
    public static final String GROUP_POTENZA = "groupPotenza";
    public static final String POTENZA_MIN = "potenzaMin";
    public static final String POTENZA_MAX = "potenzaMax";
    public static final String COEFFICIENTE_POTENZA = "coefficientePotenza";
    public static final String COEFFICIENTE_POTENZA_CONSUMI = "coefficientePotenzaConsumi";

    public GroupOfPotenza() {
    }

    public GroupOfPotenza(int ID, int groupPotenza, int potenzaMin, int potenzaMax, double coefficientePotenza, double coefficientePotenzaConsumi) {
        this.ID = ID;
        this.groupPotenza = groupPotenza;
        this.potenzaMin = potenzaMin;
        this.potenzaMax = potenzaMax;
        this.coefficientePotenza = coefficientePotenza;
        this.coefficientePotenzaConsumi = coefficientePotenzaConsumi;
    }

    @DatabaseField(id = true, columnName = ID_POTENZA, canBeNull = false, dataType = DataType.INTEGER)
    private int ID;
    @DatabaseField(columnName = GROUP_POTENZA, canBeNull = false, dataType = DataType.INTEGER)
    private int groupPotenza;
    @DatabaseField(columnName = POTENZA_MIN, canBeNull = false, dataType = DataType.INTEGER)
    private int potenzaMin;
    @DatabaseField(columnName = POTENZA_MAX, canBeNull = false, dataType = DataType.INTEGER)
    private int potenzaMax;
    @DatabaseField(columnName = COEFFICIENTE_POTENZA, canBeNull = false, dataType = DataType.DOUBLE)
    private double coefficientePotenza;
    @DatabaseField(columnName = COEFFICIENTE_POTENZA_CONSUMI, canBeNull = false, dataType = DataType.DOUBLE)
    private double coefficientePotenzaConsumi;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getGroupPotenza() {
        return groupPotenza;
    }

    public void setGroupPotenza(int groupPotenza) {
        this.groupPotenza = groupPotenza;
    }

    public int getPotenzaMin() {
        return potenzaMin;
    }

    public void setPotenzaMin(int potenzaMin) {
        this.potenzaMin = potenzaMin;
    }

    public int getPotenzaMax() {
        return potenzaMax;
    }

    public void setPotenzaMax(int potenzaMax) {
        this.potenzaMax = potenzaMax;
    }

    public double getCoefficientePotenza() {
        return coefficientePotenza;
    }

    public void setCoefficientePotenza(double coefficientePotenza) {
        this.coefficientePotenza = coefficientePotenza;
    }

    public double getCoefficientePotenzaConsumi() {
        return coefficientePotenzaConsumi;
    }

    public void setCoefficientePotenzaConsumi(double coefficientePotenzaConsumi) {
        this.coefficientePotenzaConsumi = coefficientePotenzaConsumi;
    }
}
