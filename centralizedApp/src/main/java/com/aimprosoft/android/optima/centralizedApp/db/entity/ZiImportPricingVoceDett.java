package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ZiImportPricingVoceDett")
public class ZiImportPricingVoceDett extends BaseEntity {

    public static final String ID_CLIENTE = "idCliente";
    public static final String ID_DIALER = "idDialer";
    public static final String ID_DIALER_RIFERIMENTO = "idDialerRiferimento";
    public static final String DIALER = "dialer";
    public static final String ID_PADRE = "idPadre";
    public static final String RETE = "rete";
    public static final String TIPOLOGIA_LINEA = "tipologiaLinea";
    public static final String NUM_AGGIUNTIVI = "numAggiuntivi";
    public static final String FLAG_PRINCIPALE = "flagPrincipale";
    public static final String NUM = "num";
    public static final String DIALER_PADRE = "dialerPadre";

    public ZiImportPricingVoceDett() {
    }

    public ZiImportPricingVoceDett(int idCliente, int idDialer, Integer idDialerRiferimento, String dialer,
                                   int idPadre, String rete, String tipologiaLinea, int numAggiuntivi,
                                   int flagPrincipale, int num, String dialerPadre) {
        this.idCliente = idCliente;
        this.idDialer = idDialer;
        this.idDialerRiferimento = idDialerRiferimento;
        this.dialer = dialer;
        this.idPadre = idPadre;
        this.rete = rete;
        this.tipologiaLinea = tipologiaLinea;
        this.numAggiuntivi = numAggiuntivi;
        this.flagPrincipale = flagPrincipale;
        this.num = num;
        this.dialerPadre = dialerPadre;
    }

    @DatabaseField(columnName = ID_CLIENTE, canBeNull = false, dataType = DataType.INTEGER)
    private int idCliente;
    @DatabaseField(columnName = ID_DIALER, canBeNull = false, dataType = DataType.INTEGER)
    private int idDialer;
    @DatabaseField(columnName = ID_DIALER_RIFERIMENTO, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer idDialerRiferimento;
    @DatabaseField(columnName = DIALER, canBeNull = false, dataType = DataType.STRING)
    private String dialer;
    @DatabaseField(columnName = ID_PADRE, canBeNull = false, dataType = DataType.INTEGER)
    private int idPadre;
    @DatabaseField(columnName = RETE, canBeNull = false, dataType = DataType.STRING)
    private String rete;
    @DatabaseField(columnName = TIPOLOGIA_LINEA, canBeNull = false, dataType = DataType.STRING)
    private String tipologiaLinea;
    @DatabaseField(columnName = NUM_AGGIUNTIVI, canBeNull = false, dataType = DataType.INTEGER)
    private int numAggiuntivi;
    @DatabaseField(columnName = FLAG_PRINCIPALE, canBeNull = false, dataType = DataType.INTEGER)
    private int flagPrincipale;
    @DatabaseField(columnName = NUM, canBeNull = false, dataType = DataType.INTEGER)
    private int num;
    @DatabaseField(columnName = DIALER_PADRE, canBeNull = false, dataType = DataType.STRING)
    private String dialerPadre;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdDialer() {
        return idDialer;
    }

    public void setIdDialer(int idDialer) {
        this.idDialer = idDialer;
    }

    public int getIdDialerRiferimento() {
        return idDialerRiferimento;
    }

    public void setIdDialerRiferimento(int idDialerRiferimento) {
        this.idDialerRiferimento = idDialerRiferimento;
    }

    public String getDialer() {
        return dialer;
    }

    public void setDialer(String dialer) {
        this.dialer = dialer;
    }

    public int getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(int idPadre) {
        this.idPadre = idPadre;
    }

    public String getRete() {
        return rete;
    }

    public void setRete(String rete) {
        this.rete = rete;
    }

    public String getTipologiaLinea() {
        return tipologiaLinea;
    }

    public void setTipologiaLinea(String tipologiaLinea) {
        this.tipologiaLinea = tipologiaLinea;
    }

    public int getNumAggiuntivi() {
        return numAggiuntivi;
    }

    public void setNumAggiuntivi(int numAggiuntivi) {
        this.numAggiuntivi = numAggiuntivi;
    }

    public int getFlagPrincipale() {
        return flagPrincipale;
    }

    public void setFlagPrincipale(int flagPrincipale) {
        this.flagPrincipale = flagPrincipale;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDialerPadre() {
        return dialerPadre;
    }

    public void setDialerPadre(String dialerPadre) {
        this.dialerPadre = dialerPadre;
    }
}
