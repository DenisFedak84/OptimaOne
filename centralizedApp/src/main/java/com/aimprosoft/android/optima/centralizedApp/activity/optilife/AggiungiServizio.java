package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.AssicurazioneOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.AssicurazioneOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.BoilerTypes;
import com.aimprosoft.android.optima.centralizedApp.db.entity.FlatType;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasYearOfReference;
import com.aimprosoft.android.optima.centralizedApp.db.entity.HouseHolder;
import com.aimprosoft.android.optima.centralizedApp.db.entity.HouseMontlyUse;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Restoration;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ThermalInsulation;
import com.aimprosoft.android.optima.centralizedApp.service.AbitantiDellaCasaSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.AnnoDiCostruzioneSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.PeriodoUsoAbitazioneSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.RistrutturazioneSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.TipologiaAbitazioneSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.TipologiaDiCaldaiaSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.TipologiaDiIsolamentoSpinner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggiungiServizio extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private CheckBox elettricitaServizio;
    private CheckBox gasServisio;
    private CheckBox mobileServizio;
    private CheckBox internetServizio;
    private CheckBox telefoniaFissaServizio;
    private CheckBox assicurazioneServizio;
    private Map<Integer, Class> navigationMap;
    private Offer offer;
    ///
    private RadioGroup energyQuestionarioRadioGroup;
    private RadioGroup gasQuestionarioRadioGroup;

    private RadioButton conFatturaElettricita;
    private RadioButton conFatturaGas;
    private RadioButton senzaFatturaElettricita;
    private RadioButton senzaFatturaGas;

    private RadioButton riscaldamentoAPavimentoSi;
    private RadioButton vetriDoppi;

    private LinearLayout llInformazioniAbitazion;
    private LinearLayout llAbitantiDellaCasa;
    private LinearLayout llTipologiaAbitazione;
    private LinearLayout llSuperficieAbitazione;
    private LinearLayout llPeriodoDiUso;
    private LinearLayout llAnnoDiCostruzione;
    private LinearLayout llRistrutturazione;
    private LinearLayout llTipologiaDiIsolamento;
    private LinearLayout llRiscaldamentoAPavimento;
    private LinearLayout llVetriDoppi;
    private LinearLayout llTipologiaDiCaldaia;

    private Spinner abitantiSpinner;
    private Spinner periodoSpinner;
    private Spinner tipologiaAbitazione;
    private Spinner annoDiCostruzoine;
    private Spinner ristrutturazione;
    private Spinner tipologiaDiIsolamento;
    private Spinner tipologiaDiCaldaia;

    //    private Animation showAnimation;
    //    private Animation hideAnimation;
    boolean isConsumerFlag;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiungi_servizio);
        offer = MyApplication.get(Constants.OFFER_ENTITY, Offer.class);
        isConsumerFlag = offer != null && offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR;

        initializeViews();
        setVisibilityLayouts(false, false, false);
        fillViewsByValues();
        setListeners();
        checkCheckBoxesForEnabled();

        findViewById(R.id.continuaAggiungiServizio).setOnClickListener(v -> {
            if ((isConsumerFlag) && (llSuperficieAbitazione.getVisibility() != View.VISIBLE)
                    || isValid(true, R.id.dimensioniEditText) || assicurazioneServizio.isChecked()) {
                saveDataToContinue();
            } else {
                saveDataToContinue();
            }
        });
    }

    private void saveDataToContinue() {
        checkChoiceStatus("energeticiServizio");
        checkChoiceStatus("gasServizio");
        MyApplication.set("ServizioNavigationMap", createServizioNavigationMap());
        if (offer != null && isOfferRequiredUpdate()) {
            setDataToOffer();
        }
        startConfigurationOffer(navigationMap);
    }

    private boolean isOfferRequiredUpdate() {
        return ((offer.getEnergyOfferId() == null && senzaFatturaElettricita.isChecked() && offer.getGasOfferId() == null) ||
                (offer.getGasOfferId() == null && senzaFatturaGas.isChecked()) && offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR);
    }

    private void setListeners() {
        elettricitaServizio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkAssicurazioneService(isChecked, gasServisio.isChecked());
//                    energyQuestionarioRadioGroup.startAnimation(isChecked ? showAnimation : hideAnimation);
//                    energyQuestionarioRadioGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE);
//                    if (!isChecked) {
//                        energyQuestionarioRadioGroup.clearCheck();
//                    } else {
//                        checkComputationWay();
//                    }
            }
        });

        gasServisio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkAssicurazioneService(elettricitaServizio.isChecked(), isChecked);
