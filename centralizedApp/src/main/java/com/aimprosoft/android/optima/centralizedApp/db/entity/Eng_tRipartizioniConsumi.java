package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Eng_tRipartizioniConsumi")
public class Eng_tRipartizioniConsumi extends BaseEntity {

    public static final String RC_ID = "rc_id";
    public static final String RC_FASCIA = "rc_fascia";
    public static final String RC_DTVAL = "rc_dtval";
    public static final String RC_DESC = "rc_desc";
    public static final String RC_PERC_MESE1 = "rc_perc_mese1";
    public static final String RC_PERC_MESE2 = "rc_perc_mese2";
    public static final String RC_PERC_MESE3 = "rc_perc_mese3";
    public static final String RC_PERC_MESE4 = "rc_perc_mese4";
    public static final String RC_PERC_MESE5 = "rc_perc_mese5";
    public static final String RC_PERC_MESE6 = "rc_perc_mese6";
    public static final String RC_PERC_MESE7 = "rc_perc_mese7";
    public static final String RC_PERC_MESE8 = "rc_perc_mese8";
    public static final String RC_PERC_MESE9 = "rc_perc_mese9";
    public static final String RC_PERC_MESE10 = "rc_perc_mese10";
    public static final String RC_PERC_MESE11 = "rc_perc_mese11";
    public static final String RC_PERC_MESE12 = "rc_perc_mese12";

    public Eng_tRipartizioniConsumi(int rc_id, int rc_fascia, String rc_dtval, String rc_desc, double rc_perc_mese1, double rc_perc_mese2, double rc_perc_mese3, double rc_perc_mese4, double rc_perc_mese5, double rc_perc_mese6, double rc_perc_mese7, double rc_perc_mese8, double rc_perc_mese9, double rc_perc_mese10, double rc_perc_mese11, double rc_perc_mese12) {
        this.rc_id = rc_id;
        this.rc_fascia = rc_fascia;
        this.rc_dtval = convertDate(rc_dtval);
        this.rc_desc = rc_desc;
        this.rc_perc_mese1 = rc_perc_mese1;
        this.rc_perc_mese2 = rc_perc_mese2;
        this.rc_perc_mese3 = rc_perc_mese3;
        this.rc_perc_mese4 = rc_perc_mese4;
        this.rc_perc_mese5 = rc_perc_mese5;
        this.rc_perc_mese6 = rc_perc_mese6;
        this.rc_perc_mese7 = rc_perc_mese7;
        this.rc_perc_mese8 = rc_perc_mese8;
        this.rc_perc_mese9 = rc_perc_mese9;
        this.rc_perc_mese10 = rc_perc_mese10;
        this.rc_perc_mese11 = rc_perc_mese11;
        this.rc_perc_mese12 = rc_perc_mese12;
    }

    public Eng_tRipartizioniConsumi() {
    }

