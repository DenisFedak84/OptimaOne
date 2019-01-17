package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientDetailEnergyOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientServicesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyMeterDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TariffaDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientDetailEnergyOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientServices;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyMeter;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Tariffa;
import com.aimprosoft.android.optima.centralizedApp.event.ConfirmationDialog;
import com.aimprosoft.android.optima.centralizedApp.event.DoubleButtonConfirmationDialog;
import com.aimprosoft.android.optima.centralizedApp.event.listener.OnDialogSubmitListener;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.PotenzaContatoreSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.TariffaService;
import com.aimprosoft.android.optima.centralizedApp.service.net.EnergyAdditionalWebServices;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.AbstractJarUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EnergeticiLaFattura extends BaseActivity implements Runnable, TextWatcher, OnDialogSubmitListener {
    private EnergyOffer energyOffer;
    private EnergyOfferDetails energyOfferDetails = new EnergyOfferDetails();
    private Offer offer = MyApplication.get("offerEntity", Offer.class);
    private EnergyOfferDAOImpl energyOfferDAO = new EnergyOfferDAOImpl();
    private EnergyOfferDetailsDAOImpl energyOfferDetailsDAO = new EnergyOfferDetailsDAOImpl();
    private EnergyOfferDateIntervalDAOImpl energyOfferDateIntervalDAO = new EnergyOfferDateIntervalDAOImpl();

    //    private final Calendar calendar = Calendar.getInstance();
    private int choiceCode = 0;
    private int pageNumber = 1;
    private int existingClientId = 0;
//    private int specificModFieldNumber = 0;
//    private int year;
//    private int month;
//    private int day;

    private RadioGroup tensioneRadioGroup;
    private RadioButton bt;
    private RadioButton bt6;
    private RadioButton mt;

    //    private ConfirmationDialog confirmationDialog;
    private ConfirmationDialog energyConsumerDomesticoDialog;

    private TextView periodoDa;
    private TextView periodoA;
    private TextView potenzaImpegnataTableTittle;

    private EditText kwh1;
    private EditText kwh2;
    private EditText kwh3;
    private EditText kwh_intervali_1;
    private EditText kwh_intervali_2;
    private EditText kwh_intervali_3;
    private EditText potenzaContatoreManual;
    private EditText pod;

    private TableLayout dynamicTable;
    //    private LinearLayout kwh2Layout;
//    private LinearLayout annoKwhLayout2;
//    private LinearLayout annoKwhLayout3;
//    private LinearLayout kwh3Layout;
    private LinearLayout podLayout;

    private Button calcolaEnabled;
    private Button calcolaDisabled;
    private Button ricalcolaEnabled;
    private Button modificaPOD;
    private Button annula;
    private Button ricalcolaDisabled;

    private ImageButton continuaEnabled;
    private ImageButton continuaDisabled;

    private LinearLayout modificaPODLayout;
    private LinearLayout addPodButtonsLayout;
    private LinearLayout tariffaLayout;
    private LinearLayout potenzaImpegnataLayout;
    private LinearLayout currentPodRow;
    private LinearLayout potenzaImpegnataManualLayout;

    private Spinner potenzaContatore;
    private Spinner tariffaSpinner;
    private RadioButton domestico;
    private RadioButton altriUsi;

    private Calendar periodoDaCalendar;
    private Calendar periodoACalendar;

    private boolean isPodChecked = false;
    //    private boolean isSpecificMode = false;
    private boolean isDomesticoOffer = false;

    private final int BT = 1;
    private final int BTA6 = 2;
    private final int MT = 3;
    private int tensioneId = 1;
    private int INTERVAL_TYPE = Constants.SIMPLE;

    private final int BTA6_INTERVALS_LIMIT = 3;
    private final int MT_INTERVALS_LIMIT = 6;

    private List<Integer> intervalsIdForRemoving = new ArrayList<>();
    private List<Integer> intervalsIdForAdding = new ArrayList<>();
    private DoubleButtonConfirmationDialog doubleButtonConfirmationDialog;

    private DatePickerDialog.OnDateSetListener onDateFromSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newYear, int monthOfYear, int dayOfMonth) {
            periodoDaCalendar.set(newYear, monthOfYear, dayOfMonth);
            updateDisplayDate(R.id.elettricitaIntervalFrom, periodoDaCalendar);
        }
    };

    private DatePickerDialog.OnDateSetListener onDateToSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newYear2, int monthOfYear2, int dayOfMonth2) {
            periodoACalendar.set(newYear2, monthOfYear2, dayOfMonth2);
            updateDisplayDate(R.id.elettricitaIntervalTo, periodoACalendar);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.energetici_la_fattura_layout);
        initializeViews();
        showRequiredInfo();

        periodoDaCalendar = Calendar.getInstance();
        periodoACalendar = Calendar.getInstance();

        checkContinuaButton(R.id.continuaLinearLayout4, R.id.calcolaLinearLayout4, Constants.ENERGETICI);
        checkRicalcolaButton();

        MyApplication.set("energetici", true);

