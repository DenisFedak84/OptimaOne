package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "GasProfiliUtilizzo")
public class GasProfiliUtilizzo extends BaseEntity {

    public static final String PROFILI_UTILIZZO_ID = "profiliUtilizzoId";
    public static final String CATEGORIAUSO = "categoriaUso";
    public static final String ZONA_CLIMATICA = "zonaClimatica";
    public static final String CLASSE_PRELIEVO = "classePrelievo";
    public static final String PROFILO_UTILIZZO = "profiloUtilizzo";

    public GasProfiliUtilizzo(int profiliUtilizzoId, String categoriaUso, String zonaClimatica, int classePrelievo, String profiloUtilizzo) {
        this.profiliUtilizzoId = profiliUtilizzoId;
        this.categoriaUso = categoriaUso;
        this.zonaClimatica = zonaClimatica;
        this.classePrelievo = classePrelievo;
        this.profiloUtilizzo = profiloUtilizzo;
    }

    public GasProfiliUtilizzo() {
    }

    @DatabaseField(columnName = PROFILI_UTILIZZO_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int profiliUtilizzoId;
    @DatabaseField(columnName = CATEGORIAUSO, canBeNull = false, dataType = DataType.STRING)
    private String categoriaUso;
    @DatabaseField(columnName = ZONA_CLIMATICA, canBeNull = false, dataType = DataType.STRING)
    private String zonaClimatica;
    @DatabaseField(columnName = CLASSE_PRELIEVO, canBeNull = false, dataType = DataType.INTEGER)
    private int classePrelievo;
    @DatabaseField(columnName = PROFILO_UTILIZZO, canBeNull = false, dataType = DataType.STRING)
    private String profiloUtilizzo;

    public int getProfiliUtilizzoId() {
        return profiliUtilizzoId;
    }

    public void setProfiliUtilizzoId(int profiliUtilizzoId) {
        this.profiliUtilizzoId = profiliUtilizzoId;
    }

    public String getCategoriaUso() {
        return categoriaUso;
    }

    public void setCategoriaUso(String categoriaUso) {
        this.categoriaUso = categoriaUso;
    }

    public String getZonaClimatica() {
        return zonaClimatica;
    }

    public void setZonaClimatica(String zonaClimatica) {
        this.zonaClimatica = zonaClimatica;
    }

    public int getClassePrelievo() {
        return classePrelievo;
    }

    public void setClassePrelievo(int classePrelievo) {
        this.classePrelievo = classePrelievo;
    }

    public String getProfiloUtilizzo() {
        return profiloUtilizzo;
    }

    public void setProfiloUtilizzo(String profiloUtilizzo) {
        this.profiloUtilizzo = profiloUtilizzo;
    }
}
