package com.aimprosoft.android.optima.centralizedApp.db.entity;

import android.database.Cursor;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "AdditionalNumbers")
public class AdditionalNumbers extends BaseEntity {

    public static final String ID_ADDITIONAL_NUMBER = "idAdditionalNumber";
    public static final String VALUE_ADDITIONAL_NUMBER = "valueAdditionalNumber";
    public static final String ADDITIONAL_NUMBER_COST = "additionalNumberCost";
    public static final String ADDITIONAL_NUMBER_COSTIVA = "additionalNumberCostIVA";
    public static final String RELAX = "relax";
    public static final String VERSION = "version";
    public static final String DISPLAY = "display";
    public static final String SYSTEM = "system";

    public AdditionalNumbers() {
    }

    public AdditionalNumbers(int idAdditionalNumber, int valueAdditionalNumber, double additionalNumberCost, double additionalNumberCostIVA, int relax, String version, String display, String system) {
        this.idAdditionalNumber = idAdditionalNumber;
        this.valueAdditionalNumber = valueAdditionalNumber;
        this.additionalNumberCost = additionalNumberCost;
        this.additionalNumberCostIVA = additionalNumberCostIVA;
        this.relax = relax;
        this.version = version;
        this.display = display;
        this.system = system;
    }

    @DatabaseField(columnName = ID_ADDITIONAL_NUMBER, canBeNull = false, dataType = DataType.INTEGER)
    private int idAdditionalNumber;

    @DatabaseField(columnName = VALUE_ADDITIONAL_NUMBER, canBeNull = false, dataType = DataType.INTEGER)
    private int valueAdditionalNumber;

    @DatabaseField(columnName = ADDITIONAL_NUMBER_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double additionalNumberCost;

    @DatabaseField(columnName = ADDITIONAL_NUMBER_COSTIVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double additionalNumberCostIVA;

    @DatabaseField(columnName = RELAX, canBeNull = false, dataType = DataType.INTEGER)
    private int relax;

    @DatabaseField(columnName = VERSION, canBeNull = false, dataType = DataType.STRING)
    private String version;

    @DatabaseField(columnName = DISPLAY, canBeNull = false, dataType = DataType.STRING)
    private String display;

    @DatabaseField(columnName = SYSTEM, canBeNull = true, dataType = DataType.STRING)
    private String system;

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public int getRelax() {
        return relax;
    }

    public void setRelax(int relax) {
        this.relax = relax;
    }

    public double getAdditionalNumberCostIVA() {
        return additionalNumberCostIVA;
    }

    public void setAdditionalNumberCostIVA(double additionalNumberCostIVA) {
        this.additionalNumberCostIVA = additionalNumberCostIVA;
    }

    public double getAdditionalNumberCost() {
        return additionalNumberCost;
    }

    public void setAdditionalNumberCost(double additionalNumberCost) {
        this.additionalNumberCost = additionalNumberCost;
    }

    public int getIdAdditionalNumber() {
        return idAdditionalNumber;
    }

    public void setIdAdditionalNumber(int idAdditionalNumber) {
        this.idAdditionalNumber = idAdditionalNumber;
    }

    public int getValueAdditionalNumber() {
        return valueAdditionalNumber;
    }

    public void setValueAdditionalNumber(int valueAdditionalNumber) {
        this.valueAdditionalNumber = valueAdditionalNumber;
    }

    @Override
    public String toString() {
        return ""+valueAdditionalNumber;
    }


    /**
     * For using with dbLite.db and ConfiguratoreEngine jar
     **/
    public static List<AdditionalNumbers> getListFromCursor(Cursor cursor) {
        List<AdditionalNumbers> additionalNumbersList = new ArrayList<>();
        if (cursor != null) {
            AdditionalNumbers additionalNumbers;
            while (cursor.moveToNext()) {
                additionalNumbers = new AdditionalNumbers();
                additionalNumbers.setValueAdditionalNumber(cursor.getInt(1));
                additionalNumbersList.add(additionalNumbers);
            }
        }
        return additionalNumbersList;
    }
}

