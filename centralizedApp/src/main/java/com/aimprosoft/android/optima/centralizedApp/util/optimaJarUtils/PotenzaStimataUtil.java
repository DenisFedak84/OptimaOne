package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyMeterDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.util.SDCardHelper;
import com.optima.consumi.domain.ResponseConsume;
import com.optima.potenza.domain.Potenza;
import com.optima.potenza.domain.ResponsePotenza;
import com.optima.potenza.service.PotenzaCentralizzato;
import com.optima.pricing.db.QueryManager;

import java.io.File;
import java.sql.Connection;
import java.util.List;

public class PotenzaStimataUtil {

    public PotenzaStimataUtil() {
    }

    public ResponsePotenza getPotenzaStimata(ResponseConsume responseConsume, EnergyOfferDetails energyOfferDetails) {
        PotenzaCentralizzato potenzaCentralizzato = new PotenzaCentralizzato(AbstractJarUtil.getConfigMap(AbstractJarUtil.POTENZA_STIMATA_JAR));

        Potenza potenza = new Potenza();
        Potenza.Consumi consumi = new Potenza.Consumi();
        List<Potenza.Consumi.Competenza> competenzaList = consumi.getCompetenza();
        EnergyOfferDateIntervalDAOImpl energyOfferDateIntervalDAO = new EnergyOfferDateIntervalDAOImpl();
        EnergyMeterDAOImpl energyMeterDAO = new EnergyMeterDAOImpl();
        List<ResponseConsume.ConsumeEnergiaResponse.ResponseConsumi> responseConsumes = responseConsume.getConsumeEnergiaResponse().getResponseConsumi();
        String tariffTransporId = energyMeterDAO.getEnergiMeterById(energyOfferDetails.getEnergyMeter()).getTariffOption();
        for (ResponseConsume.ConsumeEnergiaResponse.ResponseConsumi consume : responseConsumes) {
            Potenza.Consumi.Competenza competenza = new Potenza.Consumi.Competenza();
            Integer potenzaImpegnata = energyOfferDateIntervalDAO.getPotenzaImpegnataByDetailsIdAndMonth(energyOfferDetails.getEnergyDetailOfferId(),
                    formatMonth(consume.getMeseRiferimento()));
            competenza.setPotenza(potenzaImpegnata != null ? Float.valueOf(potenzaImpegnata) : -1);
            competenza.setAnno(Integer.valueOf(consume.getAnnoRiferimento()));
            competenza.setConsumoF1(Float.valueOf(consume.getConsumoF1()));
            competenza.setConsumoF2(Float.valueOf(consume.getConsumoF2()));
            competenza.setConsumoF3(Float.valueOf(consume.getConsumoF3()));
            competenza.setMese(Integer.valueOf(consume.getMeseRiferimento()));
//            competenza.setPod();
            competenza.setTrasporto(tariffTransporId);
            competenzaList.add(competenza);
        }
        potenza.setConsumi(consumi);

        ResponsePotenza responsePotenza = null;
        try {
            String urlLite = "jdbc:sqlite:" + SDCardHelper.DATABASE_LOCATION_DIR + File.separator + SDCardHelper.CPI2_DB_NAME;
            QueryManager queryManager = new QueryManager(AbstractJarUtil.SQLITE_DRIVER, urlLite, true);
            Connection connection = queryManager.openConnection(AbstractJarUtil.SQLITE_DRIVER, urlLite);
            responsePotenza = potenzaCentralizzato.calculatePower(potenza, queryManager, connection);
            if(connection!=null){
                connection.close();
            }
        } catch (Exception e) {
            Log.e(getClass().getName(), "error while get StimaPotenza", e);
        }
        return responsePotenza;
    }

    private String formatMonth(String month) {
        return month.length() < 2 ? "0" + month : month;
    }
}