//        year = calendar.get(Calendar.YEAR);
//        month = calendar.get(Calendar.MONTH);
//        day = calendar.get(Calendar.DAY_OF_MONTH);

        domestico.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) fillPotenzaContatoreSpinner(1);

        });

        altriUsi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) fillPotenzaContatoreSpinner(2);
        });

        potenzaContatore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addPodValidation(false);
                continuaButtonValidation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        tariffaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addPodValidation(false);
                continuaButtonValidation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        bt6.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tensioneId = 2;
                cleanDateFields();
                periodoA.setEnabled(false);
                tariffaSpinner.setSelection(0);
                potenzaImpegnataManualLayout.setVisibility(View.VISIBLE);
                potenzaImpegnataLayout.setVisibility(View.GONE);
                tariffaLayout.setVisibility(View.GONE);
                tariffaSpinner.setSelection(0);
                potenzaContatore.setSelection(0);
                addPodValidation(false);
            }
        });

        bt.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tensioneId = 1;
                cleanDateFields();
                periodoA.setEnabled(true);
                tariffaSpinner.setSelection(0);
                potenzaContatoreManual.setText("");
                potenzaImpegnataManualLayout.setVisibility(View.GONE);
                potenzaImpegnataLayout.setVisibility(View.VISIBLE);
                tariffaLayout.setVisibility(View.GONE);
                tariffaSpinner.setSelection(0);
                addPodValidation(false);
            }
        });

        mt.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tensioneId = 3;
                cleanDateFields();
                periodoA.setEnabled(false);
                potenzaImpegnataManualLayout.setVisibility(View.VISIBLE);
                potenzaImpegnataLayout.setVisibility(View.GONE);
                tariffaLayout.setVisibility(View.VISIBLE);
                potenzaContatore.setSelection(0);
                addPodValidation(false);
            }
        });

        periodoA.setOnLongClickListener(v -> {
            periodoA.setText("");
            return true;
        });

        periodoDa.setOnLongClickListener(v -> {
            periodoDa.setText("");
            return true;
        });

        findViewById(R.id.addPodEnabled).setOnClickListener(v -> addPod(false, false));
        ImageButton continua = (ImageButton) findViewById(R.id.continua4);
        continua.setOnClickListener(v -> processForwardAction(null));

        findViewById(R.id.plusButton).setOnClickListener(v -> saveIntervalData(true));

        new TariffaService(this, new AbstractService.OnTaskCompleted<TariffaService>() {
            @Override
            public void onTaskCompleted(TariffaService service) {
                setSpinner(tariffaSpinner, service.getResult());
            }
        }).execute();

        findViewById(R.id.ricalcolaElettriciti4).setOnClickListener(v -> processForwardAction(Canone.class));

        findViewById(R.id.calcola4).setOnClickListener(v -> processForwardAction(Canone.class));

        kwh1.addTextChangedListener(this);
        kwh2.addTextChangedListener(this);
        kwh3.addTextChangedListener(this);
        kwh_intervali_1.addTextChangedListener(this);
        kwh_intervali_2.addTextChangedListener(this);
        kwh_intervali_3.addTextChangedListener(this);
        potenzaContatoreManual.addTextChangedListener(this);

        periodoDa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tensioneId != BT) {
                    periodoA.setText(getLastDayOfMonth(s.toString(), 0));
                }
                addPodValidation(false);
                continuaButtonValidation();
            }
        });

        pod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                colorLengthValidation(pod, s, Constants.POD_FIELD_LENGTH);
                addPodValidation(false);
            }
        });

        modificaPOD.setOnClickListener(v -> {
            if (((choiceCode != 2) || !isIntervalTableHasEnoughChild(true, 1)) & addPodValidation(true)) {
                removeAllDataMarkedForRemoving(intervalsIdForRemoving);
                addPod(true, false);
                modificaPODLayout.setVisibility(View.GONE);
                addPodButtonsLayout.setVisibility(View.VISIBLE);
                isPodChecked = false;
                annula.setVisibility(View.VISIBLE);
                continuaButtonValidation();
            }
        });

        annula.setOnClickListener(v -> {
            if ((choiceCode != 2) || !isIntervalTableHasEnoughChild(true, 1)) {
                if (!intervalsIdForRemoving.isEmpty() || !intervalsIdForAdding.isEmpty()) {
                    showConfirmDialog();
                } else {
                    processingAnnulaAction();
                }
            }
        });
        setPotenzaSpinner();
    }

    private void processForwardAction(final Class clas) {
        if (offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR &&
                offer.getClientTypeId() == Constants.RESIDENZIALE && isDomesticoOffer) {
            initializeConsumerConfirmationDialog();
            energyConsumerDomesticoDialog.setOnDialogDismissedListener(() -> forwardAction(clas));
            energyConsumerDomesticoDialog.showDialog();
        } else {
            forwardAction(clas);
        }
    }

    private void forwardAction(Class clas) {
        if (clas != null) {
            startNextPage(clas);
        } else {
            startNextPage(null);
            isLastPage(pageNumber);
        }
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
            energyOfferDateIntervalDAO.deleteEnergyDateIntervallById(id);
        }
        intervalsIdForRemoving.clear();
        intervalsIdForAdding.clear();
    }

    private void showConfirmDialog() {
        if (doubleButtonConfirmationDialog == null) {
            doubleButtonConfirmationDialog = new DoubleButtonConfirmationDialog(this,
                    getString(R.string.annula_warning));
        }
        doubleButtonConfirmationDialog.setOnDialogSubmitListener(this);
        doubleButtonConfirmationDialog.showDialog();
    }

    private void cleanDateFields() {
        periodoA.setText(Constants.EMPTY_STRING);
        periodoDa.setText(Constants.EMPTY_STRING);
        periodoACalendar = Calendar.getInstance();
        periodoDaCalendar = Calendar.getInstance();
    }

    private void updateDisplayDate(int id, Calendar calendar) {
        ((TextView) findViewById(id)).setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void setPotenzaSpinner() {
        if (offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR) {
            fillPotenzaContatoreSpinner(null);
            bt.setChecked(true);
        } else {
            domestico.setChecked(true);
        }
        checkForModificaMode();
    }

    private void checkForModificaMode() {
        if (MyApplication.get("ArchivioOfferEntity", EnergyOffer.class) != null) {
            setEntityValues();
        } else {
            energyOffer = new EnergyOffer();
            checkForExistingClient();
            drawPodTable();
        }
    }

    private void showRequiredInfo() {
        if (offer != null && offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR) {
            findViewById(R.id.tensioneLayout).setVisibility(View.VISIBLE);
            findViewById(R.id.tipologiaContrattoLayout).setVisibility(View.GONE);
        }
    }

    private void annulaAction() {
        deleteAllIntervalsFromTable();
        clearPodFieldOnThePage();
        clearFieldAssociatedWithIntervals();
        currentPodRow.setBackgroundColor(ContextCompat.getColor(this, R.color.item_background_lite));
        modificaPODLayout.setVisibility(View.GONE);
    }

    private void fillPotenzaContatoreSpinner(Integer contractType) {
        new PotenzaContatoreSpinner(EnergeticiLaFattura.this, new AbstractService.OnTaskCompleted<PotenzaContatoreSpinner>() {
            @Override
            public void onTaskCompleted(PotenzaContatoreSpinner service) {
                setSpinner(potenzaContatore, service.getResult());
                new Handler().postDelayed(() -> preselectEnergyMeterData(), 500);
            }
        }).execute(getConfiguratorType(offer.getConfiguratorType()), contractType);
    }

    private void makeContinuaButtonsEnabled(boolean isVisible) {
        int visibility1 = isVisible ? View.VISIBLE : View.GONE;
        int visibility2 = !isVisible ? View.VISIBLE : View.GONE;
        setVisibilityToGroupOfViews(visibility1, calcolaEnabled, ricalcolaEnabled, continuaEnabled);
        setVisibilityToGroupOfViews(visibility2, calcolaDisabled, ricalcolaDisabled, continuaDisabled);
    }

    private void initializeViews() {
        potenzaImpegnataTableTittle = (TextView) findViewById(R.id.potenzaImpegnataTableTittle);
        tensioneRadioGroup = (RadioGroup) findViewById(R.id.tensioneRadioGroup);
        periodoDa = (TextView) findViewById(R.id.elettricitaIntervalFrom);
        periodoA = (TextView) findViewById(R.id.elettricitaIntervalTo);
        bt = (RadioButton) findViewById(R.id.radioButtonBT);
        bt6 = (RadioButton) findViewById(R.id.radioButtonBT6);
        mt = (RadioButton) findViewById(R.id.radioButtonMT);
        potenzaContatore = (Spinner) findViewById(R.id.potensaContatoreSpinner4);
        kwh1 = (EditText) findViewById(R.id.annuoKwh1);
        kwh2 = (EditText) findViewById(R.id.annuoKwh2);
        kwh3 = (EditText) findViewById(R.id.annuoKwh3);
        kwh_intervali_1 = (EditText) findViewById(R.id.kwh1_intervali);
        kwh_intervali_2 = (EditText) findViewById(R.id.kwh2_intervali);
        kwh_intervali_3 = (EditText) findViewById(R.id.kwh3_intervali);
        pod = (EditText) findViewById(R.id.podConfFattura);
        bt.setClickable(true);
        bt6.setClickable(true);
        mt.setClickable(true);
        dynamicTable = (TableLayout) findViewById(R.id.dinamicTable);
        podLayout = (LinearLayout) findViewById(R.id.podLayout);
        calcolaDisabled = (Button) findViewById(R.id.calcola4Disabled);
        calcolaEnabled = (Button) findViewById(R.id.calcola4);
        ricalcolaDisabled = (Button) findViewById(R.id.ricalcolaElettriciti4Disabled);
        ricalcolaEnabled = (Button) findViewById(R.id.ricalcolaElettriciti4);
        continuaDisabled = (ImageButton) findViewById(R.id.continua4disabled);
        continuaEnabled = (ImageButton) findViewById(R.id.continua4);
        annula = (Button) findViewById(R.id.annulaButton);
        modificaPOD = (Button) findViewById(R.id.modificaButton);
        modificaPODLayout = (LinearLayout) findViewById(R.id.modificaPodLayout);
        addPodButtonsLayout = (LinearLayout) findViewById(R.id.addPodButtonlayout);
        tariffaLayout = (LinearLayout) findViewById(R.id.tariffaLayout);
        potenzaImpegnataLayout = (LinearLayout) findViewById(R.id.potenzaImpegnataLayout);
        potenzaImpegnataManualLayout = (LinearLayout) findViewById(R.id.potenzaImpegnataManualLayout);
        tariffaSpinner = (Spinner) findViewById(R.id.tariffa);
        potenzaContatoreManual = (EditText) findViewById(R.id.potenzaImpegnatgaManual);
        domestico = (RadioButton) findViewById(R.id.domestico);
        altriUsi = (RadioButton) findViewById(R.id.altriUsi);
    }

    public void createTableRow(EnergyOfferDateInterval energyOfferDateInterval) {
        dynamicTable.setVisibility(View.VISIBLE);

        TableRow newRow = (TableRow) getLayoutInflater().inflate(R.layout.list_item, null);
        EditText daText = (EditText) newRow.findViewById(R.id.datastart);
        EditText aText = (EditText) newRow.findViewById(R.id.datainvisible);
        EditText aVisibleText = (EditText) newRow.findViewById(R.id.datafinish);
        EditText kwhText = (EditText) newRow.findViewById(R.id.valueKwh1);
        EditText kwh2Text = (EditText) newRow.findViewById(R.id.valueKwh2);
        EditText kwh3Text = (EditText) newRow.findViewById(R.id.valueKwh3);
        EditText potenzaImpegnataText = (EditText) newRow.findViewById(R.id.potenzaImpegnata);

        EditText energyIntervalId = (EditText) newRow.findViewById(R.id.energyIntervalId);

        daText.setText(convertDate(energyOfferDateInterval.getDateFrom()));
        aText.setVisibility(View.GONE);
//        aText.setText(nextDay(convertDate(energyOfferDateInterval.getDateTo())));
        aVisibleText.setText(convertDate(energyOfferDateInterval.getDateTo()));
        kwhText.setText(energyOfferDateInterval.getKwh1() != Constants.NO_VALUE ? String.valueOf(energyOfferDateInterval.getKwh1()) : "");
        kwh2Text.setText(energyOfferDateInterval.getKwh2() != Constants.NO_VALUE ? String.valueOf(energyOfferDateInterval.getKwh2()) : "");
        kwh3Text.setText(energyOfferDateInterval.getKwh3() != Constants.NO_VALUE ? String.valueOf(energyOfferDateInterval.getKwh3()) : "");
        potenzaImpegnataText.setText(energyOfferDateInterval.getPotenzaImpegnata() != 0 ? String.valueOf(energyOfferDateInterval.getPotenzaImpegnata()) : "");
        if (energyOfferDateInterval.getPotenzaImpegnata() != 0) {
            potenzaImpegnataText.setVisibility(View.VISIBLE);
            potenzaImpegnataTableTittle.setVisibility(View.VISIBLE);
        }

        energyIntervalId.setText(String.valueOf(energyOfferDateInterval.getEnergyOfferDateIntervalId()));
        ImageView deleteIcon = (ImageView) newRow.findViewById(R.id.delete);
        deleteIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.delete_line_icon));
        deleteIcon.setOnClickListener(v -> {
            LinearLayout deleteRow = (LinearLayout) v.getParent().getParent();
            dynamicTable.removeView(deleteRow);
            EditText intervalId = (EditText) deleteRow.findViewById(R.id.energyIntervalId);
            if (intervalId != null) {
                if (isPodChecked) {
                    intervalsIdForRemoving.add(Integer.valueOf(intervalId.getText().toString()));
                } else {
                    energyOfferDateIntervalDAO.deleteEnergyDateIntervallById(Integer.valueOf(intervalId.getText().toString()));
                }
                if (dynamicTable.getChildCount() == 1) {
                    kwh_intervali_3.setEnabled(true);
                    INTERVAL_TYPE = Constants.SIMPLE;
                    bt6.setClickable(true);
                    bt.setClickable(true);
                    mt.setClickable(true);
                    dynamicTable.setVisibility(View.GONE);
                }
                addPodValidation(false);
            }
        });
        checkForIntervalsType(energyOfferDateInterval);
        dynamicTable.addView(newRow);
        clearFieldAssociatedWithIntervals();
        bt6.setClickable(false);
        bt.setClickable(false);
        mt.setClickable(false);
    }

    private void drawPodRow(final EnergyOfferDetails details) {
        podLayout.setVisibility(View.VISIBLE);

        int annuo;
        int intervalAnnuo = 0;

        LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.energia_pod_table_row, null);
        final TextView podName = (TextView) row.findViewById(R.id.POD);
        TextView tensione = (TextView) row.findViewById(R.id.tensione);
        TextView potenza = (TextView) row.findViewById(R.id.potenza);
