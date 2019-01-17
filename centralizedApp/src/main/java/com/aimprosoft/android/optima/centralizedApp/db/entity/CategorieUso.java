package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "CategorieUso")
public class CategorieUso extends BaseEntity {

    public static final String ID_CATEGORIA_USO = "idCategoriaUso";
    public static final String DESC_CATEGORIA_USO = "descCategoriaUso";
    public static final String CODICE_CATEGORIA = "codiceCategoria";
    public static final String SYSTEM = "system";

    public CategorieUso() {
    }

    public CategorieUso(int idCategoriaUso, String descCategoriaUso, String codiceCategoria, String system) {
        this.idCategoriaUso = idCategoriaUso;
        this.descCategoriaUso = descCategoriaUso;
        this.codiceCategoria = codiceCategoria;
        this.system = system;
    }

    @DatabaseField(columnName = ID_CATEGORIA_USO, canBeNull = false, dataType = DataType.INTEGER)
    private int idCategoriaUso;
    @DatabaseField(columnName = DESC_CATEGORIA_USO, canBeNull = false, dataType = DataType.STRING)
    private String descCategoriaUso;
    @DatabaseField(columnName = CODICE_CATEGORIA, canBeNull = false, dataType = DataType.STRING)
    private String codiceCategoria;
    @DatabaseField(columnName = SYSTEM, canBeNull = true, dataType = DataType.STRING)
    private String system;

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public int getIdCategoriaUso() {
        return idCategoriaUso;
    }

    public void setIdCategoriaUso(int idCategoriaUso) {
        this.idCategoriaUso = idCategoriaUso;
    }

    public String getDescCategoriaUso() {
        return descCategoriaUso;
    }

    public void setDescCategoriaUso(String descCategoriaUso) {
        this.descCategoriaUso = descCategoriaUso;
    }

    public String getCodiceCategoria() {
        return codiceCategoria;
    }

    public void setCodiceCategoria(String codiceCategoria) {
        this.codiceCategoria = codiceCategoria;
    }

    @Override
    public String toString() {
        return getDescCategoriaUso();
    }
}
