package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.MobileBundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileDetailsOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileBundle;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.MobileBundleSpinnerService;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.AbstractJarUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.aimprosoft.android.optima.centralizedApp.R.id.offertaBaseSim;

public class MobilePage extends BaseActivity {

    public final int CURRENT_PAGE_NUMBER = 6;
    private Offer offer = MyApplication.get("offerEntity", Offer.class);
    private MobileOffer mobileOffer;

    private ImageButton continuaEnabledButton;

    private Spinner offertaBaseSim1;
    private Spinner offertaBaseSim2;

    private Spinner offertaAlternativaVoceSim1;
    private Spinner offertaAlternativaVoceSim2;

    private Spinner offertaAlternativaSoloSim1;
    private Spinner offertaAlternativaSoloSim2;

    private Space soloSpaceSim1;
    private Space soloSpaceSim2;

    private CheckBox simCheckBox1;
    private CheckBox simCheckBox2;

    private RadioGroup offertaRadioGroupSim1;
    private RadioGroup offertaRadioGroupSim2;
    private RadioButton offertaBaseRadioButtonSim1;
    private RadioButton offertaBaseRadioButtonSim2;
    private RadioButton offertaAlternativaVoceRadioButtonSim1;
    private RadioButton offertaAlternativaVoceRadioButtonSim2;
    private RadioButton offertaAlternativaSoloRadioButtonSim1;
    private RadioButton offertaAlternativaSoloRadioButtonSim2;

    private TextView costoMobileSim1;
    private TextView costoMobileSim2;
    private TextView scontoMobileSim1;
    private TextView scontoMobileSim2;
    private TextView costoMobileScontato;
    private TextView costoMobileScontatoSim2;

    private TextView alternativaCostoMobileSim1;
    private TextView alternativaCostoMobileScontato;
    private TextView alternativaCostoMobileSim2;
    private TextView alternativaCostoMobileScontato2;

    private ImageButton addEnabled;
    private ImageButton addDisabled;

    private LinearLayout simContainer;
    private LinearLayout alternativaSim1PriceContainer;
    private LinearLayout alternativaSim2PriceContainer;
    private RelativeLayout sim2Info;
    private TextView scontoHeaderForSim2;
    private TextView costHeaderForSim2;
    private TextView scontatocostHeaderForSim2;

    private int firstSimSconto = 0;
    private int secondSimSconto = 0;

    private int servicesCount = 0;

    private DecimalFormat decimalFormat;
    private DecimalFormatSymbols fts = new DecimalFormatSymbols();

    private List<MobileBundle> offertaBaseList = new ArrayList<>();
    private List<MobileBundle> offertaEDati = new ArrayList<>();
    private List<MobileBundle> offertaSoloDati = new ArrayList<>();
    private List<CheckBox> simCheckBoxList = new ArrayList<>();

    private int simSpinnerCompleteCounter = 0;
    private boolean simCheckBoxValidationMode = false;

    private MobileOfferDAOImpl mobileOfferDAO = new MobileOfferDAOImpl();
    private MobileDetailsOfferDAOImpl mobileDetailsOfferDAO = new MobileDetailsOfferDAOImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_page);

        initializeViews();
