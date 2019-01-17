package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy;

import com.optima.consumi.domain.Consume;

public interface QuestionarioValidation {

    Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico getInfoElettrodomestico();

    boolean validationElettrodomestico();
}