    @DatabaseField(columnName = RC_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int rc_id;
    @DatabaseField(columnName = RC_FASCIA, canBeNull = false, dataType = DataType.INTEGER)
    private int rc_fascia;
    @DatabaseField(columnName = RC_DTVAL, canBeNull = false, dataType = DataType.DATE)
    private Date rc_dtval;
    @DatabaseField(columnName = RC_DESC, canBeNull = false, dataType = DataType.STRING)
    private String rc_desc;
    @DatabaseField(columnName = RC_PERC_MESE1, canBeNull = false, dataType = DataType.DOUBLE)
    private double rc_perc_mese1;
    @DatabaseField(columnName = RC_PERC_MESE2, canBeNull = false, dataType = DataType.DOUBLE)
    private double rc_perc_mese2;
    @DatabaseField(columnName = RC_PERC_MESE3, canBeNull = false, dataType = DataType.DOUBLE)
    private double rc_perc_mese3;
    @DatabaseField(columnName = RC_PERC_MESE4, canBeNull = false, dataType = DataType.DOUBLE)
    private double rc_perc_mese4;
    @DatabaseField(columnName = RC_PERC_MESE5, canBeNull = false, dataType = DataType.DOUBLE)
    private double rc_perc_mese5;
    @DatabaseField(columnName = RC_PERC_MESE6, canBeNull = false, dataType = DataType.DOUBLE)
    private double rc_perc_mese6;
    @DatabaseField(columnName = RC_PERC_MESE7, canBeNull = false, dataType = DataType.DOUBLE)
    private double rc_perc_mese7;
    @DatabaseField(columnName = RC_PERC_MESE8, canBeNull = false, dataType = DataType.DOUBLE)
    private double rc_perc_mese8;
    @DatabaseField(columnName = RC_PERC_MESE9, canBeNull = false, dataType = DataType.DOUBLE)
    private double rc_perc_mese9;
    @DatabaseField(columnName = RC_PERC_MESE10, canBeNull = false, dataType = DataType.DOUBLE)
    private double rc_perc_mese10;
    @DatabaseField(columnName = RC_PERC_MESE11, canBeNull = false, dataType = DataType.DOUBLE)
    private double rc_perc_mese11;
    @DatabaseField(columnName = RC_PERC_MESE12, canBeNull = false, dataType = DataType.DOUBLE)
    private double rc_perc_mese12;


    public int getRc_id() {
        return rc_id;
    }

    public void setRc_id(int rc_id) {
        this.rc_id = rc_id;
    }

    public int getRc_fascia() {
        return rc_fascia;
    }

    public void setRc_fascia(int rc_fascia) {
        this.rc_fascia = rc_fascia;
    }

    public Date getRc_dtval() {
        return rc_dtval;
    }

    public void setRc_dtval(Date rc_dtval) {
        this.rc_dtval = rc_dtval;
    }

    public String getRc_desc() {
        return rc_desc;
    }

    public void setRc_desc(String rc_desc) {
        this.rc_desc = rc_desc;
    }

    public double getRc_perc_mese1() {
        return rc_perc_mese1;
    }

    public void setRc_perc_mese1(double rc_perc_mese1) {
        this.rc_perc_mese1 = rc_perc_mese1;
    }

    public double getRc_perc_mese2() {
        return rc_perc_mese2;
    }

    public void setRc_perc_mese2(double rc_perc_mese2) {
        this.rc_perc_mese2 = rc_perc_mese2;
    }

    public double getRc_perc_mese3() {
        return rc_perc_mese3;
    }

    public void setRc_perc_mese3(double rc_perc_mese3) {
        this.rc_perc_mese3 = rc_perc_mese3;
    }

    public double getRc_perc_mese4() {
        return rc_perc_mese4;
    }

    public void setRc_perc_mese4(double rc_perc_mese4) {
        this.rc_perc_mese4 = rc_perc_mese4;
    }

    public double getRc_perc_mese5() {
        return rc_perc_mese5;
    }

    public void setRc_perc_mese5(double rc_perc_mese5) {
        this.rc_perc_mese5 = rc_perc_mese5;
    }

    public double getRc_perc_mese6() {
        return rc_perc_mese6;
    }

    public void setRc_perc_mese6(double rc_perc_mese6) {
        this.rc_perc_mese6 = rc_perc_mese6;
    }

    public double getRc_perc_mese7() {
        return rc_perc_mese7;
    }

    public void setRc_perc_mese7(double rc_perc_mese7) {
        this.rc_perc_mese7 = rc_perc_mese7;
    }

    public double getRc_perc_mese8() {
        return rc_perc_mese8;
    }

    public void setRc_perc_mese8(double rc_perc_mese8) {
        this.rc_perc_mese8 = rc_perc_mese8;
    }

    public double getRc_perc_mese9() {
        return rc_perc_mese9;
    }

    public void setRc_perc_mese9(double rc_perc_mese9) {
        this.rc_perc_mese9 = rc_perc_mese9;
    }

    public double getRc_perc_mese10() {
        return rc_perc_mese10;
    }

    public void setRc_perc_mese10(double rc_perc_mese10) {
        this.rc_perc_mese10 = rc_perc_mese10;
    }

    public double getRc_perc_mese11() {
        return rc_perc_mese11;
    }

    public void setRc_perc_mese11(double rc_perc_mese11) {
        this.rc_perc_mese11 = rc_perc_mese11;
    }

    public double getRc_perc_mese12() {
        return rc_perc_mese12;
    }

    public void setRc_perc_mese12(double rc_perc_mese12) {
        this.rc_perc_mese12 = rc_perc_mese12;
    }
}