//        addListeners();
        initDecimalFormater();
        defineScontoValues();
        fillMobileBundleSpinners();

        if (offer != null && offer.getMobileOfferId() == null) {
            mobileOffer = new MobileOffer();
        } else {
            mobileOffer = mobileOfferDAO.getMobileOfferById(offer.getMobileOfferId());
        }
    }

    private void fillWithExistingOffer(Integer mobileOfferId) {
        List<MobileDetailsOffer> mobileDetailsOffers = mobileDetailsOfferDAO.getMobileOfferDetailsByMobileOfferId(mobileOfferId);

        if (mobileDetailsOffers.size() >= 1) {
            MobileDetailsOffer sim1Model = mobileDetailsOffers.get(0);
            simCheckBox1.setChecked(true);
            fillSimLayout(offertaRadioGroupSim1, sim1Model, offertaBaseSim1,
                    offertaAlternativaVoceSim1, offertaAlternativaSoloSim1);
        }

        if (servicesCount >= 2 && mobileDetailsOffers.size() >= 2) {
            MobileDetailsOffer sim2Model = mobileDetailsOffers.get(1);
            simCheckBox2.setChecked(true);
            fillSimLayout(offertaRadioGroupSim2, sim2Model, offertaBaseSim2,
                    offertaAlternativaVoceSim2, offertaAlternativaSoloSim2);
        }

        for (int i = servicesCount > 2 ? 2 : 1; i < mobileDetailsOffers.size(); i++) {
            addSimAction(mobileDetailsOffers.get(i));
        }
    }

    private void fillSimLayout(RadioGroup offertaRadioGroupSim, MobileDetailsOffer simModel, Spinner offertaBaseSim,
                               Spinner voceSpinner, Spinner soloDatiSpinner) {
//        ((RadioButton) offertaRadioGroupSim.getChildAt(simModel.getOffertaId())).setChecked(true);
        ((RadioButton) offertaRadioGroupSim.getChildAt(0)).setChecked(true);

        preselectMobileBundleSpinner(offertaBaseSim, simModel.getBundleId());

//        switch (simModel.getOffertaId()) {
//            case 0:
//                preselectMobileBundleSpinner(offertaBaseSim, simModel.getBundleId());
//                break;
//            case 1:
//                preselectMobileBundleSpinner(voceSpinner, simModel.getBundleId());
//                break;
//            case 2:
//                preselectMobileBundleSpinner(soloDatiSpinner, simModel.getBundleId());
//                break;
//        }
    }

    private void preselectMobileBundleSpinner(Spinner spinner, int mobileBundleId) {
        for (int i = 0; i < spinner.getCount(); i++) {
            MobileBundle bundle = (MobileBundle) spinner.getItemAtPosition(i);
            if (bundle.getId() == mobileBundleId) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void initDecimalFormater() {
        fts.setDecimalSeparator('.');
        String PATTERN = "#####0.00";
        decimalFormat = new DecimalFormat(PATTERN, fts);
    }

    private void fillMobileBundleSpinners() {
        int currentIdCampagana = AbstractJarUtil.getMobileIdCampagna(offer);
        new MobileBundleSpinnerService(this, new AbstractService.OnTaskCompleted<MobileBundleSpinnerService>() {
            @Override
            public void onTaskCompleted(MobileBundleSpinnerService service) {
                offertaBaseList = service.getResult();
                checkForAllSpinnerLoaded();
            }
        }, currentIdCampagana, MobileBundleDAOImpl.TipoProdotto.MINUTI_SMS_GIGA, 1).execute();

        new MobileBundleSpinnerService(this, new AbstractService.OnTaskCompleted<MobileBundleSpinnerService>() {
            @Override
            public void onTaskCompleted(MobileBundleSpinnerService service) {
                offertaSoloDati = service.getResult();
                checkForAllSpinnerLoaded();
            }
        }, currentIdCampagana, MobileBundleDAOImpl.TipoProdotto.SOLO_DATI, 0).execute();

        new MobileBundleSpinnerService(this, new AbstractService.OnTaskCompleted<MobileBundleSpinnerService>() {
            @Override
            public void onTaskCompleted(MobileBundleSpinnerService service) {
                offertaEDati = service.getResult();
                checkForAllSpinnerLoaded();
            }
        }, currentIdCampagana, MobileBundleDAOImpl.TipoProdotto.MINUTI_SMS_GIGA, 0).execute();
    }

    private void checkForAllSpinnerLoaded() {
        simSpinnerCompleteCounter++;
        if (simSpinnerCompleteCounter == 3) {
            addListeners();
            offertaEDati.add(0, new MobileBundle());
            offertaSoloDati.add(0, new MobileBundle());
            setSpinner(offertaAlternativaVoceSim1, offertaEDati);
            setSpinner(offertaAlternativaVoceSim2, offertaEDati);
            setSpinner(offertaAlternativaSoloSim1, offertaSoloDati);
            setSpinner(offertaAlternativaSoloSim2, offertaSoloDati);
            setSpinner(offertaBaseSim1, offertaBaseList);
            setSpinner(offertaBaseSim2, offertaBaseList);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (offer != null && offer.getMobileOfferId() != null) {
                        fillWithExistingOffer(offer.getMobileOfferId());
                    } else {
                        preselectSimObjects();
                    }
                }
            }, 500);
        }
    }

    private void preselectSimObjects() {
//        simCheckBox1.setChecked(firstSimSconto != 0);
//        simCheckBox2.setChecked(secondSimSconto != 0);
        setStrikedText(costoMobileSim1, getString(R.string.euro_value, decimalFormat.format(0)));
        costoMobileScontato.setText(getString(R.string.euro_value, decimalFormat.format(0)));
        setStrikedText(costoMobileSim2, getString(R.string.euro_value, decimalFormat.format(0)));
        costoMobileScontatoSim2.setText(getString(R.string.euro_value, decimalFormat.format(0)));
    }

    private void defineScontoValues() {
        Map navigationMap = MyApplication.get("navigationMap", Map.class);
        servicesCount = 0;
        if (navigationMap != null) {
            boolean isEnergySelected = offer.getEnergyOfferId() != null;
            servicesCount += isEnergySelected ? 1 : 0;
            boolean isGasSelected = offer.getGasOfferId() != null;
            servicesCount += isGasSelected ? 1 : 0;
            boolean isAdslSelected = offer.getInternetOfferId() != null;
            servicesCount += isAdslSelected ? 1 : 0;
//            boolean isVoceSelected = offer.getTlcOfferId() != null || offer.getWlrOfferId() != null;
//            servicesCount += isVoceSelected ? 1 : 0;

            if (isEnergySelected || isGasSelected) {
                firstSimSconto = 100;
            }

            if ((isEnergySelected && isGasSelected) || (isEnergySelected && isAdslSelected) ||
                    (isGasSelected && isAdslSelected)) {
                firstSimSconto = 100;
            }

            if (isEnergySelected && isGasSelected && isAdslSelected) {
                secondSimSconto = 50;
            }
        }
        simCheckBoxList.add(simCheckBox1);

        if (servicesCount > 2) {
            sim2Info.setVisibility(View.VISIBLE);
            scontoHeaderForSim2.setVisibility(View.VISIBLE);
            costHeaderForSim2.setVisibility(View.VISIBLE);
            scontatocostHeaderForSim2.setVisibility(View.VISIBLE);
            simCheckBoxList.add(simCheckBox2);
        }

        scontoMobileSim1.setText(getString(R.string.perc_formatted_string, firstSimSconto));
        scontoMobileSim2.setText(getString(R.string.perc_formatted_string, secondSimSconto));

    }

    private void addListeners() {
        continuaEnabledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAllSim();
            }
        });

        simCheckBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (!isChecked && simCheckBox2.isChecked()) {
                if (!isChecked && !validateUncheckAction(simCheckBox1)) {
                    Toast.makeText(MobilePage.this, "Deselezionare dall'ultima SIM", Toast.LENGTH_SHORT).show();
                    simCheckBoxValidationMode = true;
                    simCheckBox1.setChecked(true);
                    return;
                }

//                offertaAlternativaSoloRadioButtonSim1.setEnabled(isChecked);
//                offertaAlternativaVoceRadioButtonSim1.setEnabled(isChecked);
                offertaBaseRadioButtonSim1.setEnabled(isChecked);
//                offertaAlternativaSoloSim1.setClickable(false);
//                offertaAlternativaVoceSim1.setClickable(false);

                processOffertaBase1Bundle(isChecked ? (MobileBundle) offertaBaseSim1.getSelectedItem() : new MobileBundle(), false);

                if (isChecked && simCheckBoxValidationMode) {
                    simCheckBoxValidationMode = false;
                    return;
                }

                if (!isChecked) {
                    offertaBaseRadioButtonSim1.setChecked(true);
                    validateAddSimButton(true);
                } else {
                    offertaBaseSim1.setClickable(true);
                    offertaBaseSim1.setSelection(0);
                    validateAddSimButton(false);
                }
            }
        });

        simCheckBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (!isChecked && simCheckBoxValidationMode) {
//                    simCheckBoxValidationMode = false;
//                    return;
//                }

                if (!isChecked) {
//                    if ((simCheckBoxList.indexOf(simCheckBox2) != simCheckBoxList.size() - 1) || !simCheckBox1.isChecked()) {
//                    if (simCheckBoxList.get(simCheckBoxList.size() - 1).hashCode() != simCheckBox2.hashCode()) {
                    if (!validateUncheckAction(simCheckBox2)) {
                        Toast.makeText(MobilePage.this, !simCheckBox1.isChecked() ? "Selezionare dalla prima SIM" : "Deselezionare dall'ultima SIM", Toast.LENGTH_SHORT).show();
                        simCheckBoxValidationMode = true;
                        simCheckBox2.setChecked(true);
                        return;
                    }
                }

                if (isChecked && !simCheckBox1.isChecked()) {
                    Toast.makeText(MobilePage.this, "Selezionare dalla prima SIM", Toast.LENGTH_SHORT).show();
                    simCheckBoxValidationMode = true;
                    simCheckBox2.setChecked(false);
                    return;
                }

//                offertaAlternativaSoloRadioButtonSim2.setEnabled(isChecked);
//                offertaAlternativaVoceRadioButtonSim2.setEnabled(isChecked);
                offertaBaseRadioButtonSim2.setEnabled(isChecked);
//                offertaAlternativaSoloSim2.setClickable(false);
//                offertaAlternativaVoceSim2.setClickable(false);

                processOffertaBase2Bundle(isChecked ? (MobileBundle) offertaBaseSim2.getSelectedItem() : new MobileBundle(), false);

                if (isChecked && simCheckBoxValidationMode) {
                    simCheckBoxValidationMode = false;
                    return;
                }

                if (!isChecked) {
                    offertaBaseRadioButtonSim2.setChecked(true);
                    validateAddSimButton(true);
                } else {
                    offertaBaseSim2.setClickable(true);
                    offertaBaseSim2.setSelection(0);
                    validateAddSimButton(false);
                }
            }
        });

        offertaBaseSim1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (offertaBaseRadioButtonSim1.isChecked() && simCheckBox1.isChecked()) {
                    MobileBundle mobileBundle = (MobileBundle) adapterView.getAdapter().getItem(i);
                    processOffertaBase1Bundle(mobileBundle, false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        offertaAlternativaSoloSim1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (offertaAlternativaSoloRadioButtonSim1.isChecked() && simCheckBox1.isChecked()) {
                    MobileBundle mobileBundle = (MobileBundle) adapterView.getAdapter().getItem(i);
                    processOffertaBase1Bundle(mobileBundle, true);
                }
                alternativaCostoMobileScontato.setVisibility(i == 0 ? View.INVISIBLE : View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        offertaAlternativaVoceSim1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (offertaAlternativaVoceRadioButtonSim1.isChecked() && simCheckBox1.isChecked()) {
                    MobileBundle mobileBundle = (MobileBundle) adapterView.getAdapter().getItem(i);
                    processOffertaBase1Bundle(mobileBundle, true);
                }
                alternativaCostoMobileScontato.setVisibility(i == 0 ? View.INVISIBLE : View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        offertaBaseRadioButtonSim1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    processOffertaBase1Bundle((MobileBundle) offertaBaseSim1.getSelectedItem(), false);
                    applyFirstSimSpinnersVisibility(true, false, false);
                    soloSpaceSim1.setVisibility(View.GONE);
                    alternativaSim1PriceContainer.setVisibility(View.GONE);
                    offertaAlternativaSoloSim1.setSelection(0);
                    offertaAlternativaVoceSim1.setSelection(0);
                }
            }
        });


        offertaAlternativaVoceRadioButtonSim1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    processOffertaBase1Bundle((MobileBundle) offertaAlternativaVoceSim1.getSelectedItem(), true);
                    applyFirstSimSpinnersVisibility(false, true, false);
                    soloSpaceSim1.setVisibility(View.GONE);
                    alternativaSim1PriceContainer.setVisibility(View.VISIBLE);
                    offertaAlternativaSoloSim1.setSelection(0);
                }
            }
        });

        offertaAlternativaSoloRadioButtonSim1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    processOffertaBase1Bundle((MobileBundle) offertaAlternativaSoloSim1.getSelectedItem(), true);
                    applyFirstSimSpinnersVisibility(false, false, true);
                    soloSpaceSim1.setVisibility(View.VISIBLE);
                    alternativaSim1PriceContainer.setVisibility(View.VISIBLE);
                    offertaAlternativaVoceSim1.setSelection(0);
                }
            }
        });

        offertaBaseSim2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (offertaBaseRadioButtonSim2.isChecked() && simCheckBox2.isChecked()) {
                    MobileBundle mobileBundle = (MobileBundle) adapterView.getAdapter().getItem(i);
                    processOffertaBase2Bundle(mobileBundle, false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        offertaAlternativaSoloSim2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (offertaAlternativaSoloRadioButtonSim2.isChecked() && simCheckBox2.isChecked()) {
                    MobileBundle mobileBundle = (MobileBundle) adapterView.getAdapter().getItem(i);
                    processOffertaBase2Bundle(mobileBundle, true);
                }
                alternativaCostoMobileScontato2.setVisibility(i == 0 ? View.INVISIBLE : View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        offertaAlternativaVoceSim2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (offertaAlternativaVoceRadioButtonSim2.isChecked() && simCheckBox2.isChecked()) {
                    MobileBundle mobileBundle = (MobileBundle) adapterView.getAdapter().getItem(i);
                    processOffertaBase2Bundle(mobileBundle, true);
                }
                alternativaCostoMobileScontato2.setVisibility(i == 0 ? View.INVISIBLE : View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        offertaBaseRadioButtonSim2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    processOffertaBase2Bundle((MobileBundle) offertaBaseSim2.getSelectedItem(), false);
                    applySecondSimSpinnersVisibility(true, false, false);
                    soloSpaceSim2.setVisibility(View.GONE);
                    alternativaSim2PriceContainer.setVisibility(View.GONE);
                    offertaAlternativaVoceSim2.setSelection(0);
                    offertaAlternativaSoloSim2.setSelection(0);
                }
            }
        });


        offertaAlternativaVoceRadioButtonSim2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    processOffertaBase2Bundle((MobileBundle) offertaAlternativaVoceSim2.getSelectedItem(), true);
                    applySecondSimSpinnersVisibility(false, true, false);
                    soloSpaceSim2.setVisibility(View.GONE);
                    alternativaSim2PriceContainer.setVisibility(View.VISIBLE);
                    offertaAlternativaSoloSim2.setSelection(0);
                }
            }
        });


        offertaAlternativaSoloRadioButtonSim2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    processOffertaBase2Bundle((MobileBundle) offertaAlternativaSoloSim2.getSelectedItem(), true);
                    applySecondSimSpinnersVisibility(false, false, true);
                    soloSpaceSim2.setVisibility(View.VISIBLE);
                    alternativaSim2PriceContainer.setVisibility(View.VISIBLE);
                    offertaAlternativaVoceSim2.setSelection(0);
                }
            }
        });

        addEnabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSimAction(null);
                validateAddSimButton(true);
            }
        });
    }

    private void saveAllSim() {
        if (simCheckBox1.isChecked()) {
            List<OffertaModel> simList = new ArrayList<>();
            simList.add(getSim1Offerta(offertaRadioGroupSim1.getCheckedRadioButtonId()));
            if (sim2Info.getVisibility() == View.VISIBLE) {
                simList.add(getSim2Offerta(offertaRadioGroupSim2.getCheckedRadioButtonId()));
            }

            for (int i = 0; i < simContainer.getChildCount(); i++) {
                simList.add(getSimAdditionalOfferta(i));
            }

            for (OffertaModel offertaModel : simList) {
                if (offertaModel != null && !offertaModel.isValid()) {
                    Toast.makeText(this, "Selezione l'offerta prima di procedere", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            writeMobileOfferToBase();
            mobileDetailsOfferDAO.deleteMobileDetailslByMobileOfferId(mobileOffer.getMobileOfferId());

            OffertaModel offertaModelSim1 = simList.get(0);
            if (offertaModelSim1 != null) {
                saveSingleSim(offertaModelSim1, firstSimSconto, "SIM 1");
            }

            if (sim2Info.getVisibility() == View.VISIBLE) {
                OffertaModel offertaModelSim2 = simList.get(1);
                if (offertaModelSim2 != null) {
                    saveSingleSim(offertaModelSim2, secondSimSconto, "SIM 2");
                }
            }

            for (int i = sim2Info.getVisibility() == View.VISIBLE ? 2 : 1; i < simList.size(); i++) {
                OffertaModel offertaModelSimCustom = simList.get(i);
                if (offertaModelSimCustom != null) {
                    saveSingleSim(offertaModelSimCustom, 0, "SIM " + (i + 1));
                }
            }

            offer.setMobileOfferId(mobileOffer.getMobileOfferId());
        } else {
            mobileDetailsOfferDAO.deleteMobileDetailslByMobileOfferId(mobileOffer.getMobileOfferId());
            offer.setMobileOfferId(null);
            new MobileOfferDAOImpl().delete(mobileOffer);
        }
        new OfferDAOImpl().update(offer);
        MyApplication.set("offerEntity", offer);
        isLastPage(CURRENT_PAGE_NUMBER);
    }

    private void writeMobileOfferToBase() {
        if (mobileOffer.getMobileOfferId() == 0) {
            mobileOfferDAO.insert(mobileOffer);
        }
    }

    private void saveSingleSim(OffertaModel offertaModel, int sconto, String sim) {
        MobileBundle mobileBundle = offertaModel.getMobileBundle();
        MobileDetailsOffer mobileDetailsOffer = new MobileDetailsOffer();
        mobileDetailsOffer.setBundleId(mobileBundle.getId());
        mobileDetailsOffer.setMobileOfferId(mobileOffer.getMobileOfferId());
        mobileDetailsOffer.setSconto(sconto);
        mobileDetailsOffer.setSim(sim);
//        mobileDetailsOffer.setCost(sconto != 0 ? (mobileBundle.getCosto() / sconto) * 100 : mobileBundle.getCosto());
//        mobileDetailsOffer.setCostIVA(sconto != 0 ? (mobileBundle.getCostoIva() / sconto) * 100 : mobileBundle.getCostoIva());
        mobileDetailsOffer.setCost(mobileBundle.getCosto());
        mobileDetailsOffer.setCostIVA(mobileBundle.getCostoIva());
        mobileDetailsOffer.setCostExtra(mobileBundle.getCostoAttivazione());
        mobileDetailsOffer.setOffertaId(offertaModel.getOffertaId());
        mobileDetailsOfferDAO.insert(mobileDetailsOffer);
    }

    private void validateAddSimButton(boolean isForcedDisabling) {
        boolean isButtonEnabled = !isForcedDisabling && validateAllSim();

        if (isButtonEnabled) {
            addEnabled.setVisibility(View.VISIBLE);
            addDisabled.setVisibility(View.GONE);
        } else {
            addEnabled.setVisibility(View.GONE);
            addDisabled.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateAllSim() {
        boolean isButtonEnabled = true;
        for (CheckBox checkBox : simCheckBoxList) {
            if (!checkBox.isChecked()) {
                isButtonEnabled = false;
                break;
            }
        }
        return isButtonEnabled;
    }

    private void addSimAction(MobileDetailsOffer mobileDetailsOffer) {
        RelativeLayout simLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.sim_layout, null);

        CheckBox simCheckboxAlt = (CheckBox) simLayout.findViewById(R.id.simOneCheckbox);
        simCheckboxAlt.setText(getString(R.string.sim_number, simContainer.getChildCount()
                + (sim2Info.getVisibility() == View.VISIBLE ? 3 : 2)));

        Spinner offertaBaseSpinnerAlt = (Spinner) simLayout.findViewById(offertaBaseSim);
        Spinner offertaAlternativaVoceAlt = (Spinner) simLayout.findViewById(R.id.offertaAlternativaVoceSim);
        Spinner offertaAlternativaSoloAlt = (Spinner) simLayout.findViewById(R.id.offertaAlternativaSoloSim);

        Space soloSpace = (Space) simLayout.findViewById(R.id.soloSpaceOptional);

        setSpinner(offertaAlternativaSoloAlt, offertaSoloDati);
        setSpinner(offertaAlternativaVoceAlt, offertaEDati);
        setSpinner(offertaBaseSpinnerAlt, offertaBaseList);

        RadioGroup offertaRadioGroup = (RadioGroup) simLayout.findViewById(R.id.offertaRadioGroupSim1);
        RadioButton offertaBaseRadioButton = (RadioButton) simLayout.findViewById(R.id.offertaBaseRadioButtonSim);
        RadioButton offertaAlternativaVoceRadioButton = (RadioButton) simLayout.findViewById(R.id.offertaAlternativaVoceRadioButtonSim);
        RadioButton offertaAlternativaSoloBaseRadioButton = (RadioButton) simLayout.findViewById(R.id.offertaAlternativaSoloRadioButtonSim);

        TextView costoMobile = (TextView) simLayout.findViewById(R.id.costoMobileSim);
        costoMobile.setText(getString(R.string.euro_value, decimalFormat.format(0)));
        TextView alternativaCostoMobile = (TextView) simLayout.findViewById(R.id.alternativaCostoMobileSim);
        LinearLayout alternativaContainerAdditionalSim = (LinearLayout) simLayout.findViewById(R.id.alternativaContainerAdditionalSim);

        LinearLayout deleteLayout = (LinearLayout) simLayout.findViewById(R.id.deleteLayout);
        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (simCheckboxAlt.isChecked()) {
                    Toast.makeText(MobilePage.this, "Selezione l'offerta prima di procedere", Toast.LENGTH_SHORT).show();
                } else {
                    simCheckBoxList.remove(simCheckboxAlt);
                    simContainer.removeView(simLayout);
                    updateSimNames();
                }
            }
        });

        simCheckboxAlt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (!isChecked && simCheckBoxValidationMode) {
//                    simCheckBoxValidationMode = false;
//                    return;
//                }

                if (!isChecked) {
//                    if (simCheckBoxList.get(simCheckBoxList.size() - 1).hashCode() != simCheckbox.hashCode()) {
                    if (!validateUncheckAction(simCheckboxAlt)) {
                        Toast.makeText(MobilePage.this, "Deselezionare dall'ultima SIM", Toast.LENGTH_SHORT).show();
                        simCheckBoxValidationMode = true;
                        simCheckboxAlt.setChecked(true);
                        return;
                    }
                }

                if (isChecked && !validateAllSimBeforeCurrent(simCheckboxAlt)) {
                    Toast.makeText(MobilePage.this, "Selezionare dalla prima SIM", Toast.LENGTH_SHORT).show();
                    simCheckBoxValidationMode = true;
                    simCheckboxAlt.setChecked(false);
                    return;
                }

//                offertaAlternativaVoceRadioButton.setEnabled(isChecked);
//                offertaAlternativaSoloBaseRadioButton.setEnabled(isChecked);
                offertaBaseRadioButton.setEnabled(isChecked);
//                offertaAlternativaVoceAlt.setClickable(false);
//                offertaAlternativaSoloAlt.setClickable(false);

                float costforDisplaying = 0f;
                if (isChecked) {
                    MobileBundle mobileBundle = (MobileBundle) offertaBaseSpinnerAlt.getSelectedItem();
                    costforDisplaying = mobileBundle.getCostoIva();
                }
                costoMobile.setText(getString(R.string.euro_value, decimalFormat.format(costforDisplaying)));

                if (isChecked && simCheckBoxValidationMode) {
                    simCheckBoxValidationMode = false;
                    return;
                }

                if (!isChecked) {
                    offertaBaseRadioButton.setChecked(true);
//                    MobileBundle mobileBundle = (MobileBundle) offertaBaseSpinner.getSelectedItem();
//                    costoMobile.setText(getString(R.string.euro_value, decimalFormat.format(mobileBundle.getCostoIva())));
                    validateAddSimButton(true);
//                    simCheckBoxList.remove(simCheckbox);
                } else {
                    offertaBaseSpinnerAlt.setClickable(true);
                    offertaBaseSpinnerAlt.setSelection(0);
//                    MobileBundle mobileBundle = (MobileBundle) offertaBaseSpinner.getSelectedItem();
//                    costoMobile.setText(getString(R.string.euro_value, decimalFormat.format(mobileBundle.getCostoIva())));
//                    if (!simCheckBoxList.contains(simCheckbox)) {
//                        simCheckBoxList.add(simCheckbox);
//                    }
                    validateAddSimButton(false);
                }
            }
        });

        offertaBaseSpinnerAlt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (offertaBaseRadioButton.isChecked() && simCheckboxAlt.isChecked()) {
                    MobileBundle mobileBundle = (MobileBundle) adapterView.getAdapter().getItem(i);
                    costoMobile.setText(getString(R.string.euro_value, decimalFormat.format(mobileBundle.getCostoIva())));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        offertaAlternativaVoceAlt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (offertaAlternativaVoceRadioButton.isChecked() && simCheckboxAlt.isChecked()) {
                    MobileBundle mobileBundle = (MobileBundle) adapterView.getAdapter().getItem(i);
                    alternativaCostoMobile.setText(getString(R.string.euro_value, decimalFormat.format(mobileBundle.getCostoIva())));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        offertaAlternativaSoloAlt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (offertaAlternativaSoloBaseRadioButton.isChecked() && simCheckboxAlt.isChecked()) {
                    MobileBundle mobileBundle = (MobileBundle) adapterView.getAdapter().getItem(i);
                    alternativaCostoMobile.setText(getString(R.string.euro_value, decimalFormat.format(mobileBundle.getCostoIva())));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        offertaBaseRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    MobileBundle mobileBundle = (MobileBundle) offertaBaseSpinnerAlt.getSelectedItem();
                    costoMobile.setText(getString(R.string.euro_value, decimalFormat.format(mobileBundle.getCostoIva())));
//                    offertaBaseSpinnerAlt.setClickable(true);
//                    offertaAlternativaVoceAlt.setClickable(false);
//                    offertaAlternativaSoloAlt.setClickable(false);
                    soloSpace.setVisibility(View.GONE);
                    alternativaContainerAdditionalSim.setVisibility(View.GONE);
                    offertaAlternativaVoceAlt.setSelection(0);
                    offertaAlternativaSoloAlt.setSelection(0);
                }
            }
        });


        offertaAlternativaVoceRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    MobileBundle mobileBundle = (MobileBundle) offertaAlternativaVoceAlt.getSelectedItem();
                    alternativaCostoMobile.setText(getString(R.string.euro_value, decimalFormat.format(mobileBundle.getCostoIva())));
