package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "LoginEntity")
public class LoginEntity extends BaseEntity {
    public static final String ENTITY_ID = "entityId";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String DATE_OF_LOGIN = "dateOfLogin";

    public LoginEntity() {
    }

    public LoginEntity(String login, String password, Date dateOfLogin) {
        this.login = login;
        this.password = password;
        this.dateOfLogin = dateOfLogin;
    }

    @DatabaseField(id = true, columnName = ENTITY_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int entityId;
    @DatabaseField(columnName = LOGIN, canBeNull = false, dataType = DataType.STRING)
    private String login;
    @DatabaseField(columnName = PASSWORD, canBeNull = false, dataType = DataType.STRING)
    private String password;
    @DatabaseField(columnName = DATE_OF_LOGIN, canBeNull = false, dataType = DataType.DATE)
    private Date dateOfLogin;

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfLogin() {
        return dateOfLogin;
    }

    public void setDateOfLogin(Date dateOfLogin) {
        this.dateOfLogin = dateOfLogin;
    }
}
