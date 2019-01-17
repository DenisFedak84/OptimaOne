package com.aimprosoft.android.optima.centralizedApp.db.entity;

public class EnergyExitResponseModel extends BaseEntity {
    private int Esito;
    private Ammissibilita ammissibilita;
    private String cod_servizio;
    private int cod_flusso;
    private String Dettaglio_Esito;


    public EnergyExitResponseModel() {
    }

    public EnergyExitResponseModel(int esito, Ammissibilita ammissibilita, String cod_servizio, int cod_flusso, String dettaglio_Esito) {
        Esito = esito;
        this.ammissibilita = ammissibilita;
        this.cod_servizio = cod_servizio;
        this.cod_flusso = cod_flusso;
        Dettaglio_Esito = dettaglio_Esito;
    }

    public int getEsito() {
        return Esito;
    }

    public void setEsito(int esito) {
        Esito = esito;
    }

    public Ammissibilita getAmmissibilita() {
        return ammissibilita;
    }

    public void setAmmissibilita(Ammissibilita ammissibilita) {
        this.ammissibilita = ammissibilita;
    }

    public String getCod_servizio() {
        return cod_servizio;
    }

    public void setCod_servizio(String cod_servizio) {
        this.cod_servizio = cod_servizio;
    }

    public int getCod_flusso() {
        return cod_flusso;
    }

    public void setCod_flusso(int cod_flusso) {
        this.cod_flusso = cod_flusso;
    }

    public String getDettaglio_Esito() {
        return Dettaglio_Esito;
    }

    public void setDettaglio_Esito(String dettaglio_Esito) {
        Dettaglio_Esito = dettaglio_Esito;
    }
}