//                    offertaBaseSpinnerAlt.setClickable(false);
//                    offertaAlternativaVoceAlt.setClickable(true);
//                    offertaAlternativaSoloAlt.setClickable(false);
                    soloSpace.setVisibility(View.GONE);
                    alternativaContainerAdditionalSim.setVisibility(View.VISIBLE);
                    offertaAlternativaSoloAlt.setSelection(0);
                }
            }
        });

        offertaAlternativaSoloBaseRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    MobileBundle mobileBundle = (MobileBundle) offertaAlternativaSoloAlt.getSelectedItem();
                    alternativaCostoMobile.setText(getString(R.string.euro_value, decimalFormat.format(mobileBundle.getCostoIva())));
//                    offertaBaseSpinnerAlt.setClickable(false);
//                    offertaAlternativaVoceAlt.setClickable(false);
//                    offertaAlternativaSoloAlt.setClickable(true);
                    soloSpace.setVisibility(View.VISIBLE);
                    alternativaContainerAdditionalSim.setVisibility(View.VISIBLE);
                    offertaAlternativaVoceAlt.setSelection(0);
                }
            }
        });

        simCheckBoxList.add(simCheckboxAlt);
        simContainer.addView(simLayout);

        if (mobileDetailsOffer != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    simCheckboxAlt.setChecked(true);
                    fillSimLayout(offertaRadioGroup, mobileDetailsOffer, offertaBaseSpinnerAlt,
                            offertaAlternativaVoceAlt, offertaAlternativaSoloAlt);
                }
            }, 400);
        }
    }

    private boolean validateUncheckAction(CheckBox simCheckbox) {
        boolean isActionValid = false;
        if (simCheckBoxList.size() == 1) {
            return true;
        }
        for (int i = 0; i < simCheckBoxList.size(); i++) {
            if (simCheckBoxList.get(i).hashCode() == simCheckbox.hashCode()) {
                isActionValid = (i == simCheckBoxList.size() - 1) || (i + 1 < simCheckBoxList.size() && !simCheckBoxList.get(i + 1).isChecked());
            }
        }
        return isActionValid;
    }

    private void updateSimNames() {
        for (int i = 0; i < simContainer.getChildCount(); i++) {
            RelativeLayout currentSim = (RelativeLayout) simContainer.getChildAt(i);
            CheckBox simName = (CheckBox) currentSim.findViewById(R.id.simOneCheckbox);
            simName.setText(getString(R.string.sim_number, i + 3));
        }
    }

    private boolean validateAllSimBeforeCurrent(CheckBox currentCheckBox) {
        boolean result = true;
        for (int i = 0; i < simCheckBoxList.size(); i++) {
            CheckBox checkBox = simCheckBoxList.get(i);
            if (checkBox.hashCode() == currentCheckBox.hashCode()) {
                break;
            }

            if (!checkBox.isChecked()) {
                result = false;
                break;
            }
        }
        return result;
    }

    private void applyFirstSimSpinnersVisibility(boolean isFirstSpinner, boolean isSecondSpinner, boolean isThirdSpinner) {
//        offertaBaseSim1.setClickable(isFirstSpinner);
//        offertaAlternativaVoceSim1.setClickable(isSecondSpinner);
//        offertaAlternativaSoloSim1.setClickable(isThirdSpinner);
    }

    private void applySecondSimSpinnersVisibility(boolean isFirstSpinner, boolean isSecondSpinner, boolean isThirdSpinner) {
//        offertaBaseSim2.setClickable(isFirstSpinner);
//        offertaAlternativaVoceSim2.setClickable(isSecondSpinner);
//        offertaAlternativaSoloSim2.setClickable(isThirdSpinner);
    }

    private void processOffertaBase1Bundle(MobileBundle mobileBundle, boolean isAlternativaMode) {
        float scontatoBundleCost = 0;
        if (isAlternativaMode) {
            MobileBundle baseBundle = MobileBundleDAOImpl.getMobileBundle(((MobileBundle) offertaBaseSim1.getSelectedItem()).getId());
            scontatoBundleCost = mobileBundle.getCostoIva() - (firstSimSconto * baseBundle.getCostoIva()) / 100;
            setStrikedText(alternativaCostoMobileSim1, getString(R.string.euro_value, decimalFormat.format(mobileBundle.getCostoIva())));
            alternativaCostoMobileScontato.setText(getString(R.string.euro_value, decimalFormat.format(scontatoBundleCost)));
        } else {
            if (firstSimSconto != 100) {
                scontatoBundleCost = firstSimSconto != 0 ? mobileBundle.getCostoIva() * firstSimSconto / 100 : mobileBundle.getCostoIva();
            }
            setStrikedText(costoMobileSim1, getString(R.string.euro_value, decimalFormat.format(mobileBundle.getCostoIva())));
            costoMobileScontato.setText(getString(R.string.euro_value, decimalFormat.format(scontatoBundleCost)));
        }
    }

    private void processOffertaBase2Bundle(MobileBundle mobileBundle, boolean isAlternativaMode) {
        float scontatoBundleCost = 0;
        if (isAlternativaMode) {
            MobileBundle baseBundle = MobileBundleDAOImpl.getMobileBundle(((MobileBundle) offertaBaseSim1.getSelectedItem()).getId());
            scontatoBundleCost = mobileBundle.getCostoIva() - (secondSimSconto * baseBundle.getCostoIva()) / 100;
            setStrikedText(alternativaCostoMobileSim2, getString(R.string.euro_value, decimalFormat.format(mobileBundle.getCostoIva())));
            alternativaCostoMobileScontato2.setText(getString(R.string.euro_value, decimalFormat.format(scontatoBundleCost)));
        } else {
            if (secondSimSconto != 100) {
                scontatoBundleCost = secondSimSconto != 0 ? mobileBundle.getCostoIva() * secondSimSconto / 100 : mobileBundle.getCostoIva();
            }
            setStrikedText(costoMobileSim2, getString(R.string.euro_value, decimalFormat.format(mobileBundle.getCostoIva())));
            costoMobileScontatoSim2.setText(getString(R.string.euro_value, decimalFormat.format(scontatoBundleCost)));
        }
    }

    private void initializeViews() {
        continuaEnabledButton = (ImageButton) findViewById(R.id.continua);

        offertaBaseSim1 = (Spinner) findViewById(R.id.offertaBaseSim1);
        offertaBaseSim2 = (Spinner) findViewById(R.id.offertaBaseSim2);
        offertaAlternativaVoceSim1 = (Spinner) findViewById(R.id.offertaAlternativaVoceSim1);
        offertaAlternativaVoceSim2 = (Spinner) findViewById(R.id.offertaAlternativaVoceSim2);
        offertaAlternativaSoloSim1 = (Spinner) findViewById(R.id.offertaAlternativaSoloSim1);
        offertaAlternativaSoloSim2 = (Spinner) findViewById(R.id.offertaAlternativaSoloSim2);

        soloSpaceSim1 = (Space) findViewById(R.id.soloSpaceSim1);
        soloSpaceSim2 = (Space) findViewById(R.id.soloSpaceSim2);

        simCheckBox1 = (CheckBox) findViewById(R.id.simOneCheckbox);
        simCheckBox2 = (CheckBox) findViewById(R.id.simTwoCheckbox);
        offertaRadioGroupSim1 = (RadioGroup) findViewById(R.id.offertaRadioGroupSim1);
        offertaRadioGroupSim2 = (RadioGroup) findViewById(R.id.offertaRadioGroupSim2);
        offertaBaseRadioButtonSim1 = (RadioButton) findViewById(R.id.offertaBaseRadioButtonSim1);
        offertaBaseRadioButtonSim2 = (RadioButton) findViewById(R.id.offertaBaseRadioButtonSim2);
        offertaAlternativaVoceRadioButtonSim1 = (RadioButton) findViewById(R.id.offertaAlternativaVoceRadioButtonSim1);
        offertaAlternativaVoceRadioButtonSim2 = (RadioButton) findViewById(R.id.offertaAlternativaVoceRadioButtonSim2);
        offertaAlternativaSoloRadioButtonSim1 = (RadioButton) findViewById(R.id.offertaAlternativaSoloRadioButtonSim1);
        offertaAlternativaSoloRadioButtonSim2 = (RadioButton) findViewById(R.id.offertaAlternativaSoloRadioButtonSim2);

        costoMobileSim1 = (TextView) findViewById(R.id.costoMobileSim1);
        costoMobileSim2 = (TextView) findViewById(R.id.costoMobileSim2);

        scontoMobileSim1 = (TextView) findViewById(R.id.sconto);
        scontoMobileSim2 = (TextView) findViewById(R.id.scontoSim2);

        costoMobileScontato = (TextView) findViewById(R.id.costoMobileScontato);
        costoMobileScontatoSim2 = (TextView) findViewById(R.id.costoMobileScontatoSim2);

        alternativaSim1PriceContainer = (LinearLayout) findViewById(R.id.alternativaSim1PriceContainer);
        alternativaSim2PriceContainer = (LinearLayout) findViewById(R.id.alternativaSim2PriceContainer);
        sim2Info = (RelativeLayout) findViewById(R.id.simInfo2);
        scontoHeaderForSim2 = (TextView) findViewById(R.id.scontoHeaderSim2);
        costHeaderForSim2 = (TextView) findViewById(R.id.costoMobileHeaderSim2);
        scontatocostHeaderForSim2 = (TextView) findViewById(R.id.costoMobileScontatoHeaderSim2);

        alternativaCostoMobileSim1 = (TextView) findViewById(R.id.alternativaCostoMobileSim1);
        alternativaCostoMobileScontato = (TextView) findViewById(R.id.alternativaCostoMobileScontato);
        alternativaCostoMobileSim2 = (TextView) findViewById(R.id.alternativaCostoMobileSim2);
        alternativaCostoMobileScontato2 = (TextView) findViewById(R.id.alternativaCostoMobileScontatoSim2);

        simContainer = (LinearLayout) findViewById(R.id.additionalSimLayout);
        addEnabled = (ImageButton) findViewById(R.id.addEnabled);
        addDisabled = (ImageButton) findViewById(R.id.addDisabled);
    }

    private void setStrikedText(TextView target, String text) {
        if (target != null && text != null) {
            target.setText(text, TextView.BufferType.SPANNABLE);
            Spannable spannable = (Spannable) target.getText();
            spannable.setSpan(new StrikethroughSpan(), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private class OffertaModel {
        private int offertaid;
        private boolean isValid;
        private MobileBundle mobileBundle;

        public OffertaModel(int offertaid, MobileBundle mobileBundle, boolean isValid) {
            this.offertaid = offertaid;
            this.isValid = isValid;
            this.mobileBundle = mobileBundle;
        }

        public OffertaModel(int offertaid, MobileBundle mobileBundle) {
            this.offertaid = offertaid;
            isValid = true;
            this.mobileBundle = mobileBundle;
        }

        public OffertaModel() {
        }

        public boolean isValid() {
            return isValid;
        }

        public void setValid(boolean valid) {
            isValid = valid;
        }

        public int getOffertaId() {
            return offertaid;
        }

        public void setOffertaid(int offertaid) {
            this.offertaid = offertaid;
        }

        public MobileBundle getMobileBundle() {
            return mobileBundle;
        }

        public void setMobileBundle(MobileBundle mobileBundle) {
            this.mobileBundle = mobileBundle;
        }
    }

    private OffertaModel getSim1Offerta(int checkedRadioButtonId) {
        OffertaModel offertaModel = null;
        if (simCheckBox1.isChecked()) {
            switch (checkedRadioButtonId) {
                case R.id.offertaBaseRadioButtonSim1:
                    offertaModel = new OffertaModel(0, (MobileBundle) offertaBaseSim1.getSelectedItem());
                    break;
                case R.id.offertaAlternativaVoceRadioButtonSim1:
                    offertaModel = new OffertaModel(1, (MobileBundle) offertaAlternativaVoceSim1.getSelectedItem(), offertaAlternativaVoceSim1.getSelectedItemPosition() != 0);
                    break;
                case R.id.offertaAlternativaSoloRadioButtonSim1:
                    offertaModel = new OffertaModel(2, (MobileBundle) offertaAlternativaSoloSim1.getSelectedItem(), offertaAlternativaSoloSim1.getSelectedItemPosition() != 0);
                    break;
            }
        }
        return offertaModel;
    }

    private OffertaModel getSim2Offerta(int checkedRadioButtonId) {
        OffertaModel offertaModel = null;
        if (simCheckBox2.isChecked()) {
            switch (checkedRadioButtonId) {
                case R.id.offertaBaseRadioButtonSim2:
                    offertaModel = new OffertaModel(0, (MobileBundle) offertaBaseSim2.getSelectedItem());
                    break;
                case R.id.offertaAlternativaVoceRadioButtonSim2:
                    offertaModel = new OffertaModel(1, (MobileBundle) offertaAlternativaVoceSim2.getSelectedItem(), offertaAlternativaVoceSim2.getSelectedItemPosition() != 0);
                    break;
                case R.id.offertaAlternativaSoloRadioButtonSim2:
                    offertaModel = new OffertaModel(2, (MobileBundle) offertaAlternativaSoloSim2.getSelectedItem(), offertaAlternativaSoloSim2.getSelectedItemPosition() != 0);
                    break;
            }
        }
        return offertaModel;
    }

    private OffertaModel getSimAdditionalOfferta(int simLayoutIndex) {
        OffertaModel offertaModel = null;
        RelativeLayout simLayout = (RelativeLayout) simContainer.getChildAt(simLayoutIndex);
        CheckBox simCheckBox = (CheckBox) simLayout.findViewById(R.id.simOneCheckbox);
        if (simCheckBox.isChecked()) {
            RadioGroup offertaRadioGroup = (RadioGroup) simLayout.findViewById(R.id.offertaRadioGroupSim1);
            switch (offertaRadioGroup.getCheckedRadioButtonId()) {
                case R.id.offertaBaseRadioButtonSim:
                    Spinner offertaBaseSpinner = (Spinner) simLayout.findViewById(R.id.offertaBaseSim);
                    offertaModel = new OffertaModel(0, (MobileBundle) offertaBaseSpinner.getSelectedItem());
                    break;
                case R.id.offertaAlternativaVoceRadioButtonSim:
                    Spinner offertaAlternativaVoceSpinner = (Spinner) simLayout.findViewById(R.id.offertaAlternativaVoceSim);
                    offertaModel = new OffertaModel(1, (MobileBundle) offertaAlternativaVoceSpinner.getSelectedItem(), offertaAlternativaVoceSpinner.getSelectedItemPosition() != 0);
                    break;
                case R.id.offertaAlternativaSoloRadioButtonSim:
                    Spinner offertaAlternativaSoloSpinner = (Spinner) simLayout.findViewById(R.id.offertaAlternativaSoloSim);
                    offertaModel = new OffertaModel(2, (MobileBundle) offertaAlternativaSoloSpinner.getSelectedItem(), offertaAlternativaSoloSpinner.getSelectedItemPosition() != 0);
                    break;
            }
        }
        return offertaModel;
    }
}