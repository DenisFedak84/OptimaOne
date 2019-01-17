package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyClassDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WeeklyUtilizationDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.YearOfReferenceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.QuestionarioValidation;
import com.optima.consumi.domain.Consume;

public class Forno implements QuestionarioValidation {

    private final String FORNO = "forno";
    private EnergyOfferDetails energyOfferDetails;

    public Forno(EnergyOfferDetails energyOfferDetails) {
        this.energyOfferDetails = energyOfferDetails;
    }

    public Forno() {
    }

    @Override
    public Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico getInfoElettrodomestico() {

        YearOfReferenceDAOImpl yearOfReferenceDAO = new YearOfReferenceDAOImpl();
        EnergyClassDAOImpl energyClassDAO = new EnergyClassDAOImpl();

        Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico infoElettrodomestico = new Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico();
        infoElettrodomestico.setCodiceElettrodomestico(FORNO);
        infoElettrodomestico.setAnnoAcquisto(yearOfReferenceDAO.getYearOfReferenceDescById(energyOfferDetails.getOvenPurchaseYear()));//yearOfReferenceDAO.getYearOfReferenceDescById(energyOfferDetails.getOvenPurchaseYear())
        infoElettrodomestico.setNumSplit(null);
        infoElettrodomestico.setNumUtilizzo(new WeeklyUtilizationDAOImpl().getWeeklyUtilizationValueById(energyOfferDetails.getOvenWeeklyUse()));
        infoElettrodomestico.setDimensione(null);
        infoElettrodomestico.setPotenza(null);
        infoElettrodomestico.setCe(energyClassDAO.getEnergyClassDescById(energyOfferDetails.getOvenEnergyClass()));//energyClassDAO.getEnergyClassDescById(energyOfferDetails.getOvenEnergyClass())
        return infoElettrodomestico;
    }

    @Override
    public boolean validationElettrodomestico() {
        return energyOfferDetails.getOvenEnergyClass() != 0;
    }
}
