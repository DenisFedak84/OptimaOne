package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.app.adapter.AutoCompleteTownAdapter;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CategorieUsoDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClassePrelievoGasDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientDetailGasOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientGasOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientServicesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TownDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.CategorieUso;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ClassePrelievoGas;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientDetailGasOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientGasOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientServices;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Town;
import com.aimprosoft.android.optima.centralizedApp.event.DoubleButtonConfirmationDialog;
import com.aimprosoft.android.optima.centralizedApp.event.RigmeFiscaleDialog;
import com.aimprosoft.android.optima.centralizedApp.event.listener.OnDialogSubmitListener;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.CategoriaDusoService;
import com.aimprosoft.android.optima.centralizedApp.service.ClasseDiPrelievoService;
import com.aimprosoft.android.optima.centralizedApp.service.ComuneSelect;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gas extends BaseActivity implements Runnable, TextWatcher, OnDialogSubmitListener {

    private int pageNumber = 2;
    private int existingPdrNumber = 0;
    private GasOffer gasOffer;
    private GasOfferDateInterval gasOfferDateInterval;
    private GasDetailOffers gasDetailOffers = new GasDetailOffers();
    private Offer offer;
    private Town town = null;
    private GasOfferDateIntervalDAOImpl gasOfferDateIntervalDAO = new GasOfferDateIntervalDAOImpl();
    private GasDetailOffersDAOImpl gasDetailOffersDAO = new GasDetailOffersDAOImpl();
    private GasOfferDAOImpl gasOfferDAO = new GasOfferDAOImpl();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private TableLayout tableLayout;
    private int choiceCode = 0;
    private int fiscaleId;

    private int year;
    private int month;
    private int day;
    private int year2;
    private int month2;
    private int day2;
    private EditText smcGas;
    private EditText smcGasIntervallo;
    private TextView gasIntervalDateFrom;
    private TextView gasIntervalDateTo;
    private TextView periodoDa;
    private TextView periodoA;
    private RadioGroup fiscaleRadioGroup;
    private RadioButton industriale;
    private RadioButton civile;
    private RadioButton annuoEnergetici;
    private RadioButton intervalDiTempo;
    private Spinner categoriaDusoSpinner;
    private Spinner classeDiPrelievo;
    private TableLayout dynamicTableGas;
    private Button annula;
    private Button modificaPDR;
    private LinearLayout modificaPDRLayout;
    private LinearLayout addPdrButtonsLayout;
    private boolean isPdrChecked = false;
    private LinearLayout currentPdrRow;
    private LinearLayout pdrLayout;
    private Button calcolaEnabled;
    private Button calcolaDisabled;
    private Button ricalcolaEnabled;
    private Button ricalcolaDisabled;
    private ImageButton continuaEnabled;
    private ImageButton continuaDisabled;
    private AutoCompleteTextView comuneAutoComplete;
    private LinearLayout llTipologiaContratto;
    private RadioGroup rgTipologiaContratto;
    private RadioButton domestico;
    private RadioButton altriUsi;
    private RadioButton condominio;
    private LinearLayout llRegimeFiscale;
    private LinearLayout llComune;
    private LinearLayout llClasseDiPrelievo;
    private boolean isBussinesType;
    private String system;
    private List<Integer> intervalsIdForRemoving = new ArrayList<>();
    private List<Integer> intervalsIdForAdding = new ArrayList<>();
    private DoubleButtonConfirmationDialog doubleButtonConfirmationDialog;

    private DatePickerDialog.OnDateSetListener onDateFromSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newYear, int monthOfYear, int dayOfMonth) {
            year = newYear;
            month = monthOfYear;
            day = dayOfMonth;
            updateDisplayDateFrom(R.id.gasIntervalFrom);
        }
    };
    private DatePickerDialog.OnDateSetListener onDateToSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newYear2, int monthOfYear2, int dayOfMonth2) {
            year2 = newYear2;
            month2 = monthOfYear2;
            day2 = dayOfMonth2;
            updateDisplayDateTo(R.id.gasIntervalTo);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gas_layout);

        smcGasIntervallo = (EditText) findViewById(R.id.smcGasIntervallo);
        smcGas = (EditText) findViewById(R.id.smcGas);
        gasIntervalDateFrom = (TextView) findViewById(R.id.gasIntervalFrom);
        gasIntervalDateTo = (TextView) findViewById(R.id.gasIntervalTo);
        categoriaDusoSpinner = (Spinner) findViewById(R.id.categoria_di_utilizzo);
        classeDiPrelievo = (Spinner) findViewById(R.id.classe_di_prelievo);
        intervalDiTempo = (RadioButton) findViewById(R.id.intervaloDiTempoGas);
        annuoEnergetici = (RadioButton) findViewById(R.id.annuoGas);
        tableLayout = (TableLayout) findViewById(R.id.dynamicTableGas);
        fiscaleRadioGroup = (RadioGroup) findViewById(R.id.fiscaleRadioGroup);
        civile = (RadioButton) findViewById(R.id.civile);
        industriale = (RadioButton) findViewById(R.id.industriale);
        dynamicTableGas = (TableLayout) findViewById(R.id.dynamicTableGas);
        periodoA = (TextView) findViewById(R.id.gasIntervalTo);
        periodoDa = (TextView) findViewById(R.id.gasIntervalFrom);
        annula = (Button) findViewById(R.id.annulaButton);
        modificaPDR = (Button) findViewById(R.id.modificaButton);
        modificaPDRLayout = (LinearLayout) findViewById(R.id.modificaPDRLayout);
        addPdrButtonsLayout = (LinearLayout) findViewById(R.id.addPdrButtonlayout);
        calcolaDisabled = (Button) findViewById(R.id.calcola5Disabled);
        calcolaEnabled = (Button) findViewById(R.id.calcola5);
        ricalcolaDisabled = (Button) findViewById(R.id.ricalcolaGas5Disabled);
        ricalcolaEnabled = (Button) findViewById(R.id.ricalcolaGas5);
        continuaDisabled = (ImageButton) findViewById(R.id.сontinua5disabled);
        continuaEnabled = (ImageButton) findViewById(R.id.continua5);
        pdrLayout = (LinearLayout) findViewById(R.id.PDRLayout);
        comuneAutoComplete = (AutoCompleteTextView) findViewById(R.id.comuneAutoCompleteTextView);
        ///new fields
        rgTipologiaContratto = (RadioGroup) findViewById(R.id.rgTipologiaContratto);
        domestico = (RadioButton) findViewById(R.id.domestico);
        altriUsi = (RadioButton) findViewById(R.id.altriUsi);
        condominio = (RadioButton) findViewById(R.id.condominio);
        llTipologiaContratto = (LinearLayout) findViewById(R.id.llTipologiaContratto);
        llRegimeFiscale = (LinearLayout) findViewById(R.id.llRegimeFiscale);
        llComune = (LinearLayout) findViewById(R.id.llComune);
        llClasseDiPrelievo = (LinearLayout) findViewById(R.id.llClasseDiPrelievo);
        ///end
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        year2 = c.get(Calendar.YEAR);
        month2 = c.get(Calendar.MONTH);
        day2 = c.get(Calendar.DAY_OF_MONTH);
        checkContinuaButton(R.id.continuaLinearLayout5, R.id.calcolaLinearLayout5, Constants.GAS);
        checkRicalcolaButton();
        MyApplication.set("gas", true);

        offer = MyApplication.get("offerEntity", Offer.class);
        if (MyApplication.get("ArchivioGasOffer", GasOffer.class) != null) {
            setEntityValues();
        } else {
            gasOffer = new GasOffer();
            if (offer != null) {
                checkForExistingClient();
                drawPdrTable();
                preSelectTownFromOffer(offer);
            }
        }
        if (MyApplication.get("ArchivioGasDateInterval", GasOfferDateInterval.class) != null) {
            gasOfferDateInterval = MyApplication.get("ArchivioGasDateInterval", GasOfferDateInterval.class);
        } else {
            gasOfferDateInterval = new GasOfferDateInterval();
        }

        smcGas.addTextChangedListener(this);
        smcGasIntervallo.addTextChangedListener(this);
        periodoA.addTextChangedListener(this);
        periodoDa.addTextChangedListener(this);

        ///get isBussinesType
        isBussinesType = offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR;
        ///display bussines, consumer fields
        if (isBussinesType) {
            llTipologiaContratto.setVisibility(View.GONE);
        } else {
            llRegimeFiscale.setVisibility(View.GONE);
            llComune.setVisibility(View.GONE);
            llClasseDiPrelievo.setVisibility(View.GONE);
//
        }
        ///end
        new ComuneSelect(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                ArrayAdapter<Town> adapter = new AutoCompleteTownAdapter(Gas.this,
                        android.R.layout.simple_dropdown_item_1line, (List<Town>) service.getResult());
                comuneAutoComplete.setAdapter(adapter);
            }
        }).execute();

        categoriaDusoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0 && town != null) {
//                    classePrelievoAutoFill(town.getTownId(), ((CategorieUso) parent.getSelectedItem()).getIdCategoriaUso());
                    fillClassePrelievoSpinner(town.getTownId(), ((CategorieUso) parent.getSelectedItem()).getIdCategoriaUso(), classeDiPrelievo);
                } else {
                    classeDiPrelievo.setEnabled(false);
                    if (classeDiPrelievo.getChildCount() != 0) classeDiPrelievo.setSelection(0);
                }
                continuaButtonValidation();
                addPDRValidation(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        classeDiPrelievo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addPDRValidation(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ///display different values depend on flag bussines or consumer
        system = isBussinesType ? Constants.BUSINESS_CONFIGURATOR_FLAG : Constants.CONSUMER_CONFIGURATOR_FLAG;
//        new CategoriaDusoService(this, new AbstractService.OnTaskCompleted() {
//            @Override
//            public void onTaskCompleted(AbstractService service) {
//                ArrayAdapter<CategorieUso> adapter = new ArrayAdapter<>(Gas.this, android.R.layout.simple_spinner_item, android.R.id.text1, (List<CategorieUso>) service.getResult());
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                categoriaDusoSpinner.setAdapter(adapter);
//                if (gasDetailOffers.getUserTypeId() != 0)
//                    categoriaDusoSpinner.setSelection(gasDetailOffers.getUserTypeId());
//            }
//        }).execute(system); ///flag isBussines  getConfiguratorType

        fillCategoriaDusoSpinner(system, categoriaDusoSpinner, gasDetailOffers);

        comuneAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                town = (Town) parent.getAdapter().getItem(position);
                if (categoriaDusoSpinner.getSelectedItemPosition() != 0)
//                    classePrelievoAutoFill(town.getTownId(), ((CategorieUso) categoriaDusoSpinner.getSelectedItem()).getIdCategoriaUso());
                    fillClassePrelievoSpinner(town.getTownId(), ((CategorieUso) categoriaDusoSpinner.getSelectedItem()).getIdCategoriaUso(), classeDiPrelievo);
                addPDRValidation(false);
            }
        });

        annuoEnergetici.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    choiceCode = 1;
