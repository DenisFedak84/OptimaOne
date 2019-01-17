package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.Date;

@DatabaseTable(tableName = "GasCurveConsumo")
public class GasCurveConsumo extends BaseEntity {

    public static final String GAS_CURVE_CONSUMO_ID = "gasCurveConsumoId";
    public static final String DATE = "data";
    public static final String CODICE = "codice";
    public static final String PERC = "perc";


    public GasCurveConsumo(int gasCurveConsumoId, String data, String codice, BigDecimal perc) {
        this.gasCurveConsumoId = gasCurveConsumoId;
        this.data = convertDate(data);
        this.codice = codice;
        this.perc = perc;
    }

    public GasCurveConsumo() {
    }

    @DatabaseField(id = true, columnName = GAS_CURVE_CONSUMO_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int gasCurveConsumoId;
    @DatabaseField(columnName = DATE, canBeNull = false, dataType = DataType.DATE)
    private Date data;
    @DatabaseField(columnName = CODICE, canBeNull = false, dataType = DataType.STRING)
    private String codice;
    @DatabaseField(columnName = PERC, canBeNull = false, dataType = DataType.BIG_DECIMAL)
    private BigDecimal perc;

    public Date getDate() {
        return data;
    }

    public int getGasCurveConsumoId() {
        return gasCurveConsumoId;
    }

    public void setGasCurveConsumoId(int gasCurveConsumoId) {
        this.gasCurveConsumoId = gasCurveConsumoId;
    }

    public void setDate(Date data) {
        this.data = data;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public BigDecimal getPerc() {
        return perc;
    }

    public void setPerc(BigDecimal perc) {
        this.perc = perc;
    }
}
