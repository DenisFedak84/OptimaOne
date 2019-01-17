package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "User")
public class User extends BaseEntity {
    public static final String USER_ID = "userId";
    public static final String CRITERIO_PWD = "CriterioPwd";
    public static final String ID_SOGGETTO = "IdSoggetto";
    public static final String LAST_CHANGE_PWD = "LastChangePwd";
    public static final String PAGINAINIZIALE = "PaginaIniziale";
    public static final String TIPO_SOGGETTO = "TipoSoggetto";
    public static final String USERNAME = "Username";
    public static final String VALIDITA_PWD = "ValiditaPwd";
    public static final String DESC_UTENTE = "DescUtente";
    public static final String IS_CAPO_AREA = "IsCapoArea";

    public User() {
    }

    public User(int userId, String criterioPwd, String idSoggetto, String lastChangePwd,
                String paginaIniziale, String tipoSoggetto, String username, String validitaPwd, String descUtente, String isCapoArea) {
        this.userId = userId;
        CriterioPwd = criterioPwd;
        IdSoggetto = idSoggetto;
        LastChangePwd = lastChangePwd;
        PaginaIniziale = paginaIniziale;
        TipoSoggetto = tipoSoggetto;
        Username = username;
        ValiditaPwd = validitaPwd;
        DescUtente = descUtente;
        IsCapoArea = isCapoArea;
    }

    @DatabaseField(id = true, columnName = USER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int userId;
    @DatabaseField(columnName = CRITERIO_PWD, canBeNull = true, dataType = DataType.STRING)
    private String CriterioPwd;
    @DatabaseField(columnName = ID_SOGGETTO, canBeNull = true, dataType = DataType.STRING)
    private String IdSoggetto;
    @DatabaseField(columnName = LAST_CHANGE_PWD, canBeNull = true, dataType = DataType.STRING)
    private String LastChangePwd;
    @DatabaseField(columnName = PAGINAINIZIALE, canBeNull = true, dataType = DataType.STRING)
    private String PaginaIniziale;
    @DatabaseField(columnName = TIPO_SOGGETTO, canBeNull = true, dataType = DataType.STRING)
    private String TipoSoggetto;
    @DatabaseField(columnName = USERNAME, canBeNull = true, dataType = DataType.STRING)
    private String Username;
    @DatabaseField(columnName = VALIDITA_PWD, canBeNull = true, dataType = DataType.STRING)
    private String ValiditaPwd;
    @DatabaseField(columnName = DESC_UTENTE, canBeNull = true, dataType = DataType.STRING)
    private String DescUtente;
    @DatabaseField(columnName = IS_CAPO_AREA, canBeNull = true, dataType = DataType.STRING)
    private String IsCapoArea;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCriterioPwd() {
        return CriterioPwd;
    }

    public void setCriterioPwd(String criterioPwd) {
        CriterioPwd = criterioPwd;
    }

    public String getIdSoggetto() {
        return IdSoggetto;
    }

    public void setIdSoggetto(String idSoggetto) {
        IdSoggetto = idSoggetto;
    }

    public String getLastChangePwd() {
        return LastChangePwd;
    }

    public void setLastChangePwd(String lastChangePwd) {
        LastChangePwd = lastChangePwd;
    }

    public String getPaginaIniziale() {
        return PaginaIniziale;
    }

    public void setPaginaIniziale(String paginaIniziale) {
        PaginaIniziale = paginaIniziale;
    }

    public String getTipoSoggetto() {
        return TipoSoggetto;
    }

    public void setTipoSoggetto(String tipoSoggetto) {
        TipoSoggetto = tipoSoggetto;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getValiditaPwd() {
        return ValiditaPwd;
    }

    public void setValiditaPwd(String validitaPwd) {
        ValiditaPwd = validitaPwd;
    }

    public String getDescUtente() {
        return DescUtente;
    }

    public void setDescUtente(String descUtente) {
        DescUtente = descUtente;
    }

    public String getIsCapoArea() {
        return IsCapoArea;
    }

    public void setIsCapoArea(String isCapoArea) {
        IsCapoArea = isCapoArea;
    }
}
