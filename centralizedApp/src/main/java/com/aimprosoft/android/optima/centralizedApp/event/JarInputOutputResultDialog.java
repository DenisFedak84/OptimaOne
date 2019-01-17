package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.optima.pricing.domain.Pricing;
import com.optima.pricing.domain.ResponsePrincing;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class JarInputOutputResultDialog extends BaseEvent {
    private Activity activity;
    private LinearLayout mainLayout;

    public JarInputOutputResultDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
    }

    private void addRaw(int left, String name, Object value) {
        LinearLayout ll = new LinearLayout(activity);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llParams.leftMargin = left;
        ll.setLayoutParams(llParams);
        TextView tv_name = new TextView(activity);
        tv_name.setText(name + " = " + value);
        ll.addView(tv_name);
        mainLayout.addView(ll);
    }

    private void addRawOnlyValue(int left, String name) {
        LinearLayout ll = new LinearLayout(activity);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llParams.leftMargin = left;
        ll.setLayoutParams(llParams);
        TextView tv_name = new TextView(activity);
        tv_name.setText(name);
        ll.addView(tv_name);
        mainLayout.addView(ll);
    }

    private void addDiv(String name) {
        LinearLayout ll = new LinearLayout(activity);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        TextView rrr = new TextView(activity);
        rrr.setTextColor(Color.RED);
        rrr.setText("__________________________" + name + "_______________________________");
        ll.addView(rrr);
        mainLayout.addView(ll);
    }

    private void addDiv(int color) {
        LinearLayout ll = new LinearLayout(activity);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        TextView rrr = new TextView(activity);
        rrr.setTextColor(color);
        rrr.setText("_________________________________________________________");
        ll.addView(rrr);
        mainLayout.addView(ll);
    }

    private void addDiv(String name, int color) {
        LinearLayout ll = new LinearLayout(activity);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        TextView rrr = new TextView(activity);
        rrr.setTextColor(color);
        rrr.setText("__________________________" + name + "_______________________________");
        ll.addView(rrr);
        mainLayout.addView(ll);
    }

    public void showDialog(Map<String, Object> map) {
        mainLayout = new LinearLayout(activity);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        Pricing pr = (Pricing) map.get("Pricing");
        addDiv("Pricing");
        addDiv("ADSL", Color.BLUE);
        if (pr.getDatiAdsl() != null) {
            List<Pricing.DatiAdsl.DettaglioAdsl> listDettaglioAdsl = pr.getDatiAdsl().getDettaglioAdsl();
            for (Pricing.DatiAdsl.DettaglioAdsl aListDettaglioAdsl : listDettaglioAdsl) {
                addDiv(Color.GREEN);
                //Opzioni linea list
                List<Pricing.DatiAdsl.DettaglioAdsl.OpzioniLinea> optioniLineaList = aListDettaglioAdsl.getOpzioniLinea();
                addRawOnlyValue(100, "optioniLinea");
                for (int j = 0; j < optioniLineaList.size(); j++) {
                    addRawOnlyValue(150, optioniLineaList.get(j).getCodiceOpzione());
                }
//                addDiv(Color.YELLOW);
                //
                String dataFineValidita = aListDettaglioAdsl.getDataFineValidita();
                addRaw(100, "dataFineValidita", dataFineValidita);
                String dataInizioValidita = aListDettaglioAdsl.getDataInizioValidita();
                addRaw(100, "dataInizioValidita", dataInizioValidita);
                String giaCliente = aListDettaglioAdsl.getGiaCliente();
                addRaw(100, "giaCliente", giaCliente);
                BigInteger idCampagna = aListDettaglioAdsl.getIdCampagna();
                addRaw(100, "idCampagna", idCampagna);
//                String ipAggiuntivi = aListDettaglioAdsl.getIpAggiuntivi();
//                addRaw(100, "ipAggiuntivi", ipAggiuntivi);
                String iva = aListDettaglioAdsl.getIva();
                addRaw(100, "iva", iva);
//                String lineaSoloDati = aListDettaglioAdsl.getLineaSoloDati();
//                addRaw(100, "lineaSoloDati", lineaSoloDati);
//                String router = aListDettaglioAdsl.getRouter();
//                addRaw(100, "router", router);
                String tipoAdsl = aListDettaglioAdsl.getTipoAdsl();
                addRaw(100, "tipoAdsl", tipoAdsl);
                addDiv(Color.GREEN);
            }
            int idCampagnaAttivazioneADSL = pr.getDatiAdsl().getIdCampagnaAttivazione();
            addRaw(60, "idCampagnaAttivazioneADSL", idCampagnaAttivazioneADSL);
        }
        addDiv("ADSL", Color.BLUE);
        addDiv("ENERGIA", Color.BLUE);
        if (pr.getDatiEnergia() != null) {
            List<Pricing.DatiEnergia.DettaglioEnergia> listDettaglioEnergia = pr.getDatiEnergia().getDettaglioEnergia();
            for (Pricing.DatiEnergia.DettaglioEnergia aListDettaglioEnergia : listDettaglioEnergia) {
                addDiv(Color.GREEN);

                List<Pricing.DatiEnergia.DettaglioEnergia.Consumi.Competenza> listCompetenzaEnergia = aListDettaglioEnergia.getConsumi().getCompetenza();
                for (Pricing.DatiEnergia.DettaglioEnergia.Consumi.Competenza aListCompetenzaEnergia : listCompetenzaEnergia) {
                    addDiv(Color.YELLOW);
                    String consumo = aListCompetenzaEnergia.getConsumo();
                    addRaw(150, "consumo", consumo);
                    String consumof1 = aListCompetenzaEnergia.getConsumoF1();
                    addRaw(150, "consumof1", consumof1);
                    String consumof2 = aListCompetenzaEnergia.getConsumoF2();
                    addRaw(150, "consumof2", consumof2);
                    String consumof3 = aListCompetenzaEnergia.getConsumoF3();
                    addRaw(150, "consumof3", consumof3);
                    String costoAcquisto = aListCompetenzaEnergia.getCostoAcquisto();
                    addRaw(150, "costoAcquisto", costoAcquisto);
                    String meseRiferimento = aListCompetenzaEnergia.getMeseRiferimento();
                    addRaw(150, "meseRiferimento", meseRiferimento);
                    addDiv(Color.YELLOW);
                }
                //
                Float percMargine = aListDettaglioEnergia.getPercMargine();
                addRaw(100, "percMargine", percMargine);
                Float percRelax = aListDettaglioEnergia.getPercRelax();
                addRaw(100, "percRelax", percRelax);
                //
                BigInteger campagnaOfferta = aListDettaglioEnergia.getCampagnaOfferta();
                addRaw(100, "campagnaOfferta", campagnaOfferta);
                String dataFineValidita = aListDettaglioEnergia.getDataFineValidita();
                addRaw(100, "dataFineValidita", dataFineValidita);
                String dataInizioValidita = aListDettaglioEnergia.getDataInizioValidita();
                addRaw(100, "dataInizioValidita", dataInizioValidita);
                String dataTariffa = aListDettaglioEnergia.getDataTariffa();
                addRaw(100, "dataTariffa", dataTariffa);
                String giaCliente = aListDettaglioEnergia.getGiaCliente();
                addRaw(100, "giaCliente", giaCliente);
                String iva = aListDettaglioEnergia.getIva();
                addRaw(100, "iva", iva);
                String misuratoreva = aListDettaglioEnergia.getMisuratore();
                addRaw(100, "misuratoreva", misuratoreva);
                float potenzaContatore = aListDettaglioEnergia.getPotenzaContatore();
                addRaw(100, "potenzaContatore", potenzaContatore);
                String ariffaTrasporto = aListDettaglioEnergia.getTariffaTrasporto();
                addRaw(100, "tariffaTrasporto", ariffaTrasporto);
                addDiv(Color.GREEN);
            }
            int idCampagnaAttivazioneEnergia = pr.getDatiEnergia().getIdCampagnaAttivazione();
            addRaw(60, "idCampagnaAttivazioneEnergia", idCampagnaAttivazioneEnergia);
        }
        addDiv("ENERGIA", Color.BLUE);
        addDiv("GAS", Color.BLUE);
        if (pr.getDatiGas() != null) {
            List<Pricing.DatiGas.DettaglioGas> listDettaglioGas = pr.getDatiGas().getDettaglioGas();
            for (Pricing.DatiGas.DettaglioGas listDettaglioGa : listDettaglioGas) {
                addDiv(Color.GREEN);

                List<Pricing.DatiGas.DettaglioGas.Consumi.Competenza> listCompetenzaGas = listDettaglioGa.getConsumi().getCompetenza();
                for (Pricing.DatiGas.DettaglioGas.Consumi.Competenza listCompetenzaGa : listCompetenzaGas) {
                    addDiv(Color.YELLOW);
                    String consumo = listCompetenzaGa.getConsumo();
                    addRaw(150, "consumo", consumo);
                    String consumoCumulato = listCompetenzaGa.getConsumoCumulato();
                    addRaw(150, "consumoCumulato", consumoCumulato);
                    String costoAcquisto = listCompetenzaGa.getCostoAcquisto();
                    addRaw(150, "costoAcquisto", costoAcquisto);
                    String meseRiferimento = listCompetenzaGa.getMeseRiferimento();
                    addRaw(150, "meseRiferimento", meseRiferimento);
                    addDiv(Color.YELLOW);
                }
                //
//                float percMargine = aListDettaglioEnergia.getPercMargine();
//                addRaw(100, "percMargine", percMargine);
//                float percRelax = aListDettaglioEnergia.getPercRelax();
//                addRaw(100, "percRelax", percRelax);
                //
                //
                Float percMargine = listDettaglioGa.getPercMargine();
                addRaw(100, "percMargine", percMargine);
                Float percRelax = listDettaglioGa.getPercRelax();
                addRaw(100, "percRelax", percRelax);
                //
                BigInteger campagnaOfferta = listDettaglioGa.getCampagnaOfferta();
                addRaw(100, "campagnaOfferta", campagnaOfferta);
                String comuneFornitura = listDettaglioGa.getComuneFornitura();
                addRaw(100, "comuneFornitura", comuneFornitura);
                String dataFineValidita = listDettaglioGa.getDataFineValidita();
                addRaw(100, "dataFineValidita", dataFineValidita);
                String dataInizioValidita = listDettaglioGa.getDataInizioValidita();
                addRaw(100, "dataInizioValidita", dataInizioValidita);
                String dataTariffa = listDettaglioGa.getDataTariffa();
                addRaw(100, "dataTariffa", dataTariffa);
                String giaCliente = listDettaglioGa.getGiaCliente();
                addRaw(100, "giaCliente", giaCliente);
                String iva = listDettaglioGa.getIva();
                addRaw(100, "iva", iva);
                String misuratore = listDettaglioGa.getMisuratore();
                addRaw(100, "misuratore", misuratore);
                String tipologiaPDR = listDettaglioGa.getTipologiaPDR();
                addRaw(100, "tipologiaPDR", tipologiaPDR);
                String tipologiaImposte = listDettaglioGa.getTipologiaImposte(); //now String, it was int
                addRaw(100, "tipologiaImposte", tipologiaImposte);
                addDiv(Color.GREEN);

            }
            int idCampagnaAttivazioneGas = pr.getDatiGas().getIdCampagnaAttivazione();
            addRaw(60, "idCampagnaAttivazioneGas", idCampagnaAttivazioneGas);
        }

        addDiv("GAS", Color.BLUE);
        addDiv("VOICE", Color.BLUE);

        if (pr.getDatiVoce() != null) {
            List<Pricing.DatiVoce.DettaglioVoce> listDettaglioVoce = pr.getDatiVoce().getDettaglioVoce();
            for (int i = 0; i < listDettaglioVoce.size(); i++) {
                addDiv(Color.GREEN);

                List<Pricing.DatiVoce.DettaglioVoce.LineaVoce> listLineaVoce = listDettaglioVoce.get(i).getLineaVoce();
                for (Pricing.DatiVoce.DettaglioVoce.LineaVoce aListLineaVoce : listLineaVoce) {
                    addDiv(Color.YELLOW);
                    String cli = aListLineaVoce.getCli();
                    addRaw(150, "cli", cli);
                    String giaCliente = aListLineaVoce.getGiaCliente();
                    addRaw(150, "giaCliente", giaCliente);
                    String operatore = aListLineaVoce.getOperatore();
                    addRaw(150, "operatore", operatore);
                    String tipoLinea = aListLineaVoce.getTipoLinea();
                    addRaw(150, "tipoLinea", tipoLinea);
                    String tipoRete = aListLineaVoce.getTipoRete();
                    addRaw(150, "tipoRete", tipoRete);

                    BigInteger costoLineaGiaCliente = aListLineaVoce.getCostoLineaGiaCliente();
                    addRaw(150, "costoLineaGiaCliente", costoLineaGiaCliente);
                    BigInteger numeroAggiuntivi = aListLineaVoce.getNumeroAggiuntivi();
                    addRaw(150, "numeroAggiuntivi", numeroAggiuntivi);
                    BigInteger numeroLinee = aListLineaVoce.getNumeroLinee();
                    addRaw(150, "numeroLinee", numeroLinee);

                    List<Pricing.DatiVoce.DettaglioVoce.LineaVoce.OpzioneLinea> listOpzioneLinea = aListLineaVoce.getOpzioneLinea();
                    for (Pricing.DatiVoce.DettaglioVoce.LineaVoce.OpzioneLinea aListOpzioneLinea : listOpzioneLinea) {
                        addDiv(Color.RED);
                        String codiceOpzione = aListOpzioneLinea.getCodiceOpzione();
                        addRaw(160, "codiceOpzione", codiceOpzione);
                        addDiv(Color.RED);
                    }
                    addDiv(Color.YELLOW);
                }
                String dataFineValidita = listDettaglioVoce.get(i).getDataFineValidita();
                addRaw(100, "dataFineValidita", dataFineValidita);
                String dtaInizioValidita = listDettaglioVoce.get(i).getDataInizioValidita();
                addRaw(100, "dtaInizioValidita", dtaInizioValidita);
                BigInteger idCampagna = listDettaglioVoce.get(i).getIdCampagna();
                addRaw(100, "idCampagna", idCampagna);
                String iva = listDettaglioVoce.get(i).getIva();
                addRaw(100, "iva", iva);
                String minutiFisso = listDettaglioVoce.get(i).getMinutiFisso();
                addRaw(100, "minutiFisso", minutiFisso);
                String minutiMobile = listDettaglioVoce.get(i).getMinutiMobile();
                addRaw(100, "minutiMobile", minutiMobile);
                addDiv(Color.GREEN);
            }
            int idCampagnaAttivazioneVoice = pr.getDatiVoce().getIdCampagnaAttivazione();
            addRaw(60, "idCampagnaAttivazioneVoice", idCampagnaAttivazioneVoice);
        }
        addDiv("VOICE", Color.BLUE);


        ResponsePrincing rp = (ResponsePrincing) map.get("ResponsePrincing");
        addDiv("ResponsePrincing");

        addDiv("VOICE", Color.BLUE);
        if (rp != null) {
//----------------------------------VOISE-----------------------------------------------------
            if (rp.getPricingVoceResponse() != null) {
                List<ResponsePrincing.PricingVoceResponse.ResponseOfferta> voce_offerta_list = rp.getPricingVoceResponse().getResponseOfferta();
                for (ResponsePrincing.PricingVoceResponse.ResponseOfferta aVoce_offerta_list : voce_offerta_list) {
                    addDiv(Color.GREEN);


                    List<ResponsePrincing.PricingVoceResponse.ResponseOfferta.PrezzoSforamentoMancatoConsumo> list1 = aVoce_offerta_list.getPrezzoSforamentoMancatoConsumo();
                    for (ResponsePrincing.PricingVoceResponse.ResponseOfferta.PrezzoSforamentoMancatoConsumo aList1 : list1) {
                        addDiv(Color.YELLOW);
                        String codice1 = aList1.getCodice();
                        addRaw(150, "codice1", codice1);
                        Double prezzoMancatoConsumoVoice = aList1.getPrezzoMancatoConsumo();
                        addRaw(150, "prezzoMancatoConsumoVoice", prezzoMancatoConsumoVoice);
                        Double prezzoSforamentoVoice = aList1.getPrezzoSforamento();
                        addRaw(150, "prezzoSforamentoVoice", prezzoSforamentoVoice);
                        int romoVoice = aList1.getRamo();
                        addRaw(150, "romoVoice", romoVoice);
                        addDiv(Color.YELLOW);

                    }
                    String cli = aVoce_offerta_list.getCli();
                    addRaw(100, "cli", cli);
                    String minutiVersoMobile = aVoce_offerta_list.getMinutiVersoMobile();
                    addRaw(100, "minutiVersoMobile", minutiVersoMobile);
                    String minutiVersoFisso = aVoce_offerta_list.getMinutiVersoFisso();
                    addRaw(100, "minutiVersoFisso", minutiVersoFisso);
                    String dataTariffa = aVoce_offerta_list.getDataTariffa();
                    addRaw(100, "dataTariffa", dataTariffa);
                    float costoPacchettoMensileIva = aVoce_offerta_list.getCostoPacchettoMensileIva();
                    addRaw(100, "costoPacchettoMensileIva", costoPacchettoMensileIva);
                    float costoPacchettoMensile = aVoce_offerta_list.getCostoPacchettoMensile();
                    addRaw(100, "costoPacchettoMensile", costoPacchettoMensile);
                    float percentuale = aVoce_offerta_list.getPercentuale();
                    addRaw(100, "percentuale", percentuale);
                    float costoAttivazionePacchetto1 = aVoce_offerta_list.getCostoAttivazionePacchetto();
                    addRaw(100, "costoAttivazionePacchetto1", costoAttivazionePacchetto1);
                    int versione = aVoce_offerta_list.getVersione();
                    addRaw(100, "versione", versione);
                    addDiv(Color.GREEN);
                }

                String errorVoce = rp.getPricingVoceResponse().getError();
                addRaw(60, "errorVoce", errorVoce);
                double costoTotalePacchettoMensileIvaVoce = rp.getPricingVoceResponse().getCostoTotalePacchettoMensileIva();
                addRaw(60, "costoTotalePacchettoMensileIvaVoce", costoTotalePacchettoMensileIvaVoce);
                double costoTotalePacchettoMensileVoce = rp.getPricingVoceResponse().getCostoTotalePacchettoMensile();
                addRaw(60, "costoTotalePacchettoMensileVoce", costoTotalePacchettoMensileVoce);
                double costoAttivazionePacchettoVoce = rp.getPricingVoceResponse().getCostoAttivazionePacchetto();
                addRaw(60, "costoAttivazionePacchettoVoce", costoAttivazionePacchettoVoce);
            }
//---------------------------------------------------------------------------------------
            addDiv("VOICE", Color.BLUE);
            addDiv("GAS", Color.BLUE);
//----------------------------------Gas-----------------------------------------------------
            if (rp.getPricingGasResponse() != null) {
                List<ResponsePrincing.PricingGasResponse.ResponseOfferta> gas_offerta_list = rp.getPricingGasResponse().getResponseOfferta();
                for (ResponsePrincing.PricingGasResponse.ResponseOfferta aGas_offerta_list : gas_offerta_list) {
                    addDiv(Color.GREEN);
                    List<ResponsePrincing.PricingGasResponse.ResponseOfferta.PrezzoSforamentoMancatoConsumo> list_gas = aGas_offerta_list.getPrezzoSforamentoMancatoConsumo();
                    for (ResponsePrincing.PricingGasResponse.ResponseOfferta.PrezzoSforamentoMancatoConsumo list_ga : list_gas) {
                        addDiv(Color.YELLOW);
                        String codice = list_ga.getCodice();
                        addRaw(150, "codice", codice);
                        float prezzoMancatoConsumo = list_ga.getPrezzoMancatoConsumo();
                        addRaw(150, "prezzoMancatoConsumo", prezzoMancatoConsumo);
                        float prezzoSforamento = list_ga.getPrezzoSforamento();
                        addRaw(150, "prezzoSforamento", prezzoSforamento);
                        int romo = list_ga.getRamo();
                        addRaw(150, "romo", romo);
                        addDiv(Color.YELLOW);

                    }

                    String misuratore = aGas_offerta_list.getMisuratore();
                    addRaw(100, "misuratore", misuratore);
                    String dataTariffa = aGas_offerta_list.getDataTariffa();
                    addRaw(100, "dataTariffa", dataTariffa);

                    float costoPacchettoMensileIva = aGas_offerta_list.getCostoPacchettoMensileIva();
                    addRaw(100, "costoPacchettoMensileIva", costoPacchettoMensileIva);
                    float percentuale = aGas_offerta_list.getPercentuale();
                    addRaw(100, "percentuale", percentuale);
                    float costoPacchettoMensile = aGas_offerta_list.getCostoPacchettoMensile();
                    addRaw(100, "costoPacchettoMensile", costoPacchettoMensile);
                    float tagliaAnnuale = aGas_offerta_list.getTagliaAnnuale();
                    addRaw(100, "tagliaAnnuale", tagliaAnnuale);
                    float tagliaMensile = aGas_offerta_list.getTagliaMensile();
                    addRaw(100, "tagliaMensile", tagliaMensile);
                    float venditaAnnuale = aGas_offerta_list.getVenditaAnnuale();
                    addRaw(100, "venditaAnnuale", venditaAnnuale);
                    float venditaMensile = aGas_offerta_list.getVenditaMensile();
                    addRaw(100, "venditaMensile", venditaMensile);
                    int versione = aGas_offerta_list.getVersione();
                    addRaw(100, "versione", versione);
                    addDiv(Color.GREEN);
                }

                String errorGas = rp.getPricingGasResponse().getError();
                addRaw(60, "errorGas", errorGas);
                double costoTotalePacchettoMensileIvaGas = rp.getPricingGasResponse().getCostoTotalePacchettoMensileIva();
                addRaw(60, "costoTotalePacchettoMensileIvaGas", costoTotalePacchettoMensileIvaGas);
                double costoTotalePacchettoMensile1Gas = rp.getPricingGasResponse().getCostoTotalePacchettoMensile();
                addRaw(60, "costoTotalePacchettoMensile1Gas", costoTotalePacchettoMensile1Gas);
                double costoAttivazionePacchetto1Gas = rp.getPricingGasResponse().getCostoAttivazionePacchetto();
                addRaw(60, "costoAttivazionePacchetto1Gas", costoAttivazionePacchetto1Gas);
            }
//---------------------------------------------------------------------------------------
            addDiv("GAS", Color.BLUE);
            addDiv("ENERGIA", Color.BLUE);
            //----------------------------------ENERGI-----------------------------------------------------
            if (rp.getPricingEnergiaResponse() != null) {
                List<ResponsePrincing.PricingEnergiaResponse.ResponseOfferta> energy_offerta_list = rp.getPricingEnergiaResponse().getResponseOfferta();
                for (ResponsePrincing.PricingEnergiaResponse.ResponseOfferta anEnergy_offerta_list : energy_offerta_list) {
                    addDiv(Color.GREEN);
                    ResponsePrincing.PricingEnergiaResponse.ResponseOfferta.PrezzoSforamentoMancatoConsumo list_energy = anEnergy_offerta_list.getPrezzoSforamentoMancatoConsumo();
                    String codice = list_energy.getCodice();
                    addRaw(100, "codice", codice);
                    float prezzoMancatoConsumo = list_energy.getPrezzoMancatoConsumo();
                    addRaw(100, "prezzoMancatoConsumo", prezzoMancatoConsumo);
                    float prezzoSforamento = list_energy.getPrezzoSforamento();
                    addRaw(100, "prezzoSforamento", prezzoSforamento);
                    int romo = list_energy.getRamo();
                    addRaw(100, "romo", romo);

                    String misuratore = anEnergy_offerta_list.getMisuratore();
                    addRaw(100, "misuratore", misuratore);

                    String dataTariffa = anEnergy_offerta_list.getDataTariffa();
                    addRaw(100, "dataTariffa", dataTariffa);

                    float costoPacchettoMensileIva = anEnergy_offerta_list.getCostoPacchettoMensileIva();
                    addRaw(100, "costoPacchettoMensileIva", costoPacchettoMensileIva);
                    float percentuale = anEnergy_offerta_list.getPercentuale();
                    addRaw(100, "percentuale", percentuale);
                    float costoPacchettoMensile = anEnergy_offerta_list.getCostoPacchettoMensile();
                    addRaw(100, "costoPacchettoMensile", costoPacchettoMensile);
                    float tagliaAnnuale = anEnergy_offerta_list.getTagliaAnnuale();
                    addRaw(100, "tagliaAnnuale", tagliaAnnuale);
                    float tagliaMensile = anEnergy_offerta_list.getTagliaMensile();
                    addRaw(100, "tagliaMensile", tagliaMensile);
                    float venditaAnnuale = anEnergy_offerta_list.getVenditaAnnuale();
                    addRaw(100, "venditaAnnuale", venditaAnnuale);
                    float venditaMensile = anEnergy_offerta_list.getVenditaMensile();
                    addRaw(100, "venditaMensile", venditaMensile);
                    float versione = anEnergy_offerta_list.getVersione();
                    addRaw(100, "versione", versione);
                    addDiv(Color.GREEN);
                }

                String errorEnergy = rp.getPricingEnergiaResponse().getError();
                addRaw(60, "errorEnergy", errorEnergy);
                double costoTotalePacchettoMensileIvaEnergy = rp.getPricingEnergiaResponse().getCostoTotalePacchettoMensileIva();
                addRaw(60, "costoTotalePacchettoMensileIvaEnergy", costoTotalePacchettoMensileIvaEnergy);
                double costoTotalePacchettoMensile1Energy = rp.getPricingEnergiaResponse().getCostoTotalePacchettoMensile();
                addRaw(60, "costoTotalePacchettoMensile1Energy", costoTotalePacchettoMensile1Energy);
                double costoAttivazionePacchetto1Energy = rp.getPricingEnergiaResponse().getCostoAttivazionePacchetto();
                addRaw(60, "costoAttivazionePacchetto1Energy", costoAttivazionePacchetto1Energy);
            }
//---------------------------------------------------------------------------------------
            addDiv("ENERGIA", Color.BLUE);
            addDiv("ADSL", Color.BLUE);
//----------------------------------ADSL-----------------------------------------------------
            if (rp.getPricingAdslResponse() != null) {
                List<ResponsePrincing.PricingAdslResponse.ResponseOfferta> adsl_offerta_list = rp.getPricingAdslResponse().getResponseOfferta();
                for (ResponsePrincing.PricingAdslResponse.ResponseOfferta anAdsl_offerta_list : adsl_offerta_list) {
                    addDiv(Color.GREEN);
                    String cli = anAdsl_offerta_list.getCli();
                    addRaw(100, "cli", cli);
                    String dataTariffa = anAdsl_offerta_list.getDataTariffa();
                    addRaw(100, "dataTariffa", dataTariffa);
                    float costoPacchettoMensileIva = anAdsl_offerta_list.getCostoPacchettoMensileIva();
                    addRaw(100, "costoPacchettoMensileIva", costoPacchettoMensileIva);
                    float costoPacchettoMensile = anAdsl_offerta_list.getCostoPacchettoMensile();
                    addRaw(100, "costoPacchettoMensile", costoPacchettoMensile);
                    float percentuale = anAdsl_offerta_list.getPercentuale();
                    addRaw(100, "percentuale", percentuale);
                    int versione = anAdsl_offerta_list.getVersione();
                    addRaw(100, "versione", versione);
                    addDiv(Color.GREEN);
                }

                String errorADSL = rp.getPricingAdslResponse().getError();
                addRaw(60, "errorADSL", errorADSL);
                double costoTotalePacchettoMensileIvaADSL = rp.getPricingAdslResponse().getCostoTotalePacchettoMensileIva();
                addRaw(60, "costoTotalePacchettoMensileIvaADSL", costoTotalePacchettoMensileIvaADSL);
                double costoTotalePacchettoMensileADSL = rp.getPricingAdslResponse().getCostoTotalePacchettoMensile();
                addRaw(60, "costoTotalePacchettoMensileADSL", costoTotalePacchettoMensileADSL);
                double costoAttivazionePacchettoADSL = rp.getPricingAdslResponse().getCostoAttivazionePacchetto();
                addRaw(60, "costoAttivazionePacchettoADSL", costoAttivazionePacchettoADSL);
            }

            addDiv("ADSL", Color.BLUE);

            int idOferta = rp.getIdOfferta();
            addRaw(60, "idOferta", idOferta);
            float costoPacchetto = rp.getCostoPacchetto();
            addRaw(60, "costoPacchetto", costoPacchetto);
            float costoPacchettoIva = rp.getCostoPacchettoIva();
            addRaw(60, "costoPacchettoIva", costoPacchettoIva);
            float contoRelax = rp.getContoRelax();
            addRaw(60, "contoRelax", contoRelax);
        }
//---------------------------------------------------------------------------------------

        dialog.setContentView(R.layout.test_dialog_response);
        ScrollView scrollView = (ScrollView) dialog.findViewById(R.id.scrollView);

        LinearLayout ll_btn = new LinearLayout(activity);
        ll_btn.setGravity(Gravity.CENTER);
        ll_btn.setOrientation(LinearLayout.HORIZONTAL);
        Button btn_close = new Button(activity);
        btn_close.setText("Close");
        btn_close.setGravity(Gravity.CENTER);


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ll_btn.addView(btn_close);
        mainLayout.addView(ll_btn);

        scrollView.addView(mainLayout);
        dialog.show();
    }
}
