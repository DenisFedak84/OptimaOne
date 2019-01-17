package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "LineNumbers")
public class LineNumbers extends BaseEntity {

    public static final String LINE_NUMBER_ID = "lineNumberId";
    public static final String LINE_NUMBER_DESC = "lineNumberDesc";

    public LineNumbers() {
    }

    public LineNumbers(int lineNumberId, int lineNumberDesc) {
        this.lineNumberId = lineNumberId;
        this.lineNumberDesc = lineNumberDesc;
    }

    @DatabaseField(id = true, columnName = LINE_NUMBER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int lineNumberId;

    @DatabaseField(columnName = LINE_NUMBER_DESC, canBeNull = false, dataType = DataType.INTEGER)
    private int lineNumberDesc;

    public int getLineNumberId() {
        return lineNumberId;
    }

    public void setLineNumberId(int lineNumberId) {
        this.lineNumberId = lineNumberId;
    }

    public int getLineNumberDesc() {
        return lineNumberDesc;
    }

    public void setLineNumberDesc(int lineNumberDesc) {
        this.lineNumberDesc = lineNumberDesc;
    }

    @Override
    public String toString() {
        return String.valueOf(getLineNumberDesc());
    }
}