//                    changeViewVisibility(R.id.calcola5Disabled, R.id.calcola5);
//                    changeViewVisibility(R.id.ricalcolaGas5Disabled, R.id.ricalcolaGas5);
//                    changeViewVisibility(R.id.сontinua5disabled, R.id.continua5);
//                    makeContinuaButtonsEnabled(true);
                    tableLayout.setVisibility(View.GONE);
                    findViewById(R.id.smcGas).setEnabled(true);
//                    categoriaDusoSpinner.setClickable(false);
//                    classeDiPrelievo.setClickable(false);
//                    categoriaDusoSpinner.setSelection(0);
//                    removeAllTableContent(tableLayout);
                    smcGasIntervallo.setText("");
                } else {
//                    categoriaDusoSpinner.setClickable(true);
//                    classeDiPrelievo.setClickable(true);
                    tableLayout.setVisibility(View.VISIBLE);
                    findViewById(R.id.smcGas).setEnabled(false);
                }
                continuaButtonValidation();
                addPDRValidation(false);
            }
        });

        intervalDiTempo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean flag;
                if (isChecked) {
                    choiceCode = 2;
//                    changeViewVisibility(R.id.calcola5Disabled, R.id.calcola5);
//                    changeViewVisibility(R.id.ricalcolaGas5Disabled, R.id.ricalcolaGas5);
//                    changeViewVisibility(R.id.сontinua5disabled, R.id.continua5);
//                    makeContinuaButtonsEnabled(true);
//                    categoriaDusoSpinner.setClickable(true);
//                    classeDiPrelievo.setClickable(true);
                    flag = true;
                    smcGas.setText("");
                } else {
//                    categoriaDusoSpinner.setClickable(false);
//                    classeDiPrelievo.setClickable(false);
                    flag = false;
                }
                continuaButtonValidation();
                findViewById(R.id.smcGasIntervallo).setEnabled(flag);
                findViewById(R.id.gasIntervalFrom).setEnabled(flag);
                findViewById(R.id.gasIntervalTo).setEnabled(flag);
                findViewById(R.id.plusButtonGas).setClickable(flag);
            }
        });

        gasIntervalDateFrom.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                gasIntervalDateFrom.setText("");
                return true;
            }
        });

        gasIntervalDateTo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                gasIntervalDateTo.setText("");
                return true;
            }
        });

        ImageButton continua = (ImageButton) findViewById(R.id.continua5);
        continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateOffer(null);
                isLastPage(pageNumber);
            }
        });

        findViewById(R.id.plusButtonGas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIntervalData();
            }
        });

        findViewById(R.id.ricalcolaGas5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateOffer(Canone.class);
            }
        });

        findViewById(R.id.calcola5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateOffer(Canone.class);
            }
        });

        findViewById(R.id.addPdrEnabled).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPdr(false);
            }
        });

        modificaPDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((choiceCode != 2) || !isIntervalTableEmpty(true)) & addPDRValidation(true)) {
                    removeAllDataMarkedForRemoving(intervalsIdForRemoving);
                    addPdr(true);
                    modificaPDRLayout.setVisibility(View.GONE);
                    addPdrButtonsLayout.setVisibility(View.VISIBLE);
                    isPdrChecked = false;
                    annula.setVisibility(View.VISIBLE);
                    continuaButtonValidation();
                }
            }
        });

        annula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gasIntervalsSize = gasOfferDateIntervalDAO.getOfferDateIntervalByGasDetailsId(gasDetailOffers.getGasDetailsOfferId()).size();
