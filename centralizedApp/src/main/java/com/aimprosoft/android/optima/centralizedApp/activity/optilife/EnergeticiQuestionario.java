package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.YearOfReferenceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.AppliancesDimension;
import com.aimprosoft.android.optima.centralizedApp.db.entity.BaseEntity;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyClass;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyMeter;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.HoursOfReference;
import com.aimprosoft.android.optima.centralizedApp.db.entity.LightingTypes;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.SplitNumber;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WeeklyUtilization;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WeeklyWashing;
import com.aimprosoft.android.optima.centralizedApp.db.entity.YearOfReference;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.AnnoDiAcquistoSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.EnergyClassSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.LightningTypeSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.OreDiUtilizzoSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.PotenzaContatoreSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.RefrigeratorDimensionSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.SplitNumberSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.WeeklyUtilizationSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.WeeklyWashingSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.net.EnergyAdditionalWebServices;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnergeticiQuestionario extends BaseActivity implements Runnable {
    private int pageNumber = 1;
    private boolean isCEAutoSelectModOn = true;
    private Offer offer = MyApplication.get(Constants.OFFER_ENTITY, Offer.class);
    private EnergyOffer energyOffer;
    private EnergyOfferDetails energyOfferDetails = new EnergyOfferDetails();
    private EnergyOfferDAOImpl energyOfferDAO = new EnergyOfferDAOImpl();
    private EnergyOfferDetailsDAOImpl energyOfferDetailsDAO = new EnergyOfferDetailsDAOImpl();
    private YearOfReferenceDAOImpl yearOfReferenceDAO = new YearOfReferenceDAOImpl();

    private Spinner potensaContatore;
    private Spinner annoDiAcquistoWater;
    private Spinner nSplit;
    private Spinner utilizzoNeiMesiEstivi;
    private Spinner utilizzoNeiMesiIntervalli;
    private Spinner annoDiAcquistoClimatizzatore;
    private Spinner utilizzoSettimanale;
    private Spinner utilizzoSettimanaleCE;
    private Spinner lavaggiSettimanaliLavatrice;
    private Spinner lavaggiSettimanaliLavatriceCE;
    private Spinner lavastoviglieSettimanaliLavatrice;
    private Spinner lavastoviglieSettimanaliLavatriceCE;
    private Spinner frigoriferoDimensione;
    private Spinner frigoriferoDimensioneCE;
    private Spinner annoForno;
    private Spinner annoLavatrice;
    private Spinner annoLavastoviglie;
    private Spinner annoFrigorifero;
    private Spinner annoAsciugatrice;
    private Spinner asciugatriceUtilizzoSettimanale;
    private Spinner numeroOreUtilizzoScaldabagno;
    private Spinner tipologiaDiIlluminazione;
    private Spinner asciugatriceUtilizzoSettimanaleCE;

    private CheckBox climazzatoreCheckBox;
    private CheckBox scaldabagnoCheckBox;
    private EditText potenzaAp;
    private EditText pod;
    private EditText utilizzoAp;

    private Animation shake;

    private boolean scaldabagnoFlag = false;
    private boolean climazzatoreFlag = false;
    private boolean fornoFlag = false;
    private boolean lavatriceFlag = false;
    private boolean lavastoviglieFlag = false;
    private boolean frigoriferoFlag = false;
    private boolean apprecchiaturaFlag = false;
    private boolean asciugatriceFlag = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.energetici_questionario_layout);

        pod = (EditText) findViewById(R.id.pod);
        annoDiAcquistoWater = (Spinner) findViewById(R.id.annoDiAcquistoElettricoSpinner);
        nSplit = (Spinner) findViewById(R.id.splitNumberSpinner);
        utilizzoNeiMesiEstivi = (Spinner) findViewById(R.id.utilizzoNeiMesiEstiviSpinner);
        utilizzoNeiMesiIntervalli = (Spinner) findViewById(R.id.utilizzoNeiMesiIntervali);
        annoDiAcquistoClimatizzatore = (Spinner) findViewById(R.id.annoDiAcquistoClimatizzatoreSpinner);
        utilizzoSettimanale = (Spinner) findViewById(R.id.utilizzoSettimanalSpinner);
        utilizzoSettimanaleCE = (Spinner) findViewById(R.id.CE1);
        asciugatriceUtilizzoSettimanale = (Spinner) findViewById(R.id.asciugatriceUtilizzoSettimanale);
        asciugatriceUtilizzoSettimanaleCE = (Spinner) findViewById(R.id.CE5);
        lavaggiSettimanaliLavatrice = (Spinner) findViewById(R.id.nLavaggiSettimanaliSpinner1);
        lavaggiSettimanaliLavatriceCE = (Spinner) findViewById(R.id.CE2);
        lavastoviglieSettimanaliLavatrice = (Spinner) findViewById(R.id.nLavaggiSettimanaliSpinner2);
        lavastoviglieSettimanaliLavatriceCE = (Spinner) findViewById(R.id.CE3);
        frigoriferoDimensione = (Spinner) findViewById(R.id.refrigeratorDimensioneSpinner);
        frigoriferoDimensioneCE = (Spinner) findViewById(R.id.CE4);
        potensaContatore = (Spinner) findViewById(R.id.potensaContatoreSpinner3);
        annoForno = (Spinner) findViewById(R.id.annoAquisto1);
        annoLavatrice = (Spinner) findViewById(R.id.annoAquisto2);
        annoLavastoviglie = (Spinner) findViewById(R.id.annoAquisto3);
        annoFrigorifero = (Spinner) findViewById(R.id.annoAquisto4);
        annoAsciugatrice = (Spinner) findViewById(R.id.annoAquisto5);
        potenzaAp = (EditText) findViewById(R.id.potenzaAp);
        utilizzoAp = (EditText) findViewById(R.id.utilizzoAp);
        numeroOreUtilizzoScaldabagno = (Spinner) findViewById(R.id.numeroOreUtilizzoScaldabagno);
        tipologiaDiIlluminazione = (Spinner) findViewById(R.id.tipologiaDiIlluminazione);

        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        checkContinuaButton(R.id.continuaLinearLayout3, R.id.calcolaLinearLayout3, Constants.ENERGETICI);
        checkRicalcolaButton();
        checkForArchivioOffer();
        setValuesForView();

        ScaldabagnoOnItemSelectedListener scaldabagnoOnItemSelectedListener = new ScaldabagnoOnItemSelectedListener();
        annoDiAcquistoWater.setOnItemSelectedListener(scaldabagnoOnItemSelectedListener);

        ClemazzatoreOnItemSelectedListener climazzatoreOnClickListener = new ClemazzatoreOnItemSelectedListener();
        nSplit.setOnItemSelectedListener(climazzatoreOnClickListener);
        utilizzoNeiMesiEstivi.setOnItemSelectedListener(climazzatoreOnClickListener);
        annoDiAcquistoClimatizzatore.setOnItemSelectedListener(climazzatoreOnClickListener);
        utilizzoNeiMesiIntervalli.setOnItemSelectedListener(climazzatoreOnClickListener);

        FornoOnItemSelectedListener fornoOnItemSelectedListener = new FornoOnItemSelectedListener();
        utilizzoSettimanale.setOnItemSelectedListener(fornoOnItemSelectedListener);
        utilizzoSettimanaleCE.setOnItemSelectedListener(fornoOnItemSelectedListener);
        annoForno.setOnItemSelectedListener(fornoOnItemSelectedListener);
        LavatriceOnItemSelectedListener lavatriceOnItemClickListener = new LavatriceOnItemSelectedListener();
        lavaggiSettimanaliLavatrice.setOnItemSelectedListener(lavatriceOnItemClickListener);
        lavaggiSettimanaliLavatriceCE.setOnItemSelectedListener(lavatriceOnItemClickListener);
        annoLavatrice.setOnItemSelectedListener(lavatriceOnItemClickListener);

        LavastiviglieOnItemSelectedListener lavastiviglieOnItemClickListener = new LavastiviglieOnItemSelectedListener();
        lavastoviglieSettimanaliLavatrice.setOnItemSelectedListener(lavastiviglieOnItemClickListener);
        lavastoviglieSettimanaliLavatriceCE.setOnItemSelectedListener(lavastiviglieOnItemClickListener);
        annoLavastoviglie.setOnItemSelectedListener(lavastiviglieOnItemClickListener);

        FrigoriferoOnItemSelectedListener frigoriferoOnItemClickListener = new FrigoriferoOnItemSelectedListener();
        frigoriferoDimensione.setOnItemSelectedListener(frigoriferoOnItemClickListener);
        frigoriferoDimensioneCE.setOnItemSelectedListener(frigoriferoOnItemClickListener);
        annoFrigorifero.setOnItemSelectedListener(frigoriferoOnItemClickListener);

        ApparecchiatiraTextWatcher apparecchiatiraTextWatcher = new ApparecchiatiraTextWatcher();
        potenzaAp.addTextChangedListener(apparecchiatiraTextWatcher);
        utilizzoAp.addTextChangedListener(apparecchiatiraTextWatcher);

        AsciugatriceOnItemSelectedListener asciugatriceOnItemSelectedListener = new AsciugatriceOnItemSelectedListener();
        asciugatriceUtilizzoSettimanale.setOnItemSelectedListener(asciugatriceOnItemSelectedListener);
        asciugatriceUtilizzoSettimanaleCE.setOnItemSelectedListener(asciugatriceOnItemSelectedListener);
        annoAsciugatrice.setOnItemSelectedListener(asciugatriceOnItemSelectedListener);
    }

    private void setValuesForView() {
        new PotenzaContatoreSpinner(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                setSpinner(potensaContatore, (List) service.getResult());
            }
        }).execute(getConfiguratorType(offer.getConfiguratorType()), 1);

        new SplitNumberSpinner(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                setSpinner(nSplit, (List) service.getResult());
            }
        }).execute();

        new OreDiUtilizzoSpinner(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                setSpinner(utilizzoNeiMesiEstivi, (List) service.getResult());
                setSpinner(utilizzoNeiMesiIntervalli, (List) service.getResult());
            }
        }).execute("condizionatore_raffreddamento");

        new WeeklyUtilizationSpinner(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                setSpinner(utilizzoSettimanale, (List) service.getResult());
                setSpinner(asciugatriceUtilizzoSettimanale, (List) service.getResult());
            }
        }).execute();

        new WeeklyWashingSpinner(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                setSpinner(lavaggiSettimanaliLavatrice, (List) service.getResult());
                setSpinner(lavastoviglieSettimanaliLavatrice, (List) service.getResult());
            }
        }).execute();

        new RefrigeratorDimensionSpinner(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                setSpinner(frigoriferoDimensione, (List) service.getResult());
            }
        }).execute();

        new EnergyClassSpinner(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                setSpinner(utilizzoSettimanaleCE, (List) service.getResult());
                setSpinner(lavaggiSettimanaliLavatriceCE, (List) service.getResult());
                setSpinner(lavastoviglieSettimanaliLavatriceCE, (List) service.getResult());
                setSpinner(frigoriferoDimensioneCE, (List) service.getResult());
                setSpinner(asciugatriceUtilizzoSettimanaleCE, (List) service.getResult());
            }
        }).execute();

        new OreDiUtilizzoSpinner(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                setSpinner(numeroOreUtilizzoScaldabagno, (List) service.getResult());
            }
        }).execute("scaldabagno");

        new LightningTypeSpinner(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                setSpinner(tipologiaDiIlluminazione, (List) service.getResult());
            }
        }).execute();

        setAnnoDiAcquistoSpinner(annoDiAcquistoWater, "scaldabagno");
        setAnnoDiAcquistoSpinner(annoDiAcquistoClimatizzatore, "condizionatore_raffreddamento");
        setAnnoDiAcquistoSpinner(annoForno, "forno");
        setAnnoDiAcquistoSpinner(annoLavatrice, "lavatrice");
        setAnnoDiAcquistoSpinner(annoLavastoviglie, "lavastoviglie");
        setAnnoDiAcquistoSpinner(annoFrigorifero, "frigorifero");
        setAnnoDiAcquistoSpinner(annoAsciugatrice, "asciugatrice");
        new Handler().postDelayed(EnergeticiQuestionario.this, 800);

        scaldabagnoCheckBox = (CheckBox) findViewById(R.id.scaldabagnoCheckbox);
        scaldabagnoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                annoDiAcquistoWater.setClickable(isChecked);
                numeroOreUtilizzoScaldabagno.setClickable(isChecked);
                scaldabagnoFlag = isChecked;
                if (!isChecked) {
                    annoDiAcquistoWater.setSelection(0);
                    numeroOreUtilizzoScaldabagno.setSelection(0);
                }
            }
        });

        climazzatoreCheckBox = (CheckBox) findViewById(R.id.climatizzatoreCheckBox);
        climazzatoreCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nSplit.setClickable(isChecked);
                utilizzoNeiMesiEstivi.setClickable(isChecked);
                utilizzoNeiMesiIntervalli.setClickable(isChecked);
                annoDiAcquistoClimatizzatore.setClickable(isChecked);
                climazzatoreFlag = isChecked;
                if (!isChecked) {
                    utilizzoNeiMesiIntervalli.setSelection(0);
                    utilizzoNeiMesiEstivi.setSelection(0);
                    nSplit.setSelection(0);
                    annoDiAcquistoClimatizzatore.setSelection(0);
                }
            }
        });

        ImageButton continua = (ImageButton) findViewById(R.id.continua3);
        continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOfferAndStartActivity(null);
            }
        });

        findViewById(R.id.calcola4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOfferAndStartActivity(Canone.class);
            }
        });

        findViewById(R.id.ricalcolaElettriciti4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOfferAndStartActivity(Modifica.class);
            }
        });
    }


    private void setAnnoDiAcquistoSpinner(final Spinner spinner, String codeForFiltering) {
        new AnnoDiAcquistoSpinner(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                setSpinner(spinner, (List) service.getResult());
            }
        }).execute(codeForFiltering);
    }

    private void checkForArchivioOffer() {
        if (MyApplication.get("ArchivioOfferEntity", EnergyOffer.class) == null) {
            energyOffer = new EnergyOffer();
        } else {
            energyOffer = MyApplication.get("ArchivioOfferEntity", EnergyOffer.class);
        }
    }

    private void setEnergyOfferDetailsIfItNeeded() {
        if (energyOfferDetails.getEnergyDetailOfferId() == 0) {
            energyOfferDetails.setEnergyOfferId(energyOffer.getEnergyOfferId());
            energyOfferDetails.setYearlyKwh(null);
            energyOfferDetails.setYearlyKwh2(null);
            energyOfferDetails.setYearlyKwh3(null);
            energyOfferDetailsDAO.insert(energyOfferDetails);
        }
    }

    private void setEnergyOfferToBaseIfItNeeded() {
        if (energyOffer.getEnergyOfferId() == 0) {
            energyOffer.setYearlyKwh(0);
            energyOffer.setYearlyConsumption(0);
            energyOffer.setOfferCost(new BigDecimal(0.00));
            energyOffer.setOfferCostIVA(new BigDecimal(0.00));
            energyOfferDAO.insert(energyOffer);
        }
    }

    private void saveOfferAndStartActivity(final Class clas) {
        if (validation()) {
//            checkForArchivioOffer();
            setEnergyOfferToBaseIfItNeeded();
            setEnergyOfferDetailsIfItNeeded();
            getUserInfoFromViews();

            EnergyAdditionalWebServices energyAdditionalWebServices = new EnergyAdditionalWebServices(EnergeticiQuestionario.this, new AbstractService.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(AbstractService service) {
                    Map<String, Long> resultMap = (Map<String, Long>) service.getResult();
                    int totalConsumption = resultMap.get(EnergyAdditionalWebServices.SUM_ATTIVA) != null ? getEnergyRemoteAlgoritmResult(resultMap, offer) : 0;
                    energyOfferDetails.setCountMese(resultMap.get(EnergyAdditionalWebServices.COUNT_MESE) != null ? resultMap.get(EnergyAdditionalWebServices.COUNT_MESE).intValue() : 0);
                    energyOfferDetails.setCountPod(resultMap.get(EnergyAdditionalWebServices.COUNT_POD) != null ? resultMap.get(EnergyAdditionalWebServices.COUNT_POD).intValue() : 0);
                    energyOfferDetails.setSumAttiva(resultMap.get(EnergyAdditionalWebServices.SUM_ATTIVA) != null ? resultMap.get(EnergyAdditionalWebServices.SUM_ATTIVA).intValue() : 0.00);
                    energyOfferDetails.setQuestionarioYearlyKwh(totalConsumption);
                    energyOffer.setYearlyKwh(0);
                    cleanNonQuestionarioField();
                    energyOffer.setQuestionarioUsing(true);
                    energyOfferDetails.setQuestionarioUsing(true);
                    energyOfferDetails.setTipologiaContratto(Constants.DOMESTICO);
//                  energyOfferDetails.setYearlyConsumption(yearlyConsumption);
                    energyOfferDetails.setEnergyMeter2(getEnergyMeter());
                    energyOfferDetails.setEnergyMeter(getEnergyMeter());
                    energyOfferDetails.setYearlyKwh(0);
//                  energyOfferDetails.setOfferCost(offerCost);
//                  energyOfferDetails.setOfferCostIVA(offerCostIVA);
                    MyApplication.set("energyOfferEntity", energyOffer);
                    energyOfferDetailsDAO.update(energyOfferDetails);
                    setOfferEntityToBase();
                    energyOfferDetails = new EnergyOfferDetails();
                    energyOfferDAO.update(energyOffer);
                    startNextPage(clas);
//                  energyOffer.setGasHeatingType(offer.getGasHeatingType());
//                    questionarioComputation(totalConsumption);
//                    getSforamentoService(clas, resultMap);
//                    if (clas != null && (clas.getSimpleName().equals("Canone") | clas.getSimpleName().equals("Modifica"))) {
//                        new SendAllOfferParamsToWebService(EnergeticiQuestionario.this, null).execute(offer);
//                    }
//                    TestQuestionarioDialog testQuestionarioDialog = new TestQuestionarioDialog(EnergeticiQuestionario.this, new EnergyAlgoritm(offer, energyOffer), offer, resultMap.toString(), clas);
//                    testQuestionarioDialog.show();
                }
            });
            Map<String, BaseEntity> objectMap = new HashMap<>();
            objectMap.put(EnergyAdditionalWebServices.OFFER, offer);
            objectMap.put(EnergyAdditionalWebServices.ENERGY_OFFER_DETAILS, energyOfferDetails);
//            energyAdditionalWebServices.execute(objectMap);
        }
    }

    private void getSforamentoService(final Class clas, final Map<String, Long> resultMap) {
//        new EnergySforamentoService(this, new AbstractService.OnTaskCompleted() {
//            @Override
//            public void onTaskCompleted(AbstractService service) {
//                ResultSforamentoDTO resultSforamentoDTO = (ResultSforamentoDTO) service.getResult();
//                energyOffer.setCodicePmcPS(resultSforamentoDTO.getCodice());
//                energyOffer.setPmc(String.valueOf(resultSforamentoDTO.getPrezzoMancatoConsumo()));
//                energyOffer.setPs(String.valueOf(resultSforamentoDTO.getPrezzoSforamento()));
//                setEnergyOfferEntityToBase();
//                Deamon.set("energyOfferEntity", energyOffer);
//                if (clas != null && (clas.getSimpleName().equals("Canone") | clas.getSimpleName().equals("Modifica"))) {
////                    new SendAllOfferParamsToWebService(EnergeticiQuestionario.this, null).execute(offer);
//                }
//                    TestQuestionarioDialog testQuestionarioDialog = new TestQuestionarioDialog(EnergeticiQuestionario.this, new EnergyAlgoritm(offer, energyOffer), offer, resultMap.toString(), clas);
//                    testQuestionarioDialog.show();
//                startNextPage(clas);
//            }
//        }).execute(energyOffer);
    }

    public void startNextPage(Class clas) {   //    change to private later
        if (clas != null) {
            startChildActivity(clas);
        } else {
            isLastPage(pageNumber);
        }
    }

    private void getUserInfoFromViews() {
        scaldabagnoPart();
        condizionatorePart();
        fornoPart();
        lavatricePart();
        lavastovigliePart();
        frigoriferoPart();
        apparecchiaturaPart();
        asciugatricePart();
        lightningTypePart();
        energyOfferDetails.setPod(editTextGetTextString(pod.getId()));
    }

    private void setEntityValues() {
        energyOfferDetails = energyOfferDetailsDAO.getEnergyOfferDetailsByEnergyOfferId(energyOffer.getEnergyOfferId()).get(0);
        isCEAutoSelectModOn = false;
        pod.setText(energyOfferDetails.getPod());
        if (energyOfferDetails.getWaterHeaterPurchaseYear() != 0)
            scaldabagnoCheckBox.setChecked(true);
        if (energyOfferDetails.getAirConditionerSplitId() != 0 || energyOfferDetails.getAirConditionerHoursId() != 0 ||
                energyOfferDetails.getAirConditionerPurchaseYear() != 0 || energyOfferDetails.getAirConditionerWinterHoursId() != 0)
            climazzatoreCheckBox.setChecked(true);

        for (int iter = 1; iter < annoDiAcquistoWater.getCount(); iter++) {
            YearOfReference energyOfferFromSpinner = (YearOfReference) annoDiAcquistoWater.getItemAtPosition(iter);
            if ((energyOfferDetails.getWaterHeaterPurchaseYear()) == energyOfferFromSpinner.getYearOfReferenceId()) {
                annoDiAcquistoWater.setSelection(iter);
                break;
            }
        }

        for (int iter = 1; iter < numeroOreUtilizzoScaldabagno.getCount(); iter++) {
            HoursOfReference hoursOfReference = (HoursOfReference) numeroOreUtilizzoScaldabagno.getItemAtPosition(iter);
            if (energyOfferDetails.getWaterHeaterHours() == hoursOfReference.getHoursId()) {
                numeroOreUtilizzoScaldabagno.setSelection(iter);
                break;
            }
        }

        tipologiaDiIlluminazione.setSelection(energyOfferDetails.getLightingType());

        for (int iter2 = 1; iter2 < nSplit.getCount(); iter2++) {
            SplitNumber splitNumberSpinner = (SplitNumber) nSplit.getItemAtPosition(iter2);
            if ((energyOfferDetails.getAirConditionerSplitId()) == splitNumberSpinner.getSplitNumberId()) {
                nSplit.setSelection(iter2);
                break;
            }
        }

        for (int iter3 = 1; iter3 < utilizzoNeiMesiEstivi.getCount(); iter3++) {
            HoursOfReference hoursOfReferenceSpinner = (HoursOfReference) utilizzoNeiMesiEstivi.getItemAtPosition(iter3);
            if ((energyOfferDetails.getAirConditionerHoursId()) == hoursOfReferenceSpinner.getHoursId()) {
                utilizzoNeiMesiEstivi.setSelection(iter3);
                break;
            }
        }

        for (int iter4 = 1; iter4 < annoDiAcquistoClimatizzatore.getCount(); iter4++) {
            YearOfReference yearOfReferenceSpinner = (YearOfReference) annoDiAcquistoClimatizzatore.getItemAtPosition(iter4);
            if ((energyOfferDetails.getAirConditionerPurchaseYear()) == yearOfReferenceSpinner.getYearOfReferenceId()) {
                annoDiAcquistoClimatizzatore.setSelection(iter4);
                break;
            }
        }

        for (int iter3 = 1; iter3 < utilizzoNeiMesiIntervalli.getCount(); iter3++) {
            HoursOfReference hoursOfReferenceSpinner = (HoursOfReference) utilizzoNeiMesiIntervalli.getItemAtPosition(iter3);
            if ((energyOfferDetails.getAirConditionerWinterHoursId()) == hoursOfReferenceSpinner.getHoursId()) {
                utilizzoNeiMesiIntervalli.setSelection(iter3);
                break;
            }
        }

        for (int iter5 = 1; iter5 < utilizzoSettimanale.getCount(); iter5++) {
            WeeklyUtilization weeklyUtilizationSpinner = (WeeklyUtilization) utilizzoSettimanale.getItemAtPosition(iter5);
            if ((energyOfferDetails.getOvenWeeklyUse()) == weeklyUtilizationSpinner.getWeeklyUtilizationId()) {
                utilizzoSettimanale.setSelection(iter5);
                break;
            }
        }

        for (int iter5 = 1; iter5 < asciugatriceUtilizzoSettimanale.getCount(); iter5++) {
            WeeklyUtilization weeklyUtilizationSpinner = (WeeklyUtilization) asciugatriceUtilizzoSettimanale.getItemAtPosition(iter5);
            if ((energyOfferDetails.getAsciugatriceOvenWeeklyUse()) == weeklyUtilizationSpinner.getWeeklyUtilizationId()) {
                asciugatriceUtilizzoSettimanale.setSelection(iter5);
                break;
            }
        }

        for (int iter6 = 1; iter6 < lavaggiSettimanaliLavatrice.getCount(); iter6++) {
            WeeklyWashing weeklyWashingSpinner = (WeeklyWashing) lavaggiSettimanaliLavatrice.getItemAtPosition(iter6);
            if ((energyOfferDetails.getWashingMachineWeeklyUse()) == weeklyWashingSpinner.getWeeklyWashingId()) {
                lavaggiSettimanaliLavatrice.setSelection(iter6);
                break;
            }
        }

        for (int iter7 = 1; iter7 < lavastoviglieSettimanaliLavatrice.getCount(); iter7++) {
            WeeklyWashing weeklyWashingSpinner = (WeeklyWashing) lavastoviglieSettimanaliLavatrice.getItemAtPosition(iter7);
            if ((energyOfferDetails.getDishwasherWeeklyUse()) == weeklyWashingSpinner.getWeeklyWashingId()) {
                lavastoviglieSettimanaliLavatrice.setSelection(iter7);
                break;
            }
        }

        for (int iter8 = 1; iter8 < frigoriferoDimensione.getCount(); iter8++) {
            AppliancesDimension appliancesDimensionSpinner = (AppliancesDimension) frigoriferoDimensione.getItemAtPosition(iter8);
            if ((energyOfferDetails.getRefrigeratorWeeklyUse()) == appliancesDimensionSpinner.getRefrigeratorDimensionId()) {
                frigoriferoDimensione.setSelection(iter8);
                break;
            }
        }
        EnergyClass energyClassSpinner;
        for (int iter9 = 1; iter9 < utilizzoSettimanaleCE.getCount(); iter9++) {
            energyClassSpinner = (EnergyClass) utilizzoSettimanaleCE.getItemAtPosition(iter9);
            if ((energyOfferDetails.getOvenEnergyClass()) == energyClassSpinner.getEnergyClassId()) {
                utilizzoSettimanaleCE.setSelection(iter9);
                break;
            }
        }

        for (int iter10 = 1; iter10 < lavaggiSettimanaliLavatriceCE.getCount(); iter10++) {
            energyClassSpinner = (EnergyClass) lavaggiSettimanaliLavatriceCE.getItemAtPosition(iter10);
            if ((energyOfferDetails.getWashingMachineEnergyClass()) == energyClassSpinner.getEnergyClassId()) {
                lavaggiSettimanaliLavatriceCE.setSelection(iter10);
                break;
            }
        }

        for (int iter11 = 1; iter11 < lavastoviglieSettimanaliLavatriceCE.getCount(); iter11++) {
            energyClassSpinner = (EnergyClass) lavastoviglieSettimanaliLavatriceCE.getItemAtPosition(iter11);
            if ((energyOfferDetails.getDishwasherEnergyClass()) == energyClassSpinner.getEnergyClassId()) {
                lavastoviglieSettimanaliLavatriceCE.setSelection(iter11);
                break;
            }
        }

        for (int iter12 = 1; iter12 < frigoriferoDimensioneCE.getCount(); iter12++) {
            energyClassSpinner = (EnergyClass) frigoriferoDimensioneCE.getItemAtPosition(iter12);
            if ((energyOfferDetails.getRefrigeratorEnergyClass()) == energyClassSpinner.getEnergyClassId()) {
                frigoriferoDimensioneCE.setSelection(iter12);
                break;
            }
        }

        for (int iter12 = 1; iter12 < asciugatriceUtilizzoSettimanaleCE.getCount(); iter12++) {
            energyClassSpinner = (EnergyClass) asciugatriceUtilizzoSettimanaleCE.getItemAtPosition(iter12);
            if ((energyOfferDetails.getAsciugatriceOvenEnergyClass()) == energyClassSpinner.getEnergyClassId()) {
                asciugatriceUtilizzoSettimanaleCE.setSelection(iter12);
                break;
            }
        }

        for (int iter13 = 1; iter13 < potensaContatore.getCount(); iter13++) {
            EnergyMeter energyMeterSpinner = (EnergyMeter) potensaContatore.getItemAtPosition(iter13);
            if ((energyOfferDetails.getEnergyMeter2()) == energyMeterSpinner.getEnergyMeterId()) {
                potensaContatore.setSelection(iter13);
                break;
            }
        }

        for (int iter14 = 1; iter14 < annoLavatrice.getCount(); iter14++) {
            YearOfReference yearOfReference = (YearOfReference) annoLavatrice.getItemAtPosition(iter14);
            if ((energyOfferDetails.getWashingMachinePurchaseYear()) == yearOfReference.getYearOfReferenceId()) {
                annoLavatrice.setSelection(iter14);
                break;
            }
        }

        for (int iter14 = 1; iter14 < annoForno.getCount(); iter14++) {
            YearOfReference yearOfReference = (YearOfReference) annoForno.getItemAtPosition(iter14);
            if ((energyOfferDetails.getOvenPurchaseYear()) == yearOfReference.getYearOfReferenceId()) {
                annoForno.setSelection(iter14);
                break;
            }
        }

        for (int iter15 = 1; iter15 < annoLavastoviglie.getCount(); iter15++) {
            YearOfReference yearOfReference = (YearOfReference) annoLavastoviglie.getItemAtPosition(iter15);
            if ((energyOfferDetails.getDishwasherPurchaseYear()) == yearOfReference.getYearOfReferenceId()) {
                annoLavastoviglie.setSelection(iter15);
                break;
            }
        }

        for (int iter16 = 1; iter16 < annoFrigorifero.getCount(); iter16++) {
            YearOfReference yearOfReference = (YearOfReference) annoFrigorifero.getItemAtPosition(iter16);
            if ((energyOfferDetails.getRefrigeratorPurchaseYear()) == yearOfReference.getYearOfReferenceId()) {
                annoFrigorifero.setSelection(iter16);
                break;
            }
        }

        for (int iter16 = 1; iter16 < annoAsciugatrice.getCount(); iter16++) {
            YearOfReference yearOfReference = (YearOfReference) annoAsciugatrice.getItemAtPosition(iter16);
            if ((energyOfferDetails.getAsciugatriceOvenPurchaseYear()) == yearOfReference.getYearOfReferenceId()) {
                annoAsciugatrice.setSelection(iter16);
                break;
            }
        }

        if (energyOfferDetails.getOtherDeviceWatt() != 0) {
            potenzaAp.setText(String.valueOf(energyOfferDetails.getOtherDeviceWatt()));
        } else {
            potenzaAp.setText("");
        }
        if (energyOfferDetails.getOtherDeviceWeeklyHourUse() != 0) {
            utilizzoAp.setText(String.valueOf(energyOfferDetails.getOtherDeviceWeeklyHourUse()));
        } else {
            utilizzoAp.setText("");
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isCEAutoSelectModOn = true;
            }
        }, 500);
    }

    private void questionarioComputation(int totalKwh) {
        cleanNonQuestionarioField();
//        ConsuptionClassDAOImpl consuptionClassDAO = new ConsuptionClassDAOImpl();
//        List<Object[]> montlyCostList = consuptionClassDAO.offerCostComputation(offer.getClientTypeId(), getEnergyMeter(), totalKwh);
//        if (montlyCostList.size() != 0) {
//            Object[] objects = montlyCostList.get(0);
//            BigDecimal offerCost = (BigDecimal) objects[0];
//            Integer yearlyConsumption = (Integer) objects[1];
//            BigDecimal offerCostIVA = (BigDecimal) objects[2];
//            Integer consumptionClassType = (Integer) objects[3];
//            energyOfferDetails.setConsumptionClassTypeId(consumptionClassType);
        energyOffer.setQuestionarioUsing(true);
//            energyOfferDetails.setYearlyConsumption(yearlyConsumption);
        energyOfferDetails.setEnergyMeter2(getEnergyMeter());
        energyOfferDetails.setEnergyMeter(getEnergyMeter());
        energyOfferDetails.setYearlyKwh(0);
//            energyOfferDetails.setOfferCost(offerCost);
//            energyOfferDetails.setOfferCostIVA(offerCostIVA);
        MyApplication.set("energyOfferEntity", energyOffer);
        setOfferEntityToBase();
//        }
    }

    private void setOfferEntityToBase() {
        offer.setEnergyOfferId(energyOffer.getEnergyOfferId());
//        offer.setPriceAlreadyBeIncreased(true);
//        new OfferCostUtils().buildOfferWithCosts(offer);
        OfferDAOImpl offerDAO = new OfferDAOImpl();
        offerDAO.update(offer);
        MyApplication.set(Constants.OFFER_ENTITY, offer);
    }

    @Override
    public void run() {
        if (MyApplication.get("modificaElettricitiLable", Boolean.class) != null && MyApplication.get("ArchivioOfferEntity", EnergyOffer.class) != null) {
            setEntityValues();
        }
    }

    private void scaldabagnoPart() {
        Object yearOfReferenceWaterStr = annoDiAcquistoWater.getSelectedItem();
        if ((yearOfReferenceWaterStr.toString()).equals("")) {
            energyOfferDetails.setWaterHeaterPurchaseYear(0);
        } else {
            YearOfReference yearOfReferenceWater = (YearOfReference) annoDiAcquistoWater.getSelectedItem();
            energyOfferDetails.setWaterHeaterPurchaseYear(yearOfReferenceWater.getYearOfReferenceId());
        }

        HoursOfReference hoursOfReference = numeroOreUtilizzoScaldabagno.getSelectedItemPosition() != 0 ?
                (HoursOfReference) numeroOreUtilizzoScaldabagno.getSelectedItem() : new HoursOfReference();
        energyOfferDetails.setWaterHeaterHours(hoursOfReference.getHoursId());
    }

    private void condizionatorePart() {
        Object splitNumberStr = nSplit.getSelectedItem();
        if ((splitNumberStr.toString()).equals("")) {
            energyOfferDetails.setAirConditionerSplitId(0);
        } else {
            SplitNumber splitNumber = (SplitNumber) nSplit.getSelectedItem();
            energyOfferDetails.setAirConditionerSplitId(splitNumber.getSplitNumberId());
        }

        Object hoursOfReferenceStr = utilizzoNeiMesiEstivi.getSelectedItem();
        if ((hoursOfReferenceStr.toString()).equals("")) {
            energyOfferDetails.setAirConditionerHoursId(0);
        } else {
            HoursOfReference hoursOfReference = (HoursOfReference) utilizzoNeiMesiEstivi.getSelectedItem();
            energyOfferDetails.setAirConditionerHoursId(hoursOfReference.getHoursId());
        }

        Object hoursOfReferenceMesiIntervalliStr = utilizzoNeiMesiIntervalli.getSelectedItem();
        if ((hoursOfReferenceMesiIntervalliStr.toString()).equals("")) {
            energyOfferDetails.setAirConditionerWinterHoursId(0);
        } else {
            HoursOfReference hoursOfReference = (HoursOfReference) utilizzoNeiMesiIntervalli.getSelectedItem();
            energyOfferDetails.setAirConditionerWinterHoursId(hoursOfReference.getHoursId());
        }

        Object yearOfReferenceConditionerStr = annoDiAcquistoClimatizzatore.getSelectedItem();
        if ((yearOfReferenceConditionerStr.toString()).equals("")) {
            energyOfferDetails.setAirConditionerPurchaseYear(0);
        } else {
            YearOfReference yearOfReferenceConditioner = (YearOfReference) annoDiAcquistoClimatizzatore.getSelectedItem();
            energyOfferDetails.setAirConditionerPurchaseYear(yearOfReferenceConditioner.getYearOfReferenceId());
        }
    }

    private void fornoPart() {
        Object weeklyUtilizationStr = utilizzoSettimanale.getSelectedItem();
        if ((weeklyUtilizationStr.toString()).equals("")) {
            energyOfferDetails.setOvenWeeklyUse(0);
        } else {
            WeeklyUtilization weeklyUtilization = (WeeklyUtilization) utilizzoSettimanale.getSelectedItem();
            energyOfferDetails.setOvenWeeklyUse(weeklyUtilization.getWeeklyUtilizationId());
        }

        Object energyClassUtilizzoStr = utilizzoSettimanaleCE.getSelectedItem();
        if ((energyClassUtilizzoStr.toString()).equals("")) {
            energyOfferDetails.setOvenEnergyClass(0);
        } else {
            EnergyClass energyClassUtilizzo = (EnergyClass) utilizzoSettimanaleCE.getSelectedItem();
            energyOfferDetails.setOvenEnergyClass(energyClassUtilizzo.getEnergyClassId());
        }

        Object annoFornoStr = annoForno.getSelectedItem();
        if ((annoFornoStr.toString()).equals("")) {
            energyOfferDetails.setOvenPurchaseYear(0);
        } else {
            YearOfReference yearOfReferenceForno = (YearOfReference) annoForno.getSelectedItem();
            energyOfferDetails.setOvenPurchaseYear(yearOfReferenceForno.getYearOfReferenceId());
        }
    }

    private void lavatricePart() {
        Object lavaggiWeeklyWashingStr = lavaggiSettimanaliLavatrice.getSelectedItem();
        if ((lavaggiWeeklyWashingStr.toString()).equals("")) {
            energyOfferDetails.setWashingMachineWeeklyUse(0);
        } else {
            WeeklyWashing lavaggiWeeklyWashing = (WeeklyWashing) lavaggiSettimanaliLavatrice.getSelectedItem();
            energyOfferDetails.setWashingMachineWeeklyUse(lavaggiWeeklyWashing.getWeeklyWashingId());
        }

        Object energyClassLavaggiStr = lavaggiSettimanaliLavatriceCE.getSelectedItem();
        if ((energyClassLavaggiStr.toString()).equals("")) {
            energyOfferDetails.setWashingMachineEnergyClass(0);
        } else {
            EnergyClass energyClassLavaggi = (EnergyClass) lavaggiSettimanaliLavatriceCE.getSelectedItem();
            energyOfferDetails.setWashingMachineEnergyClass(energyClassLavaggi.getEnergyClassId());
        }
        Object annoLavatriceStr = annoLavatrice.getSelectedItem();
        if ((annoLavatriceStr.toString()).equals("")) {
            energyOfferDetails.setWashingMachinePurchaseYear(0);
        } else {
            YearOfReference yearOfReferenceLavatrice = (YearOfReference) annoLavatrice.getSelectedItem();
            energyOfferDetails.setWashingMachinePurchaseYear(yearOfReferenceLavatrice.getYearOfReferenceId());
        }
    }

    private void lavastovigliePart() {
        Object lavastoviglieWeeklyWashingStr = lavastoviglieSettimanaliLavatrice.getSelectedItem();
        if ((lavastoviglieWeeklyWashingStr.toString()).equals("")) {
            energyOfferDetails.setDishwasherWeeklyUse(0);
        } else {
            WeeklyWashing lavastoviglieWeeklyWashing = (WeeklyWashing) lavastoviglieSettimanaliLavatrice.getSelectedItem();
            energyOfferDetails.setDishwasherWeeklyUse(lavastoviglieWeeklyWashing.getWeeklyWashingId());
        }

        Object energyClassLavastoviglieStr = lavastoviglieSettimanaliLavatriceCE.getSelectedItem();
        if ((energyClassLavastoviglieStr.toString()).equals("")) {
            energyOfferDetails.setDishwasherEnergyClass(0);
        } else {
            EnergyClass energyClassLavastoviglie = (EnergyClass) lavastoviglieSettimanaliLavatriceCE.getSelectedItem();
            energyOfferDetails.setDishwasherEnergyClass(energyClassLavastoviglie.getEnergyClassId());
        }

        Object annoLavastoviglieStr = annoLavastoviglie.getSelectedItem();
        if ((annoLavastoviglieStr.toString()).equals("")) {
            energyOfferDetails.setDishwasherPurchaseYear(0);
        } else {
            YearOfReference yearOfReference = (YearOfReference) annoLavastoviglie.getSelectedItem();
            energyOfferDetails.setDishwasherPurchaseYear(yearOfReference.getYearOfReferenceId());
        }
    }

    private void frigoriferoPart() {
        Object refrigeratorDimensionStr = frigoriferoDimensione.getSelectedItem();
        if ((refrigeratorDimensionStr.toString()).equals("")) {
            energyOfferDetails.setRefrigeratorWeeklyUse(0);
        } else {
            AppliancesDimension appliancesDimension = (AppliancesDimension) frigoriferoDimensione.getSelectedItem();
            energyOfferDetails.setRefrigeratorWeeklyUse(appliancesDimension.getRefrigeratorDimensionId());
        }

        Object energyClassFrigoriferoDimensioneStr = frigoriferoDimensioneCE.getSelectedItem();
        if ((energyClassFrigoriferoDimensioneStr.toString()).equals("")) {
            energyOfferDetails.setRefrigeratorEnergyClass(0);
        } else {
            EnergyClass energyClassFrigoriferoDimensione = (EnergyClass) frigoriferoDimensioneCE.getSelectedItem();
            energyOfferDetails.setRefrigeratorEnergyClass(energyClassFrigoriferoDimensione.getEnergyClassId());
        }

        Object annoFrigoriferoStr = annoFrigorifero.getSelectedItem();
        if ((annoFrigoriferoStr.toString()).equals("")) {
            energyOfferDetails.setRefrigeratorPurchaseYear(0);
        } else {
            YearOfReference yearOfReference = (YearOfReference) annoFrigorifero.getSelectedItem();
            energyOfferDetails.setRefrigeratorPurchaseYear(yearOfReference.getYearOfReferenceValue());
            energyOfferDetails.setRefrigeratorPurchaseYear(yearOfReference.getYearOfReferenceId());
        }
    }

    private void lightningTypePart() {
        LightingTypes lightingTypes = tipologiaDiIlluminazione.getSelectedItemPosition() != 0 ?
                (LightingTypes) tipologiaDiIlluminazione.getSelectedItem() : new LightingTypes();
        energyOfferDetails.setLightingType(lightingTypes.getIdType());
    }


    private void asciugatricePart() {
        Object weeklyUtilizationStr = asciugatriceUtilizzoSettimanale.getSelectedItem();
        if ((weeklyUtilizationStr.toString()).equals("")) {
            energyOfferDetails.setAsciugatriceOvenWeeklyUse(0);
        } else {
            WeeklyUtilization weeklyUtilization = (WeeklyUtilization) asciugatriceUtilizzoSettimanale.getSelectedItem();
            energyOfferDetails.setAsciugatriceOvenWeeklyUse(weeklyUtilization.getWeeklyUtilizationId());
        }

        Object energyClassUtilizzoStr = asciugatriceUtilizzoSettimanaleCE.getSelectedItem();
        if ((energyClassUtilizzoStr.toString()).equals("")) {
            energyOfferDetails.setAsciugatriceOvenEnergyClass(0);
        } else {
            EnergyClass energyClassUtilizzo = (EnergyClass) asciugatriceUtilizzoSettimanaleCE.getSelectedItem();
            energyOfferDetails.setAsciugatriceOvenEnergyClass(energyClassUtilizzo.getEnergyClassId());
        }

        Object asciugatriceStr = annoAsciugatrice.getSelectedItem();
        if ((asciugatriceStr.toString()).equals("")) {
            energyOfferDetails.setAsciugatriceOvenPurchaseYear(0);
        } else {
            YearOfReference yearOfReferenceForno = (YearOfReference) annoAsciugatrice.getSelectedItem();
            energyOfferDetails.setAsciugatriceOvenPurchaseYear(yearOfReferenceForno.getYearOfReferenceId());
        }
    }


    private void apparecchiaturaPart() {
        if (editTextGetTextString(R.id.potenzaAp) == null || editTextGetTextString(R.id.potenzaAp).equals("")) {
            energyOfferDetails.setOtherDeviceWatt(0);
        } else {
            energyOfferDetails.setOtherDeviceWatt(Integer.valueOf(editTextGetTextString(R.id.potenzaAp)));
        }

        if (editTextGetTextString(R.id.utilizzoAp) == null || editTextGetTextString(R.id.utilizzoAp).equals("")) {
            energyOfferDetails.setOtherDeviceWeeklyHourUse(0);
        } else {
            energyOfferDetails.setOtherDeviceWeeklyHourUse(Integer.valueOf(editTextGetTextString(R.id.utilizzoAp)));
        }
    }

    private int getEnergyMeter() {
        EnergyMeter energyMeter = (EnergyMeter) potensaContatore.getSelectedItem();
        return energyMeter.getEnergyMeterId();
    }

    private String getEnergyMeterDesc() {
        EnergyMeter energyMeter = (EnergyMeter) potensaContatore.getSelectedItem();
        return energyMeter.getEnergyMeterValue();
    }

    private void checkRicalcolaButton() {
        if (MyApplication.get("modificaElettricitiLable", Boolean.class) != null) {
            findViewById(R.id.continuaLinearLayout3).setVisibility(View.GONE);
            findViewById(R.id.calcolaLinearLayout3).setVisibility(View.GONE);
            findViewById(R.id.ricalcolaLinearLayout3).setVisibility(View.VISIBLE);
        }
    }

    private boolean validation() {
        return checkSpinnerSelection(true, R.id.potensaContatoreSpinner3) && isMandatoriePartChecked() &&
                ((!scaldabagnoFlag && !scaldabagnoValidation(false)) || (scaldabagnoFlag && scaldabagnoValidation(true))) &&
                ((!climazzatoreFlag && !climazzatoreValidation(false)) || (climazzatoreFlag && climazzatoreValidation(true))) &&
                frequenzaPartValidation(false, true);
    }

    private boolean frequenzaPartValidation(boolean flagValidationAnim, boolean contentValidationAnim) {
        return ((!fornoFlag && !fornoValidation(flagValidationAnim)) || (fornoFlag && fornoValidation(contentValidationAnim))) &&
                ((!lavatriceFlag && !lavatriceValidation(flagValidationAnim)) || (lavatriceFlag && lavatriceValidation(contentValidationAnim))) &&
                ((!lavastoviglieFlag && !lavastoviglieValidation(flagValidationAnim)) || (lavastoviglieFlag && lavastoviglieValidation(contentValidationAnim))) &&
                ((!frigoriferoFlag && !frigoriferoValidation(flagValidationAnim)) || (frigoriferoFlag && frigoriferoValidation(contentValidationAnim))) &&
                ((!asciugatriceFlag && !asciugatriceValidation(flagValidationAnim)) || (asciugatriceFlag && asciugatriceValidation(contentValidationAnim))) &&
                ((!apprecchiaturaFlag && !apparecchiaturaValidation(flagValidationAnim)) || (apprecchiaturaFlag && apparecchiaturaValidation(contentValidationAnim)));
    }

    private boolean isMandatoriePartChecked() {
        boolean flag = true;
        if (!(isCheckBoxChecked(false, R.id.scaldabagnoCheckbox) | isCheckBoxChecked(false, R.id.climatizzatoreCheckBox) | isFrequnzaPresente())) {
            scaldabagnoCheckBox.startAnimation(shake);
            climazzatoreCheckBox.startAnimation(shake);
            findViewById(R.id.frequenzaTittle).startAnimation(shake);
            flag = false;
        }
        return flag;
    }

    private boolean isFrequnzaPresente() {
        boolean flag = true;
        if (!(fornoFlag || lavatriceFlag || lavastoviglieFlag || frigoriferoFlag || apprecchiaturaFlag || asciugatriceFlag)) {
            flag = false;
        }
        return flag;
    }


    private boolean scaldabagnoValidation(boolean animatedError) {
        return checkSpinnerSelection(animatedError, R.id.annoDiAcquistoElettricoSpinner);
    }

    private boolean climazzatoreValidation(boolean animatedError) {
        return checkSpinnerSelection(animatedError, R.id.splitNumberSpinner) &&
                (checkSpinnerSelection(animatedError, R.id.utilizzoNeiMesiEstiviSpinner) ||
                        checkSpinnerSelection(animatedError, R.id.utilizzoNeiMesiIntervali));
    }

    private boolean fornoValidation(boolean animatedError) {
        return ((checkSpinnerSelection(animatedError, R.id.CE1) ||
                checkSpinnerSelection(animatedError, R.id.annoAquisto1)));
    }

    private boolean lavatriceValidation(boolean animatedError) {
        return (checkSpinnerSelection(animatedError, R.id.CE2) || checkSpinnerSelection(animatedError, R.id.annoAquisto2));
    }

    private boolean lavastoviglieValidation(boolean animatedError) {
        return (checkSpinnerSelection(animatedError, R.id.CE3) || checkSpinnerSelection(animatedError, R.id.annoAquisto3));
    }

    private boolean frigoriferoValidation(boolean animatedError) {
        return (checkSpinnerSelection(animatedError, R.id.CE4) || checkSpinnerSelection(animatedError, R.id.annoAquisto4));
    }

    private boolean asciugatriceValidation(boolean animatedError) {
        return (checkSpinnerSelection(animatedError, R.id.CE5) || checkSpinnerSelection(animatedError, R.id.annoAquisto5));
    }

    private boolean apparecchiaturaValidation(boolean animatedError) {
        return isValid(animatedError, R.id.potenzaAp, R.id.utilizzoAp);
    }

    private void setCEValueToSpinner(AdapterView parent, Spinner spinner, int currentViewId, int targetViewId) {
        if (currentViewId == targetViewId && isCEAutoSelectModOn) {
            int yearOfReferenceId = parent.getSelectedItemPosition() > 0 ?
                    ((YearOfReference) parent.getSelectedItem()).getYearOfReferenceId() : 0;
            if (spinner.getSelectedItemPosition() == 0)
                spinner.setSelection(yearOfReferenceDAO.getEnergyClassIdByYearOfReferenceId(yearOfReferenceId));
        }
    }

    private class ScaldabagnoOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            scaldabagnoFlag = scaldabagnoFlag ? checkSpinnerSelection(false, R.id.annoDiAcquistoElettricoSpinner) : position != 0;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class ClemazzatoreOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            climazzatoreFlag = climazzatoreFlag ? checkSpinnerSelection(false, R.id.splitNumberSpinner) ||
                    checkSpinnerSelection(false, R.id.utilizzoNeiMesiEstiviSpinner) ||
                    checkSpinnerSelection(false, R.id.utilizzoNeiMesiIntervali) : position != 0;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class FornoOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            fornoFlag = fornoFlag ? checkSpinnerSelection(false, R.id.utilizzoSettimanalSpinner) ||
                    (checkSpinnerSelection(false, R.id.CE1) ||
                            checkSpinnerSelection(false, R.id.annoAquisto1)) : position != 0;
            setCEValueToSpinner(parent, utilizzoSettimanaleCE, parent.getId(), R.id.annoAquisto1);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class AsciugatriceOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            asciugatriceFlag = asciugatriceFlag ? checkSpinnerSelection(false, R.id.asciugatriceUtilizzoSettimanale) ||
                    (checkSpinnerSelection(false, R.id.CE5) ||
                            checkSpinnerSelection(false, R.id.annoAquisto5)) : position != 0;
            setCEValueToSpinner(parent, asciugatriceUtilizzoSettimanaleCE, parent.getId(), R.id.annoAquisto5);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class LavatriceOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            lavatriceFlag = lavatriceFlag ? checkSpinnerSelection(false, R.id.nLavaggiSettimanaliSpinner1) ||
                    (checkSpinnerSelection(false, R.id.CE2) ||
                            checkSpinnerSelection(false, R.id.annoAquisto2)) : position != 0;
            setCEValueToSpinner(parent, lavaggiSettimanaliLavatriceCE, parent.getId(), R.id.annoAquisto2);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class LavastiviglieOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            lavastoviglieFlag = lavastoviglieFlag ? checkSpinnerSelection(false, R.id.nLavaggiSettimanaliSpinner2) ||
                    (checkSpinnerSelection(false, R.id.CE3) ||
                            checkSpinnerSelection(false, R.id.annoAquisto3)) : position != 0;
            setCEValueToSpinner(parent, lavastoviglieSettimanaliLavatriceCE, parent.getId(), R.id.annoAquisto3);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class FrigoriferoOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            frigoriferoFlag = frigoriferoFlag ? checkSpinnerSelection(false, R.id.CE4) ||
                    checkSpinnerSelection(false, R.id.annoAquisto4) : position != 0;
            setCEValueToSpinner(parent, frigoriferoDimensioneCE, parent.getId(), R.id.annoAquisto4);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class ApparecchiatiraTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            apprecchiaturaFlag = apprecchiaturaFlag ? isValid(false, R.id.potenzaAp) ||
                    isValid(false, R.id.utilizzoAp) : s.toString().trim().length() != 0;
        }
    }

    private void cleanNonQuestionarioField() {
        energyOffer.setQuestionarioUsing(true);
        energyOfferDetails.setEnergyMeter(0);
        energyOfferDetails.setTipologiaContratto(null);
    }
}