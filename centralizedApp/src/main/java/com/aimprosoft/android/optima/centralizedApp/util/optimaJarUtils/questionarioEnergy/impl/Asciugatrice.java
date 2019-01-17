package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyClassDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WeeklyUtilizationDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.YearOfReferenceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.QuestionarioValidation;
import com.optima.consumi.domain.Consume;

public class Asciugatrice implements QuestionarioValidation {

    private final String ASCIUGATRICE = "asciugatrice";
    private EnergyOfferDetails energyOfferDetails;

    public Asciugatrice(EnergyOfferDetails energyOfferDetails) {
        this.energyOfferDetails = energyOfferDetails;
    }

    public Asciugatrice() {
    }

    @Override
    public Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico getInfoElettrodomestico() {

        YearOfReferenceDAOImpl yearOfReferenceDAO = new YearOfReferenceDAOImpl();
        EnergyClassDAOImpl energyClassDAO = new EnergyClassDAOImpl();
        WeeklyUtilizationDAOImpl weeklyUtilizationDAO = new WeeklyUtilizationDAOImpl();

        Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico infoElettrodomestico = new Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico();
        infoElettrodomestico.setCodiceElettrodomestico(ASCIUGATRICE);
        infoElettrodomestico.setAnnoAcquisto(yearOfReferenceDAO.getYearOfReferenceDescById(energyOfferDetails.getAsciugatriceOvenPurchaseYear()));//yearOfReferenceDAO.getYearOfReferenceDescById(energyOfferDetails.getAsciugatriceOvenPurchaseYear())
        infoElettrodomestico.setNumSplit(null);
        infoElettrodomestico.setNumUtilizzo(weeklyUtilizationDAO.getWeeklyUtilizationValueById(energyOfferDetails.getAsciugatriceOvenWeeklyUse()));
        infoElettrodomestico.setDimensione(null);
        infoElettrodomestico.setPotenza(null);
        infoElettrodomestico.setCe(energyClassDAO.getEnergyClassDescById(energyOfferDetails.getAsciugatriceOvenEnergyClass()));//energyClassDAO.getEnergyClassDescById(energyOfferDetails.getAsciugatriceOvenEnergyClass())
        return infoElettrodomestico;
    }

    @Override
    public boolean validationElettrodomestico() {
        return energyOfferDetails.getAsciugatriceOvenEnergyClass() != 0;
    }
}
