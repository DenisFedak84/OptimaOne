package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Clients")
public class Clients extends BaseEntity {

    public static final String CLIENT_ID = "clientId";
    public static final String NAME = "name";
    public static final String PIVA = "piva";
    public static final String CLIENT_CODE_SIN = "clientCodeSin";
    public static final String DISPLAY = "display";

    public Clients() {
    }

    public Clients(int clientId, String name, String piva, int clientCodeSin, int display) {
        this.clientId = clientId;
        this.name = name;
        this.piva = piva;
        this.clientCodeSin = clientCodeSin;
        this.display = display;
    }

    @DatabaseField(id = true, columnName = CLIENT_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int clientId;
    @DatabaseField(columnName = NAME, canBeNull = false, dataType = DataType.STRING)
    private String name;
    @DatabaseField(columnName = PIVA, canBeNull = false, dataType = DataType.STRING)
    private String piva;
    @DatabaseField(columnName = CLIENT_CODE_SIN, canBeNull = false, dataType = DataType.INTEGER)
    private int clientCodeSin;
    @DatabaseField(columnName = DISPLAY, canBeNull = false, dataType = DataType.INTEGER)
    private int display;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public int getClientCodeSin() {
        return clientCodeSin;
    }

    public void setClientCodeSin(int clientCodeSin) {
        this.clientCodeSin = clientCodeSin;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

//    public String toString(int targetViewId, Clients clients) {
//        String desc;
//        switch (targetViewId) {
//            case R.id.ragioneSocialeAutocomplete:
//                desc = clients.getName();
//                break;
//            case R.id.piva:
//                desc = clients.getPiva();
//                break;
//            default:
//                desc = "";
//                break;
//        }
//        return desc;
//    }
    public String toString(int targetViewId) {
        String desc;
        switch (targetViewId) {
            case R.id.ragioneSocialeAutocomplete:
                desc = name;
                break;
            case R.id.pivaAutocomplete:
                desc = piva;
                break;
            default:
                desc = "";
                break;
        }
        return desc;
    }
}