//        TextView tipoContatore = (TextView) row.findViewById(R.id.tipoContatore);
        TextView consumoAnnuo = (TextView) row.findViewById(R.id.consumoAnnuo);
        TextView intervalConsumoAnno = (TextView) row.findViewById(R.id.intervalloAnno);
        final TextView podId = (TextView) row.findViewById(R.id.podId);

        annuo = (details.getYearlyKwh() != null ? details.getYearlyKwh() : 0) + (details.getYearlyKwh2() != null ? details.getYearlyKwh2() : 0) + (details.getYearlyKwh3() != null ? details.getYearlyKwh3() : 0);

        final List<EnergyOfferDateInterval> energyOfferDateIntervals = energyOfferDateIntervalDAO.getOfferDateIntervalByDetailsId(details.getEnergyDetailOfferId());
        if (energyOfferDateIntervals.size() > 0) {
            for (EnergyOfferDateInterval dateInterval : energyOfferDateIntervals) {
                intervalAnnuo +=
                        (dateInterval.getKwh1() != Constants.NO_VALUE ? dateInterval.getKwh1() : 0)
                                + (dateInterval.getKwh2() != Constants.NO_VALUE ? dateInterval.getKwh2() : 0)
                                + (dateInterval.getKwh3() != Constants.NO_VALUE ? dateInterval.getKwh3() : 0);
            }
        }

        final String name = details.getPod().contains("POD") || details.getPod().length() == 0 ?
                "POD" + (podLayout.getChildCount() - existingClientId) : details.getPod();

        podName.setText(name);
        tensione.setText(AbstractJarUtil.getTariffTransporto(offer, details));
        potenza.setText(getPotenzaDescription(details, false));