//                if ((choiceCode != 2) || !isIntervalTableEmpty(true)) {
                if ((choiceCode != 2) ||
                        (((gasIntervalsSize - uniqueMergedListItemSize(intervalsIdForRemoving, intervalsIdForAdding)) > 0) ||
                                (intervalsIdForRemoving.size() - intervalsIdForAdding.size() > 0))) {
                    if (!intervalsIdForRemoving.isEmpty() || !intervalsIdForAdding.isEmpty()) {
                        showAnnulaConfirmDialog();
                    } else {
                        processingAnnulaAction();
                    }
                } else {
                    if (gasDetailOffers.getYearlySmc() != 0) {
                        createFakeInterval(gasDetailOffers.getYearlySmc());
                        showAnnulaConfirmDialog();
                    }
                }
            }
        });

        civile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                continuaButtonValidation();
                addPDRValidation(false);
            }
        });

        industriale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                continuaButtonValidation();
                addPDRValidation(false);
            }
        });


        comuneAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().equals(town != null ? town.toString() : "")) town = null;
            }
        });
    }

    private void processingAnnulaAction() {
        annulaAction();
        intervalsIdForRemoving.clear();
        intervalsIdForAdding.clear();
        addPdrButtonsLayout.setVisibility(View.VISIBLE);
        cleanPdrTable();
        drawPdrTable();
        isPdrChecked = false;
        preSelectTownFromOffer(offer);
        gasDetailOffers = new GasDetailOffers();
    }

    private void preSelectTownFromOffer(Offer offer) {
        if (offer != null) {
            setTownToAutocomplete(offer.getTownId());
        }
    }

    private void preSelectTownFromGasOffer(GasDetailOffers gasDetailOffers) {
        if (gasDetailOffers != null) {
            setTownToAutocomplete(gasDetailOffers.getTownId());
        }
    }

    private void showAnnulaConfirmDialog() {
        if (doubleButtonConfirmationDialog == null) {
            doubleButtonConfirmationDialog = new DoubleButtonConfirmationDialog(this,
                    getString(R.string.annula_warning));
        }
        doubleButtonConfirmationDialog.setOnDialogSubmitListener(this);
        doubleButtonConfirmationDialog.showDialog();
    }

    private void checkForExistingClient() {
        if (offer != null && offer.getOldTlcOfferId() != 0) {
            ClientServices clientServices = new ClientServicesDAOImpl().getClientServicesByClientId(offer.getOldTlcOfferId());
            ClientGasOffers clientGasOffers = new ClientGasOffersDAOImpl().getClientGasOfferById(clientServices.getGasServiceId() != null ? clientServices.getGasServiceId() : 0);
            List<ClientDetailGasOffers> existingClientsPdrs = new ClientDetailGasOffersDAOImpl().getClientDetailGasOffersById(clientGasOffers.getClientGasOfferId());
            if (existingClientsPdrs.size() > 0) {
                setGasOfferToBaseIfItNeeded();
                for (ClientDetailGasOffers clientDetailGasOffers : existingClientsPdrs) {
                    GasDetailOffers existingOffer = new GasDetailOffers();
                    existingOffer.setGasOfferId(gasOffer.getGasOfferId());
                    existingOffer.setExistingClientOffer(true);
                    existingOffer.setYearlySmc(clientDetailGasOffers.getSmc());
                    existingOffer.setYearlyConsumption(Math.round(clientDetailGasOffers.getSmc() / 12));
                    existingOffer.setFiscalClass(clientDetailGasOffers.getFiscalClass());
                    existingOffer.setPdr(clientDetailGasOffers.getPdr());
                    existingOffer.setOfferCost(new BigDecimal(clientDetailGasOffers.getCost()));
                    existingOffer.setOfferCostIVA(new BigDecimal(clientDetailGasOffers.getCostIva()));
                    existingOffer.setCostVersion(clientDetailGasOffers.getVersion());
                    existingOffer.setPmc(clientDetailGasOffers.getPmc());
                    existingOffer.setPs(clientDetailGasOffers.getPs());
                    existingOffer.setCodicePmcPS(clientDetailGasOffers.getCodicePmcPS());
                    gasDetailOffersDAO.insert(existingOffer);
                }
            }
        }
    }

    private void annulaAction() {
        deleteAllIntervalsFromTable();
        clearPdrFieldsOnThePage();
        clearFieldAssociatedWithIntervals();
//        currentPdrRow.setBackgroundColor(R.color.item_background_lite);
        modificaPDRLayout.setVisibility(View.GONE);

    }

    public void fillClassePrelievoSpinner(int townId, int categoriaUsoId, final Spinner classeDiPrelievoSpinner) {
        ClasseDiPrelievoService classeDiPrelievoService = new ClasseDiPrelievoService(this, new AbstractService.OnTaskCompleted<ClasseDiPrelievoService>() {
            @Override
            public void onTaskCompleted(ClasseDiPrelievoService service) {
                ArrayAdapter adapter = new ArrayAdapter(Gas.this, android.R.layout.simple_spinner_item, android.R.id.text1, (List) service.getResult());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                classeDiPrelievoSpinner.setAdapter(adapter);
                classeDiPrelievoSpinner.setEnabled(true);
            }
        });
        Map<String, Integer> param = new HashMap<>();
        param.put("townId", townId);
        param.put("categoriaUso", categoriaUsoId);
        classeDiPrelievoService.execute(param);
    }

    public void fillCategoriaDusoSpinner(String isBusiness, final Spinner categoriaDusoSpinner, final GasDetailOffers gasDetailOffer) {
        new CategoriaDusoService(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                ArrayAdapter<CategorieUso> adapter = new ArrayAdapter<>(Gas.this, android.R.layout.simple_spinner_item, android.R.id.text1, (List<CategorieUso>) service.getResult());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categoriaDusoSpinner.setAdapter(adapter);
                if (gasDetailOffer.getUserTypeId() != 0)
                    categoriaDusoSpinner.setSelection(gasDetailOffer.getUserTypeId());
            }
        }).execute(isBusiness); ///flag isBussines  getConfiguratorType
    }

    ;

    public void calculateOffer(Class clas) {
        offer.setGasOfferId(gasOffer.getGasOfferId());
        new OfferDAOImpl().update(offer);
        MyApplication.set("offerEntity", offer);
        MyApplication.set("GasOffer", gasOffer);

        if (clas != null) {
            startChildActivity(clas);
        }
    }

    private void continuaButtonValidation() {
        makeContinuaButtonsEnabled(pdrLayout.getChildCount() > 1 && modificaPDRLayout.getVisibility() == View.GONE);
    }

    private void makeContinuaButtonsEnabled(boolean isVisible) {
        int visibility1 = isVisible ? View.VISIBLE : View.GONE;
        int visibility2 = !isVisible ? View.VISIBLE : View.GONE;
        setVisibilityToGroupOfViews(visibility1, calcolaEnabled, ricalcolaEnabled, continuaEnabled);
        setVisibilityToGroupOfViews(visibility2, calcolaDisabled, ricalcolaDisabled, continuaDisabled);
    }

    private void annuoConsumoCalcola(boolean isModificaMod) {
        int smc = Integer.valueOf(editTextGetTextString(R.id.smcGas));

        createFakeInterval(smc);
        fiscaleId = getFiscaleId();
        gasDetailOffers.setYearlySmc(smc);
        gasDetailOffers.setComputedYearlyConsumption(smc);
        gasDetailOffers.setFiscalClass(fiscaleId);
        gasDetailOffers.setTipoContratto(getTipoContratto());
        int categoriaDusoid = ((CategorieUso) categoriaDusoSpinner.getSelectedItem()).getIdCategoriaUso();
        gasDetailOffers.setUserTypeId(categoriaDusoid);
        int classeDePrelievoId = classeDiPrelievo.getSelectedItemPosition() != 0 && classeDiPrelievo.getSelectedItemPosition() != -1 ?
                ((ClassePrelievoGas) classeDiPrelievo.getSelectedItem()).getIdClassePrelievo() :
                0;
        gasDetailOffers.setDayClassId(classeDePrelievoId);
//        gasDetailOffers.setUserTypeId(0);
//        gasDetailOffers.setDayClassId(0);
        gasDetailOffers.setTownId(offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ? town.getTownId() : offer.getTownId());
        gasDetailOffersDAO.update(gasDetailOffers);

        if (!isModificaMod) {
            drawPdrRow(gasDetailOffers);
        } else {
            cleanPdrTable();
            drawPdrTable();
        }
        gasDetailOffers = new GasDetailOffers();
        clearPdrFieldsOnThePage();
    }

    private void createFakeInterval(int smc) {
        GasOfferDateInterval gasOfferDateInterval = new GasOfferDateInterval();
        gasOfferDateInterval.setDateTo(null);
        gasOfferDateInterval.setDateFrom(null);
        gasOfferDateInterval.setSmc(smc);
        gasOfferDateInterval.setGasDetailOfferId(gasDetailOffers.getGasDetailsOfferId());
        deleteAllDateIntervalById();
        gasOfferDateIntervalDAO.insert(gasOfferDateInterval);
    }

    private void intervalConsumoCalcola(final boolean isModificaMod) {
        fiscaleId = getFiscaleId();
        gasDetailOffers.setFiscalClass(fiscaleId);
        int categoriaDusoid = ((CategorieUso) categoriaDusoSpinner.getSelectedItem()).getIdCategoriaUso();
        gasDetailOffers.setUserTypeId(categoriaDusoid);
        int classeDePrelievoId = classeDiPrelievo.getSelectedItemPosition() != 0 && classeDiPrelievo.getSelectedItemPosition() != -1 ?
                ((ClassePrelievoGas) classeDiPrelievo.getSelectedItem()).getIdClassePrelievo() :
                0;
        gasDetailOffers.setDayClassId(classeDePrelievoId);
        gasDetailOffers.setTipoContratto(getTipoContratto());
        gasDetailOffers.setYearlySmc(0);
        gasDetailOffers.setTownId(offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ? town.getTownId() : offer.getTownId());
        gasDetailOffersDAO.update(gasDetailOffers);

        if (!isModificaMod) {
            drawPdrRow(gasDetailOffers);
        } else {
            cleanPdrTable();
            drawPdrTable();
        }
        gasDetailOffers = new GasDetailOffers();
        clearPdrFieldsOnThePage();
    }

    private void clearPdrFieldsOnThePage() {
        String defaultStr = "";
        civile.setChecked(true);
        smcGas.setText(defaultStr);
        smcGasIntervallo.setText(defaultStr);
        periodoA.setText(defaultStr);
        periodoDa.setText(defaultStr);
        categoriaDusoSpinner.setSelection(0);
        classeDiPrelievo.setSelection(0);
    }

    public void drawPdrTable() {
        List<GasDetailOffers> gasDetailOfferses = gasDetailOffersDAO.getGasOfferDetailsByGasOfferId(gasOffer.getGasOfferId());
        if (gasDetailOfferses.size() > 0) {
            existingPdrNumber = 0;
            for (GasDetailOffers offerDetails : gasDetailOfferses) {
                drawPdrRow(offerDetails);
            }
        }
        continuaButtonValidation();
    }

    public void cleanPdrTable() {
        for (int i = pdrLayout.getChildCount() - 1; i > 0; i--) {
            pdrLayout.removeViewAt(i);
        }
    }

    private void drawPdrRow(final GasDetailOffers details) {
        pdrLayout.setVisibility(View.VISIBLE);

        int annuo = 0;
        int intervalAnno = 0;

        LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.gas_pdr_table_row, null);
        TextView pdrName = (TextView) row.findViewById(R.id.PDR);
        TextView fiscale = (TextView) row.findViewById(R.id.fiscale);
        TextView consumoAnnuo = (TextView) row.findViewById(R.id.consumoAnnuo);
        TextView intervalloAnnuo = (TextView) row.findViewById(R.id.intervalloAnno);
        TextView categoriaDuso = (TextView) row.findViewById(R.id.categoriaUso);
        TextView classeDiPrelievo = (TextView) row.findViewById(R.id.classeDiprelievo);
        TextView comuneTextView = (TextView) row.findViewById(R.id.comuneTextView);
        final TextView pdrId = (TextView) row.findViewById(R.id.pdrId);

        final List<GasOfferDateInterval> gasOfferDateIntervals = gasOfferDateIntervalDAO.getOfferDateIntervalByGasDetailsId(details.getGasDetailsOfferId());

        if (gasOfferDateIntervals.size() > 0 && gasOfferDateIntervals.size() == 1 && gasOfferDateIntervals.get(0).getDateFrom() == null) {
            GasOfferDateInterval gasOfferDateInterval1 = gasOfferDateIntervals.get(0);
            annuo = gasOfferDateInterval1.getSmc();
        }

        if (gasOfferDateIntervals.size() > 0 && gasOfferDateIntervals.size() > 0 && gasOfferDateIntervals.get(0).getDateFrom() != null) {
            for (GasOfferDateInterval gasOfferDateInterval1 : gasOfferDateIntervals) {
                intervalAnno += gasOfferDateInterval1.getSmc();
            }
        }

        final String name = "PDR" + (pdrLayout.getChildCount() - existingPdrNumber);

        pdrName.setText(name);
        fiscale.setText(details.getFiscalClass() == 1 ? getString(R.string.fiscale_civile) : getString(R.string.fiscale_industriale));
        consumoAnnuo.setText(String.valueOf(annuo) + " Smc");
        intervalloAnnuo.setText(String.valueOf(intervalAnno) + " Smc");
        pdrId.setText(String.valueOf(details.getGasDetailsOfferId()));
        comuneTextView.setText(new TownDAOImpl().getTownById(details.getTownId() != 0 ? details.getTownId() : offer.getTownId()).toString());
        classeDiPrelievo.setText(new ClassePrelievoGasDAOImpl().getClasseDiPrelievoDescById(details.getDayClassId()));
        categoriaDuso.setText(new CategorieUsoDAOImpl().getCategorieUsoDescById(details.getUserTypeId()));

        LinearLayout currentPdr = (LinearLayout) row.findViewById(R.id.currentPDR);
        LinearLayout deleteLayout = (LinearLayout) row.findViewById(R.id.deleteLayout);
        currentPdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPdrChecked) {
                    if (details.isExistingClientOffer()) {
                        new RigmeFiscaleDialog(Gas.this, details, offer);
                    } else {
                        currentPdrRow = (LinearLayout) v;
                        currentPdrRow.setBackgroundColor(getResources().getColor(R.color.item_background_blue));
                        gasDetailOffers = gasDetailOffersDAO.getGasOfferDetailsById(Integer.valueOf(pdrId.getText().toString().trim()));
                        addPdrButtonsLayout.setVisibility(View.GONE);
                        setPdrValuesToView();
                    }
                }
            }
        });

        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPdrChecked) {
                    LinearLayout deleteRow = (LinearLayout) v.getParent().getParent();
                    pdrLayout.removeView(deleteRow);
                    gasDetailOffersDAO.deleteGasDetailslById(Integer.valueOf(pdrId.getText().toString().trim()));
                    if (name.startsWith("PDR")) existingPdrNumber--;
                    if (pdrLayout.getChildCount() == 1) {
                        pdrLayout.setVisibility(View.GONE);
                    }
                    cleanPdrTable();
                    drawPdrTable();
                    continuaButtonValidation();
                }
            }
        });

        if (details.isExistingClientOffer()) {
//            currentPdr.setClickable(false);
            pdrName.setText(details.getPdr());
            consumoAnnuo.setText(String.valueOf(details.getYearlySmc()) + " Smc");
            existingPdrNumber++;
        } else {
            details.setPdr(name);
        }

        new GasDetailOffersDAOImpl().update(details);
        pdrLayout.addView(row);
        deleteAllIntervalsFromTable();
        clearFieldAssociatedWithIntervals();
    }

    private void deleteAllIntervalsFromTable() {
        for (int i = tableLayout.getChildCount() - 1; i > 0; i--) {
            tableLayout.removeViewAt(i);
        }
        tableLayout.setVisibility(View.GONE);
    }

    private void setPdrValuesToView() {
        List<GasOfferDateInterval> gasOfferDateIntervals = gasOfferDateIntervalDAO.getOfferDateIntervalByGasDetailsId(gasDetailOffers.getGasDetailsOfferId());
        if (gasOfferDateIntervals.size() > 0) {
            if (gasOfferDateIntervals.get(0).getDateFrom() != null) {
                for (GasOfferDateInterval interval : gasOfferDateIntervals) {
                    createTableRow(interval);
                }
                intervalDiTempo.setChecked(true);
            } else {
                smcGas.setText(String.valueOf(gasOfferDateIntervals.get(0).getSmc()));
                annuoEnergetici.setChecked(true);
            }
            for (int i = 1; i < categoriaDusoSpinner.getCount(); i++) {
                CategorieUso categorieUso = (CategorieUso) categoriaDusoSpinner.getItemAtPosition(i);
                if (categorieUso.getIdCategoriaUso() == gasDetailOffers.getUserTypeId()) {
                    categoriaDusoSpinner.setSelection(i);
                    break;
                }
            }
            civile.setChecked(gasDetailOffers.getFiscalClass() == 1);
            industriale.setChecked(gasDetailOffers.getFiscalClass() == 2);
            preSelectTownFromGasOffer(gasDetailOffers);
            modificaPDRLayout.setVisibility(View.VISIBLE);
            pdrLayout.setClickable(false);
            isPdrChecked = true;
            setTipologiaContratto(gasDetailOffers, offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR);
            setDataPrelievoSpinner(gasDetailOffers, classeDiPrelievo);
        }
    }

    private void setTipologiaContratto(GasDetailOffers gasDetailOffers, boolean isBusinessConfigurator) {
        if (offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR) {
            int radioButtonToCheck;
            switch (gasDetailOffers.getTipoContratto()) {
                case Constants.ALTRI_USI:
                    radioButtonToCheck = R.id.altriUsi;
                    break;
                case Constants.DOMESTICO:
                    radioButtonToCheck = R.id.domestico;
                    break;
                case Constants.CONDOMINIO:
                    radioButtonToCheck = R.id.condominio;
                    break;
                default:
                    radioButtonToCheck = R.id.domestico;
                    break;
            }
            rgTipologiaContratto.check(radioButtonToCheck);
        }
    }

    private void setTownToAutocomplete(int currentTownId) {
        if (offer != null && offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR) {
            Town currentTown = new TownDAOImpl().getTownById(currentTownId);
            if (currentTown != null) {
                comuneAutoComplete.setText(currentTown.toString());
                town = currentTown;
            }
        }
    }

    private void updateDisplayDateFrom(int id) {
        ((TextView) findViewById(id)).setText(
                new StringBuilder()
                        .append(pad(day))
                        .append("/")
                        .append(pad(month + 1))
                        .append("/")
                        .append(pad(year))
        );
    }

    private void updateDisplayDateTo(int id) {
        ((TextView) findViewById(id)).setText(
                new StringBuilder()
                        .append(pad(day2))
                        .append("/")
                        .append(pad(month2 + 1))
                        .append("/")
                        .append(pad(year2))
        );
    }

    private String pad(Integer d) {
        return d.toString().length() == 1 ? "0" + d.toString() : d.toString();
    }

    public void changeDateFrom(View v) {
        new DatePickerDialog(this, onDateFromSetListener, year, month, day).show();
    }

    public void changeDateTo(View v) {
        new DatePickerDialog(this, onDateToSetListener, year2, month2, day2).show();
    }

    private void saveIntervalData() {
        if (intervaPartValidation(true)) {
            checkForPodTypeChanged();
            setGasOfferToBaseIfItNeeded();
            setGasOfferDetailsIfItNeeded();
            String dateDa = TextViewGetText(R.id.gasIntervalFrom);
            String dateAInvisible = TextViewGetText(R.id.gasIntervalTo);
            String smc = editTextGetTextString(R.id.smcGasIntervallo);
            GasOfferDateInterval gasOfferDateInterval = new GasOfferDateInterval();
            gasOfferDateInterval.setDateFrom(dateDa);
            gasOfferDateInterval.setDateTo(dateAInvisible);
            gasOfferDateInterval.setSmc(Integer.valueOf(smc));
            gasOfferDateInterval.setGasDetailOfferId(gasDetailOffers.getGasDetailsOfferId());
            gasOfferDateIntervalDAO.insert(gasOfferDateInterval);
            if (isPdrChecked) {
                intervalsIdForAdding.add(gasOfferDateInterval.getGasDetailOfferIntervalId());
            }
            createTableRow(gasOfferDateInterval);
        }
    }

    private void setGasOfferDetailsIfItNeeded() {
        if (gasDetailOffers.getGasDetailsOfferId() == 0) {
            gasDetailOffers.setGasOfferId(gasOffer.getGasOfferId());
            gasDetailOffersDAO.insert(gasDetailOffers);
        }
    }

    private void setGasOfferToBaseIfItNeeded() {
        if (gasOffer.getGasOfferId() == 0) {
            gasOffer.setQuestionarioUsing(false); /// isQuestionarioUsing
            gasOfferDAO.insert(gasOffer);
        }
    }

    private void checkForPodTypeChanged() {
        List<GasOfferDateInterval> gasOfferDateIntervals = gasOfferDateIntervalDAO.getOfferDateIntervalByGasDetailsId(gasDetailOffers.getGasDetailsOfferId());
        if (gasOfferDateIntervals.size() > 0 && gasOfferDateIntervals.get(0).getDateFrom() == null) {
            gasOfferDateIntervalDAO.deleteGasDateIntervallByDetailId(gasDetailOffers.getGasDetailsOfferId());
        }
    }

    private boolean intervaPartValidation(boolean isAnimated) {
        return (isTextViewValid(true, R.id.gasIntervalFrom, R.id.gasIntervalTo) && isIntervalDateRangeValid(TextViewGetText(R.id.gasIntervalFrom), TextViewGetText(R.id.gasIntervalTo)) && isValid(isAnimated, R.id.smcGasIntervallo));
    }

    public boolean isIntervalDateRangeValid(String startDate, String finishDate) {
        boolean flag = true;
        try {
            Date dateFrom = parseDate(startDate);
            Date dateTo = parseDate(finishDate);
            if (dateFrom.before(dateTo)) {
                List<GasOfferDateInterval> dateIntervals = gasOfferDateIntervalDAO.getOfferDateIntervalByGasDetailsId(gasDetailOffers.getGasDetailsOfferId());
                for (GasOfferDateInterval dateInterval : dateIntervals) {
                    if ((dateInterval.getDateFrom().getTime() <= dateTo.getTime()) &&
                            (dateInterval.getDateTo().getTime() >= dateFrom.getTime()) &&
                            !(intervalsIdForRemoving.contains(dateInterval.getGasDetailOfferIntervalId()))) {
                        Toast.makeText(this, "l'intervallo di tempo che si sta inserendo contiene date già previste in un precedente intervallo", Toast.LENGTH_LONG).show();
                        flag = false;
                        break;
                    }
                }
            } else {
                Toast.makeText(this, "data Da deve essere inferiore alla data della A", Toast.LENGTH_SHORT).show();
                flag = false;
            }
        } catch (Throwable throwable) {
            Log.e(this.getClass().getSimpleName(), "can't parse date", throwable);
            flag = false;
        }
        return flag;
    }

    public void createTableRow(GasOfferDateInterval gasOfferDateInterval) {
        dynamicTableGas.setVisibility(View.VISIBLE);

        TableRow newRow = (TableRow) getLayoutInflater().inflate(R.layout.list_item_gas, null);

        EditText daText = (EditText) newRow.findViewById(R.id.datastart);
//        EditText aText = (EditText) newRow.findViewById(R.id.datainvisible);
        EditText aVisibleText = (EditText) newRow.findViewById(R.id.datafinish);
        EditText smcText = (EditText) newRow.findViewById(R.id.value);
        final EditText gasIntervalId = (EditText) newRow.findViewById(R.id.gasIntervalId);


        daText.setText(convertDate(gasOfferDateInterval.getDateFrom()));
        String convertedDateTo = convertDate(gasOfferDateInterval.getDateTo());
//        aText.setText(nextDay(convertedDateTo));
        aVisibleText.setText(convertedDateTo);

        smcText.setText(String.valueOf(gasOfferDateInterval.getSmc()));
        gasIntervalId.setText(String.valueOf(gasOfferDateInterval.getGasDetailOfferIntervalId()));

        ImageView deleteIcon = (ImageView) newRow.findViewById(R.id.delete);
        deleteIcon.setImageDrawable(getResources().getDrawable(R.drawable.delete_line_icon));
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout deleteRow = (LinearLayout) v.getParent().getParent();
                tableLayout.removeView(deleteRow);
                if (isPdrChecked) {
                    intervalsIdForRemoving.add(Integer.valueOf(gasIntervalId.getText().toString().trim()));
                } else {
                    gasOfferDateIntervalDAO.deleteGasDateIntervallById(Integer.valueOf(gasIntervalId.getText().toString().trim()));
                }
                if (dynamicTableGas.getChildCount() == 1) {
                    dynamicTableGas.setVisibility(View.GONE);
                    addPDRValidation(false);
//                    if (isPdrChecked) annula.setVisibility(View.GONE);
                }
            }
        });
        tableLayout.addView(newRow);
        clearFieldAssociatedWithIntervals();
    }

    private void clearFieldAssociatedWithIntervals() {
        String emptyField = "";
        smcGasIntervallo.setText(emptyField);
        periodoA.setText(emptyField);
        periodoDa.setText(emptyField);
    }


    private List<String> getDynamicTableContent() {
        TableLayout dynavicTable = (TableLayout) findViewById(R.id.dynamicTableGas);
        int childCount = dynavicTable.getChildCount();

        List<String> result = new ArrayList<>();

        for (int index = 1; index < childCount; index++) {
            TableRow tableRow = (TableRow) dynavicTable.getChildAt(index);
            LinearLayout parent = (LinearLayout) tableRow.getChildAt(0);
            EditText dateFrom = (EditText) parent.getChildAt(0);
            EditText dateTo = (EditText) parent.getChildAt(1);
            EditText smc = (EditText) parent.getChildAt(3);

            result.add(dateFrom.getEditableText().toString());
            result.add(dateTo.getEditableText().toString());
            result.add(smc.getEditableText().toString());
        }
        return result;
    }

    private void addPdr(boolean isMofificaMod) {
        if (addPDRValidation(false)) {
            setGasOfferToBaseIfItNeeded();
            setGasOfferDetailsIfItNeeded();
            if (choiceCode == 1)
                annuoConsumoCalcola(isMofificaMod);
            else
                intervalConsumoCalcola(isMofificaMod);
        }
    }

    //    private boolean addPDRValidation(boolean isAnimated) {