//                    gasQuestionarioRadioGroup.startAnimation(isChecked ? showAnimation : hideAnimation);
//                    gasQuestionarioRadioGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE);
//                    if (!isChecked) {
//                        gasQuestionarioRadioGroup.clearCheck();
//                    } else {
//                        checkComputationWay();
//                    }
            }
        });

        if (isConsumerFlag) {
            energyQuestionarioRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                mainValidation();
                visibilityLayoutFields();
            });

            gasQuestionarioRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                mainValidation();
                visibilityLayoutFields();
            });

            conFatturaElettricita.setOnCheckedChangeListener((buttonView, isChecked) -> visibilityLayoutFields());

            conFatturaGas.setOnCheckedChangeListener((buttonView, isChecked) -> visibilityLayoutFields());

            senzaFatturaElettricita.setOnCheckedChangeListener((buttonView, isChecked) -> visibilityLayoutFields());

            senzaFatturaGas.setOnCheckedChangeListener((buttonView, isChecked) -> visibilityLayoutFields());
        }
    }

    private void visibilityLayoutFields() {
        if (offer != null && offer.getConfiguratorType() != Constants.BUSINESS_CONFIGURATOR) {
            boolean isEnergyChoiceValid = energyQuestionarioRadioGroup.getCheckedRadioButtonId() != -1;
            boolean isGasChoiceValid = gasQuestionarioRadioGroup.getCheckedRadioButtonId() != -1;

            if ((elettricitaServizio.isChecked() & senzaFatturaElettricita.isChecked()) & !(gasServisio.isChecked() & senzaFatturaGas.isChecked())) {
                setVisibilityLayouts(true, true, false);
            } else if ((gasServisio.isChecked() & senzaFatturaGas.isChecked())) {
                setVisibilityLayouts(true, true, true);
            } else if (!((elettricitaServizio.isChecked() & !isEnergyChoiceValid) | (gasServisio.isChecked() & !isGasChoiceValid))) {
                setVisibilityLayouts(false, false, false);
            }
        }
    }

