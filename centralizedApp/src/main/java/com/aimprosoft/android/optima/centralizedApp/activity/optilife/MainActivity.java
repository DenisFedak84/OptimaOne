package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.app.adapter.AutoCompleteTownAdapter;
import com.aimprosoft.android.optima.centralizedApp.app.adapter.MemorySafeAdapter;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TownDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TownsCapDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Agreements;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Clients;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Town;
import com.aimprosoft.android.optima.centralizedApp.event.AutoUpdateDialog;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.AgreementsService;
import com.aimprosoft.android.optima.centralizedApp.service.CheckForUpdatesService;
import com.aimprosoft.android.optima.centralizedApp.service.ComuneSelect;
import com.aimprosoft.android.optima.centralizedApp.service.CopyDataBaseFromAssets;
import com.aimprosoft.android.optima.centralizedApp.service.GetOffersForJsonService;
import com.aimprosoft.android.optima.centralizedApp.service.ReadDBVersionFromSQLiteFile;
import com.aimprosoft.android.optima.centralizedApp.util.CodiceFiscaleUtils;
import com.aimprosoft.android.optima.centralizedApp.util.LocalSharedPreferencesManager;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import flexjson.JSONSerializer;

public class MainActivity extends BaseActivity {
    private Offer offer = MyApplication.get("offerEntity", Offer.class);
    public final String PIVA_ADAPTER = "piva_adapter";
    public final String CLIENTS_ADAPTER = "clients_adapter";
    public final String TOWN_ADAPTER = "town_adapter";
    private final String CHAR_ALLOWED = "[A-Za-zÀÈÉÌÒÙ'][A-Za-zÀÈÉÌÒÙ' ]*";
    private final String LENGTH_16_CHARS = "[A-Za-zÀÈÉÌÒÙ'0-9]{16}";
    private Town town = null;
    private Town townDiNascita = null;
    private Clients clients = null;
    private AutoCompleteTextView ragioneSociale;
    private AutoCompleteTextView piva;
    private AutoCompleteTextView comuneAutoComplete;
    private EditText mail;
    private Spinner agreements;
    private EditText nome;
    private EditText cognome;
    private TextView dataDiNascita;
    private RadioGroup sessoRadioGroup;
    private RadioButton sessoFemale;
    private RadioButton nonResidenzialse;
    private AutoCompleteTextView comuneDiNascita;
    private AutoCompleteTextView comuneDiFornitura;
    private EditText indirizzoDiFornitura;
    private EditText cap;
    private EditText codiceFiscale;
    private RadioButton female;
    private RadioGroup tipologiaClienteRadioGroup;
    private RadioButton nonResidenziale;
    private RadioButton consumer;
    private RadioButton business;
    private CheckBox centroCommerciale;
    private RadioGroup configuratorTypeRadioGroup;
    private LinearLayout businessContainer;
    private LinearLayout consumerContainer;
    private Button generateButton;
    private Calendar calendar;

    private EditText cap_buesnes;
    private EditText indirizzoDiFornitura_buesnes;
    private Boolean isModifica;
    private String itusClientName;
    private String itusClientCognome;

    private Map<String, MemorySafeAdapter> autocompleteAdapterMap = new HashMap<>();

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newYear, int monthOfYear, int dayOfMonth) {
            calendar.set(newYear, monthOfYear, dayOfMonth);
            updateDisplayDate(calendar.getTime(), dataDiNascita);
            codiceFiscaleValidation(false, true);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout_optilife);

        initializeViews();
        restoreNomeFromItus();
        addListeners();

        isModifica = getIntent().getBooleanExtra(Constants.MODIFICA_ANAGRAFICA, false);
        if (!isModifica) {
            MyApplication.remove(null);
            offer = new Offer();
        } else {
            setValuesfromDb();
        }

        checkDbVersion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String idSoggetto = LocalSharedPreferencesManager.getInstance().getSharedPreferencesStringValue(
                this, LocalSharedPreferencesManager.ID_SOGGETTO);
        getMenuInflater().inflate(idSoggetto.equals(Constants.TEST_ACCOUNT_ID_SOGGETTO) ?
                R.menu.special_user_menu : R.menu.regular_menu, menu);