//        boolean result = (((choiceCode != 1 && choiceCode != 0) || (isValid(isAnimated, R.id.smcGas))) &&
//                ((choiceCode != 2 && choiceCode != 0) || (!isIntervalTableEmpty(isAnimated))) &&
//                (checkSpinnerSelection(isAnimated, R.id.categoria_di_utilizzo) && checkSpinnerSelection(isAnimated, R.id.classe_di_prelievo)) &&
//                checkRadioGroupForSelection(isAnimated, fiscaleRadioGroup) && town != null);
//        if (result) {
//            changeViewVisibility(R.id.addPdrDisabled, R.id.addPdrEnabled);
//        } else {
//            changeViewVisibility(R.id.addPdrEnabled, R.id.addPdrDisabled);
//        }
//        return result;
//    }
    ///add new validation
    private boolean addPDRValidation(boolean isAnimated) {
        boolean result;
        if (isBussinesType) {
            result = (((choiceCode != 1 && choiceCode != 0) || (isValid(isAnimated, R.id.smcGas))) &&
                    ((choiceCode != 2 && choiceCode != 0) || (!isIntervalTableEmpty(isAnimated))) &&
                    (checkSpinnerSelection(isAnimated, R.id.categoria_di_utilizzo) && checkSpinnerSelection(isAnimated, R.id.classe_di_prelievo)) &&
                    checkRadioGroupForSelection(isAnimated, fiscaleRadioGroup) &&
                    town != null);
        } else {
            result = (((choiceCode != 1 && choiceCode != 0) || (isValid(isAnimated, R.id.smcGas))) &&
                    ((choiceCode != 2 && choiceCode != 0) || (!isIntervalTableEmpty(isAnimated))) &&
                    (checkSpinnerSelection(isAnimated, R.id.categoria_di_utilizzo)) &&
                    checkRadioGroupForSelection(isAnimated, rgTipologiaContratto));
        }
//        boolean result = (((choiceCode != 1 && choiceCode != 0) || (isValid(isAnimated, R.id.smcGas))) &&
//                ((choiceCode != 2 && choiceCode != 0) || (!isIntervalTableEmpty(isAnimated))) &&
//                (checkSpinnerSelection(isAnimated, R.id.categoria_di_utilizzo) && checkSpinnerSelection(isAnimated, R.id.classe_di_prelievo)) &&
//                checkRadioGroupForSelection(isAnimated, fiscaleRadioGroup) && town != null);
        if (result) {
            changeViewVisibility(R.id.addPdrDisabled, R.id.addPdrEnabled);
        } else {
            changeViewVisibility(R.id.addPdrEnabled, R.id.addPdrDisabled);
        }
        return result;
    }

