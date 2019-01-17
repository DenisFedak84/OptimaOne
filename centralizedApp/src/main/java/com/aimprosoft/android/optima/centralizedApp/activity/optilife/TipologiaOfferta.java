package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientServicesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.BoilerTypes;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientServices;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.FlatType;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasYearOfReference;
import com.aimprosoft.android.optima.centralizedApp.db.entity.HouseHolder;
import com.aimprosoft.android.optima.centralizedApp.db.entity.HouseMontlyUse;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Restoration;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ThermalInsulation;
import com.aimprosoft.android.optima.centralizedApp.service.AbitantiDellaCasaSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.AnnoDiCostruzioneSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.PeriodoUsoAbitazioneSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.RistrutturazioneSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.TipologiaAbitazioneSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.TipologiaDiCaldaiaSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.TipologiaDiIsolamentoSpinner;
import com.aimprosoft.android.optima.centralizedApp.util.LocalSharedPreferencesManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipologiaOfferta extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private ClientServices clientServices = new ClientServices();
    private Map<Integer, Class> navigationMap;

    private Offer offer = MyApplication.get(Constants.OFFER_ENTITY, Offer.class);
    private EditText dimensioniEditText;
    private Spinner abitantiSpinner;
    private Spinner periodoSpinner;
    private Spinner tipologiaAbitazione;
    private Spinner annoDiCostruzoine;
    private LinearLayout llVetriDoppi;
    private Spinner ristrutturazione;
    private Spinner tipologiaDiIsolamento;
    private Spinner tipologiaDiCaldaia;
    private EditText superficie;
    private boolean isModificaMod;
    private ImageButton continuaEnabled;
    private ImageButton continuaDisabled;
    private Button ricalcolaEnabled;
    private Button ricalcolaDisabled;
    private LinearLayout continuaLayout;
    private LinearLayout ricalcolaLayout;
    private LinearLayout llInformazioniAbitazion;
    private LinearLayout llAbitantiDellaCasa;
    private LinearLayout llTipologiaAbitazione;
    private LinearLayout llSuperficieAbitazione;
    private LinearLayout llPeriodoDiUso;
    private LinearLayout llAnnoDiCostruzione;
    private LinearLayout llRistrutturazione;
    private LinearLayout llTipologiaDiIsolamento;
    private LinearLayout llRiscaldamentoAPavimento;

    private LinearLayout llTipologiaDiCaldaia;

    private RadioButton riscaldamentoAPavimentoSi;
    private RadioButton vetriDoppi;
    private RadioGroup energyQuestionarioRadioGroup;
    private RadioGroup gasQuestionarioRadioGroup;
    private CheckBox completa;
    private CheckBox elettricita;
    private CheckBox gas;
    private CheckBox telefoniaFissa;
    private CheckBox internet;
    private CheckBox mobile;
    private CheckBox assicurazione;

    private RadioButton conFatturaElettricita;
    private RadioButton conFatturaGas;
    private RadioButton senzaFatturaElettricita;
    private RadioButton senzaFatturaGas;

    private Animation showAnimation;
    private Animation hideAnimation;

    private CompoundButton.OnCheckedChangeListener tlcGroupListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mainValidation();
            visibilityLayoutFields();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tipologia_offert_layout_optilife);
        initializeValues();
        fillViewsByValues();
        setVisibilityLayouts(false, false, false);
//       checkForUserReturn();

        checkOfferForExistingClient();

        isModificaMod = MyApplication.get("modificaInformazioniAbitazione", Boolean.class) != null ? MyApplication.get("modificaInformazioniAbitazione", Boolean.class) : false;

        elettricita.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                setFatturaViewGroupVisibility(isChecked, energyQuestionarioRadioGroup);
                checkAssicurazioneService(isChecked, gas.isChecked());
                if (!isChecked) {
//                    energyQuestionarioRadioGroup.clearCheck();
                }
            }
        });

        gas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                setFatturaViewGroupVisibility(isChecked, gasQuestionarioRadioGroup);
                checkAssicurazioneService(elettricita.isChecked(), isChecked);
                if (!isChecked) {
//                    gasQuestionarioRadioGroup.clearCheck();
                }
            }
        });