//        tipoContatore.setText("");
        consumoAnnuo.setText(getString(R.string.energy_value_with_units, annuo));
        intervalConsumoAnno.setText(getString(R.string.energy_value_with_units, intervalAnnuo));
        podId.setText(String.valueOf(details.getEnergyDetailOfferId()));

        LinearLayout currentPod = (LinearLayout) row.findViewById(R.id.currentPod);
        LinearLayout deleteLayout = (LinearLayout) row.findViewById(R.id.deleteLayout);

        currentPod.setOnClickListener(v -> {
            if (!isPodChecked) {
                currentPodRow = (LinearLayout) v;
                currentPodRow.setBackgroundColor(ContextCompat.getColor(EnergeticiLaFattura.this, R.color.item_background_blue));
                deleteUnSavedPodInfo();
                energyOfferDetails = new EnergyOfferDetailsDAOImpl().getEnergyOfferDetailsById(Integer.valueOf(podId.getText().toString().trim()));
                addPodButtonsLayout.setVisibility(View.GONE);
                tensioneRadioGroup.setEnabled(false);
                setPodValuesToView();
            }
        });

        deleteLayout.setOnClickListener(v -> {
            if (!isPodChecked) {
                LinearLayout deleteRow = (LinearLayout) v.getParent().getParent();
                podLayout.removeView(deleteRow);
                if (!name.startsWith("POD")) existingClientId--;
                energyOfferDetailsDAO.deleteEnergyDetailslById(Integer.valueOf(podId.getText().toString().trim()));
                if (podLayout.getChildCount() == 1) {
                    podLayout.setVisibility(View.GONE);
                    tensioneRadioGroup.setEnabled(true);
                }
                cleanPodTable();
                drawPodTable();
                continuaButtonValidation();
            }
        });

        if (details.isExistingClientOffer()) {
            currentPod.setClickable(false);
            podName.setText(details.getPod());
            existingClientId++;
        } else {
            details.setPod(name);
        }

        isDomesticoEnergyOfferCheck(details);

        new EnergyOfferDetailsDAOImpl().update(details);
        podLayout.addView(row);
        deleteAllIntervalsFromTable();
        clearFieldAssociatedWithIntervals();
        clearPodFieldOnThePage();
    }

    private void isDomesticoEnergyOfferCheck(EnergyOfferDetails details) {
        if (details.getTipologiaContratto() == Constants.DOMESTICO) {
            isDomesticoOffer = true;
        }
    }

    private void checkForIntervalsType(EnergyOfferDateInterval details) {
        if (dynamicTable.getChildCount() == 1) {
            boolean isKwh3NotValid = details.getKwh3() == Constants.NO_VALUE;
            kwh_intervali_3.setEnabled(!isKwh3NotValid);
            INTERVAL_TYPE = isKwh3NotValid ? Constants.MONORARIO : Constants.TRIORARIO;
        }
    }

    private void setPodValuesToView() {
        List<EnergyOfferDateInterval> energyOfferDateIntervals = energyOfferDateIntervalDAO.getOfferDateIntervalByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
        if (energyOfferDateIntervals.size() > 0) {
            for (EnergyOfferDateInterval interval : energyOfferDateIntervals) {
                createTableRow(interval);
            }
        }

        if (energyOfferDetails != null) {
            if (energyOfferDetails.getTipologiaContratto() != null &&
                    ((domestico.isChecked() && energyOfferDetails.getTipologiaContratto() == Constants.DOMESTICO)
                            || (altriUsi.isChecked() && energyOfferDetails.getTipologiaContratto() == Constants.ALTRI_USI)
                            || (offer != null && offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR))) {
                preselectEnergyMeterData();
            }

            if (energyOfferDetails.getTipologiaContratto() != null) {
                domestico.setChecked(energyOfferDetails.getTipologiaContratto() == Constants.DOMESTICO);
                altriUsi.setChecked(energyOfferDetails.getTipologiaContratto() == Constants.ALTRI_USI);
            }

            kwh1.setText(String.valueOf(energyOfferDetails.getYearlyKwh() != null ? energyOfferDetails.getYearlyKwh() : ""));
            kwh2.setText(String.valueOf(energyOfferDetails.getYearlyKwh2() != null ? energyOfferDetails.getYearlyKwh2() : ""));
            kwh3.setText(String.valueOf(energyOfferDetails.getYearlyKwh3() != null ? energyOfferDetails.getYearlyKwh3() : ""));

            setCheckToTensioneGroup(energyOfferDetails.getTensione());

            for (int iter = 1; iter < tariffaSpinner.getCount(); iter++) {
                Tariffa tariffa = (Tariffa) tariffaSpinner.getItemAtPosition(iter);
                if (energyOfferDetails.getTariffaId() == tariffa.getTariffaId()) {
                    tariffaSpinner.setSelection(iter);
                    break;
                }
            }
            pod.setText(energyOfferDetails.getPod());
        }

        modificaPODLayout.setVisibility(View.VISIBLE);
        podLayout.setClickable(false);
        isPodChecked = true;
    }

    private void preselectEnergyMeterData() {
        if (energyOfferDetails != null) {
            for (int iter = 1; iter < potenzaContatore.getCount(); iter++) {
                EnergyMeter energyMeter = (EnergyMeter) potenzaContatore.getItemAtPosition(iter);
                if (energyOfferDetails.getEnergyMeter() == energyMeter.getEnergyMeterId()) {
                    potenzaContatore.setSelection(iter);
                    break;
                }
            }
        }
    }

    private void setCheckToTensioneGroup(int tensioneid) {
        switch (tensioneid) {
            case 1:
                bt.setChecked(true);
                break;
            case 2:
                bt6.setChecked(true);
                break;
            case 3:
                mt.setChecked(true);
                break;
        }
    }

    private void clearPodFieldOnThePage() {
        String defaultStr = "";
        potenzaContatore.setSelection(0);
        tariffaSpinner.setSelection(0);
        kwh1.setText(defaultStr);
        kwh2.setText(defaultStr);
        kwh3.setText(defaultStr);
    }

    private void addPod(boolean isMofificaMod, boolean isAnimated) {
        if (addPodValidation(isAnimated)) {
            setEnergyOfferToBaseIfItNeeded();
            setEnergyOfferDetailsIfItNeeded();

            if (annuoPartValidationForBT(false))
                annuoConsumoCalcola(isMofificaMod);
            if ((intervalPartValidationBT(false) |
                    (!isIntervalTableHasEnoughChild(false, 1))) && !annuoPartValidationForBT(false))
                intervalConsumoCalcola(isMofificaMod);
            bt.setClickable(true);
            bt6.setClickable(true);
            mt.setClickable(true);
            kwh_intervali_3.setEnabled(true);
        }
    }

    private void deleteUnSavedPodInfo() {
        energyOfferDateIntervalDAO.deleteEnergyDateIntervallByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
        energyOfferDetailsDAO.delete(energyOfferDetails);
    }

//    private int getIntervaliKwh3Sum() {
//        int result = 0;
//        List<EnergyOfferDateInterval> energyOfferDateIntervals = energyOfferDateIntervalDAO.getOfferDateIntervalByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
//        for (EnergyOfferDateInterval energyOfferDateInterval : energyOfferDateIntervals) {
//            result += energyOfferDateInterval.getKwh3();
//        }
//        return result;
//    }

//    private boolean checkForValidAndNotZero(boolean isAnimated, EditText editText) {
//        return isValid(isAnimated, editText.getId()) && !editText.getText().toString().trim().equals("0");
//    }

    public void startNextPage(Class clas) {
        deleteUnSavedPodInfo();
        offer.setEnergyOfferId(energyOffer.getEnergyOfferId());
        new OfferDAOImpl().update(offer);
        MyApplication.set("offerEntity", offer);
        MyApplication.set("energyOfferEntity", energyOffer);
        if (clas != null) {
            startChildActivity(clas);
        }
    }