//    private boolean addPDRValidation(boolean isAnimated) {
//        boolean result = (((choiceCode != 1 && choiceCode != 0) || (isValid(isAnimated, R.id.smcGas))) &&
//                ((choiceCode != 2 && choiceCode != 0) || (!isIntervalTableEmpty(isAnimated) &&
//                        checkSpinnerSelection(isAnimated, R.id.categoria_di_utilizzo) && checkSpinnerSelection(isAnimated, R.id.classe_di_prelievo))) &&
//                checkRadioGroupForSelection(isAnimated, fiscaleRadioGroup) && town != null);
//        if (result) {
//            changeViewVisibility(R.id.addPdrDisabled, R.id.addPdrEnabled);
//        } else {
//            changeViewVisibility(R.id.addPdrEnabled, R.id.addPdrDisabled);
//        }
//        return result;
//    }

    private boolean isIntervalTableEmpty(boolean isAnimated) {
        boolean flag = getDynamicTableContent().size() == 0;
        if (flag && isAnimated) {
            findViewById(R.id.GasAggiungiPlusButtonLayout).requestFocus();
            findViewById(R.id.GasAggiungiPlusButtonLayout).startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        }
        return flag;
    }

    private void setEntityValues() {
        gasOffer = MyApplication.get("ArchivioGasOffer", GasOffer.class);
        drawPdrTable();
    }

    private void checkRicalcolaButton() {
        if (MyApplication.get("modificaGasLable", Boolean.class) != null) {
            findViewById(R.id.continuaLinearLayout5).setVisibility(View.GONE);
            findViewById(R.id.calcolaLinearLayout5).setVisibility(View.GONE);
            findViewById(R.id.ricalcolaGasLayout).setVisibility(View.VISIBLE);

        }
    }

    private void deleteAllDateIntervalById() {
        new GasOfferDateIntervalDAOImpl().deleteGasDateIntervallByDetailId(gasDetailOffers.getGasDetailsOfferId());
    }


    private void updateOfferEntityInBase() {
        GasOfferDAOImpl gasOfferDAO = new GasOfferDAOImpl();
        int id = gasOffer.getGasOfferId();
        offer.setGasOfferId(id);
        OfferDAOImpl offerDAO = new OfferDAOImpl();
        offerDAO.update(offer);
        MyApplication.set("offerEntity", offer);
    }

    private void setGasOfferEntityToBase() {
        GasOfferDAOImpl gasOfferDAO = new GasOfferDAOImpl();
        if (gasOffer.getGasOfferId() == 0) {
            gasOfferDAO.insert(gasOffer);
        } else {
            gasOfferDAO.update(gasOffer);
        }
    }

    private int getTipoContratto() {
        int result;
        if (offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR) {
            switch (rgTipologiaContratto.getCheckedRadioButtonId()) {
                case R.id.altriUsi:
                    result = 1;
                    break;
                case R.id.domestico:
                    result = 2;
                    break;
                case R.id.condominio:
                    result = 3;
                    break;
                default:
                    result = 2;
                    break;
            }
        } else {
            result = 1;
        }
        return result;
    }


    @Override
    public void run() {
//        if (gasDetailOffers.getUserTypeId() != 0 && classeDiPrelievo.getChildCount() != 0) {
//            classeDiPrelievo.setSelection(gasDetailOffers.getDayClassId());
//        }
    }

    private int getFiscaleId() {
        return civile.isChecked() ? 1 : 2;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        addPDRValidation(false);
        continuaButtonValidation();
    }

    public String getSystem() {
        return system;
    }

    public void setDataPrelievoSpinner(final GasDetailOffers gasDetailOffer, final Spinner classeDiPrelievoSpinner) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                classeDiPrelievoSpinner.setSelection(gasDetailOffer.getDayClassId());
            }
        }, 450);
    }

    @Override
    public void onDialogSubmit(boolean isAccepted) {
        if (isAccepted) {
            removeAllDataMarkedForRemoving(intervalsIdForAdding);
            processingAnnulaAction();
        }
    }

    private void removeAllDataMarkedForRemoving(List<Integer> intervalsId) {
        for (Integer id : intervalsId) {
            if (!intervalsIdForAdding.contains(id)) {
                gasOfferDateIntervalDAO.deleteGasDateIntervallById(id);
            }
        }
        intervalsIdForRemoving.clear();
        intervalsIdForAdding.clear();
    }
}