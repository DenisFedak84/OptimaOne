package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TownsCap")
public class TownsCap extends BaseEntity {
    public static final String ID = "id";
    public static final String TOWN_ID = "townId";
    public static final String CAP = "cap";

    public TownsCap() {
    }

    public TownsCap(int id, Integer townId, String cap) {
        this.id = id;
        this.townId = townId;
        this.cap = cap;
    }

    @DatabaseField(id = true, columnName = ID, canBeNull = false, dataType = DataType.INTEGER)
    private int id;
    @DatabaseField(columnName = TOWN_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer townId;
    @DatabaseField(columnName = CAP, canBeNull = false, dataType = DataType.STRING)
    private String cap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }
}