//      getMenuInflater().inflate(R.menu.special_user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (checkForLogout()) {
            switch (item.getItemId()) {
                case R.id.esci: {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }

                case R.id.impostazioni: {
                    startChildActivity(ConfigActivity.class);
                    break;
                }

                case R.id.autotest: {
                    startChildActivity(AutoTestActivity.class);
                    break;
                }
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForApplicationUpdate();
        if (!autocompleteAdapterMap.isEmpty()) {
            setUpAutocompleteFields();
        }
        checkForLogout();
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearMemory();
    }

    private void clearMemory() {
        for (MemorySafeAdapter arrayAdapter : autocompleteAdapterMap.values()) {
            arrayAdapter.clearAdapter();
        }
//        autocompleteAdapterMap.clear();
    }

    private void checkDbVersion() {
        if (MyApplication.getRemoteDbVersion() == null) {
            new ReadDBVersionFromSQLiteFile(this, new AbstractService.OnTaskCompleted<ReadDBVersionFromSQLiteFile>() {
                @Override
                public void onTaskCompleted(ReadDBVersionFromSQLiteFile service) {
                    MyApplication.setRemoteDbVersion(service.getResult());
                    int currentInstalledVersion = LocalSharedPreferencesManager.getInstance().getSharedPreferencesIntValue(MainActivity.this, LocalSharedPreferencesManager.CURRENT_DB_VERSION);
//                    Toast.makeText(MainActivity.this, "replacing db", Toast.LENGTH_SHORT).show();
                    if (service.getResult() > currentInstalledVersion) {
                        runReplaceDbFile(service.getResult());
                    } else {
                        setView();
                    }
                }
            }).execute();
        } else {
            setView();
        }
    }

    private void updateDisplayDate(Date date, TextView dataDiNascita) {
        dataDiNascita.setText(dateFormat.format(date));
    }

    public void changeDate(View v) {
        new DatePickerDialog(this, onDateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void runReplaceDbFile(final int version) {
        new CopyDataBaseFromAssets(MainActivity.this, new AbstractService.OnTaskCompleted<CopyDataBaseFromAssets>() {
            @Override
            public void onTaskCompleted(CopyDataBaseFromAssets service) {
                if (service.getResult()) {
                    LocalSharedPreferencesManager.getInstance().storeSharedPreferencesIntValue(MainActivity.this, LocalSharedPreferencesManager.CURRENT_DB_VERSION, version);
                } else {
                    Log.e(this.getClass().getSimpleName(), "error while setup databse");
                }
                setView();
            }
        }).execute(version);
    }

    private boolean businessValidation(boolean isAnimated) {
        boolean result = isValid(isAnimated, R.id.ragioneSocialeAutocomplete, R.id.comuneAutoCompleteTextView, R.id.pivaAutocomplete, cap_buesnes.getId())
                && checkTownSelection(town, comuneAutoComplete, isAnimated) && checkMail();
//      checkMail() && checkSpinnerSelection(true, agreements.getId()
        if (result) {
            changeViewVisibility(R.id.ricalcolaDisabled, R.id.ricalcolaEnabled);
        } else {
            changeViewVisibility(R.id.ricalcolaEnabled, R.id.ricalcolaDisabled);
        }
        return result;
    }

    private boolean consumerValidation(boolean isAnimated) {
        boolean result = checkTownSelection(town, comuneDiFornitura, isAnimated) &&
                isValid(isAnimated, codiceFiscale.getId()) &&
                codiceFiscale.getCurrentTextColor() == getResources().getInteger(R.integer.text_black) &&
                !TextUtils.isEmpty(codiceFiscale.getText()) &&
                isValid(isAnimated, cap.getId()) &&
                (findViewById(R.id.calcolaArchivio).getVisibility() == View.VISIBLE ?
                        codiceFiscaleValidationWithManualCFCheck(true) : codiceFiscaleValidation(true, false));
        if (result) {
            changeViewVisibility(R.id.ricalcolaDisabled, R.id.ricalcolaEnabled);
        } else {
            changeViewVisibility(R.id.ricalcolaEnabled, R.id.ricalcolaDisabled);
        }
        return result;
    }

    private void setUpAutocompleteFields() {
        new ComuneSelect(this, new AbstractService.OnTaskCompleted<ComuneSelect>() {
            @Override
            public void onTaskCompleted(ComuneSelect service) {
                if (!autocompleteAdapterMap.containsKey(TOWN_ADAPTER)) {
                    AutoCompleteTownAdapter townAdapter = new AutoCompleteTownAdapter(MainActivity.this,
                            android.R.layout.simple_dropdown_item_1line, service.getResult());
                    comuneAutoComplete.setAdapter(townAdapter);
                    comuneDiNascita.setAdapter(townAdapter);
                    comuneDiFornitura.setAdapter(townAdapter);
                    autocompleteAdapterMap.put(TOWN_ADAPTER, townAdapter);
                    comuneDiNascita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            townDiNascita = (Town) parent.getAdapter().getItem(position);
                            codiceFiscaleValidation(false, true);
                            validation(false);
                        }
                    });

                    comuneDiFornitura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            town = (Town) parent.getAdapter().getItem(position);
                            String townsCap = new TownsCapDAOImpl().getCapRelatedWithTownId(town.getTownId()).getCap();
                            if (townsCap != null) {
                                cap.setText(townsCap);
                            }
                            validation(false);
                        }
                    });
                } else {
                    ((AutoCompleteTownAdapter) autocompleteAdapterMap.get(TOWN_ADAPTER)).notifyDataSetUpdated(service.getResult());
                }
            }
        }).execute();
