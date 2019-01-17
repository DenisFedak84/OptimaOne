package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;

@DatabaseTable(tableName = "CoeffTransport")
public class CoeffTransport extends BaseEntity {
    public static final String ID = "id";
    public static final String TARIFFA_TRASPORTO_ID = "tariffaTrasportoId";
    public static final String COEFFICIENTE_TARIFFA = "coefficienteTariffa";

    public CoeffTransport() {
    }

    public CoeffTransport(int id, int tariffaTrasportoId, BigDecimal coefficienteTariffa) {
        this.id = id;
        this.tariffaTrasportoId = tariffaTrasportoId;
        this.coefficienteTariffa = coefficienteTariffa;
    }

    @DatabaseField(id = true, columnName = ID, canBeNull = false, dataType = DataType.INTEGER)
    private int id;
    @DatabaseField(columnName = TARIFFA_TRASPORTO_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int tariffaTrasportoId;
    @DatabaseField(columnName = COEFFICIENTE_TARIFFA, canBeNull = false, dataType = DataType.BIG_DECIMAL)
    private BigDecimal coefficienteTariffa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTariffaTrasportoId() {
        return tariffaTrasportoId;
    }

    public void setTariffaTrasportoId(int tariffaTrasportoId) {
        this.tariffaTrasportoId = tariffaTrasportoId;
    }

    public BigDecimal getCoefficienteTariffa() {
        return coefficienteTariffa;
    }

    public void setCoefficienteTariffa(BigDecimal coefficienteTariffa) {
        this.coefficienteTariffa = coefficienteTariffa;
    }
}