//    private void specificComputation(final boolean isModificaMod) {
//        isSpecificMode = true;
//        boolean isAnnuoKwh3Present = isValid(false, R.id.annuoKwh3) && !kwh3.getText().toString().trim().equals("0");
//        Integer kwh1 = isValid(false, R.id.annuoKwh1) ? Integer.valueOf(editTextGetTextString(R.id.annuoKwh1)) : null;
//        Integer kwh2 = isValid(false, R.id.annuoKwh2) ? Integer.valueOf(editTextGetTextString(R.id.annuoKwh2)) : null;
//        Integer kwh3 = isAnnuoKwh3Present ? Integer.valueOf(editTextGetTextString(R.id.annuoKwh3)) : null;
////        if (dynamicTable.getChildCount() == 1) saveIntervalData(false);
//
//        energyOfferDetails.setEnergyMeter(getEnergyMeterId());
//        energyOfferDetails.setClientType(0);
//        energyOfferDetails.setTensione(tensioneId);
//        energyOfferDetails.setYearlyKwh(kwh1);
//        energyOfferDetails.setYearlyKwh2(kwh2);
//        energyOfferDetails.setYearlyKwh3(kwh3);
//        energyOfferDetails.setTipologiaContratto(getTipoContratto());
//        energyOfferDetails.setTariffaId(tensioneId == 3 ? ((Tariffa) tariffaSpinner.getSelectedItem()).getTariffaId() : 0);
//        energyOfferDetailsDAO.update(energyOfferDetails);
//        if (!isModificaMod) {
//            drawPodRow(energyOfferDetails);
//        } else {
//            cleanPodTable();
//            drawPodTable();
//        }
//        clearPodFieldOnThePage();
//    }

    private void annuoConsumoCalcola(boolean isMofificaMod) {
        Integer kwh1 = isValid(false, R.id.annuoKwh1) ? Integer.valueOf(editTextGetTextString(R.id.annuoKwh1)) : null;
        Integer kwh2 = isValid(false, R.id.annuoKwh2) ? Integer.valueOf(editTextGetTextString(R.id.annuoKwh2)) : null;
        Integer kwh3 = isValid(false, R.id.annuoKwh3) ? Integer.valueOf(editTextGetTextString(R.id.annuoKwh3)) : null;
        if (dynamicTable.getChildCount() == 1) saveIntervalData(false);
        energyOfferDetails.setYearlyKwh(kwh1);
        energyOfferDetails.setYearlyKwh2(kwh2);
        energyOfferDetails.setYearlyKwh3(kwh3);
        int yearlyConsumption = (kwh1 != null ? kwh1 : 0) + (kwh2 != null ? kwh2 : 0) + (kwh3 != null ? kwh3 : 0);
        energyOfferDetails.setPod(editTextGetTextString(pod.getId()));
        energyOfferDetails.setComputedYearlyConsumption(yearlyConsumption);
        callRemoteServiceForConsumption(isMofificaMod);
//        if (dynamicTable.getChildCount() == 1) saveIntervalData(false);
    }

    private void callRemoteServiceForConsumption(final boolean isMofificaMod) {
//        Map<String, BaseEntity> entityMap = new HashMap<>();
//        entityMap.put(EnergyAdditionalWebServices.OFFER, offer);
//        entityMap.put(EnergyAdditionalWebServices.ENERGY_OFFER_DETAILS, energyOfferDetails);
        EnergyAdditionalWebServices webService = new EnergyAdditionalWebServices(EnergeticiLaFattura.this, new AbstractService.OnTaskCompleted<EnergyAdditionalWebServices>() {
            @Override
            public void onTaskCompleted(EnergyAdditionalWebServices service) {
                Map<String, Long> resultMap = service.getResult();
                energyOfferDetails.setCountMese(resultMap.get(EnergyAdditionalWebServices.COUNT_MESE) != null ? resultMap.get(EnergyAdditionalWebServices.COUNT_MESE).intValue() : 0);
                energyOfferDetails.setCountPod(resultMap.get(EnergyAdditionalWebServices.COUNT_POD) != null ? resultMap.get(EnergyAdditionalWebServices.COUNT_POD).intValue() : 0);
                energyOfferDetails.setSumAttiva(resultMap.get(EnergyAdditionalWebServices.SUM_ATTIVA) != null ? resultMap.get(EnergyAdditionalWebServices.SUM_ATTIVA).intValue() : 0.00);
                energyOfferDetails.setRemoteServiceStatus(!resultMap.isEmpty());
                energyOfferDetails.setTariffaId(tensioneId == 3 ? ((Tariffa) tariffaSpinner.getSelectedItem()).getTariffaId() : 0);
                energyOfferDetails.setEnergyMeter(getEnergyMeterId());
                energyOfferDetails.setClientType(0);
                energyOfferDetails.setConsumptioClassTypeId(0);
                energyOfferDetails.setTensione(tensioneId);
                energyOfferDetails.setTipologiaContratto(getTipoContratto());
                energyOfferDetailsDAO.update(energyOfferDetails);
                //showConfirmationDialog();
                if (!isMofificaMod) {
                    drawPodRow(energyOfferDetails);
                } else {
                    cleanPodTable();
                    drawPodTable();
                }
                energyOfferDetails = new EnergyOfferDetails();
                clearPodFieldOnThePage();
            }
        });
        webService.setOffer(offer);
        webService.setEnergyOfferDetails(energyOfferDetails);
        webService.execute();
    }

//    private void showConfirmationDialog() {
//        initializeConfirmationDialog();
//        if (!energyOfferDetails.isRemoteServiceStatus()) {
//            confirmationDialog.showDialog();
//        }
//    }

//    private void initializeConfirmationDialog() {
//        if (confirmationDialog == null) {
//            confirmationDialog = new ConfirmationDialog(this, getString(R.string.remote_service_confirmation_dialog_text));
//        }
//    }

    private void initializeConsumerConfirmationDialog() {
        if (energyConsumerDomesticoDialog == null) {
            energyConsumerDomesticoDialog = new ConfirmationDialog(this, getString(R.string.consumer_domestico_residenziale_popup_text));
        }
    }

    private void deleteAllIntervalsFromTable() {
        for (int i = dynamicTable.getChildCount() - 1; i > 0; i--) {
            dynamicTable.removeViewAt(i);
        }
        dynamicTable.setVisibility(View.GONE);
        INTERVAL_TYPE = Constants.SIMPLE;
        kwh_intervali_3.setEnabled(true);
    }

    private void intervalConsumoCalcola(final boolean isModificaMod) {
        if (dynamicTable.getChildCount() == 1) saveIntervalData(false);
        energyOfferDetails.setYearlyKwh(isValid(false, kwh1.getId()) ? Integer.valueOf(kwh1.getText().toString().trim()) : null);
        energyOfferDetails.setYearlyKwh2(isValid(false, kwh2.getId()) ? Integer.valueOf(kwh2.getText().toString().trim()) : null);
        energyOfferDetails.setYearlyKwh3(isValid(false, kwh3.getId()) ? Integer.valueOf(kwh3.getText().toString().trim()) : null);
        energyOfferDetails.setPod(editTextGetTextString(pod.getId()));
        callRemoteServiceForConsumption(isModificaMod);
//        energyOfferDetails.setTariffaId(tensioneId == 3 ? ((Tariffa) tariffaSpinner.getSelectedItem()).getTariffaId() : 0);
//        energyOfferDetails.setTensione(tensioneId);
//        energyOfferDetails.setEnergyMeter(getEnergyMeterId());
//        energyOfferDetails.setTipologiaContratto(getTipoContratto());
//        energyOfferDetailsDAO.update(energyOfferDetails);
//        clearPodFieldOnThePage();
//        if (!isModificaMod) {
//            drawPodRow(energyOfferDetails);
//        } else {
//            cleanPodTable();
//            drawPodTable();
//        }
//        specificModFieldNumber = 0;
//        energyOfferDetails = new EnergyOfferDetails();
    }

