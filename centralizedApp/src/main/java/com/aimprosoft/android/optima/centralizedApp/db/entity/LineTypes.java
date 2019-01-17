package com.aimprosoft.android.optima.centralizedApp.db.entity;

import android.database.Cursor;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "LineTypes")
public class LineTypes extends BaseEntity {

    public static final String LINE_ID = "lineId";
    public static final String LINE_DESC = "lineDesc";
    public static final String LINE_DET = "lineDet";
    public static final String LINE_COST = "lineCost";
    public static final String LINE_COST_IVA = "lineCostIVA";
    public static final String CLIENT_TYPE = "clientType";
    public static final String ACTIVATION_COST = "activationCost";
    //    public static final String ACTIVATION_COST_ADDICTIONAL_NUMBERS = "activationCostAddictionalNumbers";
    public static final String ACTIVATION_COST_TLC = "activationCostTLC";
    public static final String RELAX = "relax";
    public static final String SYSTEM = "system";
    public static final String CODE = "code";

    public LineTypes() {
    }

    public LineTypes(int lineId, String lineDesc, String lineDet, double lineCost, double lineCostIVA,
                     Integer clientType, double activationCost, double activationCostTLC, int relax,
                     String system, String code) {
        this.lineId = lineId;
        this.lineDesc = lineDesc;
        this.lineDet = lineDet;
        this.lineCost = lineCost;
        this.lineCostIVA = lineCostIVA;
        this.clientType = clientType;
        this.activationCost = activationCost;
        this.activationCostTLC = activationCostTLC;
        this.relax = relax;
        this.system = system;
        this.code = code;
    }

    @DatabaseField(columnName = LINE_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int lineId;

    @DatabaseField(columnName = LINE_DESC, canBeNull = false, dataType = DataType.STRING)
    private String lineDesc;

    @DatabaseField(columnName = LINE_DET, canBeNull = false, dataType = DataType.STRING)
    private String lineDet;

    @DatabaseField(columnName = LINE_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double lineCost;

    @DatabaseField(columnName = LINE_COST_IVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double lineCostIVA;

    @DatabaseField(columnName = CLIENT_TYPE, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer clientType;

    @DatabaseField(columnName = ACTIVATION_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double activationCost;

    @DatabaseField(columnName = ACTIVATION_COST_TLC, canBeNull = false, dataType = DataType.DOUBLE)
    private double activationCostTLC;

    @DatabaseField(columnName = RELAX, canBeNull = false, dataType = DataType.INTEGER)
    private int relax;

    @DatabaseField(columnName = SYSTEM, canBeNull = true, dataType = DataType.STRING)
    private String system;

    @DatabaseField(columnName = CODE, canBeNull = true, dataType = DataType.STRING)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public int getRelax() {
        return relax;
    }

    public void setRelax(int relax) {
        this.relax = relax;
    }

    public double getActivationCostTLC() {
        return activationCostTLC;
    }

    public void setActivationCostTLC(double activationCostTLC) {
        this.activationCostTLC = activationCostTLC;
    }

    public double getLineCostIVA() {
        return lineCostIVA;
    }

    public void setLineCostIVA(double lineCostIVA) {
        this.lineCostIVA = lineCostIVA;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getLineDesc() {
        return lineDesc;
    }

    public void setLineDesc(String lineDesc) {
        this.lineDesc = lineDesc;
    }

    public String getLineDet() {
        return lineDet;
    }

    public void setLineDet(String lineDet) {
        this.lineDet = lineDet;
    }

    public double getLineCost() {
        return lineCost;
    }

    public void setLineCost(double lineCost) {
        this.lineCost = lineCost;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public double getActivationCost() {
        return activationCost;
    }

    public void setActivationCost(double activationCost) {
        this.activationCost = activationCost;
    }

    @Override
    public String toString() {
        return lineDet;
    }

    /**
     * For using with dbLite.db and ConfiguratoreEngine jar
     **/
    public static List<LineTypes> getListFromCursor(Cursor cursor) {
        List<LineTypes> lineTypesList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                lineTypesList.add(parseLineType(cursor));
            }
        }
        return lineTypesList;
    }

    private static LineTypes parseLineType(Cursor cursor) {
        LineTypes lineTypes = new LineTypes();
        lineTypes.setLineId(cursor.getInt(0));
        lineTypes.setLineDesc(cursor.getString(1));
        lineTypes.setCode(cursor.getString(2));
        lineTypes.setLineDet(cursor.getString(3));
        lineTypes.setLineCost(cursor.getDouble(4));
        lineTypes.setLineCostIVA(cursor.getDouble(5));
        lineTypes.setActivationCost(cursor.getDouble(6));
        return lineTypes;
    }

    public static LineTypes getLineTypeFromCursor(Cursor cursor) {
        LineTypes lineTypes;
        if (cursor != null && cursor.moveToNext()) {
            lineTypes = parseLineType(cursor);
        } else {
            lineTypes = new LineTypes();
        }
        return lineTypes;
    }
}