//
//        new ClientsDataService(this, new AbstractService.OnTaskCompleted<ClientsDataService>() {
//            @Override
//            public void onTaskCompleted(ClientsDataService service) {
//                if (!autocompleteAdapterMap.containsKey(CLIENTS_ADAPTER) || !autocompleteAdapterMap.containsKey(PIVA_ADAPTER)) {
//                    ClientsArrayAdapter clientsAdapter = new ClientsArrayAdapter(MainActivity.this,
//                            android.R.layout.simple_dropdown_item_1line, ragioneSociale.getId(),
//                            service.getResult());
//                    ragioneSociale.setAdapter(clientsAdapter);
//                    autocompleteAdapterMap.put(CLIENTS_ADAPTER, clientsAdapter);
//
//                    ClientsArrayAdapter pivaAdapter = new ClientsArrayAdapter(MainActivity.this,
//                            android.R.layout.simple_dropdown_item_1line, piva.getId(),
//                            service.getResult());
//                    piva.setAdapter(pivaAdapter);
//                    autocompleteAdapterMap.put(PIVA_ADAPTER, pivaAdapter);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (isModifica) {
//                                setValuesfromDb();
//                            } else {
//                                offer = new Offer();
//                            }
//                        }
//                    }, 50);
//                } else {
//                    ((ClientsArrayAdapter) autocompleteAdapterMap.get(CLIENTS_ADAPTER)).notifyDataSetUpdated(service.getResult());
//                    ((ClientsArrayAdapter) autocompleteAdapterMap.get(PIVA_ADAPTER)).notifyDataSetUpdated(service.getResult());
//                }
//            }
//        }).execute();
    }

    private void setView() {
        setUpAutocompleteFields();

        new AgreementsService(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                setSpinner(agreements, (List) service.getResult());
            }
        }).execute();