//    private Object[] getCheckedQueryResult(List<Object[]> resultList) {
//        return resultList.size() != 0 ? resultList.get(0) : new Object[]{new BigDecimal(0.00), 0, new BigDecimal(0.00)};
//    }

    private void checkForPodTypeChanged() {
        List<EnergyOfferDateInterval> energyOfferDateIntervals = energyOfferDateIntervalDAO.getOfferDateIntervalByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
        if (energyOfferDateIntervals.size() > 0 && energyOfferDateIntervals.get(0).getDateFrom() == null) {
            energyOfferDateIntervalDAO.deleteEnergyDateIntervallByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
        }
    }

    private void cleanPodTable() {
        for (int i = podLayout.getChildCount() - 1; i > 0; i--) {
            podLayout.removeViewAt(i);
        }
    }

    private void clearFieldAssociatedWithIntervals() {
        kwh_intervali_1.setText(Constants.EMPTY_STRING);
        kwh_intervali_2.setText(Constants.EMPTY_STRING);
        kwh_intervali_3.setText(Constants.EMPTY_STRING);
        potenzaContatoreManual.setText(Constants.EMPTY_STRING);
        cleanDateFields();
    }

    public void changeDateFrom(View v) {
        getDatePickerDialog(onDateFromSetListener, periodoDaCalendar).show();
    }

    public void changeDateTo(View view) {
        getDatePickerDialog(onDateToSetListener, periodoACalendar).show();
    }

    private DatePickerDialog getDatePickerDialog(DatePickerDialog.OnDateSetListener listener, Calendar calendar) {
        if (tensioneId != BT) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        if (tensioneId != BT) {
            try {
                DatePicker datePicker = datePickerDialog.getDatePicker();
                for (Field datePickerField : (datePicker).getClass().getDeclaredFields()) {
                    if (datePickerField.getName().equals("mDaySpinner")) {
                        datePickerField.setAccessible(true);
                        Object dayPicker = datePickerField.get(datePicker);
                        ((View) dayPicker).setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return datePickerDialog;
    }

    private void saveIntervalData(boolean isAnimated) {
        if (intervalValidation(isAnimated)) {
            checkForPodTypeChanged();
            setEnergyOfferToBaseIfItNeeded();
            setEnergyOfferDetailsIfItNeeded();
            String dateAInvisible = TextViewGetText(R.id.elettricitaIntervalTo);
//            if (new EnergyOfferDateIntervalDAOImpl().isMonthAlreadyExists(energyOfferDetails.getEnergyDetailOfferId(), parseDate(dateAInvisible))) {
//                Toast.makeText(this, "interval already exists", Toast.LENGTH_SHORT).show();
//                return;
//            }
            String dateDa = TextViewGetText(R.id.elettricitaIntervalFrom);
            int kwh1 = Integer.valueOf(editTextGetTextString(R.id.kwh1_intervali));
            int kwh2 = editTextGetTextString(R.id.kwh2_intervali).length() != 0 ? Integer.valueOf(editTextGetTextString(R.id.kwh2_intervali)) : Constants.NO_VALUE;
            int kwh3 = editTextGetTextString(R.id.kwh3_intervali).length() != 0 ? Integer.valueOf(editTextGetTextString(R.id.kwh3_intervali)) : Constants.NO_VALUE;
            int potenzaImpegnata = isValid(false, potenzaContatoreManual.getId()) ? Integer.valueOf(editTextGetTextString(potenzaContatoreManual.getId())) : 0;
            EnergyOfferDateInterval energyOfferDateInterval = new EnergyOfferDateInterval();
            energyOfferDateInterval.setIdEnergyDetailOffer(energyOfferDetails.getEnergyDetailOfferId());
            energyOfferDateInterval.setDateFrom(dateDa);
            energyOfferDateInterval.setDateTo(dateAInvisible);
            energyOfferDateInterval.setKwh1(kwh1);
            energyOfferDateInterval.setKwh2(kwh2);
            energyOfferDateInterval.setKwh3(kwh3);
            energyOfferDateInterval.setPotenzaImpegnata(potenzaImpegnata);
//            specificModFieldNumber = editTextGetTextString(R.id.kwh3_intervali).length() != 0 ? 3 : 1;
            if (energyOfferDateIntervalDAO.insert(energyOfferDateInterval)) {
                if (isPodChecked) {
                    intervalsIdForAdding.add(energyOfferDateInterval.getEnergyOfferDateIntervalId());
                }
                createTableRow(energyOfferDateInterval);
            } else {
                Toast.makeText(this, getString(R.string.interval_save_error_message), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean intervalValidation(boolean isAnimated) {
        return ((INTERVAL_TYPE == Constants.SIMPLE && validationWithoutIntervalType(isAnimated))
                || (INTERVAL_TYPE == Constants.MONORARIO && validationIntervalTypeMonorario(isAnimated))
                || (INTERVAL_TYPE == Constants.TRIORARIO && validationIntervalTypeTriorario(isAnimated)))
                && (periodoDa.getText().toString().trim().length() > 0 & periodoA.getText().toString().trim().length() > 0)
                && isIntervalDateRangeValid()
                && ((tensioneId == 1 || isValid(true, potenzaContatoreManual.getId())) && (!mt.isChecked() || checkSpinnerSelection(true, tariffaSpinner.getId())));
    }

    private boolean validationWithoutIntervalType(boolean isAnimated) {
        return (isValid(isAnimated, kwh_intervali_1.getId()) && !isValid(false, kwh_intervali_2.getId()) && !isValid(false, kwh_intervali_3.getId()))
                || (isValid(isAnimated, kwh_intervali_1.getId()) && isValid(isAnimated, kwh_intervali_2.getId()) && !isValid(false, kwh_intervali_3.getId()))
                || (isValid(isAnimated, kwh_intervali_1.getId(), kwh_intervali_2.getId(), kwh_intervali_3.getId()));
    }

    private boolean validationIntervalTypeTriorario(boolean isAnimated) {
        return (isValid(isAnimated, kwh_intervali_1.getId(), kwh_intervali_2.getId(), kwh_intervali_3.getId()));
    }

    private boolean validationIntervalTypeMonorario(boolean isAnimated) {
        return (isValid(isAnimated, kwh_intervali_1.getId()) && !isValid(false, kwh_intervali_2.getId()) && !isValid(false, kwh_intervali_3.getId()))
                || (isValid(isAnimated, kwh_intervali_1.getId()) && isValid(isAnimated, kwh_intervali_2.getId()) && !isValid(false, kwh_intervali_3.getId()));
    }

//    private boolean addIntervalValidation(boolean isAnimated) {
//        return tensioneId == BT ? (checkDateValue() && checkIntervalInSpecificMod(isAnimated, true)) :
//                intervalPartValidationBTA6_MT(isAnimated);
//    }

//    private boolean checkIntervalInSpecificMod(boolean isAnimated, boolean isToastShown) {
//        return ((!isSpecificMode & intervalPartValidationBT(isAnimated)) |
//                (isSpecificMode & periodoDa.getText().toString().trim().length() > 0 & periodoA.getText().toString().trim().length() > 0 &
//                        ((specificModFieldNumber == 1 & isValid(isAnimated, kwh_intervali_1.getId()) & !isValid(isAnimated, kwh_intervali_2.getId()) & !isValid(isAnimated, kwh_intervali_3.getId())) |
//                                (specificModFieldNumber == 0 & ((isValid(isAnimated, kwh_intervali_1.getId()) & !isValid(isAnimated, kwh_intervali_2.getId()) & !isValid(isAnimated, kwh_intervali_3.getId())) |
//                                        isValid(isAnimated, kwh_intervali_1.getId(), kwh_intervali_2.getId(), kwh_intervali_3.getId()))) |
//                                (specificModFieldNumber == 3 && isValid(isAnimated, kwh_intervali_1.getId(), kwh_intervali_2.getId(), kwh_intervali_3.getId()))
//                        ))) && (tensioneId == 1 || isValid(true, potenzaContatoreManual.getId())) && (!mt.isChecked() || checkSpinnerSelection(true, tariffaSpinner.getId()));
//    }

    private boolean isIntervalDateRangeValid() {
        boolean result = true;
        try {
            Date dateFrom = parseDate(periodoDa.getText().toString().trim());
            Date dateTo = parseDate(periodoA.getText().toString().trim());
            if (dateFrom.before(dateTo)) {
                List<EnergyOfferDateInterval> dateIntervals = energyOfferDateIntervalDAO.getOfferDateIntervalByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
                for (EnergyOfferDateInterval interval : dateIntervals) {
                    if ((interval.getDateFrom().getTime() <= dateTo.getTime()) &&
                            (interval.getDateTo().getTime() >= dateFrom.getTime()) &&
                            !(intervalsIdForRemoving.contains(interval.getEnergyOfferDateIntervalId()))) {
                        Toast.makeText(this, "l'intervallo di tempo che si sta inserendo contiene date già previste in un precedente intervallo", Toast.LENGTH_LONG).show();
                        result = false;
                        break;
                    }
                }
            } else {
                Toast.makeText(this, "data Da deve essere inferiore alla data della A", Toast.LENGTH_LONG).show();
                result = false;
            }
        } catch (Throwable throwable) {
            Log.e(this.getClass().getSimpleName(), "can't parse date", throwable);
            result = false;
        }
        return result;
    }

//    private boolean checkDateValue() {
//        boolean result;
//        try {
//            Date dateFrom = periodoDaCalendar.getTime();
//            Date dateTo = periodoACalendar.getTime();
//            if (dateFrom.before(dateTo)) {
//                result = true;
//            } else {
//                Toast.makeText(this, "data Da deve essere inferiore alla data della A", Toast.LENGTH_LONG).show();
//                result = false;
//            }
//        } catch (Throwable throwable) {
//            Log.e(this.getClass().getSimpleName(), "can't parse date", throwable);
//            result = false;
//        }
//        return result;
//    }

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

    private int getEnergyMeterId() {
        int energyMeterId = 0;
        switch (tensioneId) {
            case BT:
                Spinner potensaContatore = (Spinner) findViewById(R.id.potensaContatoreSpinner4);
                energyMeterId = tensioneId == 1 ? ((EnergyMeter) potensaContatore.getSelectedItem()).getEnergyMeterId() : 0;
                break;
            case BTA6:
                energyMeterId = new EnergyMeterDAOImpl().getEnergiMeterByTariffOption("BTA6").getEnergyMeterId();
                break;
            case MT:
                Tariffa tariffa = new TariffaDAOImpl().getTariffaById(((Tariffa) tariffaSpinner.getSelectedItem()).getTariffaId());
                energyMeterId = new EnergyMeterDAOImpl().getEnergiMeterByTariffOption(tariffa.getTariffaDesc()).getEnergyMeterId();
        }
        return energyMeterId;
    }

    private boolean addPodValidation(boolean isAnimated) {
        boolean result = validateFields(isAnimated);
        if (result) {
            changeViewVisibility(R.id.addPodDisabled, R.id.addPodEnabled);
        } else {
            changeViewVisibility(R.id.addPodEnabled, R.id.addPodDisabled);
        }
        return result;
    }

    private boolean validateFields(boolean isAnimated) {
        boolean validationResult;
        switch (tensioneId) {
            case BTA6:
                validationResult = (annuoPartValidationForBTA6_MT(false) &&
                        !isIntervalTableHasEnoughChild(false, BTA6_INTERVALS_LIMIT)) &&
                        validateInputByColor(isAnimated, pod);
                break;
            case MT:
                validationResult = ((annuoPartValidationForBTA6_MT(false) &&
                        !isIntervalTableHasEnoughChild(false, MT_INTERVALS_LIMIT)) &&
                        checkSpinnerSelection(false, tariffaSpinner.getId()) &&
                        validateInputByColor(isAnimated, pod));
                break;
            default:
                boolean isAnnuoValid = annuoPartValidationForBT(false);
                boolean isIntervalsValid = (!isIntervalTableHasEnoughChild(false, 1) || intervalPartValidationBT(false));
//                boolean isAnimationNeeded = !isAnnuoValid && !isIntervalsValid && isAnimated;
                validationResult = (isIntervalsValid || isAnnuoValid) && isPotensaContatoreChoosen(isAnimated) && validateInputByColor(isAnimated, pod);
//                boolean fakeValdidationForAnimating = (annuoPartValidationForBT(isAnimationNeeded) || (intervalPartValidationBT(isAnimationNeeded) | !isIntervalTableHasEnoughChild(false, 1)))
//                        && isPotensaContatoreChoosen(isAnimationNeeded) && validateInputByColor(isAnimationNeeded, pod);
                break;
        }
        return validationResult;
    }

    private void continuaButtonValidation() {
        makeContinuaButtonsEnabled(podLayout.getChildCount() > 1 && modificaPODLayout.getVisibility() == View.GONE);
    }

    private boolean intervalPartValidationBT(boolean isAnimated) {
        return (((isValid(isAnimated, R.id.kwh1_intervali, R.id.kwh2_intervali, R.id.kwh3_intervali)) ||
                (isValid(isAnimated, R.id.kwh1_intervali) && !isValid(isAnimated, R.id.kwh2_intervali) && !isValid(isAnimated, R.id.kwh3_intervali)) ||
                (isValid(isAnimated, R.id.kwh1_intervali) && isValid(isAnimated, R.id.kwh2_intervali) && !isValid(isAnimated, R.id.kwh3_intervali)))) &&
                periodoDa.getText().toString().trim().length() != 0 & periodoA.getText().toString().trim().length() != 0;
    }

//    private boolean intervalPartValidationBT(boolean isAnimated) {
//        return ((isValid(isAnimated, R.id.kwh1_intervali) && !isValid(isAnimated, R.id.kwh2_intervali) && !isValid(isAnimated, R.id.kwh3_intervali)) ||
//                ((tipoContatore == 2 && isValid(isAnimated, R.id.kwh1_intervali, R.id.kwh2_intervali, R.id.kwh3_intervali)) ||
//                        (tipoContatore == 2 && isValid(isAnimated, R.id.kwh1_intervali) && !isValid(isAnimated, R.id.kwh2_intervali) && !isValid(isAnimated, R.id.kwh3_intervali)))) &&
//                periodoDa.getText().toString().trim().length() != 0;
//    }

//    private boolean intervalPartValidationBTA6_MT(boolean isAnimated) {
//        return ((isValid(isAnimated, R.id.kwh1_intervali) && !isValid(isAnimated, R.id.kwh2_intervali) && !isValid(isAnimated, R.id.kwh3_intervali)) ||
//                ((isValid(isAnimated, R.id.kwh1_intervali, R.id.kwh2_intervali, R.id.kwh3_intervali)) ||
//                        (isValid(isAnimated, R.id.kwh1_intervali) && !isValid(isAnimated, R.id.kwh2_intervali) && !isValid(isAnimated, R.id.kwh3_intervali)) ||
//                        (isValid(isAnimated, R.id.kwh1_intervali) && isValid(isAnimated, R.id.kwh2_intervali) && !isValid(isAnimated, R.id.kwh3_intervali)))) &&
//                periodoDa.getText().toString().trim().length() != 0 && periodoA.getText().toString().trim().length() != 0 && isValid(isAnimated, potenzaContatoreManual.getId());
//    }

//    private boolean intervalPartValidationBTA6_MT(boolean isAnimated) {
//        return ((tipoContatore == 1 && isValid(isAnimated, R.id.kwh1_intervali) && !isValid(isAnimated, R.id.kwh2_intervali) && !isValid(isAnimated, R.id.kwh3_intervali)) ||
//                ((tipoContatore == 2 && isValid(isAnimated, R.id.kwh1_intervali, R.id.kwh2_intervali, R.id.kwh3_intervali)))) &&
//                periodoDa.getText().toString().trim().length() != 0 && isValid(isAnimated, potenzaContatoreManual.getId());
//    }

//    private boolean annuoPartValidationForBT(boolean isAnimated) {
//        return (isValid(isAnimated, R.id.annuoKwh1) && !isValid(isAnimated, R.id.annuoKwh2) && !isValid(isAnimated, R.id.annuoKwh3)) ||
//                (tipoContatore == 2 && (isValid(isAnimated, R.id.annuoKwh1, R.id.annuoKwh2) && !isValid(isAnimated, R.id.annuoKwh3)) ||
//                        (isValid(isAnimated, R.id.annuoKwh1, R.id.annuoKwh2, R.id.annuoKwh3)));
//

    private boolean annuoPartValidationForBT(boolean isAnimated) {
        return (isValid(isAnimated, R.id.annuoKwh1) && !isValid(isAnimated, R.id.annuoKwh2) && !isValid(isAnimated, R.id.annuoKwh3)) ||
                ((isValid(isAnimated, R.id.annuoKwh1, R.id.annuoKwh2) && !isValid(isAnimated, R.id.annuoKwh3)) ||
                        (isValid(isAnimated, R.id.annuoKwh1, R.id.annuoKwh2, R.id.annuoKwh3)));
    }

//    private boolean annuoPartValidationForBTA6_MT(boolean isAnimated) {
//        return (tipoContatore == 1 && isValid(isAnimated, R.id.annuoKwh1) && !isValid(isAnimated, R.id.annuoKwh2) && !isValid(isAnimated, R.id.annuoKwh3)) ||
//                (tipoContatore == 2 && (isValid(isAnimated, R.id.annuoKwh1, R.id.annuoKwh2, R.id.annuoKwh3))) ||
//                (!isValid(false, R.id.annuoKwh1) && !isValid(false, R.id.annuoKwh2) && !isValid(false, R.id.annuoKwh3));
//    }

    private boolean annuoPartValidationForBTA6_MT(boolean isAnimated) {
        return (isValid(isAnimated, R.id.annuoKwh1) && !isValid(isAnimated, R.id.annuoKwh2) && !isValid(isAnimated, R.id.annuoKwh3)) ||
                (((isValid(isAnimated, R.id.annuoKwh1, R.id.annuoKwh2, R.id.annuoKwh3)) ||
                        (isValid(isAnimated, R.id.annuoKwh1) && !isValid(isAnimated, R.id.annuoKwh2) && !isValid(isAnimated, R.id.annuoKwh3)) ||
                        (isValid(isAnimated, R.id.annuoKwh1) && isValid(isAnimated, R.id.annuoKwh2) && !isValid(isAnimated, R.id.annuoKwh3)))) ||
                (!isValid(false, R.id.annuoKwh1) && !isValid(false, R.id.annuoKwh2) && !isValid(false, R.id.annuoKwh3));
    }

//    private boolean annuoPartValidationForBTA6_MT(boolean isAnimated) {
//        return (tipoContatore == 1 && isValid(isAnimated, R.id.annuoKwh1) && !isValid(isAnimated, R.id.annuoKwh2) && !isValid(isAnimated, R.id.annuoKwh3)) ||
//                (tipoContatore == 2 && (isValid(isAnimated, R.id.annuoKwh1, R.id.annuoKwh2, R.id.annuoKwh3)));
//    }

//    private boolean specificComputationValidation(boolean isAnimated) {
//        return (tipoContatore == 2 & (isValid(isAnimated, kwh1.getId(), kwh2.getId()) | isValid(isAnimated, kwh1.getId())) && !isValid(isAnimated, kwh3.getId())) &
//                (((isValid(isAnimated, kwh_intervali_1.getId()) | (isValid(isAnimated, kwh_intervali_1.getId(), kwh_intervali_2.getId(), kwh_intervali_3.getId()))) & periodoDa.getText().toString().trim().length() != 0) | !isIntervalTableEmpty(isAnimated));
//    }

//    private void checkSpecificMod() {
//        isSpecificMode = specificComputationValidation(false);
//    }

    private boolean isIntervalTableHasEnoughChild(boolean isAnimated, int isMoreThan) {
        boolean flag = dynamicTable.getChildCount() < (isMoreThan + 1);
//        boolean flag = getDynamicTableContent().size() == 0;
        if (flag && isAnimated) {
            findViewById(R.id.AggiungiPlusButtonLayout).requestFocus();
            findViewById(R.id.AggiungiPlusButtonLayout).startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        }
        return flag;
    }

    private boolean isPotensaContatoreChoosen(boolean isAnimated) {
        boolean potensaPresente = potenzaContatore.getSelectedItemPosition() != 0;
        if (!potensaPresente && isAnimated) {
            findViewById(R.id.potensaContatoreSpinner4).requestFocus();
            findViewById(R.id.potensaContatoreSpinner4).startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        }
        return potensaPresente;
    }

    private void setEntityValues() {
        energyOffer = MyApplication.get("ArchivioOfferEntity", EnergyOffer.class);
        cleanPodTable();
        drawPodTable();
    }

    @Override
    public void run() {
//        if (Deamon.get("ArchivioOfferEntity", EnergyOffer.class) != null) {
//            setEntityValues();
//        } else {
//            energyOffer = new EnergyOffer();
//            checkForExistingClient();
//            drawPodTable();
//        }
    }

    private void checkForExistingClient() {
        if (offer != null && offer.getOldTlcOfferId() != 0) {
            changePodTableHeaders();
            ClientServices clientServices = new ClientServicesDAOImpl().
                    getClientServicesByClientId(offer.getOldTlcOfferId());
//            ClientEnergyOffers clientEnergyOffers = new ClientEnergyOffersDAOImpl().
//                    getClientEnergyOfferByClientId(clientServices.getEnergyServiceId() != null ? clientServices.getClientId() : 0);
            List<ClientDetailEnergyOffers> existingClientsPods = new ClientDetailEnergyOffersDAOImpl().
                    getClientDetailEnergyOffersById(clientServices.getEnergyServiceId() != null ? clientServices.getEnergyServiceId() : 0);
            if (existingClientsPods.size() > 0) {
                setEnergyOfferToBaseIfItNeeded();
                for (ClientDetailEnergyOffers clientDetailEnergyOffers : existingClientsPods) {
                    EnergyOfferDetails existingOffer = new EnergyOfferDetails();
                    existingOffer.setEnergyOfferId(energyOffer.getEnergyOfferId());
                    existingOffer.setYearlyKwh(clientDetailEnergyOffers.getKwh());
                    existingOffer.setYearlyKwh2(null);
                    existingOffer.setYearlyKwh3(null);
                    existingOffer.setYearlyConsumption(clientDetailEnergyOffers.getKwh());
                    existingOffer.setComputedYearlyConsumption(clientDetailEnergyOffers.getKwh());
                    existingOffer.setTensione(defineAlreadyClientsTensione(clientDetailEnergyOffers.getEnergyMeter()));
                    existingOffer.setExistingClientOffer(true);
                    existingOffer.setPod(clientDetailEnergyOffers.getPod());
                    existingOffer.setEnergyMeter(clientDetailEnergyOffers.getEnergyMeter());
                    existingOffer.setOfferCost(new BigDecimal(clientDetailEnergyOffers.getCost()));
                    existingOffer.setOfferCostIVA(new BigDecimal(clientDetailEnergyOffers.getCostIva()));
                    existingOffer.setCostVersion(clientDetailEnergyOffers.getVersion());
                    existingOffer.setPmc(clientDetailEnergyOffers.getPmc());
                    existingOffer.setPs(clientDetailEnergyOffers.getPs());
                    existingOffer.setPotenzaReal(clientDetailEnergyOffers.getPotenzaReal());
                    existingOffer.setCodicePmcPS(clientDetailEnergyOffers.getCodicePmcPS());
                    energyOfferDetailsDAO.insert(existingOffer);
                }
            }
        }
    }

    private int defineAlreadyClientsTensione(int energyMeterId) {
        int tensioneId;
        if (energyMeterId != 0) {
            tensioneId = energyMeterId < 7 ? Constants.BT :
                    energyMeterId == 7 ? Constants.BTA6 : Constants.MT;
        } else {
            tensioneId = 0;
        }
        return tensioneId;
    }

    private void changePodTableHeaders() {
        podLayout.getChildAt(0).findViewById(R.id.existingClientsHeader).setVisibility(View.VISIBLE);
        podLayout.getChildAt(0).findViewById(R.id.defaultHeaders).setVisibility(View.GONE);
    }

    private void checkRicalcolaButton() {
        if (MyApplication.get("modificaElettricitiLable", Boolean.class) != null) {
            findViewById(R.id.continuaLinearLayout4).setVisibility(View.GONE);
            findViewById(R.id.calcolaLinearLayout4).setVisibility(View.GONE);
            findViewById(R.id.ricalcolaElettricitiLayout).setVisibility(View.VISIBLE);
        } else {
            energyOffer = new EnergyOffer();
            checkForExistingClient();
            drawPodTable();
        }
    }

    private void drawPodTable() {
        isDomesticoOffer = false;
        if (energyOffer != null) {
            List<EnergyOfferDetails> energyOfferDetailses = energyOfferDetailsDAO.getEnergyOfferDetailsByEnergyOfferId(energyOffer.getEnergyOfferId());
            if (energyOfferDetailses.size() > 0) {
                existingClientId = 0;
                for (EnergyOfferDetails offerDetails : energyOfferDetailses) {
                    drawPodRow(offerDetails);
                }
            }
        }
        continuaButtonValidation();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        addPodValidation(false);
        continuaButtonValidation();
    }

    private int getTipoContratto() {
        return offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ? 0 : (domestico.isChecked() ? 2 : 1);
    }

    private void processingAnnulaAction() {
        annulaAction();
        intervalsIdForRemoving.clear();
        intervalsIdForAdding.clear();
        addPodButtonsLayout.setVisibility(View.VISIBLE);
        cleanPodTable();
        drawPodTable();
        isPodChecked = false;
        energyOfferDetails = new EnergyOfferDetails();
        bt.setClickable(true);
        bt6.setClickable(true);
        mt.setClickable(true);
    }
}