//        energyQuestionarioRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                mainValidation();
//                visibilityLayoutFields();
//            }
//        });
//
//        gasQuestionarioRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                mainValidation();
//                visibilityLayoutFields();
//            }
//        });
//
//        conFatturaElettricita.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                visibilityLayoutFields();
//            }
//        });
//
//        conFatturaGas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                visibilityLayoutFields();
//            }
//        });
//
//        senzaFatturaElettricita.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                visibilityLayoutFields();
//            }
//        });
//
//        senzaFatturaGas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                visibilityLayoutFields();
//            }
//        });

        telefoniaFissa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                visibilityLayoutFields();
            }
        });

        internet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                visibilityLayoutFields();
            }
        });

        telefoniaFissa.setOnCheckedChangeListener(tlcGroupListener);
        internet.setOnCheckedChangeListener(tlcGroupListener);
        completa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                visibilityLayoutFields();
                elettricita.setChecked(isChecked);
                gas.setChecked(isChecked);
                mobile.setChecked(isChecked);
//                ((CheckBox) findViewById(R.id.mobile)).setChecked(flag);
                internet.setChecked(isChecked);
                telefoniaFissa.setChecked(isChecked);
                assicurazione.setChecked(isChecked);
            }
        });

        completa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    elettricita.setChecked(isChecked);
                    gas.setChecked(isChecked);
                    telefoniaFissa.setChecked(isChecked);
                    internet.setChecked(isChecked);
                    mobile.setChecked(isChecked);
                    assicurazione.setEnabled(true);
                    assicurazione.setChecked(isChecked);
                } else {
                    assicurazione.setEnabled(false);
                    showAlreadyClientCheck();
                }
            }
        });

        ricalcolaEnabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((llSuperficieAbitazione.getVisibility() != View.VISIBLE) || isValid(true, R.id.dimensioniEditText)) {
                    MyApplication.set(Constants.OFFER_ENTITY, buildOfferEntityFromActivity());
                    startActivity(new Intent(TipologiaOfferta.this, Modifica.class));
                }
            }
        });

        ImageButton continua = (ImageButton) findViewById(R.id.continua2);
        continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkChoiceStatus("energetici");
                checkChoiceStatus("gas");
                MyApplication.set("navigationMap", createNavigationMap());
                MyApplication.set(Constants.OFFER_ENTITY, buildOfferEntityFromActivity());
                checkForUserReturn();
                startConfigurationOffer(navigationMap);
            }
        });
    }

    private void checkAssicurazioneService(boolean isEnergySelected, boolean isGasSelected) {
        if (isEnergySelected || isGasSelected) {
            assicurazione.setEnabled(true);
        } else {
            assicurazione.setEnabled(false);
            assicurazione.setChecked(false);
        }
    }

    private void setFatturaViewGroupVisibility(boolean isChecked, RadioGroup radioGroup) {
        if (offer.getConfiguratorType() != Constants.BUSINESS_CONFIGURATOR) {
            radioGroup.startAnimation(isChecked ? showAnimation : hideAnimation);
            radioGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            if (isChecked) {
                radioGroup.clearCheck();
            }
        }
    }

    private void checkOfferForExistingClient() {
        if (offer != null && offer.getOldTlcOfferUsed()) {
            clientServices = new ClientServicesDAOImpl().getClientServicesByClientId(offer.getOldTlcOfferId());
            showAlreadyClientCheck();
            checkBoxCheckValidation();
        }
    }

    private void showAlreadyClientCheck() {
        setExistingClientChoice(clientServices.getEnergyServiceId(), elettricita);
        setExistingClientChoice(clientServices.getGasServiceId(), gas);
        setExistingClientChoice(clientServices.getTlcServiceId(), telefoniaFissa);
        setExistingClientChoice(clientServices.getAdslServiceId(), internet);
    }

    private void setExistingClientChoice(Integer id, CheckBox checkBox) {
        checkBox.setChecked(id != null);
    }

    private void fillViewsByValues() {
        TextView nomeCognome = (TextView) findViewById(R.id.name);
        if (offer != null) {
            String name = offer.getName() + " " + offer.getSurname();
            nomeCognome.setText(name);
        }


        new AbitantiDellaCasaSpinner(this, new AbstractService.OnTaskCompleted<AbitantiDellaCasaSpinner>() {
            @Override
            public void onTaskCompleted(AbitantiDellaCasaSpinner service) {
                setSpinner(abitantiSpinner, service.getResult());
            }
        }).execute();

        new PeriodoUsoAbitazioneSpinner(this, new AbstractService.OnTaskCompleted<PeriodoUsoAbitazioneSpinner>() {
            @Override
            public void onTaskCompleted(PeriodoUsoAbitazioneSpinner service) {
                setSpinner(periodoSpinner, service.getResult());
                if (isModificaMod) {
                    changeVisibility(ricalcolaLayout, continuaLayout);
                    findViewById(R.id.chooseOffertaPart).setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fillPageWithDbValues();
                        }
                    }, 200);
                } else {
                    checkForUserReturn();
                }
            }
        }).execute();

        new AnnoDiCostruzioneSpinner(this, new AbstractService.OnTaskCompleted<AnnoDiCostruzioneSpinner>() {
            @Override
            public void onTaskCompleted(AnnoDiCostruzioneSpinner service) {
                setSpinner(annoDiCostruzoine, service.getResult());
            }
        }).execute();

        new TipologiaAbitazioneSpinner(this, new AbstractService.OnTaskCompleted<TipologiaAbitazioneSpinner>() {
            @Override
            public void onTaskCompleted(TipologiaAbitazioneSpinner service) {
                setSpinner(tipologiaAbitazione, service.getResult());

            }
        }).execute();

        new RistrutturazioneSpinner(this, new AbstractService.OnTaskCompleted<RistrutturazioneSpinner>() {
            @Override
            public void onTaskCompleted(RistrutturazioneSpinner service) {
                setSpinner(ristrutturazione, service.getResult());
            }
        }).execute();


        new TipologiaDiIsolamentoSpinner(this, new AbstractService.OnTaskCompleted<TipologiaDiIsolamentoSpinner>() {
            @Override
            public void onTaskCompleted(TipologiaDiIsolamentoSpinner service) {
                setSpinner(tipologiaDiIsolamento, service.getResult());
            }
        }).execute();

        new TipologiaDiCaldaiaSpinner(this, new AbstractService.OnTaskCompleted<TipologiaDiCaldaiaSpinner>() {
            @Override
            public void onTaskCompleted(TipologiaDiCaldaiaSpinner service) {
                setSpinner(tipologiaDiCaldaia, (List) service.getResult());
            }
        }).execute();

        abitantiSpinner.setOnItemSelectedListener(this);
        periodoSpinner.setOnItemSelectedListener(this);
        tipologiaAbitazione.setOnItemSelectedListener(this);
        annoDiCostruzoine.setOnItemSelectedListener(this);
    }

    private void fillPageWithDbValues() {
        if (offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR) {

            EnergyOfferDAOImpl energyOfferDAO = new EnergyOfferDAOImpl();
            if (offer.getEnergyOfferId() != null) {
                EnergyOffer energyOffer = energyOfferDAO.getEnergyOfferById(offer.getEnergyOfferId());
                if (energyOffer.isQuestionarioUsed()) {
                    setVisibilityLayouts(true, true, false);
//            }
//        }
//
//        GasOfferDAOImpl gasOfferDAO = new GasOfferDAOImpl();
//        if (offer.getGasOfferId() != null) {
//            GasOffer gasOffer = gasOfferDAO.getGasOfferById(offer.getGasOfferId());
//            if (gasOffer.isQuestionarioUsed()) {
//                setVisibilityLayouts(true, true, true);
//            }
//        }
//        abitantiSpinner.setSelection(offer.getHouseHolderId());
//        tipologiaAbitazione.setSelection(offer.getFlatType());
//        periodoSpinner.setSelection(offer.getMesi());
//        annoDiCostruzoine.setSelection(offer.getGasYearOfReference());
//        dimensioniEditText.setText(String.valueOf(offer.getSuperficie()));
//        ristrutturazione.setSelection(offer.getRestoration() - 1);
//        tipologiaDiIsolamento.setSelection(offer.getThermalinsulation() - 1);
//        riscaldamentoAPavimentoSi.setChecked(offer.getUnderfloorHeating() == 0);
//        vetriDoppi.setChecked(offer.getDoubleGlazing() == 0);
//        tipologiaDiCaldaia.setSelection(offer.getBoilerType() - 1);
                }
            }

            GasOfferDAOImpl gasOfferDAO = new GasOfferDAOImpl();
            if (offer.getGasOfferId() != null) {
                GasOffer gasOffer = gasOfferDAO.getGasOfferById(offer.getGasOfferId());
                if (gasOffer.isQuestionarioUsing()) {
                    setVisibilityLayouts(true, true, true);
                }
            }

            abitantiSpinner.setSelection(offer.getHouseHolderId());
            tipologiaAbitazione.setSelection(offer.getFlatType());
            periodoSpinner.setSelection(offer.getMesi());
            annoDiCostruzoine.setSelection(offer.getGasYearOfReference());
            dimensioniEditText.setText(String.valueOf(offer.getSuperficie()));
            ristrutturazione.setSelection(offer.getRestoration() - 1);
            tipologiaDiIsolamento.setSelection(offer.getThermalinsulation() - 1);
            riscaldamentoAPavimentoSi.setChecked(offer.getUnderfloorHeating() == 0);
            vetriDoppi.setChecked(offer.getDoubleGlazing() == 0);
            tipologiaDiCaldaia.setSelection(offer.getBoilerType() - 1);
        }
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

    private void initializeValues() {
        showAnimation = AnimationUtils.loadAnimation(this, R.anim.show_from_top);
        hideAnimation = AnimationUtils.loadAnimation(this, R.anim.hide_form_top);
        conFatturaElettricita = (RadioButton) findViewById(R.id.conFatturaEnergy);
        conFatturaGas = (RadioButton) findViewById(R.id.conFatturaGas);
        senzaFatturaElettricita = (RadioButton) findViewById(R.id.senzaFatturaEnergy);
        senzaFatturaGas = (RadioButton) findViewById(R.id.senzaFatturaGas);
        ristrutturazione = (Spinner) findViewById(R.id.ristrutturazione);
        tipologiaDiIsolamento = (Spinner) findViewById(R.id.tipologiaDiIsolamento);
        riscaldamentoAPavimentoSi = (RadioButton) findViewById(R.id.riscaldamentoAPavimentoSi);
        vetriDoppi = (RadioButton) findViewById(R.id.vertiDoppiSi);
        tipologiaDiCaldaia = (Spinner) findViewById(R.id.tipologiaDiCaldaia);
        abitantiSpinner = (Spinner) findViewById(R.id.abitantiSpinner);
        dimensioniEditText = (EditText) findViewById(R.id.dimensioniEditText);
        periodoSpinner = (Spinner) findViewById(R.id.periodoSpinner);
        tipologiaAbitazione = (Spinner) findViewById(R.id.tipologiaAbitazioneSpinner);
        annoDiCostruzoine = (Spinner) findViewById(R.id.annoDiCostruzoineSpinner);
        superficie = (EditText) findViewById(R.id.dimensioniEditText);
        continuaEnabled = (ImageButton) findViewById(R.id.continua2);
        continuaDisabled = (ImageButton) findViewById(R.id.continua2Disabled);
        ricalcolaEnabled = (Button) findViewById(R.id.ricalcolaEnabled);
        ricalcolaDisabled = (Button) findViewById(R.id.ricalcolaDisabled);
        continuaLayout = (LinearLayout) findViewById(R.id.continuaLayout);
        ricalcolaLayout = (LinearLayout) findViewById(R.id.ricalcolaLayout);
        energyQuestionarioRadioGroup = (RadioGroup) findViewById(R.id.energyQuestionaRioradioGroup);
        gasQuestionarioRadioGroup = (RadioGroup) findViewById(R.id.gasQuestionaRioradioGroup);
        elettricita = (CheckBox) findViewById(R.id.elettricita);
        gas = (CheckBox) findViewById(R.id.gas);
        internet = (CheckBox) findViewById(R.id.internet);
        mobile = (CheckBox) findViewById(R.id.mobile);
        assicurazione = (CheckBox) findViewById(R.id.assicurazione);
        telefoniaFissa = (CheckBox) findViewById(R.id.telefoniaFissa);
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
        completa = (CheckBox) findViewById(R.id.completa);

        superficie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mainValidation();
            }
        });
    }

    private Map createNavigationMap() {
        navigationMap = new HashMap<>();
        if (elettricita.isChecked()) {
            navigationMap.put(1, EnergeticiLaFattura.class);
            if (assicurazione.isChecked()) {
//                navigationMap.put(7, AssicurazionePage.class);
            }
            if (offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR) {
                navigationMap.put(8, DevicePage.class);
            }
        }

        if (gas.isChecked()) {
            navigationMap.put(2, Gas.class);
            if (assicurazione.isChecked()) {
//                navigationMap.put(7, AssicurazionePage.class);
            }
            if (offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR) {
                navigationMap.put(8, DevicePage.class);
            }
        }

        if (internet.isChecked()) {
            navigationMap.put(4, TLC.class);
        }
        if (telefoniaFissa.isChecked()) {
            navigationMap.put(5, TLC.class);
        }

        if (mobile.isChecked() || (!(navigationMap.size() == 1 && navigationMap.containsKey(5)) &&
                !(navigationMap.size() == 2 && navigationMap.containsKey(5) && navigationMap.containsKey(4)))) {
            navigationMap.put(6, MobilePage.class);
        }
        return navigationMap;
    }

    private void mainValidation() {
        if ((isModificaMod || checkBoxValidation()) &&
                checkViewSelection()) {
            changeVisibility(continuaEnabled, continuaDisabled);
            changeVisibility(ricalcolaEnabled, ricalcolaDisabled);
        } else {
            changeVisibility(continuaDisabled, continuaEnabled);
            changeVisibility(ricalcolaDisabled, ricalcolaEnabled);

        }
    }

    public void checkBoxCheckValidation(View view) {
        mainValidation();
    }

    private void visibilityLayoutFields() {
        if (offer != null && offer.getConfiguratorType() != Constants.BUSINESS_CONFIGURATOR) {
            boolean isEnergyChoiceValid = energyQuestionarioRadioGroup.getCheckedRadioButtonId() != -1;
            boolean isGasChoiceValid = gasQuestionarioRadioGroup.getCheckedRadioButtonId() != -1;

            if ((elettricita.isChecked() & senzaFatturaElettricita.isChecked()) & !(gas.isChecked() & senzaFatturaGas.isChecked())) {
                setVisibilityLayouts(true, true, false);
            } else if ((gas.isChecked() & senzaFatturaGas.isChecked())) {
                setVisibilityLayouts(true, true, true);
            } else if (!((elettricita.isChecked() & !isEnergyChoiceValid) | (gas.isChecked() & !isGasChoiceValid))) {
                setVisibilityLayouts(false, false, false);
            }
        }
    }

    private void changeVisibility(View visibleView, View invisibleView) {
        visibleView.setVisibility(View.VISIBLE);
        invisibleView.setVisibility(View.GONE);
    }

    private boolean checkBoxValidation() {
//        boolean isEnergyChoiceValid = energyQuestionarioRadioGroup.getCheckedRadioButtonId() != -1 || (elettricita.isChecked() && offer.getConfiguratorType()==Constants.BUSINESS_CONFIGURATOR);
//        boolean isGasChoiceValid = gasQuestionarioRadioGroup.getCheckedRadioButtonId() != -1 || (gas.isChecked() && offer.getConfiguratorType()==Constants.BUSINESS_CONFIGURATOR);

        boolean isEnergyChoiceValid = energyQuestionarioRadioGroup.getCheckedRadioButtonId() != -1 || elettricita.isChecked();
        boolean isGasChoiceValid = gasQuestionarioRadioGroup.getCheckedRadioButtonId() != -1 || gas.isChecked();

        return (isEnergyChoiceValid | isGasChoiceValid | telefoniaFissa.isChecked() | internet.isChecked() | mobile.isChecked());
//        return (isEnergyChoiceValid | isGasChoiceValid | telefoniaFissa.isChecked() | internet.isChecked()) &&
//                (isEnergyChoiceValid || !elettricita.isChecked()) && (isGasChoiceValid || !gas.isChecked());
    }

    private boolean checkViewSelection() {
        boolean isAbitantiDellaCasa = llAbitantiDellaCasa.getVisibility() == View.VISIBLE;
        boolean isTipologiaAbitazione = llTipologiaAbitazione.getVisibility() == View.VISIBLE;
        boolean isPeriodoDiUso = llPeriodoDiUso.getVisibility() == View.VISIBLE;
        boolean isAnnoDiCostruzione = llAnnoDiCostruzione.getVisibility() == View.VISIBLE;
        boolean isSuperficieAbitazione = llSuperficieAbitazione.getVisibility() == View.VISIBLE;
        return ((!isAbitantiDellaCasa || abitantiSpinner.getSelectedItemPosition() != 0) &&
                (!isTipologiaAbitazione || tipologiaAbitazione.getSelectedItemPosition() != 0) &&
                (!isPeriodoDiUso || periodoSpinner.getSelectedItemPosition() != 0) &&
                (!isAnnoDiCostruzione || annoDiCostruzoine.getSelectedItemPosition() != 0) &&
                (!isSuperficieAbitazione || isValid(true, superficie.getId()))
        );
    }

    private Offer buildOfferEntityFromActivity() {
        HouseHolder houseHolder = (llAbitantiDellaCasa.getVisibility() == View.VISIBLE) ? (HouseHolder) abitantiSpinner.getSelectedItem() : new HouseHolder();
        String dimension = (llSuperficieAbitazione.getVisibility() == View.VISIBLE) ? editTextGetTextString(superficie.getId()) : "0";
        HouseMontlyUse mesi = (llPeriodoDiUso.getVisibility() == View.VISIBLE) ? (HouseMontlyUse) periodoSpinner.getSelectedItem() : new HouseMontlyUse();
        FlatType flatType = (llTipologiaAbitazione.getVisibility() == View.VISIBLE) ? (FlatType) tipologiaAbitazione.getSelectedItem() : new FlatType();
        offer.setSuperficie(Integer.valueOf(dimension));

        offer.setRestoration(((Restoration) ristrutturazione.getSelectedItem()).getIdRestoration());
        offer.setThermalinsulation(((ThermalInsulation) tipologiaDiIsolamento.getSelectedItem()).getIdThermalInsulation());
        offer.setUnderfloorHeating(riscaldamentoAPavimentoSi.isChecked() ? 0 : 1);
        offer.setDoubleGlazing(vetriDoppi.isChecked() ? 0 : 1);
        offer.setBoilerType(((BoilerTypes) tipologiaDiCaldaia.getSelectedItem()).getIdType());
        offer.setFlatType(flatType.getIdFlat());
        offer.setHouseHolderId(houseHolder.getHouseHolderId());
        offer.setFlatDimensionId(Integer.valueOf(dimension));
        offer.setGasHeatingType(0);
        offer.setMesi(mesi.getHouseMontlyUseValue());
        offer.setNumeroPersone(houseHolder.getHouseHolderValue());
        offer.setEnergyClass(0);
        GasYearOfReference gasYearOfReference = (llAnnoDiCostruzione.getVisibility() == View.VISIBLE) ?
                (GasYearOfReference) annoDiCostruzoine.getSelectedItem() : new GasYearOfReference();
        offer.setGasYearOfReference(gasYearOfReference.getIdYearOfReference());
//        gasQuestionario.setAnno_edificio(gasYearOfReference.getValueYearOfReference());

        if (offer.getOfferId() == 0) {
            new OfferDAOImpl().insert(offer);
        } else {
            new OfferDAOImpl().update(offer);
        }
        return offer;
    }

    public void checkBoxCheckValidation() {
        if ((elettricita.isChecked() |
                gas.isChecked() |
                telefoniaFissa.isChecked() |
                internet.isChecked() |
                mobile.isChecked() |
//                ((CheckBox) findViewById(R.id.mobile)).isChecked() |
                completa.isChecked())) /*&&
                checkViewSelection()) */ {
            findViewById(R.id.continua2).setVisibility(View.VISIBLE);
            findViewById(R.id.continua2Disabled).setVisibility(View.GONE);
        } else {
            findViewById(R.id.continua2Disabled).setVisibility(View.VISIBLE);
            findViewById(R.id.continua2).setVisibility(View.GONE);
        }
    }

    private void checkForUserReturn() {
        offer.setEnergyOfferId(null);
        offer.setGasOfferId(null);
        offer.setTlcOfferId(null);
        offer.setInternetOfferId(null);
        offer.setMobileOfferId(null);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mainValidation();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LocalSharedPreferencesManager.getInstance().storeSharedPreferencesBooleanValue(this, LocalSharedPreferencesManager.IS_APP_CHECKED_FOR_UPDATE, false);
    }
}