//    private void visibilityLayoutFields() {
//        boolean isEnergyChoiceValid = energyQuestionarioRadioGroup.getCheckedRadioButtonId() != -1;
//        boolean isGasChoiceValid = gasQuestionarioRadioGroup.getCheckedRadioButtonId() != -1;
//
//        if ((elettricitaServizio.isChecked() & senzaFatturaElettricita.isChecked()) & !(isEnergyOffer() != null && isEnergyOffer().isQuestionarioUsed()) & !(gasServisio.isChecked() & senzaFatturaGas.isChecked())) {
//            setVisibilityLayouts(true, true, false);
//        } else if ((gasServisio.isChecked() & senzaFatturaGas.isChecked() & !(isGasOffer() != null && isGasOffer().isQuestionarioUsing()))) {
//            setVisibilityLayouts(true, true, true);
//        } else if (!((elettricitaServizio.isChecked() & !isEnergyChoiceValid) | (gasServisio.isChecked() & !isGasChoiceValid))) {
//            setVisibilityLayouts(false, false, false);
//        }
//    }

    private void initializeViews() {
        elettricitaServizio = (CheckBox) findViewById(R.id.elettricitaServizio);
        gasServisio = (CheckBox) findViewById(R.id.gasServizio);
        assicurazioneServizio = (CheckBox) findViewById(R.id.assicurazione);
        mobileServizio = (CheckBox) findViewById(R.id.mobile);
        internetServizio = (CheckBox) findViewById(R.id.internetServizio);
        telefoniaFissaServizio = (CheckBox) findViewById(R.id.telefoniaFissaServizio);

//        showAnimation = AnimationUtils.loadAnimation(this, R.anim.show_from_top);
//        hideAnimation = AnimationUtils.loadAnimation(this, R.anim.hide_form_top);

        conFatturaElettricita = (RadioButton) findViewById(R.id.conFatturaEnergy);
        conFatturaGas = (RadioButton) findViewById(R.id.conFatturaGas);
        senzaFatturaElettricita = (RadioButton) findViewById(R.id.senzaFatturaEnergy);
        senzaFatturaGas = (RadioButton) findViewById(R.id.senzaFatturaGas);

        energyQuestionarioRadioGroup = (RadioGroup) findViewById(R.id.energyQuestionaRioradioGroup);
        gasQuestionarioRadioGroup = (RadioGroup) findViewById(R.id.gasQuestionaRioradioGroup);
        //
        llInformazioniAbitazion = (LinearLayout) findViewById(R.id.informazioniAbitazionLable);
        llAbitantiDellaCasa = (LinearLayout) findViewById(R.id.linearLayout1);
        llTipologiaAbitazione = (LinearLayout) findViewById(R.id.linearLayout2);
        llSuperficieAbitazione = (LinearLayout) findViewById(R.id.linearLayout4);
        llPeriodoDiUso = (LinearLayout) findViewById(R.id.linearLayout11);
        llAnnoDiCostruzione = (LinearLayout) findViewById(R.id.linearLayout13);
        llRistrutturazione = (LinearLayout) findViewById(R.id.linearLayout14);
        llTipologiaDiIsolamento = (LinearLayout) findViewById(R.id.linearLayout3);
        llRiscaldamentoAPavimento = (LinearLayout) findViewById(R.id.linearLayout5);
        llVetriDoppi = (LinearLayout) findViewById(R.id.linearLayout6);
        llTipologiaDiCaldaia = (LinearLayout) findViewById(R.id.linearLayout7);

        abitantiSpinner = (Spinner) findViewById(R.id.abitantiSpinner);
        periodoSpinner = (Spinner) findViewById(R.id.periodoSpinner);
        tipologiaAbitazione = (Spinner) findViewById(R.id.tipologiaAbitazioneSpinner);
        annoDiCostruzoine = (Spinner) findViewById(R.id.annoDiCostruzoineSpinner);
        ristrutturazione = (Spinner) findViewById(R.id.ristrutturazione);
        tipologiaDiIsolamento = (Spinner) findViewById(R.id.tipologiaDiIsolamento);
        riscaldamentoAPavimentoSi = (RadioButton) findViewById(R.id.riscaldamentoAPavimentoSi);
        vetriDoppi = (RadioButton) findViewById(R.id.vertiDoppiSi);
        tipologiaDiCaldaia = (Spinner) findViewById(R.id.tipologiaDiCaldaia);
    }

    private void setVisibilityLayouts(boolean showTitle, boolean showEnergiaFields, boolean showGasFields) {
        int title = (showTitle ? View.VISIBLE : View.GONE);
        int energiaFields = (showEnergiaFields ? View.VISIBLE : View.GONE);
        int gasFields = (showGasFields ? View.VISIBLE : View.GONE);
        llInformazioniAbitazion.setVisibility(title);
        llAbitantiDellaCasa.setVisibility(energiaFields);
        llPeriodoDiUso.setVisibility(energiaFields);
        llTipologiaAbitazione.setVisibility(gasFields);
        llSuperficieAbitazione.setVisibility(gasFields);
        llAnnoDiCostruzione.setVisibility(gasFields);
        llRistrutturazione.setVisibility(gasFields);
        llTipologiaDiIsolamento.setVisibility(gasFields);
        llRiscaldamentoAPavimento.setVisibility(gasFields);
        llVetriDoppi.setVisibility(gasFields);
        llTipologiaDiCaldaia.setVisibility(gasFields);
    }

    private void fillViewsByValues() {
        if (offer != null && isConsumerFlag) {
            new AbitantiDellaCasaSpinner(this, service -> {
                setSpinner(abitantiSpinner, (List) service.getResult());
                if (offer.getHouseHolderId() != 0) {
                    abitantiSpinner.setSelection(offer.getHouseHolderId());
                }
            }).execute();

            new PeriodoUsoAbitazioneSpinner(this, service -> {
                setSpinner(periodoSpinner, (List) service.getResult());
                if (offer.getMesi() != 0) {
                    periodoSpinner.setSelection(offer.getMesi());
                }
            }).execute();

            new AnnoDiCostruzioneSpinner(this, service -> setSpinner(annoDiCostruzoine, (List) service.getResult())).execute();

            new TipologiaAbitazioneSpinner(this, service -> setSpinner(tipologiaAbitazione, (List) service.getResult())).execute();

            new RistrutturazioneSpinner(this, service -> setSpinner(ristrutturazione, (List) service.getResult())).execute();

            new TipologiaDiIsolamentoSpinner(this, service -> setSpinner(tipologiaDiIsolamento, (List) service.getResult())).execute();

            new TipologiaDiCaldaiaSpinner(this, service -> setSpinner(tipologiaDiCaldaia, (List) service.getResult())).execute();

            abitantiSpinner.setOnItemSelectedListener(this);
            periodoSpinner.setOnItemSelectedListener(this);
            tipologiaAbitazione.setOnItemSelectedListener(this);
            annoDiCostruzoine.setOnItemSelectedListener(this);
        }
    }

    private void checkCheckBoxesForEnabled() {
        if (offer != null) {
            setServiceCheckBoxState(elettricitaServizio, offer.getEnergyOfferId());
            setServiceCheckBoxState(gasServisio, offer.getGasOfferId());
            setServiceCheckBoxState(telefoniaFissaServizio, (offer.getTlcOfferId() == null && offer.getWlrOfferId() == null) ? null : 0);
            setServiceCheckBoxState(internetServizio, offer.getInternetOfferId());
            setServiceCheckBoxState(mobileServizio, offer.getMobileOfferId());

            if (offer != null) {
                List<AssicurazioneOffer> assicurazioneOffers = new AssicurazioneOfferDAOImpl().getAssicurazioneByOfferId(offer.getOfferId());
                if (!assicurazioneOffers.isEmpty()) {
                    assicurazioneServizio.setEnabled(false);
                    assicurazioneServizio.setChecked(true);
                }
            }
//        if (isConsumerFlag) {
//            energyQuestionarioRadioGroup.setVisibility(offer.getEnergyOfferId() != null ? View.VISIBLE : View.GONE);
//            gasQuestionarioRadioGroup.setVisibility(offer.getGasOfferId() != null ? View.VISIBLE : View.GONE);
//            checkComputationWay();
//        }
        }
    }