//        ragioneSociale.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                clients = (Clients) parent.getAdapter().getItem(position);
//                piva.setText(clients.getPiva());
//            }
//        });
//
//        piva.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                clients = (Clients) parent.getAdapter().getItem(position);
//                ragioneSociale.setText(clients.getName());
//            }
//        });

        comuneAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                town = (Town) parent.getAdapter().getItem(position);
                String townsCap = new TownsCapDAOImpl().getCapRelatedWithTownId(town.getTownId()).getCap();
                cap_buesnes.setText(townsCap);
                validation(false);
            }
        });

        findViewById(R.id.configuraOfferta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForLogout()) {
                    startNextPage();
                }
            }
        });

        findViewById(R.id.visualizzaArchivio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    List<Offer> offers = new OfferDAOImpl().getAllRows();
//                    GetOffersForJsonService service = new GetOffersForJsonService(MainActivity.this, new AbstractService.OnTaskCompleted() {
//                        @Override
//                        public void onTaskCompleted(AbstractService service) {
//                            Map<String, Object> result = (Map<String, Object>) service.getResult();
//                            String stringJson = new JSONSerializer().deepSerialize(result);
//                            String ss = stringJson + "";
//                            startChildActivity(Archivio.class);
//                        }
//                    });
//                    service.execute(offers.get(offers.size() - 1));
//                } catch (Exception e) {
//                    Toast.makeText(MainActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
//                }
                if (checkForLogout()) {
                    startChildActivity(Archivio.class);
                }
            }
        });
    }

    private void setValuesfromDb() {
        findViewById(R.id.configura_visualizza_btns).setVisibility(View.GONE);
        findViewById(R.id.check_busisnes_consumer).setVisibility(View.GONE);
        findViewById(R.id.calcolaArchivio).setVisibility(View.VISIBLE);
        TownDAOImpl townDAO = new TownDAOImpl();

        if (offer != null) {
            if (offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR) {
                consumer.setChecked(true);
                dataDiNascita.setText(offer.getBirthDate());
                centroCommerciale.setChecked(offer.isCentroCommerciale());

                town = townDAO.getTownById(offer.getTownId());
                townDiNascita = townDAO.getTownById(offer.getBirthDateTownId());

                comuneDiFornitura.setText(town.toString());
                comuneDiNascita.setText(townDiNascita != null ? townDiNascita.toString() : "");

                nome.setText(offer.getNome());
                cognome.setText(offer.getSurname());
                indirizzoDiFornitura.setText(offer.getIndirizzoDiFornitura());
                cap.setText(offer.getCap());
                female.setChecked(offer.getSex().equals("F"));
                codiceFiscale.setText(offer.getCodiceFiscale());
            } else {
                business.setChecked(true);
                cap_buesnes.setText(offer.getCap());
                indirizzoDiFornitura_buesnes.setText(offer.getIndirizzoDiFornitura());
                ragioneSociale.setText(offer.getName());
                piva.setText(offer.getPiva());
                town = townDAO.getTownById(offer.getTownId());
                comuneAutoComplete.setText(town.toString());
                mail.setText(offer.getMail());
            }

            nonResidenziale.setChecked(offer.getClientTypeId() == Constants.NON_RESIDENZIALE);
            validation(false);
        }

        findViewById(R.id.ricalcolaEnabled).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildOfferEntity();
                startChildActivity(Canone.class);
            }
        });
    }

    private boolean codiceFiscaleValidation(boolean isAnimated, boolean isCleaned) {
        boolean result = (nome.getCurrentTextColor() == getResources().getInteger(R.integer.text_black) &&
                cognome.getCurrentTextColor() == getResources().getInteger(R.integer.text_black)
                && (isTextViewValid(false, dataDiNascita.getId()) && townDiNascita != null));
        generateButton.setEnabled(result);
        generateButton.setTextColor(result ? getResources().getInteger(R.integer.text_red) : getResources().getInteger(R.integer.text_grey));
        if (!result && isCleaned) {
            codiceFiscale.setText("");
        }
        return result;
    }

    private boolean codiceFiscaleValidationWithManualCFCheck(boolean isAnimated) {
        boolean result = (nome.getCurrentTextColor() == getResources().getInteger(R.integer.text_black) &&
                cognome.getCurrentTextColor() == getResources().getInteger(R.integer.text_black)
                && ((isTextViewValid(false, dataDiNascita.getId()) && townDiNascita != null) ||
                codiceFiscale.getCurrentTextColor() == getResources().getInteger(R.integer.text_black)));
        return result;
    }

    private void addListeners() {
        consumer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clearBusinessFields();
                }
            }
        });

        business.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clearConsumerFields();
                }
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nomeStr = editTextGetTextString(nome.getId());
                    String cognomeStr = editTextGetTextString(cognome.getId());
                    String data = dataDiNascita.getText().toString().trim();
