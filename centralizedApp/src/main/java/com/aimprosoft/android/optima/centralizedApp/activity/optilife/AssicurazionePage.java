package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.AssicurazioneOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.AssicurazioneOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.event.ConfirmationDialog;
import com.aimprosoft.android.optima.centralizedApp.util.DecimalDigitsInputFilter;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.AbstractJarUtil;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AssicurazionePage extends BaseActivity implements TextWatcher {

    private Offer offer = MyApplication.get("offerEntity", Offer.class);
    private ConfirmationDialog confirmationDialog;
    private static int CURRENT_PAGE_NUMBER = 7;

    private TextView dataAssicurativa;
    private EditText costAssicurativa;
    private EditText targa;
    private RelativeLayout premioAnnuoContainer;
    private LinearLayout assicurazioneTable;
    private LinearLayout assicurazioneAddButtonLayout;
    private LinearLayout assicurazioneModificaLayout;
    private boolean isDeviceRowSelected = false;
    private Pattern targaPattern = Pattern.compile("^(([a-zA-Z]{2}\\d{3}[a-zA-Z]{2})|(([a-zA-Z]{2}|roma)(\\d{5}|\\d{6})))$");

    private Calendar dataAsscurazioneCalendar;
    private ImageView addPodEnabled;
    private AssicurazioneOffer assicurazioneOffer = new AssicurazioneOffer();
    private AssicurazioneOfferDAOImpl assicurazioneOfferDAO = new AssicurazioneOfferDAOImpl();
    private DecimalFormat decimalFormat;
    private DecimalFormatSymbols fts = new DecimalFormatSymbols();

    private DatePickerDialog.OnDateSetListener onDateAssicuraizoneSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newYear, int monthOfYear, int dayOfMonth) {
            dataAsscurazioneCalendar.set(newYear, monthOfYear, dayOfMonth);
            updateDisplayDate(dataAssicurativa, dataAsscurazioneCalendar);
        }
    };
    private AssicurazioneOffer valuesFromPod;

    private boolean addPodValidation(boolean isAnimated) {
        boolean result = validateFields(isAnimated);
        if (result) {
            changeViewVisibility(R.id.addDisabled, R.id.addEnabled);
        } else {
            changeViewVisibility(R.id.addEnabled, R.id.addDisabled);
        }
        return result;
    }

    private boolean validateFields(boolean isAnimated) {
        return !TextUtils.isEmpty(dataAssicurativa.getText())
                && isValid(false, costAssicurativa.getId())
                && validateInputByColor(false, costAssicurativa)
                && validateInputByColor(false, targa);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ascurazione);
        initializeViews();
        addListeners();
        initDecimalFormater();
        checkContinuaControl();
        drawAssicurazioneTable();
        dataAsscurazioneCalendar = Calendar.getInstance();
    }

    private void checkContinuaControl() {
        Map navMap = MyApplication.get("navigationMap", Map.class);
        if ((navMap != null && navMap.containsKey(7))) {
            changeViewVisibility(R.id.deviceCalcolaLayout, R.id.continuaLinearLayout);
        } else {
            changeViewVisibility(R.id.continuaLinearLayout, R.id.deviceCalcolaLayout);
        }
    }

    private void initDecimalFormater() {
        fts.setDecimalSeparator('.');
        String PATTERN = "#####0.00";
        decimalFormat = new DecimalFormat(PATTERN, fts);
    }

    private void addListeners() {
        dataAssicurativa.setOnLongClickListener(view -> {
            dataAssicurativa.setText("");
            costAssicurativa.setText("");
            premioAnnuoContainer.setVisibility(View.GONE);
            return true;
        });

        dataAssicurativa.addTextChangedListener(this);
        costAssicurativa.addTextChangedListener(this);
        addPodEnabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAssicurazione();
            }
        });

        findViewById(R.id.modificaAssicurazioneButton).setOnClickListener(v -> {
            if (validateFields(true)) {
                saveAssicurazione();
                assicurazioneModificaLayout.setVisibility(View.GONE);
                assicurazioneAddButtonLayout.setVisibility(View.VISIBLE);
                isDeviceRowSelected = false;
//                continuaButtonValidation();
            }
        });

        findViewById(R.id.annullaAssicurazioneButton).setOnClickListener(v -> {
            clearFealds();
            assicurazioneAddButtonLayout.setVisibility(View.VISIBLE);
            assicurazioneModificaLayout.setVisibility(View.GONE);
            premioAnnuoContainer.setVisibility(View.GONE);
            drawAssicurazioneTable();
            isDeviceRowSelected = false;
            assicurazioneOffer = new AssicurazioneOffer();
        });

        targa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateTarga(editable.toString());
            }
        });


        InputFilter[] inputFilter = new InputFilter[]{new DecimalDigitsInputFilter(2)};
        costAssicurativa.setFilters(inputFilter);

        costAssicurativa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validationByColor(costAssicurativa, s, 2);
            }

            @Override
            public void afterTextChanged(Editable s) {
                addPodValidation(false);
            }
        });

        findViewById(R.id.calcolaAssicurazione).setOnClickListener(view -> startChildActivity(Canone.class));

        findViewById(R.id.continua).setOnClickListener(view -> isLastPage(CURRENT_PAGE_NUMBER));
    }

    private void saveAssicurazione() {
        if (offer != null && assicurazioneOffer != null) {
            assicurazioneOffer.setOfferId(offer.getOfferId());
            assicurazioneOffer.setAssicurazioneCost(Double.valueOf(costAssicurativa.getText().toString()));
            assicurazioneOffer.setAssicurazioneDate(dataAsscurazioneCalendar.getTime());
            assicurazioneOffer.setCostoGestioneIntegrata(AbstractJarUtil.COSTO_GESTIONE_INTEGRATA);
            assicurazioneOffer.setCostoGestioneIntegrataIva(AbstractJarUtil.COSTO_GESTIONE_INTEGRATA_IVA_INCLUSA);
            assicurazioneOffer.setTarga(targa.getText().toString());
            if (assicurazioneOffer.getAssicurazioneId() == 0) {
                assicurazioneOfferDAO.insert(assicurazioneOffer);
                drawAssicurazioneRow(assicurazioneOffer);
//                continuaButtonValidation();
            } else {
                assicurazioneOfferDAO.update(assicurazioneOffer);
                drawAssicurazioneTable();
            }
        }
        assicurazioneOffer = new AssicurazioneOffer();
        premioAnnuoContainer.setVisibility(View.GONE);
    }

    private void drawAssicurazioneTable() {
        if (offer != null) {
            removeAllTableContent(assicurazioneTable);
            List<AssicurazioneOffer> assicurazioneOffers = assicurazioneOfferDAO.getAssicurazioneByOfferId(offer.getOfferId());
            for (AssicurazioneOffer assicurazOffer : assicurazioneOffers) {
                drawAssicurazioneRow(assicurazOffer);
            }
        }
        validateFields(false);
//        continuaButtonValidation();
    }

    private void continuaButtonValidation() {
        boolean isEnabled = assicurazioneTable.getChildCount() > 1 && assicurazioneModificaLayout.getVisibility() == View.GONE;
        if (isEnabled) {
            changeViewVisibility(R.id.continuaDisabled, R.id.continua);
            changeViewVisibility(R.id.calcolaAssicurazioneDisabled, R.id.calcolaAssicurazione);
        } else {
            changeViewVisibility(R.id.continua, R.id.continuaDisabled);
            changeViewVisibility(R.id.calcolaAssicurazione, R.id.calcolaAssicurazioneDisabled);
        }
    }

    private void drawAssicurazioneRow(AssicurazioneOffer assicurazione) {
        assicurazioneTable.setVisibility(View.VISIBLE);
        LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.assicurazione_table_item, null);

        TextView polizza = (TextView) row.findViewById(R.id.polizzaAssicurativa);
        TextView date = (TextView) row.findViewById(R.id.dataScadenzaAttuale);
        TextView cost = (TextView) row.findViewById(R.id.premioAnnuoAssicurativa);
        final TextView id = (TextView) row.findViewById(R.id.assicurativaId);

        polizza.setText(assicurazione.getTarga());
        date.setText(simpleDateFormat.format(assicurazione.getAssicurazioneDate()));
        cost.setText(decimalFormat.format(assicurazione.getAssicurazioneCost()));
        id.setText(String.valueOf(assicurazione.getAssicurazioneId()));

        row.setOnClickListener(view -> {
            if (!isDeviceRowSelected) {
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.item_background_blue));
                isDeviceRowSelected = true;
                assicurazioneAddButtonLayout.setVisibility(View.GONE);
                assicurazioneModificaLayout.setVisibility(View.VISIBLE);
                assicurazioneTable.setClickable(false);
                assicurazioneOffer = assicurazioneOfferDAO.getAssicurazioneById(Integer.valueOf(id.getText().toString()));
                setValuesFromPod(assicurazioneOffer);
//                continuaButtonValidation();
            }
        });

        row.findViewById(R.id.deleteButtonLayout).setOnClickListener(view -> {
            if (!isDeviceRowSelected) {
                LinearLayout deleteRow = (LinearLayout) view.getParent().getParent();
                assicurazioneTable.removeView(deleteRow);
                assicurazioneOfferDAO.deleteAssicurazioneOfferById(Integer.valueOf(id.getText().toString()));
                if (assicurazioneTable.getChildCount() == 1) {
                    assicurazioneTable.setVisibility(View.GONE);
                }
                drawAssicurazioneTable();
//                continuaButtonValidation();
            }
        });
        assicurazioneTable.addView(row);

        validateFields(false);
        clearFealds();
    }

    private void clearFealds() {
        dataAssicurativa.setText("");
        dataAsscurazioneCalendar = Calendar.getInstance();
        costAssicurativa.setText("");
        targa.getText().clear();
    }

    private void initializeViews() {
        dataAssicurativa = (TextView) findViewById(R.id.assicurativaDate);
        costAssicurativa = (EditText) findViewById(R.id.premioAnnuoCost);
        targa = (EditText) findViewById(R.id.targa);
        premioAnnuoContainer = (RelativeLayout) findViewById(R.id.premioAnnuoContainer);
        addPodEnabled = (ImageView) findViewById(R.id.addEnabled);
        assicurazioneTable = (LinearLayout) findViewById(R.id.assicurazioneTable);
        assicurazioneAddButtonLayout = (LinearLayout) findViewById(R.id.addAssicurazioneLayout);
        assicurazioneModificaLayout = (LinearLayout) findViewById(R.id.modificaAssicurazioneLayout);

    }

    private void showConfirmationDialog() {
        if (confirmationDialog == null) {
            confirmationDialog = new ConfirmationDialog(this,
                    getString(R.string.assicurazione_confirmation_dialog_text));
        }
        confirmationDialog.setOnDialogDismissedListener(null);
        confirmationDialog.showDialog();
    }

    private void updateDisplayDate(TextView datePickerView, Calendar calendar) {
        if (datePickerView != null) {
            if (!checkDataScadenza(calendar)) {
                showConfirmationDialog();
            }
            premioAnnuoContainer.setVisibility(View.VISIBLE);
            datePickerView.setText(simpleDateFormat.format(calendar.getTime()));
        }
    }

    private boolean checkDataScadenza(Calendar calendar) {
        LocalDate selectedDate = new LocalDate(calendar.getTime());
        return selectedDate.isAfter(LocalDate.now())
                && Days.daysBetween(LocalDate.now(), selectedDate).getDays() >= 20;
    }

    public void changeDateFrom(View view) {
        getDatePickerDialog(onDateAssicuraizoneSetListener, dataAsscurazioneCalendar).show();
    }

    private DatePickerDialog getDatePickerDialog(DatePickerDialog.OnDateSetListener listener, Calendar calendar) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        return datePickerDialog;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        addPodValidation(false);
//        continuaButtonValidation();
    }

    public void setValuesFromPod(AssicurazioneOffer assicurazione) {
        assicurazioneOffer = assicurazione;
        dataAsscurazioneCalendar.setTime(assicurazione.getAssicurazioneDate());
        costAssicurativa.setText(decimalFormat.format(assicurazione.getAssicurazioneCost()));
        targa.setText(assicurazione.getTarga());
        updateDisplayDate(dataAssicurativa, dataAsscurazioneCalendar);
    }

    private boolean validateTarga(String value) {
        boolean result = false;
        if (targaPattern.matcher(value).matches()) {
            targa.setTextColor(getResources().getInteger(R.integer.text_black));
            result = true;
        } else {
            targa.setTextColor(getResources().getInteger(R.integer.text_red));
        }
        return result;
    }
}