//    private void checkComputationWay() {
//        if (isEnergyOffer() != null) {
//            preSetEnergyComputationWay(isEnergyOffer().isQuestionarioUsed());
//        }
//
//        if (isGasOffer() != null) {
//            preSetGasComputationWay(isGasOffer().isQuestionarioUsing());
//        }
//    }

//    private void preSetEnergyComputationWay(boolean isQuestionarioUsed) {
//        conFatturaElettricita.setChecked(!isQuestionarioUsed);
//        conFatturaElettricita.setClickable(!isQuestionarioUsed);
//        senzaFatturaElettricita.setChecked(isQuestionarioUsed);
//        senzaFatturaElettricita.setClickable(isQuestionarioUsed);
//    }
//
//    private void preSetGasComputationWay(boolean isQuestionarioUsing) {
//        conFatturaGas.setChecked(!isQuestionarioUsing);
//        conFatturaGas.setClickable(!isQuestionarioUsing);
//        senzaFatturaGas.setChecked(isQuestionarioUsing);
//        senzaFatturaGas.setClickable(isQuestionarioUsing);
//    }
//
//    private EnergyOffer isEnergyOffer() {
//        EnergyOffer energyOffer = null;
//        if (offer.getEnergyOfferId() != null) {
//            energyOffer = new EnergyOfferDAOImpl().getEnergyOfferById(offer.getEnergyOfferId());
//        }
//        return energyOffer;
//    }
//
//    private GasOffer isGasOffer() {
//        GasOffer gasOffer = null;
//        if (offer.getGasOfferId() != null) {
//            gasOffer = new GasOfferDAOImpl().getGasOfferById(offer.getGasOfferId());
//        }
//        return gasOffer;
//    }

    private void setServiceCheckBoxState(CheckBox serviceCheckBox, Integer serviceId) {
        if (serviceId == null) {
            serviceCheckBox.setEnabled(true);
        } else {
            serviceCheckBox.setEnabled(false);
            serviceCheckBox.setChecked(true);
        }
    }

    private void mainValidation() {
//        boolean isEnergyChoiceValid = energyQuestionarioRadioGroup.getCheckedRadioButtonId() != -1 || (elettricitaServizio.isChecked() &&
//                offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR);
//        boolean isGasChoiceValid = gasQuestionarioRadioGroup.getCheckedRadioButtonId() != -1 || (gasServisio.isChecked()
//                && offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR);

        boolean isEnergyChoiceValid = energyQuestionarioRadioGroup.getCheckedRadioButtonId() != -1 || elettricitaServizio.isChecked();
        boolean isGasChoiceValid = gasQuestionarioRadioGroup.getCheckedRadioButtonId() != -1 || gasServisio.isChecked();

        if ((isEnergyChoiceValid | isGasChoiceValid |
                (telefoniaFissaServizio.isChecked() && telefoniaFissaServizio.isEnabled()) |
                (mobileServizio.isChecked() && mobileServizio.isEnabled()) |
                (internetServizio.isChecked() && internetServizio.isEnabled())) && checkSpinnerSelection()) {
            findViewById(R.id.continuaAggiungiServizio).setVisibility(View.VISIBLE);
            findViewById(R.id.continuaAggiungiServizioDisabled).setVisibility(View.GONE);
        } else {
            findViewById(R.id.continuaAggiungiServizioDisabled).setVisibility(View.VISIBLE);
            findViewById(R.id.continuaAggiungiServizio).setVisibility(View.GONE);
        }
    }

    private void checkAssicurazioneService(boolean isEnergySelected, boolean isGasSelected) {
        if (assicurazioneServizio.isChecked() && !assicurazioneServizio.isEnabled()) {
            return;
        }

        if (isEnergySelected || isGasSelected) {
            assicurazioneServizio.setEnabled(true);
        } else {
            assicurazioneServizio.setEnabled(false);
            assicurazioneServizio.setChecked(false);
        }
    }

    private Map createServizioNavigationMap() {
        Map<Integer, Class> servizioNavigationMap = new HashMap<>();
        navigationMap = new HashMap<>();
        if (elettricitaServizio.isChecked() && elettricitaServizio.isEnabled()) {
//            Class targetClass = (energyQuestionarioRadioGroup.getCheckedRadioButtonId() == R.id.conFatturaEnergy || (offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR))
//                    ? EnergeticiLaFattura.class : EnergeticiQuestionario.class;
            servizioNavigationMap.put(1, EnergeticiLaFattura.class);
//            if (assicurazioneServizio.isChecked() && assicurazioneServizio.isEnabled()) {
//                navigationMap.put(6, AssicurazionePage.class);
//                servizioNavigationMap.put(6, AssicurazionePage.class);
//            }
            if (offer != null && offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR) {
                servizioNavigationMap.put(8, DevicePage.class);
                navigationMap.put(8, DevicePage.class);
            }
            navigationMap.put(1, EnergeticiLaFattura.class);
        }

        if (gasServisio.isEnabled() && gasServisio.isChecked()) {
//            Class targetClass = (gasQuestionarioRadioGroup.getCheckedRadioButtonId() == R.id.conFatturaGas || (offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR))
//                    ? Gas.class : GasQuestionario.class;
            MyApplication.remove("modificaGasLable");
            servizioNavigationMap.put(2, Gas.class);
//            if (assicurazioneServizio.isChecked() && assicurazioneServizio.isEnabled()) {
//                navigationMap.put(6, AssicurazionePage.class);
//                servizioNavigationMap.put(6, AssicurazionePage.class);
//            }
            if (offer != null && offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR) {
                servizioNavigationMap.put(8, DevicePage.class);
                navigationMap.put(8, DevicePage.class);
            }
            navigationMap.put(2, Gas.class);
        }

        if (telefoniaFissaServizio.isChecked() && telefoniaFissaServizio.isEnabled()) {
            navigationMap.put(5, TLC.class);
        }
        if (internetServizio.isChecked() && internetServizio.isEnabled()) {
            navigationMap.put(4, TLC.class);
        }

        if (mobileServizio.isChecked() && mobileServizio.isEnabled()) {
            navigationMap.put(6, MobilePage.class);
        }

//        if (assicurazioneServizio.isChecked() && assicurazioneServizio.isEnabled() &&
//                (!elettricitaServizio.isEnabled() || !gasServisio.isEnabled())) {
//            navigationMap.put(6, AssicurazionePage.class);
//            servizioNavigationMap.put(6, AssicurazionePage.class);
//        }

        MyApplication.set("navigationMap", navigationMap);
        return servizioNavigationMap;
    }

    private void setDataToOffer() {
        if (offer != null && isConsumerFlag) {
            HouseHolder houseHolder = (llAbitantiDellaCasa.getVisibility() == View.VISIBLE) ? (HouseHolder) abitantiSpinner.getSelectedItem() : new HouseHolder();
            String dimension = (llSuperficieAbitazione.getVisibility() == View.VISIBLE) ? editTextGetTextString(R.id.dimensioniEditText) : "1";
            HouseMontlyUse mesi = (llPeriodoDiUso.getVisibility() == View.VISIBLE) ? (HouseMontlyUse) periodoSpinner.getSelectedItem() : new HouseMontlyUse();
            FlatType flatType = (llTipologiaAbitazione.getVisibility() == View.VISIBLE) ? (FlatType) tipologiaAbitazione.getSelectedItem() : new FlatType();
            GasYearOfReference gasYearOfReference = (llAnnoDiCostruzione.getVisibility() == View.VISIBLE) ? (GasYearOfReference) annoDiCostruzoine.getSelectedItem() : new GasYearOfReference();

            offer.setHouseHolderId(houseHolder.getHouseHolderId());
            offer.setSuperficie(Integer.valueOf(dimension));
            offer.setMesi(mesi.getHouseMontlyUseValue());
            offer.setFlatType(flatType.getIdFlat());
            offer.setGasYearOfReference(gasYearOfReference.getIdYearOfReference());

            offer.setRestoration(((Restoration) ristrutturazione.getSelectedItem()).getIdRestoration());
            offer.setThermalinsulation(((ThermalInsulation) tipologiaDiIsolamento.getSelectedItem()).getIdThermalInsulation());
            offer.setUnderfloorHeating(riscaldamentoAPavimentoSi.isChecked() ? 0 : 1);
            offer.setDoubleGlazing(vetriDoppi.isChecked() ? 0 : 1);
            offer.setBoilerType(((BoilerTypes) tipologiaDiCaldaia.getSelectedItem()).getIdType());

            new OfferDAOImpl().update(offer);
            MyApplication.set(Constants.OFFER_ENTITY, offer);
        }
    }

    private boolean checkSpinnerSelection() {
        boolean isAbitantiDellaCasa = llAbitantiDellaCasa.getVisibility() == View.VISIBLE;
        boolean isTipologiaAbitazione = llTipologiaAbitazione.getVisibility() == View.VISIBLE;
        boolean isPeriodoDiUso = llPeriodoDiUso.getVisibility() == View.VISIBLE;
        boolean isAnnoDiCostruzione = llAnnoDiCostruzione.getVisibility() == View.VISIBLE;
        return (
                (!isAbitantiDellaCasa || (abitantiSpinner.getSelectedItemPosition() != 0)) &&
                        (!isTipologiaAbitazione || (tipologiaAbitazione.getSelectedItemPosition() != 0)) &&
                        (!isPeriodoDiUso || (periodoSpinner.getSelectedItemPosition() != 0)) &&
                        (!isAnnoDiCostruzione || (annoDiCostruzoine.getSelectedItemPosition() != 0))
        );
    }

    public void checkBoxCheckValidation(View view) {
        mainValidation();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mainValidation();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode != 4 && super.onKeyDown(keyCode, event);
    }
}