//                  String codiceFiscaleStr = CodiceFiscaleUtils.calcolaCf(cognomeStr, nomeStr, getSessoType(), dateFormat.parse(data), townDiNascita.getTownDesc());
                    if (townDiNascita != null && !townDiNascita.getNationalCode().equals("null")) {
                        String codiceFiscaleStr = CodiceFiscaleUtils.calcolaCf(cognomeStr, nomeStr, getSessoType(), dateFormat.parse(data),
                                townDiNascita.getNationalCode());
                        codiceFiscale.setText(codiceFiscaleStr);
                    } else {
                        Toast.makeText(MainActivity.this, "Non è possibile calcolare il codice fiscale. I dati del comune non sono completi.", Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    Log.e(this.getClass().getName(), "can't parse date", e);
                }
            }
        });

        codiceFiscale.addTextChangedListener(codiceFiscaleWatcher);
        comuneDiFornitura.addTextChangedListener(comuneDiFornitoreWatcher);
        nonResidenzialse = (RadioButton) findViewById(R.id.nonResidenziale);
        comuneDiNascita.addTextChangedListener(comuneDiNascitaWatcher);
        nome.addTextChangedListener(nomeTextWatcher);
        cognome.addTextChangedListener(cognomeTextWatcher);
        cap.addTextChangedListener(capTextWatcher);
        cap_buesnes.addTextChangedListener(capTextWatcher);
        ragioneSociale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (!s.toString().trim().equals(clients != null ? clients.getName().trim() : "")) {
//                    clients = null;
//                }
                validation(false);
            }
        });
        piva.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (!s.toString().trim().equals(clients != null ? clients.getPiva().trim() : "")) {
//                        clients = null;
//                }
                validation(false);
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
                if (!s.toString().endsWith(")")) {
                    town = null;
                }
                validation(false);
            }
        });
        configuratorTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                consumerContainer.setVisibility(checkedId == R.id.consumerRadioButton ? View.VISIBLE : View.GONE);
                businessContainer.setVisibility(checkedId == R.id.businessRadioButton ? View.VISIBLE : View.GONE);
                town = null;
            }
        });
    }

    private void initializeViews() {
        cap_buesnes = (EditText) findViewById(R.id.cap_busines);
        indirizzoDiFornitura_buesnes = (EditText) findViewById(R.id.indirizzoDiFornitura_busines);
        piva = (AutoCompleteTextView) findViewById(R.id.pivaAutocomplete);
        comuneAutoComplete = (AutoCompleteTextView) findViewById(R.id.comuneAutoCompleteTextView);
        ragioneSociale = (AutoCompleteTextView) findViewById(R.id.ragioneSocialeAutocomplete);
        agreements = (Spinner) findViewById(R.id.agreements);
        mail = (EditText) findViewById(R.id.mail);
        nome = (EditText) findViewById(R.id.nome);
        cognome = (EditText) findViewById(R.id.cognome);
        dataDiNascita = (TextView) findViewById(R.id.dataDiNascita);
        sessoRadioGroup = (RadioGroup) findViewById(R.id.sessoRadioGroup);
        configuratorTypeRadioGroup = (RadioGroup) findViewById(R.id.configuratorTypeRadioGroup);
        tipologiaClienteRadioGroup = (RadioGroup) findViewById(R.id.tipologiaClienteRadioGroup);
        sessoFemale = (RadioButton) findViewById(R.id.radioButtonF);
        nonResidenziale = (RadioButton) findViewById(R.id.nonResidenziale);
        comuneDiNascita = (AutoCompleteTextView) findViewById(R.id.comuneDiNascita);
        comuneDiFornitura = (AutoCompleteTextView) findViewById(R.id.comuneDiFornitura);
        indirizzoDiFornitura = (EditText) findViewById(R.id.indirizzoDiFornitura);
        cap = (EditText) findViewById(R.id.cap);
        female = (RadioButton) findViewById(R.id.radioButtonF);
        codiceFiscale = (EditText) findViewById(R.id.codiceFiscale);
        consumer = (RadioButton) findViewById(R.id.consumerRadioButton);
        business = (RadioButton) findViewById(R.id.businessRadioButton);
        businessContainer = (LinearLayout) findViewById(R.id.businessLayout);
        consumerContainer = (LinearLayout) findViewById(R.id.consumerLayout);
        generateButton = (Button) findViewById(R.id.generate);
        calendar = Calendar.getInstance();
        centroCommerciale = (CheckBox) findViewById(R.id.centroCommerciale);
    }

    private boolean validation(boolean isAnimated) {
        return configuratorTypeRadioGroup.getCheckedRadioButtonId() != consumer.getId() ?
                businessValidation(isAnimated) : consumerValidation(isAnimated);
    }

    private boolean checkMail() {
        boolean result = !isValid(false, mail.getId());
        if (isValid(false, mail.getId())) {
            result = isMailAdressValid(mail.getText().toString().trim());
            if (!result) {
                mail.requestFocus();
                mail.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
                Toast.makeText(this, "Inserire un valore valido in E-mail", Toast.LENGTH_SHORT).show();
            }
        }
        return result;
    }

    private void startNextPage() {
        checkForLogout();
        if (finalValidation(true)) {
            buildOfferEntity();
            startChildActivity(TipologiaOfferta.class);
        }
    }

    private boolean finalValidation(boolean isAnimated) {
        return configuratorTypeRadioGroup.getCheckedRadioButtonId() != consumer.getId() ?
                businessValidation(isAnimated) :
                checkTownSelection(town, comuneDiFornitura, isAnimated) &
                        isValid(isAnimated, codiceFiscale.getId()) &
                        codiceFiscale.getCurrentTextColor() == getResources().getInteger(R.integer.text_black) &
                        isValid(isAnimated, cap.getId()) && codiceFiscaleValidationWithManualCFCheck(true);
    }

    public void buildOfferEntity() {
        if (offer == null) {
            offer = new Offer();
        }

        offer.setConfigurator_type(configuratorTypeRadioGroup.getCheckedRadioButtonId() == R.id.consumerRadioButton ? Constants.CONSUMER_CONFIGURATOR : Constants.BUSINESS_CONFIGURATOR);
        offer.setName(ragioneSociale.getText().toString().trim());
        offer.setNome(nome.getText().toString().trim());
        offer.setSurname(cognome.getText().toString().trim());
        offer.setBirthDateTownId(townDiNascita != null ? townDiNascita.getTownId() : 0);
        offer.setSex(getSessoType());
        offer.setCodiceFiscale(editTextGetTextString(codiceFiscale.getId()));

        if (configuratorTypeRadioGroup.getCheckedRadioButtonId() == R.id.consumerRadioButton) {
            offer.setIndirizzoDiFornitura(editTextGetTextString(indirizzoDiFornitura.getId()));
            offer.setCap(editTextGetTextString(cap.getId()));
        } else {
            offer.setIndirizzoDiFornitura(editTextGetTextString(indirizzoDiFornitura_buesnes.getId()));
            offer.setCap(editTextGetTextString(cap_buesnes.getId()));
        }

        offer.setClimaticZone(townDiNascita != null ? townDiNascita.getClimaticZone() : "");
        offer.setBirthDate(TextViewGetText(R.id.dataDiNascita));
        offer.setPiva(piva.getText().toString().trim());
        offer.setTownId(town != null ? town.getTownId() : 0);
        offer.setMail(mail.getText().toString().trim());
        offer.setAgreementId(checkSpinnerSelection(false, agreements.getId()) ? ((Agreements) agreements.getSelectedItem()).getAgreementId() : null);
        offer.setOfferCost(new BigDecimal(0.00));
        offer.setOfferCostExtra(new BigDecimal(0.00));
        offer.setOfferCostIVA(new BigDecimal(0.00));
        offer.setOptilifeCode("");
        offer.setNi(piva.getText().toString());
        offer.setOfferte1ServiceCode("");
        offer.setOfferte2ServiceCode("");
        offer.setOfferte3ServiceCode("");
        offer.setMainServiceCode("");
        offer.setCreationDate("");
        offer.setOldTlcOfferId(0);
        offer.setOldTlcOfferUsed(false);
        offer.setPdfSendingRequired(true);
        offer.setClientTypeId(getClientType());
        offer.setCentroCommerciale(centroCommerciale.isChecked());
        definePricingOfferId();
        checkForExistingClientSelected(offer);
        MyApplication.set(Constants.OFFER_ENTITY, offer);
    }

    private void definePricingOfferId() {
        if (offer != null && offer.getOfferId() == 0) {
            offer.setPricingOfferId(LocalSharedPreferencesManager.getInstance().getSharedPreferencesStringValue(this, LocalSharedPreferencesManager.ID_SOGGETTO) +
                    "|" + System.currentTimeMillis());
        }
    }

    private void checkForExistingClientSelected(Offer offer) {
        if (clients != null) {
            receiveTLCClientOffer(offer);
        }
        OfferDAOImpl offerDAO = new OfferDAOImpl();
        if (offer.getOfferId() == 0) {
            offerDAO.insert(offer);
        } else {
            offerDAO.update(offer);
        }
    }

    private void receiveTLCClientOffer(Offer offer) {
        offer.setOldTlcOfferId(clients.getClientId());
        offer.setOldTlcOfferUsed(true);
    }

    private boolean checkTownSelection(Town town, AutoCompleteTextView autoCompleteTextView, boolean isAnimated) {
        boolean flag = town != null;
        if (!flag && isAnimated) {
            autoCompleteTextView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        }
        return flag;
    }

    private void checkForApplicationUpdate() {
        if (!LocalSharedPreferencesManager.getInstance().getSharedPreferencesBooleanValue(this, LocalSharedPreferencesManager.IS_APP_CHECKED_FOR_UPDATE)) {
            CheckForUpdatesService checkForUpdatesService = new CheckForUpdatesService(this, new AbstractService.OnTaskCompleted<CheckForUpdatesService>() {
                @Override
                public void onTaskCompleted(CheckForUpdatesService service) {
                    if (service.getResult() != null) {
                        int newVersion = service.getResult();
                        int currVesion = Integer.valueOf(getResources().getString(R.string.app_version));
                        LocalSharedPreferencesManager.getInstance().storeSharedPreferencesBooleanValue(MainActivity.this, LocalSharedPreferencesManager.IS_APP_CHECKED_FOR_UPDATE, true);
                        if (currVesion < newVersion) {
                            new AutoUpdateDialog(MainActivity.this, newVersion);
                        }
                    }
                }
            });
            checkForUpdatesService.execute();
        }
    }

    public void validationByColor(EditText editText, CharSequence text, String requiredPattern) {
        Pattern pattern = Pattern.compile(requiredPattern);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            editText.setTextColor(getResources().getInteger(R.integer.text_black));
        } else {
            editText.setTextColor(getResources().getInteger(R.integer.text_red));
        }
    }

    TextWatcher comuneDiNascitaWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().endsWith(")")) townDiNascita = null;
            codiceFiscaleValidation(false, true);
            validation(false);
        }
    };

    TextWatcher codiceFiscaleWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            validationByColor(codiceFiscale, s.toString(), LENGTH_16_CHARS);
            validation(false);
        }
    };


    TextWatcher capTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) validationByColor(cap, s.toString().trim(), NUMERIC);
            validation(false);
        }
    };

    TextWatcher comuneDiFornitoreWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().endsWith(")")) {
                town = null;
//                cap.setText("");
//                cap.clearFocus();
//                cap.setFocusable(false);
            }
            validation(false);
        }
    };

    TextWatcher nomeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (codiceFiscale.length() > 0) {
                codiceFiscale.setText("");
            }
            validationByColor(nome, s.toString(), CHAR_ALLOWED);
            codiceFiscaleValidation(false, true);
        }
    };

    TextWatcher cognomeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (codiceFiscale.length() > 0) {
                codiceFiscale.setText("");
            }
            validationByColor(cognome, s.toString(), CHAR_ALLOWED);
            codiceFiscaleValidation(false, true);
        }
    };

    private String getSessoType() {
        return sessoRadioGroup.getCheckedRadioButtonId() != female.getId() ? "M" : "F";
    }

    private void clearBusinessFields() {
        ragioneSociale.getText().clear();
        piva.getText().clear();
        comuneAutoComplete.getText().clear();
        cap.getText().clear();
        town = null;
        indirizzoDiFornitura_buesnes.getText().clear();
        cap.getText().clear();
        if (itusClientName != null && itusClientCognome != null) {
            nome.setText(itusClientName);
            cognome.setText(itusClientCognome);
        }
    }

    private void clearConsumerFields() {
        nome.getText().clear();
        cognome.getText().clear();
        dataDiNascita.setText("");
        sessoRadioGroup.check(R.id.radioButtonM);
        comuneDiFornitura.getText().clear();
        comuneDiNascita.getText().clear();
        indirizzoDiFornitura.getText().clear();
        town = null;
        townDiNascita = null;
        cap.getText().clear();
        codiceFiscale.getText().clear();
        tipologiaClienteRadioGroup.check(R.id.residenziale);
        centroCommerciale.setChecked(false);
        if (itusClientName != null && itusClientCognome != null) {
            ragioneSociale.setText(itusClientName + " " + itusClientCognome);
        }
    }

    private int getClientType() {
        return tipologiaClienteRadioGroup.getCheckedRadioButtonId() != nonResidenzialse.getId() ? 1 : 2;
    }

    private void restoreNomeFromItus() {
        Intent itusIntent = getIntent();
        if (itusIntent != null) {
            Bundle bundle = itusIntent.getExtras();
            if (bundle != null && bundle.containsKey(Constants.NOME) && bundle.containsKey(Constants.COGNOME_OR_RAGIONE_SOCIALE)) {
                itusClientName = itusIntent.getExtras().getString(Constants.NOME);
                itusClientCognome = itusIntent.getExtras().getString(Constants.COGNOME_OR_RAGIONE_SOCIALE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isModifica) {
            super.onBackPressed();
        }
    }
}
