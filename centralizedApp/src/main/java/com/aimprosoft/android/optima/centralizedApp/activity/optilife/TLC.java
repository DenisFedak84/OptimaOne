package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.AdditionalNumbersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.LineTypeDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.ServicesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.TipoOpzioniDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CarriersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientAdslOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientDetailAdslOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientDetailsTlcOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientServicesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientTlcOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.LineNumbersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.NetworksDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TLCOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TlcDetailsOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ZiImportPricingVoceDettDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.AdditionalNumbers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Bundle;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Carriers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientAdslOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientDetailAdslOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientDetailTlcOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientServices;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientTlcOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.LineNumbers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.LineTypes;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Networks;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Services;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TLCOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TipoOpzioni;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TlcDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ZiImportPricingVoceDett;
import com.aimprosoft.android.optima.centralizedApp.event.ConfirmationDialog;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.AdslSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.LineTypeSpinnerService;
import com.aimprosoft.android.optima.centralizedApp.service.NumeroLineeSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.OperatoreSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.ReteSpinnerService;
import com.aimprosoft.android.optima.centralizedApp.service.VersoFissoSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.VersoMobileSpinner;
import com.aimprosoft.android.optima.centralizedApp.service.VoceVoipModeSpinner;
import com.aimprosoft.android.optima.centralizedApp.util.MobileAlgorithm;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.AbstractJarUtil;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.IdCampganaUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.aimprosoft.android.optima.centralizedApp.app.Constants.CPS;
import static com.aimprosoft.android.optima.centralizedApp.app.Constants.VOIP;
import static com.aimprosoft.android.optima.centralizedApp.app.Constants.WLR;

public class TLC extends BaseActivity implements Runnable, TextWatcher, AdapterView.OnItemSelectedListener {

    private Offer offer = MyApplication.get("offerEntity", Offer.class);
    private TLCOffer tlcOffer;
    private TlcDetailsOffer tlcDetailsOffer = new TlcDetailsOffer();
    private InternetOffer internetOffer;
    private InternetDetailOffers internetDetailOffers = new InternetDetailOffers();
    private MobileOffer mobileOffer;
    private WlrOffers wlrOffers;
    private WlrOfferDetails wlrOfferDetails = new WlrOfferDetails();

    private final String RTG = "RTG";
    private final String ISDN = "ISDN";
    private final String ADSL = "ADSL";

    private final String NUMERAZIONE_AGGIUNTIVA = "Numerazione aggiuntiva";

    private List<Integer> servicesListRtg = new ArrayList<>();
    private List<Integer> servicesListISND = new ArrayList<>();
    private int existingClientId = 0;
    private int looperNumber = 0;
    private double cost = 0.0;
    private double costIVA = 0.0;
    private boolean tlcFlag = false;
    private boolean internetFlag = false;
    private boolean mobileFlag = false;
    private boolean isWlrAlreadyFilled = false;
    private boolean isSpecificCPS = false;
    private boolean isVoipMode = false;
    private int idCampagnaVoce;
    private int idCampagnaADSL;

    private CheckBox mobile;
    private CheckBox fisso;
    private RadioButton cps;
    private RadioButton wlr;
    private Spinner fissoSpinner;
    private Spinner mobileSpinner;
    private Spinner voceModeSpinner;

    private TLCOfferDAOImpl tlcOfferDAO = new TLCOfferDAOImpl();
    private TlcDetailsOfferDAOImpl tlcDetailsOfferDAO = new TlcDetailsOfferDAOImpl();
    private InternetOfferDAOImpl internetOfferDAO = new InternetOfferDAOImpl();
    private MobileOfferDAOImpl mobileOfferDAO = new MobileOfferDAOImpl();
    private WlrOffersDAOImpl wlrOffersDAO = new WlrOffersDAOImpl();
    private WlrOfferDetailsDAOImpl wlrOfferDetailsDAO = new WlrOfferDetailsDAOImpl();
    private BundleDAOImpl bundleDAO = new BundleDAOImpl();
    private InternetDetailOffersDAOImpl internetDetailOffersDAO = new InternetDetailOffersDAOImpl();
    private ServicesDAOImpl servicesDAO = new ServicesDAOImpl();
    private LineNumbersDAOImpl lineNumbersDAO = new LineNumbersDAOImpl();
    private CarriersDAOImpl carriersDAO = new CarriersDAOImpl();
    private NetworksDAOImpl networksDAO = new NetworksDAOImpl();

    private Spinner numSimSpinner;
    private Spinner minutesMeseSpinner;
    private Spinner smsMeseSpinner;
    private Spinner gigabyteSpinner;
    private Spinner tipologiaDiLineaRTG;
    private Spinner tipologiaDiLineaISDN;
    private Spinner adsl;
    private Spinner adslServicesSpinner;
    private Spinner operatoreSpinner;
    private Spinner reteSpinner;

    private CheckBox smsCheck;
    private CheckBox gigabyteCheck;
    //    private CheckBox router;
    private CheckBox lineaSoloDatiCheckBox;
    private CheckBox rtgCheckBox;
    private CheckBox isdnCheckBox;

    private EditText lines;
    private Spinner numeroLinesRTG;
    private Spinner numeroLinesISDN;

    private ImageButton plusRTG;
    private ImageButton plusISDN;

    private LinearLayout versoLayout;
    private LinearLayout wlrLayout;
    private LinearLayout layoutISDN;
    private LinearLayout layoutRTG;
    private LinearLayout aggiungiTlcButtonLayout;
    private LinearLayout mainInfoLayout;
    private LinearLayout existingClientLayout;
    private LinearLayout deleteLineLayout;
    private LinearLayout bundleInfoLayout;
    private LinearLayout lineaSoloDatiContainer;

    private Button modificaADSL;
    private LinearLayout modificaADSLLayout;
    private LinearLayout addADSLButtonsLayout;
    private boolean isADSLChecked = false;
    private boolean isVoipLineaMode = false;
    private Button annulaADSL;
    private LinearLayout adslLayout;
    private LinearLayout currentAdslRow;
    private LinearLayout servicesRtg;
    private LinearLayout servicesIdsn;
    private LinearLayout servicesAdsl;
    private LinearLayout podTable;
    private Spinner numerazioneRelatedWithService;

    private LinearLayout addRTGLineLayout;
    private LinearLayout addISNDLineLayout;
    private boolean isCurrentRowWlr = false;
    private boolean isCurrentRowTlc = false;
    private boolean isLineTypeModificaMode = false;
    private Button annulaWlrLine;
    private LinearLayout modificaWlrLayout;

    private LinearLayout modificaTlcLayout;
    private Button annulaTlc;
    private final int TIME_DELAY_CONFIGURATION_CHANGED = 100;
    private final int VOCE_FISSO_BUNDLE_TYPE = 4;
    private final int VOCE_MOBILE_BUNDLE_TYPE = 5;
    private ImageButton continuaButton;
    private ImageButton continuaButtonDisabled;
    private Button calcolaButton;
    private Button calcolaButtonDisabled;
    private ConfirmationDialog confirmationDialog;

    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tlc_layout_optilife);
        checkContinuaButton(R.id.continuaLayout, R.id.calcolaLayout, Constants.TLC);
        initializeViews();
        Map navigationMap = MyApplication.get("navigationMap", Map.class);
        if (navigationMap != null && offer != null) {
            fillViewsWithContent();
            populateVoceModeSpinner(navigationMap.get(4) != null || offer.getInternetOfferId() != null);
            addListenersToViews();
            showRequiredContent();
            showListServices(navigationMap);
        }
    }

    private void populateVoceModeSpinner(boolean isVoipAvailable) {
        List<String> modeList = new ArrayList<>();

        if (isVoipAvailable) {
            modeList.add(VOIP);
        }

        modeList.add(WLR);
        if (offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR) {
            modeList.add(CPS);
        }

        setSpinner(voceModeSpinner, modeList);
        if (isVoipAvailable) {
            applyVoipMode();
        } else {
            applyWlrMode();
        }
    }

    private void showRequiredContent() {
        if (offer.getConfiguratorType() != Constants.BUSINESS_CONFIGURATOR) {
//            findViewById(R.id.tlcTypeRadioGroup).setVisibility(View.GONE);
//            aggiungiTlcButtonLayout.setVisibility(View.GONE);
            versoLayout.setVisibility(View.GONE);
            wlrLayout.setVisibility(View.VISIBLE);
        } else {
            versoLayout.setVisibility(View.VISIBLE);
            wlrLayout.setVisibility(View.GONE);
        }
    }

    private void setPreselectedOperatoreAndReteForExistingClient() {
        if (isCurrentRowTlc && tlcDetailsOffer.isExistingClientOffer()) {
            for (int i = 1; i < operatoreSpinner.getCount(); i++) {
                Carriers carriers = (Carriers) operatoreSpinner.getItemAtPosition(i);
                if (carriers.getCarrierDesc().equals("Optima")) {
                    operatoreSpinner.setSelection(i);
                    break;
                }
            }

            if (tlcDetailsOffer.isModified()) {
                for (int i = 1; i < numeroLinesRTG.getCount(); i++) {
                    LineNumbers lineNumbers = (LineNumbers) numeroLinesRTG.getItemAtPosition(i);
                    if (lineNumbers.getLineNumberId() == tlcDetailsOffer.getNumLines()) {
                        numeroLinesRTG.setSelection(i);
                        break;
                    }
                }

                for (int i = 1; i < numeroLinesISDN.getCount(); i++) {
                    LineNumbers lineNumbers = (LineNumbers) numeroLinesISDN.getItemAtPosition(i);
                    if (lineNumbers.getLineNumberId() == tlcDetailsOffer.getNumLines()) {
                        numeroLinesISDN.setSelection(i);
                        break;
                    }
                }
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i < reteSpinner.getCount(); i++) {
                        Networks networks = (Networks) reteSpinner.getItemAtPosition(i);
                        if (networks.getNetworkDesc().equals(Constants.WLR)) {
                            reteSpinner.setSelection(i);
                            break;
                        }
                    }
                }
            }, 700);
        }
    }

    private void addListenersToViews() {
        findViewById(R.id.calcola6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculation();
                Intent intent = new Intent(TLC.this, Canone.class);
                intent.putExtra("ChoiceCode", 1);
                startActivity(intent);
            }
        });

        continuaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculation();
                isLastPage(5);
            }
        });

        cps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wlrLayout.setVisibility(!isChecked ? View.VISIBLE : View.GONE);
                versoLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                serviceValidation();
                addTlcValidation(false);
                if (isChecked && !isCurrentRowWlr && !isCurrentRowTlc) {
//                    isAggiungiTlcButtonShouldBeShown();
//                    сlearAndActivateBundleSection();
                    checkBundleAlreadyChoosed();
//                    checkForWlrEntityPresent();
                }
            }
        });

        wlr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wlrLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                versoLayout.setVisibility(!isChecked ? View.VISIBLE : View.GONE);
                if (isChecked) {
                    aggiungiTlcButtonLayout.setVisibility(View.GONE);
                    checkBundleAlreadyChoosed();
                }
                setPreselectedOperatoreAndReteForExistingClient();
                addTlcValidation(false);
            }
        });

        voceModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getAdapter().getItem(position);
                if (confirmationDialog == null) {
                    confirmationDialog = new ConfirmationDialog(TLC.this, getString(R.string.not_voip_selection_message));
                }

                if (!selection.equals(VOIP)) {
                    confirmationDialog.showDialog();
                }
                processVoceModeSelection(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        operatoreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    new ReteSpinnerService(TLC.this, new AbstractService.OnTaskCompleted<ReteSpinnerService>() {
                        @Override
                        public void onTaskCompleted(ReteSpinnerService service) {
                            setSpinner(reteSpinner, service.getResult());
                            reteSpinner.setEnabled(true);
                        }
                    }).execute(((Carriers) parent.getAdapter().getItem(position)).getCarrierId());
                } else {
                    reteSpinner.setEnabled(false);
                    reteSpinner.setSelection(0);
                }
                serviceValidation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        reteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceValidation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lines.addTextChangedListener(this);
        numeroLinesISDN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceValidation();
//                changeISDNServiceVisibility();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        numeroLinesRTG.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceValidation();
//                changeRTGServiceVisibility();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        rtgCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int visibility = isChecked ? View.VISIBLE : View.GONE;
                layoutRTG.setVisibility(visibility);

                if (isChecked & isLineTypeModificaMode) {
                    layoutISDN.setVisibility(View.GONE);
                    isdnCheckBox.setChecked(false);
                }
            }
        });

        isdnCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int visibility = isChecked ? View.VISIBLE : View.GONE;
                layoutISDN.setVisibility(visibility);

                if (isChecked & isLineTypeModificaMode) {
                    layoutRTG.setVisibility(View.GONE);
                    rtgCheckBox.setChecked(false);
                }
            }
        });

        fisso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                findViewById(R.id.versoFisso).setClickable(isChecked);
                addTlcValidation(false);
                serviceValidation();
            }
        });

        mobile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                findViewById(R.id.versoMobile).setClickable(isChecked);
                addTlcValidation(false);
                serviceValidation();
            }
        });

        minutesMeseSpinner.setOnItemSelectedListener(this);
        tipologiaDiLineaRTG.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceValidation();
                if (!isWlrAlreadyFilled) {
                    servicesListRtg.clear();
                    servicesRtg.removeAllViews();
                    changeRTGServiceVisibility();
                }
                isWlrAlreadyFilled = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tipologiaDiLineaISDN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceValidation();
                if (!isWlrAlreadyFilled) {
                    servicesListISND.clear();
                    servicesIdsn.removeAllViews();
                    changeISDNServiceVisibility();
                }
                isWlrAlreadyFilled = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        adsl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceValidation();
                addAdslValidation(false);
                int visibility = checkSpinnerSelection(false, adsl.getId()) ? View.VISIBLE : View.GONE;
                servicesAdsl.setVisibility(visibility);
                validateLineaSoloDatiCheckBox();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        numSimSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {

                    int data = Integer.valueOf((String) numSimSpinner.getAdapter().getItem(position));

                    List<String> minutesMeseList = new ArrayList<>();
                    minutesMeseList.add(" ");
                    for (int start = data * 200; start <= data * 1500; start += 100) {
                        minutesMeseList.add(String.valueOf(start));
                    }


                    ArrayAdapter adapter = new ArrayAdapter<>(TLC.this, android.R.layout.simple_spinner_item, minutesMeseList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    minutesMeseSpinner.setAdapter(adapter);
                    minutesMeseSpinner.setEnabled(true);

                    List<String> smsMeseList = new ArrayList<>();

                    for (int i = 0; i <= data; i++)
                        smsMeseList.add(String.valueOf(i * 100));

                    ArrayAdapter smsadapter = new ArrayAdapter<>(TLC.this, android.R.layout.simple_spinner_item, smsMeseList);
                    smsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    smsMeseSpinner.setAdapter(smsadapter);

                    List<String> gigabyteList = new ArrayList<>();

                    for (int i = 0; i <= data; i++)
                        gigabyteList.add(String.valueOf(i) + " GB");

                    ArrayAdapter gigabytesAdapter = new ArrayAdapter<>(TLC.this, android.R.layout.simple_spinner_item, gigabyteList);
                    gigabytesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gigabyteSpinner.setAdapter(gigabytesAdapter);

                } else {
                    numSimSpinner.setSelection(0);
                    minutesMeseSpinner.setSelection(0);
                    minutesMeseSpinner.setEnabled(false);
                    smsMeseSpinner.setSelection(0);
                    smsMeseSpinner.setEnabled(false);
                    smsCheck.setChecked(false);
                    gigabyteSpinner.setSelection(0);
                    gigabyteSpinner.setEnabled(false);
                    gigabyteCheck.setChecked(false);
                }
                serviceValidation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        smsCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean state = smsCheck.isChecked();
                smsMeseSpinner.setSelection(0);
                smsMeseSpinner.setEnabled(state);
            }
        });

        gigabyteCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean state = gigabyteCheck.isChecked();
                gigabyteSpinner.setSelection(0);
                gigabyteSpinner.setEnabled(state);
            }
        });

        findViewById(R.id.addTlcButtonEnabled).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLineaModeValid(true)) {
                    if (isVoipMode) {
                        saveVoip();
                        isAggiungiVoipButtonShouldBeShown();
                    } else {
                        addTlcOfferToBaseIfNeeded();
                        saveTlcOfferDetails(new TlcDetailsOffer());
                        drawTlcTable();
                        isAggiungiTlcButtonShouldBeShown();
                    }
                    validateLineaSoloDatiCheckBox();
                    tlcDetailsOffer = new TlcDetailsOffer();
                }
            }
        });

        findViewById(R.id.addADSLEnabled).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAdsl(false);
                fillAdslServiceSpinner(ServicesDAOImpl.TipoOpzioni.IP_AGGIUNTIVI, servicesAdsl);
                adslServicesSpinner.setSelection(0);
            }
        });

        modificaADSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSpinnerSelection(true, R.id.adslSpinner)) {
                    addAdsl(true);
                    modificaADSLLayout.setVisibility(View.GONE);
                    addADSLButtonsLayout.setVisibility(View.VISIBLE);
                    isADSLChecked = false;
                    annulaADSL.setVisibility(View.VISIBLE);
                }
            }
        });

        annulaADSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annulaAction();
                addADSLButtonsLayout.setVisibility(View.VISIBLE);
                cleanTable(adslLayout);
                drawADSLTable();
                isADSLChecked = false;
                internetDetailOffers = new InternetDetailOffers();
            }
        });

        plusRTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLineaModeValid(true) && plusButtonAction(tipologiaDiLineaRTG, numeroLinesRTG, false)) {
                    servicesRtg.removeAllViews();
                    servicesListRtg.clear();
                    validateLineaSoloDatiCheckBox();
                }
            }
        });

        plusISDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLineaModeValid(true) && plusButtonAction(tipologiaDiLineaISDN, numeroLinesISDN, false)) {
                    servicesIdsn.removeAllViews();
                    numerazioneRelatedWithService = null;
                    servicesListISND.clear();
                    validateLineaSoloDatiCheckBox();
                }
            }
        });

        annulaWlrLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annulaTlcAction();
                servicesRtg.removeAllViews();
                servicesIdsn.removeAllViews();
                drawTlcTable();
                if (checkMode(VOIP)) {
                    isAggiungiVoipButtonShouldBeShown();
                }
                setBundleClickableCondition(true);
                setAlreadyClientsFieldsLock(false);
                wlrOfferDetails = new WlrOfferDetails();
                isLineTypeModificaMode = false;
            }
        });

        annulaTlc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redrawTable();
            }
        });
    }

    private void validateLineaSoloDatiCheckBox() {
        boolean isVisible = checkSpinnerSelection(false, adsl.getId()) &&
                isManuallSoloDatiMode(((Bundle) adsl.getSelectedItem()).getCode());
        lineaSoloDatiContainer.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void processVoceModeSelection(String selection) {
        switch (selection) {
            case CPS:
                applyCpsMode();
                break;
            case WLR:
                applyWlrMode();
                break;
            case VOIP:
                applyVoipMode();
                break;
        }
    }

    private void saveVoip() {
        checkCurrentLine();
        addWlrOfferToBaseIfNeeded();
        addWlrOfferDetailsToBaseIfNeeded();
        saveWlrOfferDetailsToBase(null, 1);
        drawTlcTable();
        isAggiungiVoipButtonShouldBeShown();
        wlrOfferDetails = new WlrOfferDetails();
    }

    private void applyWlrMode() {
        wlrLayout.setVisibility(View.VISIBLE);
        versoLayout.setVisibility(View.GONE);
        aggiungiTlcButtonLayout.setVisibility(View.GONE);
        redrawTlcBundleLayout();
        isVoipMode = false;
        checkBundleAlreadyChoosed();
//        setPreselectedOperatoreAndReteForExistingClient();
        addTlcValidation(false);
    }

    private void applyVoipMode() {
        wlrLayout.setVisibility(View.GONE);
        versoLayout.setVisibility(View.GONE);
        isVoipMode = true;
        redrawBundleInVoipMode();
        serviceValidation();
        addTlcValidation(false);
        if (!isCurrentRowWlr && !isCurrentRowTlc) {
            isAggiungiVoipButtonShouldBeShown();
        }
    }

    private void applyCpsMode() {
        wlrLayout.setVisibility(View.GONE);
        versoLayout.setVisibility(View.VISIBLE);
        redrawTlcBundleLayout();
        isVoipMode = false;
        serviceValidation();
        addTlcValidation(false);
        if (!isCurrentRowWlr && !isCurrentRowTlc) {
            isAggiungiTlcButtonShouldBeShown();
            checkBundleAlreadyChoosed();
        }
    }

    private void redrawTable() {
        annulaTlcAction();
        drawTlcTable();
        setAlreadyClientsFieldsLock(false);
        setBundleClickableCondition(true);
        isAggiungiTlcButtonShouldBeShown();
        tlcDetailsOffer = new TlcDetailsOffer();
        cps.setEnabled(true);
    }

    private void setAlreadyClientsFieldsLock(boolean isLocked) {
        numeroLinesISDN.setEnabled(!isLocked);
        numeroLinesRTG.setEnabled(!isLocked);
        reteSpinner.setEnabled(!isLocked);
        operatoreSpinner.setEnabled(!isLocked);
    }

    private void checkBundleAlreadyChoosed() {
        if (wlrOffers != null && tlcOffer != null) {
            List<WlrOfferDetails> wlrOfferDetailses = wlrOfferDetailsDAO.getNewClientDetailsByOfferId(wlrOffers.getWlrOfferId(), true);
            List<TlcDetailsOffer> tlcDetailsOffers = tlcDetailsOfferDAO.getNewClientDetailsByTlcOfferId(tlcOffer.getTlcOfferId());
            if (wlrOfferDetailses.size() > 0 || tlcDetailsOffers.size() > 0) {
//                fisso.setChecked(false);
                mobile.setChecked(false);
                Integer localBundleId = wlrOfferDetailses.size() > 0 ? wlrOffers.getLocalBundleId() : tlcDetailsOffers.get(0).getLocalBundleId();
                Integer mobileBundleId = wlrOfferDetailses.size() > 0 ? wlrOffers.getMobileBundleId() : tlcDetailsOffers.get(0).getMobileBundleId();
                setSelectionToBundleSpinner(localBundleId, fissoSpinner, fisso);
                setSelectionToBundleSpinner(mobileBundleId, mobileSpinner, mobile);
            }
        }
    }

    public void modify(View v) {
        if (isLineaModeValid(false) || mainInfoLayout.getChildCount() == 1) {
            switch ((String) voceModeSpinner.getSelectedItem()) {
                case CPS:
                    modifyTlcAction();
                    break;
                case WLR:
                    modifyWlrAction();
                    break;
                case VOIP:
                    modifyWlrAction();
                    break;
            }
        } else {
            Toast.makeText(this, getString(R.string.wrong_linee_type_message), Toast.LENGTH_SHORT).show();
        }
    }

    private void modifyWlrAction() {
        if (!isVoipMode) {
            Spinner currentLineType = rtgCheckBox.isChecked() ? tipologiaDiLineaRTG : tipologiaDiLineaISDN;
            Spinner currentlineNumber = rtgCheckBox.isChecked() ? numeroLinesRTG : numeroLinesISDN;
            if ((wlrOfferDetails.isExistingClientOffer() || tlcDetailsOffer.isExistingClientOffer() || fissoAndMobileValidation(true)) &&
                    checkSpinnerSelection(true, currentLineType.getId()) && checkSpinnerSelection(true, currentlineNumber.getId()) &
                    checkSpinnerSelection(true, operatoreSpinner.getId()) & checkSpinnerSelection(true, reteSpinner.getId())) {
                plusButtonAction(currentLineType, currentlineNumber, true);
            }
            addRTGLineLayout.setVisibility(View.VISIBLE);
            addISNDLineLayout.setVisibility(View.VISIBLE);
        } else {
            saveVoip();
        }
        modificaWlrLayout.setVisibility(View.GONE);
        setBundleClickableCondition(true);
        setAlreadyClientsFieldsLock(false);
        isCurrentRowWlr = false;
        annulaWlrLine.setVisibility(View.VISIBLE);
        isLineTypeModificaMode = false;
        annulaTlcAction();
    }

    private void modifyTlcAction() {
        if ((tlcDetailsOffer.isExistingClientOffer() || wlrOfferDetails.isExistingClientOffer() || fissoAndMobileValidation(true)) && isValid(true, lines.getId())) {
            if (isSpecificCPS) {
                saveSpecificCPS();
            } else {
                checkCurrentTlcLine();
                addTlcOfferToBaseIfNeeded();
                saveTlcOfferDetails(tlcDetailsOffer);
            }

            modificaTlcLayout.setVisibility(View.GONE);
            isCurrentRowTlc = false;
            annulaTlcAction();
            drawTlcTable();
            isAggiungiTlcButtonShouldBeShown();
            setBundleClickableCondition(true);
            setAlreadyClientsFieldsLock(false);
            addRTGLineLayout.setVisibility(View.VISIBLE);
            addISNDLineLayout.setVisibility(View.VISIBLE);
        }
    }

    private void saveSpecificCPS() {
        wlrOfferDetails.setNumLines(Integer.valueOf(lines.getText().toString().trim()));
        wlrOfferDetails.setModified(true);
        wlrOfferDetailsDAO.update(wlrOfferDetails);
        isSpecificCPS = false;
    }

    private void checkCurrentTlcLine() {
        if (isCurrentRowWlr) {
            tlcDetailsOffer = new TlcDetailsOffer();
            wlrOfferDetailsDAO.delete(wlrOfferDetails);
            if (wlrOfferDetailsDAO.getWlrOfferDetailsByWlrOfferId(wlrOffers.getWlrOfferId(), true).size() < 1)
                wlrOffers = new WlrOffers();
        }

        if (isCurrentRowWlr && wlrOfferDetails.isExistingClientOffer()) {
            ClientDetailTlcOffers clientDetailTlcOffers = new ClientDetailsTlcOffersDAOImpl().getClientDetailTlcOffersByLine(wlrOfferDetails.getWlrName());
            tlcDetailsOffer.setExistingClientOffer(true);
            tlcDetailsOffer.setTlcName(wlrOfferDetails.getWlrName());
            tlcDetailsOffer.setLocalBundleId(wlrOfferDetails.getOwnLocalBundleId());
            tlcDetailsOffer.setMobileBundleId(wlrOfferDetails.getOwnMobileBundleId());
            tlcDetailsOffer.setOfferCost(clientDetailTlcOffers.getLocalOfferCost() + clientDetailTlcOffers.getMobileOfferCost());
            tlcDetailsOffer.setOfferCostIva(clientDetailTlcOffers.getLocalOfferCostIva() + clientDetailTlcOffers.getMobileOfferCostIva());
            tlcDetailsOffer.setCostVersion(clientDetailTlcOffers.getVersion());
        }
    }

    private void checkForWlrEntityPresent() {
//        int newTlcEntitySize = tlcDetailsOfferDAO.getNewClientDetailsByTlcOfferId(tlcOffer.getTlcOfferId()).size();
//        if (modificaWlrLayout.getVisibility() == View.VISIBLE && newTlcEntitySize < 1) {
//            setSelectionToBundleSpinner(wlrOffers.getLocalBundleId(), fissoSpinner, fisso);
//            setSelectionToBundleSpinner(wlrOffers.getMobileBundleId(), mobileSpinner, mobile);
//        }
//        if (newTlcEntitySize > 0 && modificaWlrLayout.getVisibility() == View.VISIBLE) {
//            cps.setEnabled(false);
//        }
    }

    private void setSelectionToBundleSpinner(Integer localBundleId, Spinner bundleSpinner, CheckBox bundleCheckBox) {
        if (localBundleId != null) {
            for (int i = 0; i < bundleSpinner.getCount(); i++) {
                Bundle currentBundle = (Bundle) bundleSpinner.getItemAtPosition(i);
                if (currentBundle.getBundleId() == localBundleId) {
                    bundleCheckBox.setChecked(true);
                    bundleSpinner.setSelection(i);
                    break;
                }
            }
        }
    }

    private void checkForCommonWlrBundle() {
        if (wlrOffers != null) {
            List<WlrOfferDetails> wlrOfferDetailses = wlrOfferDetailsDAO.getNewClientDetailsByOfferId(wlrOffers.getWlrOfferId(), true);
            if (wlrOfferDetailses.size() > 0) {
                if (wlrOffers.getLocalBundleId() != null) {
                    fisso.setChecked(true);
                    for (int i = 0; i < fissoSpinner.getCount(); i++) {
                        Bundle currentBundle = (Bundle) fissoSpinner.getItemAtPosition(i);
                        if (currentBundle.getBundleId() == wlrOffers.getLocalBundleId()) {
                            fissoSpinner.setSelection(i);
                            break;
                        }
                    }
                }

                if (wlrOffers.getMobileBundleId() != null) {
                    mobile.setChecked(true);
                    for (int i = 0; i < mobileSpinner.getCount(); i++) {
                        Bundle currentBundle = (Bundle) mobileSpinner.getItemAtPosition(i);
                        if (currentBundle.getBundleId() == wlrOffers.getMobileBundleId()) {
                            mobileSpinner.setSelection(i);
                            break;
                        }
                    }
                }
            }
        }
    }

//    private void сlearAndActivateBundleSection() {
//        fisso.setClickable(true);
//        fisso.setChecked(false);
//        mobile.setClickable(true);
//        mobile.setChecked(false);
//        fissoSpinner.setSelection(0);
//        mobileSpinner.setSelection(0);
//    }

    private void isAggiungiTlcButtonShouldBeShown() {
        if (offer != null && tlcOffer != null) {
            boolean isConditionValid = ((offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR)
                    || tlcDetailsOfferDAO.getNewClientDetailsByTlcOfferId(tlcOffer.getTlcOfferId()).size() > 0
                    || wlrOfferDetailsDAO.isVoipLinesUsedForOfferId(wlrOffers.getWlrOfferId()))
                    && !isCurrentRowWlr && !isCurrentRowTlc;

            aggiungiTlcButtonLayout.setVisibility(isConditionValid ? View.GONE : View.VISIBLE);
        }
    }


    private void isAggiungiVoipButtonShouldBeShown() {
        boolean isConditionValid = mainInfoLayout.getChildCount() > 0
                && !isCurrentRowWlr && !isCurrentRowTlc;

        aggiungiTlcButtonLayout.setVisibility(isConditionValid ? View.GONE : View.VISIBLE);
    }

//    private void isAggiungiVoipButtonShouldBeShown() {
//        if (offer != null && wlrOffers != null) {
//            boolean isConditionValid = (wlrOfferDetailsDAO.getNewClientDetailsByOfferId(wlrOffers.getWlrOfferId(), true).size() > 0
//                    && !isCurrentRowWlr && !isCurrentRowTlc);
//
//            aggiungiTlcButtonLayout.setVisibility(isConditionValid ? View.GONE : View.VISIBLE);
//        }
//    }


    private void saveTlcOfferDetails(TlcDetailsOffer tlcDetails) {
        Bundle localBundle = fisso.isChecked() ? (Bundle) fissoSpinner.getSelectedItem() : new Bundle();
        Bundle mobileBundle = mobile.isChecked() ? (Bundle) mobileSpinner.getSelectedItem() : new Bundle();
        if (!tlcDetails.isExistingClientOffer()) {
            tlcDetails.setLocalBundleId(localBundle.getBundleId() != 0 ? localBundle.getBundleId() : null);
            tlcDetails.setMobileBundleId(mobileBundle.getBundleId() != 0 ? mobileBundle.getBundleId() : null);
        }

        List<WlrOfferDetails> wlrOfferDetailses = wlrOfferDetailsDAO.getNewClientDetailsByOfferId(wlrOffers.getWlrOfferId(), true);
        if (wlrOfferDetailses.size() > 0) {
            wlrOffers.setMobileBundleId(mobileBundle.getBundleId());
            wlrOffers.setLocalBundleId(localBundle.getBundleId());
        }
        tlcDetails.setNumLines((Integer.valueOf(lines.getText().toString().trim())));
        tlcDetails.setTlcOfferId(tlcOffer.getTlcOfferId());
        tlcDetails.setModified(true);
        if (tlcDetails.getTlcOfferDetailsId() != 0) {
            tlcDetailsOfferDAO.update(tlcDetails);
        } else {
            tlcDetailsOfferDAO.insert(tlcDetails);
        }
        tlcDetailsOffer = tlcDetails;
        recalculateAdslSoloDati();
    }

    private void recalculateAdslSoloDati() {
        if (internetOffer != null && internetOffer.getInternetOfferId() != 0) {
            List<InternetDetailOffers> offersList = internetDetailOffersDAO.getInternetDetailsOfferByInternetOfferId(internetOffer.getInternetOfferId());
            for (InternetDetailOffers detailOffers : offersList) {
                Bundle adslBundle = bundleDAO.getBundleById(detailOffers.getBundleId());
                if (!isManuallSoloDatiMode(adslBundle.getCode())) {
                    List<Services> lineaSoloDatiList = ServicesDAOImpl.getServicesByFollowingParams(idCampagnaADSL, ServicesDAOImpl.TipoOpzioni.LINEA_SOLO_DATI_LNA);
                    if (!lineaSoloDatiList.isEmpty()) {
                        List<Services> servicesList = ServicesDAOImpl.getServicesByIdRange(detailOffers.getServiceId());
                        servicesList.removeAll(lineaSoloDatiList);
                        List<Integer> servicesId = new ArrayList<>();
                        for (Services service : servicesList) {
                            servicesId.add(service.getServiceId());
                        }
                        servicesId.add(AbstractJarUtil.defineLineaSoloDati(lineaSoloDatiList, adslBundle));
                        detailOffers.setServiceId(TextUtils.join(",", servicesId));
                    }
                    internetDetailOffersDAO.update(detailOffers);
                }
            }
        }
        cleanTable(adslLayout);
        drawADSLTable();
    }

    private void annulaTlcAction() {
        numeroLinesRTG.setSelection(0);
        numeroLinesISDN.setSelection(0);
        rtgCheckBox.setChecked(false);
        isdnCheckBox.setChecked(false);
        tipologiaDiLineaRTG.setSelection(0);
        tipologiaDiLineaISDN.setSelection(0);
        modificaWlrLayout.setVisibility(View.GONE);
        modificaTlcLayout.setVisibility(View.GONE);
        addRTGLineLayout.setVisibility(View.VISIBLE);
        addISNDLineLayout.setVisibility(View.VISIBLE);
        servicesListRtg.clear();

        servicesListISND.clear();
        operatoreSpinner.setSelection(0);
        reteSpinner.setSelection(0);
//        fissoSpinner.setSelection(0);
//        mobileSpinner.setSelection(0);
//        fisso.setChecked(false);
//        mobile.setChecked(false);
        lines.setText("");
        podTable.setClickable(true);
        isCurrentRowTlc = false;
        isCurrentRowWlr = false;
        cps.setEnabled(true);
        if (checkMode(VOIP)) {
            isAggiungiVoipButtonShouldBeShown();
        }
    }

    private void annulaAction() {
        cleanInternetFields();
//        currentAdslRow.setBackgroundColor(R.color.item_background_lite);
        modificaADSLLayout.setVisibility(View.GONE);
    }

    private boolean plusButtonAction(Spinner tipoLine, Spinner numeroLinea, boolean isModificaMod) {
        boolean result = false;
        if ((wlrOfferDetails.isExistingClientOffer() || tlcDetailsOffer.isExistingClientOffer() || fissoAndMobileValidation(true)) &&
                checkSpinnerSelection(true, tipoLine.getId()) & checkSpinnerSelection(true, numeroLinea.getId())
                        & checkSpinnerSelection(true, operatoreSpinner.getId()) & checkSpinnerSelection(true, reteSpinner.getId())) {
            checkCurrentLine();
            addWlrOfferToBaseIfNeeded();
            addWlrOfferDetailsToBaseIfNeeded();
            LineTypes lineTypes = (LineTypes) tipoLine.getSelectedItem();
            int numLine = ((LineNumbers) numeroLinea.getSelectedItem()).getLineNumberId();
            saveWlrOfferDetailsToBase(lineTypes, numLine);
            if (!isModificaMod) {
                drawTlcTable();
            } else {
                drawTlcTable();
            }
            tipoLine.setSelection(0);
            numeroLinea.setSelection(0);
            operatoreSpinner.setSelection(0);
            reteSpinner.setSelection(0);
            rtgCheckBox.setChecked(false);
            isdnCheckBox.setChecked(false);
            wlrOfferDetails = new WlrOfferDetails();
            result = true;
        }
        return result;
    }

    private void checkCurrentLine() {
        if (isCurrentRowTlc) {
            wlrOfferDetails = new WlrOfferDetails();
            tlcDetailsOfferDAO.delete(tlcDetailsOffer);
            if (tlcDetailsOfferDAO.getAllTlcDetailsByTlcOfferId(tlcOffer.getTlcOfferId()).size() < 1)
                tlcOffer = new TLCOffer();
        }

        if (isCurrentRowTlc && tlcDetailsOffer.isExistingClientOffer()) {
            wlrOfferDetails.setExistingClientOffer(true);
            wlrOfferDetails.setWlrName(tlcDetailsOffer.getTlcName());
            wlrOfferDetails.setCost(tlcDetailsOffer.getOfferCost());
            wlrOfferDetails.setCostIVA(tlcDetailsOffer.getOfferCostIva());
            wlrOfferDetails.setOwnLocalBundleId(tlcDetailsOffer.getLocalBundleId());
            wlrOfferDetails.setOwnMobileBundleId(tlcDetailsOffer.getMobileBundleId());
            wlrOfferDetails.setExistingClientCostVersion(tlcDetailsOffer.getCostVersion());
        }
    }

    private void setBundleClickableCondition(boolean isEnabled) {
        fisso.setClickable(isEnabled);
        mobile.setClickable(isEnabled);
        fissoSpinner.setClickable(isEnabled);
        mobileSpinner.setClickable(isEnabled);
    }

    private void saveWlrOfferDetailsToBase(LineTypes lineTypes, int numLine) {
        String emptyServices = "[]";
        wlrOfferDetails.setNumLines(numLine);
        wlrOfferDetails.setWlrOfferId(wlrOffers.getWlrOfferId());
        wlrOfferDetails.setRete(lineTypes != null ? Constants.WLR : VOIP);
        if (lineTypes != null) {
            wlrOfferDetails.setCarrierId(((Carriers) operatoreSpinner.getSelectedItem()).getCarrierId());
            wlrOfferDetails.setNetworkId(((Networks) reteSpinner.getSelectedItem()).getNetworkId());
            wlrOfferDetails.setLineId(lineTypes.getLineId());
            String serviceIds = lineTypes.getLineDesc().equals(RTG) ?
                    (servicesListRtg.size() > 0 && servicesListRtg.get(0) != null ? servicesListRtg.toString() : emptyServices) :
                    (servicesListISND.size() > 0 && servicesListISND.get(0) != null ? servicesListISND.toString() : emptyServices);
            wlrOfferDetails.setServicesId(serviceIds.substring(1, serviceIds.length() - 1));
        } else {
            wlrOfferDetails.setServicesId("");
        }
        wlrOfferDetails.setServiceAddictionNumber(numerazioneRelatedWithService != null ?
                ((AdditionalNumbers) numerazioneRelatedWithService.getSelectedItem()).getValueAdditionalNumber() : null);
        wlrOfferDetails.setModified(true);
        if (wlrOfferDetails.getWlrOfferDettId() == 0) {
            wlrOfferDetailsDAO.insert(wlrOfferDetails);
        } else {
            wlrOfferDetailsDAO.update(wlrOfferDetails);
        }

        Integer localBundleId = fisso.isChecked() ? ((Bundle) fissoSpinner.getSelectedItem()).getBundleId() : null;
        Integer mobileBundleId = mobile.isChecked() ? ((Bundle) mobileSpinner.getSelectedItem()).getBundleId() : null;

        List<TlcDetailsOffer> tlcDetailsOffers = tlcDetailsOfferDAO.getNewClientDetailsByTlcOfferId(tlcOffer.getTlcOfferId());
        if (tlcDetailsOffers.size() > 0) {
            TlcDetailsOffer detailsOffer = tlcDetailsOffers.get(0);
            detailsOffer.setLocalBundleId(localBundleId);
            detailsOffer.setMobileBundleId(mobileBundleId);
            tlcDetailsOfferDAO.update(detailsOffer);
        }
        wlrOffers.setLocalBundleId(localBundleId);
        wlrOffers.setMobileBundleId(mobileBundleId);
        recalculateAdslSoloDati();
    }

//    private double getWlrCost(WlrOfferDetails wlrOfferDetails, LineTypes lineTypes) {
//        ClientDetailTlcOffers clientDetailTlcOffers = new ClientDetailsTlcOffersDAOImpl().getClientDetailTlcOffersByLine(wlrOfferDetails.getWlrName());
//        double cost = wlrOfferDetails.isExistingClientOffer() ? clientDetailTlcOffers.getLocalOfferCost() + clientDetailTlcOffers.getMobileOfferCost() : 0.00;
//        double costIVA = wlrOfferDetails.isExistingClientOffer() ? clientDetailTlcOffers.getLocalOfferCostIva() + clientDetailTlcOffers.getMobileOfferCostIva() : 0.00;
//        AdditionalNumbersDAOImpl additionalNumbersDAO = new AdditionalNumbersDAOImpl();
//        NetworksDAOImpl networksDAO = new NetworksDAOImpl();
//        ServicesDAOImpl servicesDAO = new ServicesDAOImpl();
//        Networks currentNetwork = networksDAO.getNetworkById(wlrOfferDetails.getNetworkId());
//        cost += (lineNumbersDAO.getLineNumbersById(wlrOfferDetails.getNumLines()).getLineNumberDesc() * (lineTypes.getLineCost() + currentNetwork.getCost()));
//        costIVA += (lineNumbersDAO.getLineNumbersById(wlrOfferDetails.getNumLines()).getLineNumberDesc() * (lineTypes.getLineCostIVA() + currentNetwork.getCostIva()));
//        List<Services> serviceses = servicesDAO.getServicesByIds(wlrOfferDetails.getServicesId().split(", "));
//        wlrOfferDetails.setAdditionalNumberCostVersion(additionalNumbersDAO.getAdditionalNumbersById(1).getVersion());
//        wlrOfferDetails.setServiceCostVersion(servicesDAO.getServiceById(1).getVersion());
//        for (Services services : serviceses) {
//            boolean isAddictionalNumberPresent = services.getServiceDesc().equals("Numerazione aggiuntiva");
//            AdditionalNumbers additionalNumbers = isAddictionalNumberPresent ? additionalNumbersDAO.getAdditionalNumbersById(wlrOfferDetails.getServiceAddictionNumber()) : new AdditionalNumbers();
////            wlrOfferDetails.setServiceCostVersion(services.getVersion());
////            wlrOfferDetails.setAdditionalNumberCostVersion(additionalNumbers.getVersion());
//            cost += ((double) lineNumbersDAO.getLineNumbersById(wlrOfferDetails.getNumLines()).getLineNumberDesc() * (isAddictionalNumberPresent ? additionalNumbers.getAdditionalNumberCost() : services.getCost()));
//            costIVA += ((double) lineNumbersDAO.getLineNumbersById(wlrOfferDetails.getNumLines()).getLineNumberDesc() * (isAddictionalNumberPresent ? additionalNumbers.getAdditionalNumberCostIVA() : services.getCostIVA()));
//        }
//        wlrOfferDetails.setCost(cost);
//        wlrOfferDetails.setCostIVA(costIVA);
//        return cost;
//    }

    private void drawTlcTableRow(final LineTypes lineTypes) {
        podTable.setVisibility(View.VISIBLE);

        final boolean isNewClient = lineTypes != null ? !wlrOfferDetails.isExistingClientOffer() : !tlcDetailsOffer.isExistingClientOffer();

        LinearLayout existingClientRow = (LinearLayout) getLayoutInflater().inflate(R.layout.existing_lines_table, null);
        LinearLayout newClientInfoLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.new_line_layout_tlc, null);
        final LinearLayout newClientDeleteLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.delete_item_for_tlc, null);

        final TextView lineType = (TextView) (isNewClient ? newClientInfoLayout.findViewById(R.id.lineType) : existingClientRow.findViewById(R.id.lineType));
        TextView numLines = (TextView) (isNewClient ? newClientInfoLayout.findViewById(R.id.numLines) : existingClientRow.findViewById(R.id.numLines));
        TextView numerazioneAggiuntive = (TextView) (isNewClient ? newClientInfoLayout.findViewById(R.id.numerazioneAggiuntive) : existingClientRow.findViewById(R.id.numerazioneAggiuntive));
        TextView rete = (TextView) (isNewClient ? newClientInfoLayout.findViewById(R.id.rete) : existingClientRow.findViewById(R.id.rete));
        TextView serviceDesc = (TextView) (isNewClient ? newClientInfoLayout.findViewById(R.id.services) : existingClientRow.findViewById(R.id.services));
        TextView localBundle = (TextView) (isNewClient ? findViewById(R.id.commonLocalBundle) : existingClientRow.findViewById(R.id.localBundle));
        TextView mobileBundle = (TextView) (isNewClient ? findViewById(R.id.commonMobileBundle) : existingClientRow.findViewById(R.id.mobileBundle));

        final TextView id = (TextView) (isNewClient ? newClientDeleteLayout.findViewById(R.id.lineIdField) : existingClientRow.findViewById(R.id.lineIdField));

        String name = "Linea " + (mainInfoLayout.getChildCount() + 1);
        String reteDesc = String.valueOf(lineTypes != null ? wlrOfferDetails.getRete() : tlcDetailsOffer.getRete());
        rete.setText(reteDesc);
        defineOfferLineaType(reteDesc);
        String lyneType = lineTypes != null ? lineTypes.getLineDesc() : "";
        String numeroLines = lineTypes != null ? String.valueOf((lineNumbersDAO.getLineNumbersById(wlrOfferDetails.getNumLines()).getLineNumberDesc())) :
                String.valueOf(tlcDetailsOffer.getNumLines());
        String numerazioneStr = lineTypes != null ? wlrOfferDetails.getServiceAddictionNumber() != null ? String.valueOf(wlrOfferDetails.getServiceAddictionNumber()) : "" : "";
        String wlrServices = lineTypes != null ? getAllServiceDesc(wlrOfferDetails) : "";
        String currentEntityId = String.valueOf(lineTypes != null ? wlrOfferDetails.getWlrOfferDettId() : tlcDetailsOffer.getTlcOfferDetailsId());
        String localBundleDesc = bundleDAO.getBundleDescByBundleId(lineTypes != null ?
                wlrOffers.getLocalBundleId() != null ? wlrOffers.getLocalBundleId() : 0
                : tlcDetailsOffer.getLocalBundleId() != null ? tlcDetailsOffer.getLocalBundleId() : 0);
        String mobileBundleDesc = bundleDAO.getBundleDescByBundleId(lineTypes != null ?
                wlrOffers.getMobileBundleId() != null ? wlrOffers.getMobileBundleId() : 0
                : tlcDetailsOffer.getMobileBundleId() != null ? tlcDetailsOffer.getMobileBundleId() : 0);
        boolean isNumLinesShouldBeVisible = lineTypes != null ? wlrOfferDetails.isModified() : tlcDetailsOffer.isModified();

        lineType.setText(lyneType);
        numLines.setText(isNumLinesShouldBeVisible ? numeroLines : "");
        numerazioneAggiuntive.setText(numerazioneStr);
        serviceDesc.setText(wlrServices);
        id.setText(currentEntityId);
        localBundle.setText(localBundleDesc);
        mobileBundle.setText(mobileBundleDesc);

        if (lineTypes != null && wlrOfferDetails.isExistingClientOffer()) {
            localBundle.setText(wlrOfferDetails.getOwnLocalBundleId() != null ?
                    (wlrOfferDetails.getOwnLocalBundleId() == 1 ? "Flat" : String.valueOf(wlrOfferDetails.getOwnLocalBundleId())) : "");
            mobileBundle.setText(wlrOfferDetails.getOwnMobileBundleId() != null ?
                    String.valueOf(wlrOfferDetails.getOwnMobileBundleId()) : "");
        }
        if (lineTypes == null && tlcDetailsOffer.isExistingClientOffer()) {
            localBundle.setText(tlcDetailsOffer.getLocalBundleId() != null ?
                    (tlcDetailsOffer.getLocalBundleId() == 1 ? "Flat" : String.valueOf(tlcDetailsOffer.getLocalBundleId())) : "");
            mobileBundle.setText(tlcDetailsOffer.getMobileBundleId() != null ?
                    String.valueOf(tlcDetailsOffer.getMobileBundleId()) : "");
        }

        LinearLayout existingCurrentRow = (LinearLayout) (isNewClient ? newClientInfoLayout : existingClientRow.findViewById(R.id.wlrCurrentRow));
        existingCurrentRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineClickAction(v, id, lineTypes, isNewClient, reteDesc.equals(VOIP));
            }
        });

        LinearLayout deleteButtonLayout = (LinearLayout) (isNewClient ? newClientDeleteLayout.findViewById(R.id.deleteLayout) : existingClientRow.findViewById(R.id.deleteLayout));
        deleteButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCurrentRowWlr && !isCurrentRowTlc) {
                    deleteCurrentEntity(lineTypes, Integer.valueOf(id.getText().toString().trim()));
                    if (existingClientLayout.getChildCount() > 0 || mainInfoLayout.getChildCount() > 0)
                        podTable.setVisibility(View.GONE);
                    drawTlcTable();
                    if (!checkMode(WLR)) {
                        isAggiungiVoipButtonShouldBeShown();
                    }
                    validateLineaSoloDatiCheckBox();
                }
                serviceValidation();
            }
        });

        if (isNewClient) {
            TextView lineCount = (TextView) newClientInfoLayout.findViewById(R.id.linea);
            lineCount.setText(String.valueOf(name));
            mainInfoLayout.addView(newClientInfoLayout);
            bundleInfoLayout.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(TLC.this, "" +lineType.getHeight(), Toast.LENGTH_LONG).show();
                    newClientDeleteLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, lineType.getHeight()));
                }
            }, 50);

            if (lineTypes != null) {
                wlrOfferDetails.setWlrName(name);
                new WlrOfferDetailsDAOImpl().update(wlrOfferDetails);
            } else {
                tlcDetailsOffer.setTlcName(name);
                new TlcDetailsOfferDAOImpl().update(tlcDetailsOffer);
            }
            deleteLineLayout.addView(newClientDeleteLayout);
        } else {
            setLineeName(existingClientRow);
            existingClientLayout.addView(existingClientRow);
        }
        annulaTlcAction();
        checkBundleAlreadyChoosed();
        checkForCommonWlrBundle();
//        if (lineTypes != null) checkForCommonWlrBundle();
    }

    private void defineOfferLineaType(String reteDesc) {
        isVoipLineaMode = reteDesc.equalsIgnoreCase(VOIP);
    }

    private boolean isLineaModeValid(boolean showErrorMessage) {
        boolean isValid = mainInfoLayout.getChildCount() == 0
                || (isVoipLineaMode &&
                ((String) voceModeSpinner.getSelectedItem()).equalsIgnoreCase(VOIP))
                || (!isVoipLineaMode &&
                !((String) voceModeSpinner.getSelectedItem()).equalsIgnoreCase(VOIP));
        if (!isValid && showErrorMessage) {
            Toast.makeText(this, getString(R.string.wrong_linee_type_message), Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    private void setLineeName(LinearLayout existingClientRow) {
        LinearLayout lineeLayout = (LinearLayout) existingClientRow.findViewById(R.id.lineeNameContainer);
        List<WlrOfferDetails> wlrOfferDetailses = new WlrOfferDetailsDAOImpl().getWlrOfferDetailsByPhoneNumber(wlrOffers.getWlrOfferId(), wlrOfferDetails.getWlrName());
        LayoutInflater inflater = getLayoutInflater();
        for (WlrOfferDetails wlrOfferDet : wlrOfferDetailses) {
            TextView lineeName = (TextView) inflater.inflate(R.layout.linee_textview, lineeLayout, false);
            lineeName.setText(wlrOfferDet.getWlrName());
            lineeLayout.addView(lineeName);
        }
    }

    private void lineClickAction(View v, TextView id, LineTypes lineTypes, boolean isNewLine, boolean isVoip) {
        if (!isCurrentRowWlr && !isCurrentRowTlc) {
            LinearLayout currentWlrRow = (LinearLayout) v;
            currentWlrRow.setBackgroundColor(getResources().getColor(R.color.item_background_blue));
            if (isNewLine)
                bundleInfoLayout.setBackgroundColor(getResources().getColor(R.color.item_background_blue));
            int currentEntityId = Integer.valueOf(id.getText().toString().trim());

            if (lineTypes != null || isVoip) {
                isVoipMode = isVoip;
                wlrOfferDetails = wlrOfferDetailsDAO.getWlrOfferDetailsById(currentEntityId);
                showRequiredWlr(lineTypes);
                if (!isVoip) {
                    checkBundleAlreadyChoosed();
                }
            } else {
                tlcDetailsOffer = tlcDetailsOfferDAO.getDetailsTlcOfferById(currentEntityId);
                setVoceMode(CPS);
                setTlcPodValue();
            }
        }
    }

    private void setVoceMode(String mode) {
        for (int i = 0; i < voceModeSpinner.getCount(); i++) {
            String currentSpinnerItem = (String) voceModeSpinner.getItemAtPosition(i);
            if (currentSpinnerItem.equals(mode)) {
                voceModeSpinner.setSelection(i);
                break;
            }
        }
    }

    private void showRequiredWlr(LineTypes lineTypes) {
        if (wlrOfferDetails.getRete().equals(CPS)) {
            isSpecificCPS = true;
            setAlreadyClientsCPS();
            setBundleClickableCondition(false);
        } else {
            setVoceMode(wlrOfferDetails.getRete().equals(VOIP) ? VOIP : WLR);
            setWlrPodValuesToView(lineTypes);
        }
    }

    private void setAlreadyClientsCPS() {
        setVoceMode(CPS);
        addISNDLineLayout.setVisibility(View.GONE);
        addRTGLineLayout.setVisibility(View.GONE);
        aggiungiTlcButtonLayout.setVisibility(View.GONE);
        modificaTlcLayout.setVisibility(View.VISIBLE);
        podTable.setClickable(false);
    }

    private void deleteCurrentEntity(LineTypes lineTypes, int id) {
        if (lineTypes != null) {
            removeWlrDetails(id);
            if (wlrOfferDetailsDAO.getNewClientDetailsByOfferId(wlrOffers.getWlrOfferId(), true).size() == 0) {
                wlrOffers.setLocalBundleId(null);
                wlrOffers.setMobileBundleId(null);
            }
            if (wlrOfferDetailsDAO.getWlrOfferDetailsByWlrOfferId(wlrOffers.getWlrOfferId(), true).size() < 1)
                wlrOffers = new WlrOffers();
            checkForCommonWlrBundle();
        } else {
            tlcDetailsOfferDAO.deleteTlcOfferDetailsById(id);
            if (tlcDetailsOfferDAO.getAllTlcDetailsByTlcOfferId(tlcOffer.getTlcOfferId()).size() < 1)
                tlcOffer = new TLCOffer();
            isAggiungiTlcButtonShouldBeShown();
        }
    }

    private void removeWlrDetails(int id) {
        WlrOfferDetails wlrDetailsParent = wlrOfferDetailsDAO.getWlrOfferDetailsById(id);
        if (wlrDetailsParent.isExistingClientOffer()) {
            wlrOfferDetailsDAO.deleteWlrOfferDetailsByMainWlrOfferIdAndLineeName(wlrOffers.getWlrOfferId(), wlrDetailsParent.getWlrName());
        } else {
            wlrOfferDetailsDAO.deleteWlrOfferDetailsByWlrOfferId(id);
        }
    }

//    private void clearTlcFields() {
//        fissoSpinner.setSelection(0);
//        mobileSpinner.setSelection(0);
//        fisso.setChecked(false);
//        mobile.setChecked(false);
//        lines.setText("");
//    }

    private void setTlcPodValue() {
        if (!tlcDetailsOffer.isExistingClientOffer()) {
            if (tlcDetailsOffer.getLocalBundleId() != null) {
                fisso.setChecked(true);
                for (int i = 0; i < fissoSpinner.getCount(); i++) {
                    Bundle currentBundle = (Bundle) fissoSpinner.getItemAtPosition(i);
                    if (currentBundle.getBundleId() == tlcDetailsOffer.getLocalBundleId()) {
                        fissoSpinner.setSelection(i);
                        break;
                    }
                }
            }

            if (tlcDetailsOffer.getMobileBundleId() != null) {
                mobile.setChecked(true);
                for (int i = 0; i < mobileSpinner.getCount(); i++) {
                    Bundle currentBundle = (Bundle) mobileSpinner.getItemAtPosition(i);
                    if (currentBundle.getBundleId() == tlcDetailsOffer.getMobileBundleId()) {
                        mobileSpinner.setSelection(i);
                        break;
                    }
                }
            }
        } else {
            clearBundleFields();
        }
        aggiungiTlcButtonLayout.setVisibility(View.GONE);
        lines.setText(tlcDetailsOffer.isModified() ? String.valueOf(tlcDetailsOffer.getNumLines()) : "");
        addISNDLineLayout.setVisibility(View.GONE);
        addRTGLineLayout.setVisibility(View.GONE);
        modificaTlcLayout.setVisibility(View.VISIBLE);
        podTable.setClickable(false);
        isCurrentRowTlc = true;
    }

    private void clearBundleFields() {
        setBundleClickableCondition(false);
        fissoSpinner.setSelection(0);
        mobileSpinner.setSelection(0);
//        fisso.setChecked(false);
        mobile.setChecked(false);
    }

    private void setWlrPodValuesToView(LineTypes lineTypes) {
        isLineTypeModificaMode = true;
//        cps.setClickable(false);

        if (lineTypes != null && lineTypes.getLineId() != 0) {
            for (int i = 1; i < operatoreSpinner.getCount(); i++) {
                Carriers currentSpinnerItem = (Carriers) operatoreSpinner.getItemAtPosition(i);
                if (currentSpinnerItem.getCarrierId() == wlrOfferDetails.getCarrierId()) {
                    operatoreSpinner.setSelection(i);
                    break;
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i < reteSpinner.getCount(); i++) {
                        Networks currentSpinnerItem = (Networks) reteSpinner.getItemAtPosition(i);
                        if (currentSpinnerItem.getNetworkId() == wlrOfferDetails.getNetworkId()) {
                            reteSpinner.setSelection(i);
                            break;
                        }
                    }

                    if (wlrOfferDetails.isExistingClientOffer()) {
                        setBundleClickableCondition(false);
                        cps.setEnabled(false);
                        setAlreadyClientsFieldsLock(true);
                    }
                }
            }, 1000);

            boolean isRTG = lineTypes.getLineDesc().equals(RTG);
            Spinner tipologiaLineeSpinner = isRTG ? tipologiaDiLineaRTG : tipologiaDiLineaISDN;
            for (int i = 1; i < tipologiaLineeSpinner.getCount(); i++) {
                LineTypes currentLineType = (LineTypes) tipologiaLineeSpinner.getItemAtPosition(i);
                if (currentLineType.getLineId() == wlrOfferDetails.getLineId()) {
                    tipologiaLineeSpinner.setSelection(i);
                    break;
                }
            }

//            if (wlrOfferDetails.isModified()) {
            Spinner numLinesSpinner = isRTG ? numeroLinesRTG : numeroLinesISDN;
            for (int i = 1; i < numLinesSpinner.getCount(); i++) {
                LineNumbers currentLineNumber = (LineNumbers) numLinesSpinner.getItemAtPosition(i);
                if (currentLineNumber.getLineNumberId() == wlrOfferDetails.getNumLines()) {
                    numLinesSpinner.setSelection(i);
                    if (wlrOfferDetails.isExistingClientOffer()) {
                        (!isRTG ? numeroLinesRTG : numeroLinesISDN).setSelection(i);
                    }
                    break;
                }
            }
//            }

            if (isRTG) rtgCheckBox.setChecked(true);
            if (!isRTG) isdnCheckBox.setChecked(true);

            if (isRTG) {
                servicesListRtg = !wlrOfferDetails.getServicesId().equals("") ?
                        new ArrayList<>(Arrays.asList(getIntArrayFromStringArray(wlrOfferDetails.getServicesId().split(", ")))) :
                        new ArrayList<Integer>();
            } else {
                servicesListISND = (!wlrOfferDetails.getServicesId().equals("") ?
                        new ArrayList<>(Arrays.asList(getIntArrayFromStringArray(wlrOfferDetails.getServicesId().split(", ")))) :
                        new ArrayList<Integer>());
            }

            LinearLayout currentServiceTable = isRTG ? servicesRtg : servicesIdsn;
            currentServiceTable.removeAllViews();
            drawTlcServicesTable(Constants.WLR, String.valueOf(lineTypes.getLineId()), currentServiceTable);
            isWlrAlreadyFilled = true;

        }
        if (!isVoipMode) {
            aggiungiTlcButtonLayout.setVisibility(View.GONE);
        }
        addISNDLineLayout.setVisibility(View.GONE);
        addRTGLineLayout.setVisibility(View.GONE);
        aggiungiTlcButtonLayout.setVisibility(View.GONE);
        modificaWlrLayout.setVisibility(View.VISIBLE);
        podTable.setClickable(false);
        isCurrentRowWlr = true;
    }

    private Integer[] getIntArrayFromStringArray(String[] strArray) {
        Integer parsedIntegers[] = new Integer[strArray.length];
        if (strArray[0].length() > 0) {
            for (int i = 0; i < strArray.length; i++) {
                parsedIntegers[i] = Integer.parseInt(strArray[i]);
            }
        }
        return parsedIntegers;
    }

    private void addWlrOfferToBaseIfNeeded() {
        if (wlrOffers.getWlrOfferId() == 0) {
            wlrOffers.setBundleCost(0.00);
            wlrOffers.setBundleCostIVA(0.00);
            wlrOffers.setOfferCost(0.00);
            wlrOffers.setOfferCostIVA(0.00);
            wlrOffersDAO.insert(wlrOffers);

        }
    }

    private void addTlcOfferToBaseIfNeeded() {
        if (tlcOffer.getTlcOfferId() == 0) {
            tlcOfferDAO.insert(tlcOffer);
        }
    }

//    private void addTlcOfferDetailsToBaseIfNeeded() {
//        if (tlcDetailsOffer.getTlcOfferId() == 0) {
//            tlcDetailsOffer.setLocalBundleId(null);
//            tlcDetailsOffer.setMobileBundleId(null);
//            tlcDetailsOfferDAO.insert(tlcDetailsOffer);
//        }
//    }

    private void addWlrOfferDetailsToBaseIfNeeded() {
        if (wlrOfferDetails.getWlrOfferDettId() == 0) {
            wlrOfferDetails.setWlrOfferId(wlrOffers.getWlrOfferId());
            wlrOfferDetailsDAO.insert(wlrOfferDetails);
        }
    }

    private void fillLineTypesSpinnerByType(String type, final Spinner spinner) {
        LineTypeSpinnerService typeSpinnerService = new LineTypeSpinnerService(this, new AbstractService.OnTaskCompleted<LineTypeSpinnerService>() {
            @Override
            public void onTaskCompleted(LineTypeSpinnerService service) {
                setSpinner(spinner, service.getResult());
            }
        });

        typeSpinnerService.setLineType(type);
        typeSpinnerService.setOffer(offer);
        typeSpinnerService.execute();
    }

    private void fillViewsWithContent() {
        fillLineTypesSpinnerByType(RTG, tipologiaDiLineaRTG);
        fillLineTypesSpinnerByType(ISDN, tipologiaDiLineaISDN);

        new OperatoreSpinner(this, new AbstractService.OnTaskCompleted<OperatoreSpinner>() {
            @Override
            public void onTaskCompleted(OperatoreSpinner service) {
                setSpinner(operatoreSpinner, service.getResult());
            }
        }).execute(getConfiguratorType(offer.getConfiguratorType()));

        new NumeroLineeSpinner(this, new AbstractService.OnTaskCompleted<NumeroLineeSpinner>() {
            @Override
            public void onTaskCompleted(NumeroLineeSpinner service) {
                setSpinner(numeroLinesRTG, service.getResult());
                setSpinner(numeroLinesISDN, service.getResult());
                new Handler().postDelayed(TLC.this, 700);
            }
        }).execute();

        new AdslSpinner(this, new AbstractService.OnTaskCompleted<AdslSpinner>() {
            @Override
            public void onTaskCompleted(AdslSpinner service) {
                setSpinner(adsl, service.getResult());
            }
        }).execute();

//        String configuratoreType = getConfiguratorType(offer.getConfiguratorType());
//        new VersoFissoSpinner(this, new AbstractService.OnTaskCompleted<VersoFissoSpinner>() {
//            @Override
//            public void onTaskCompleted(VersoFissoSpinner service) {
//                setSpinner(fissoSpinner, service.getResult());
//            }
//        }).execute(configuratoreType);
//
//        new VersoMobileSpinner(this, new AbstractService.OnTaskCompleted<VersoMobileSpinner>() {
//            @Override
//            public void onTaskCompleted(VersoMobileSpinner service) {
//                setSpinner(mobileSpinner, service.getResult());
//                new Handler().postDelayed(TLC.this, 700);
//            }
//        }).execute(configuratoreType);

//        drawTlcServicesTable(Constants.WLR, RTG, servicesRtg);
//        drawTlcServicesTable(Constants.WLR, ISDN, servicesIdsn);
        fillAdslServiceSpinner(ServicesDAOImpl.TipoOpzioni.IP_AGGIUNTIVI, servicesAdsl);
    }

    private void initializeViews() {
        mobileSpinner = (Spinner) findViewById(R.id.versoMobile);
        fissoSpinner = (Spinner) findViewById(R.id.versoFisso);
        voceModeSpinner = (Spinner) findViewById(R.id.voceMode);
        cps = (RadioButton) findViewById(R.id.cps);
        wlr = (RadioButton) findViewById(R.id.wlr);
        mobile = (CheckBox) findViewById(R.id.mobileCheckbox);
        fisso = (CheckBox) findViewById(R.id.fissoCheckbox);
        adsl = (Spinner) findViewById(R.id.adslSpinner);
//        router = (CheckBox) findViewById(R.id.router);
        numSimSpinner = (Spinner) findViewById(R.id.numsim);
        minutesMeseSpinner = (Spinner) findViewById(R.id.minutes);
        smsMeseSpinner = (Spinner) findViewById(R.id.sms);
        smsMeseSpinner.setEnabled(false);
        gigabyteSpinner = (Spinner) findViewById(R.id.gigabytes);
        gigabyteSpinner.setEnabled(false);
        smsCheck = (CheckBox) findViewById(R.id.smscheck);
        gigabyteCheck = (CheckBox) findViewById(R.id.gigabytecheck);
        tipologiaDiLineaRTG = (Spinner) findViewById(R.id.lineaRTGSpinner);
        tipologiaDiLineaISDN = (Spinner) findViewById(R.id.lineaISDNSpinner);
        lines = (EditText) findViewById(R.id.lines);
        plusRTG = (ImageButton) findViewById(R.id.plusRTG);
        plusISDN = (ImageButton) findViewById(R.id.plusISDN);
        versoLayout = (LinearLayout) findViewById(R.id.versoPart);
        wlrLayout = (LinearLayout) findViewById(R.id.wlrPart);
        modificaADSL = (Button) findViewById(R.id.modificaButton);
        modificaADSLLayout = (LinearLayout) findViewById(R.id.modificaADSLLayout);
        addADSLButtonsLayout = (LinearLayout) findViewById(R.id.addADSLButtonlayout);
        annulaADSL = (Button) findViewById(R.id.annulaButton);
        adslLayout = (LinearLayout) findViewById(R.id.ADSLTable);
        servicesIdsn = (LinearLayout) findViewById(R.id.idsnServices);
        servicesRtg = (LinearLayout) findViewById(R.id.rtgServices);
        servicesAdsl = (LinearLayout) findViewById(R.id.servicesAdsl);
        lineaSoloDatiContainer = (LinearLayout) findViewById(R.id.lineaSoloDatiContainer);
        adslServicesSpinner = (Spinner) findViewById(R.id.adslServicesSpinner);
        lineaSoloDatiCheckBox = (CheckBox) findViewById(R.id.lineaSoloDatiCheckBox);
        operatoreSpinner = (Spinner) findViewById(R.id.operatoreSpinner);
        reteSpinner = (Spinner) findViewById(R.id.reteSpinner);
        numeroLinesRTG = (Spinner) findViewById(R.id.numeroLineeRTGSpinner);
        numeroLinesISDN = (Spinner) findViewById(R.id.numeroLineeIsdnSpinner);
        rtgCheckBox = (CheckBox) findViewById(R.id.rtgCheckBox);
        isdnCheckBox = (CheckBox) findViewById(R.id.isdnCheckBox);
        layoutRTG = (LinearLayout) findViewById(R.id.RTGLayout);
        layoutISDN = (LinearLayout) findViewById(R.id.ISDNLayout);
        podTable = (LinearLayout) findViewById(R.id.podTable);
        annulaWlrLine = (Button) findViewById(R.id.annulaWlr);
        modificaWlrLayout = (LinearLayout) findViewById(R.id.wlrModificaButtonLayout);
        addRTGLineLayout = (LinearLayout) findViewById(R.id.addRTGLineLayout);
        addISNDLineLayout = (LinearLayout) findViewById(R.id.addISDNLineLayout);
        aggiungiTlcButtonLayout = (LinearLayout) findViewById(R.id.agguingiTlcButtonLayout);
        modificaTlcLayout = (LinearLayout) findViewById(R.id.modificaTlcButtonLayout);
        annulaTlc = (Button) findViewById(R.id.annulaTlc);
        mainInfoLayout = (LinearLayout) findViewById(R.id.mainInfoLayout);
        existingClientLayout = (LinearLayout) findViewById(R.id.existingTlcLines);
        deleteLineLayout = (LinearLayout) findViewById(R.id.deleteLineLayout);
        bundleInfoLayout = (LinearLayout) findViewById(R.id.bundleInfoLayout);
        continuaButton = (ImageButton) findViewById(R.id.forwardButtonEnabled);
        continuaButtonDisabled = (ImageButton) findViewById(R.id.forwardButtonDisabled);
        calcolaButton = (Button) findViewById(R.id.calcola6);
        calcolaButtonDisabled = (Button) findViewById(R.id.calcola6Disabled);

        defineIdCampagna(offer.getConfiguratorType(), offer.getIsOldTlcOfferUsed());
    }

    private void defineIdCampagna(int configuratorType, boolean isAlreadyClient) {
        idCampagnaVoce = IdCampganaUtils.getOfferIdCampagnaByServiceType(IdCampganaUtils.ServiceType.VOCE, configuratorType, isAlreadyClient);
        idCampagnaADSL = IdCampganaUtils.getOfferIdCampagnaByServiceType(IdCampganaUtils.ServiceType.ADSL, configuratorType, isAlreadyClient);
    }

    private void calculation() {
        if (!serviceValidation()) {
            Toast.makeText(this, "Non tutti i campi sono riempiti", Toast.LENGTH_SHORT).show();
            return;
        }

        if (internetFlag) internetOfferComputation();

//        if (mobileFlag) {
//            mobileCalculation();
//            offer.setMobileOfferId(mobileOfferDAO.getMobileOfferId(mobileOffer));
//        }

        if (tlcFlag) {
            offer.setWlrOfferId(wlrOffers.getWlrOfferId() != 0 ? wlrOffers.getWlrOfferId() : null);
            offer.setTlcOfferId(tlcOffer.getTlcOfferId() != 0 ? tlcOfferDAO.getTLCOfferId(tlcOffer) : null);
            cpsPartCalculation();
            wlrCalculation();
//            updateOfferInfo();
        }
        updateOfferInfo();
//        new SelectWhatToShowDialog(this);
    }

//    private void setSforamentoResultToWlrOffer(ResultSforamentoDTO resultSforamentoDTO) {
//        if (offer.getWlrOfferId() != null) {
//            wlrOffers.setCodicePmcPS(resultSforamentoDTO.getCodice());
//            wlrOffers.setPs(String.valueOf(resultSforamentoDTO.getPrezzoSforamento()));
//            wlrOffers.setPmc(String.valueOf(resultSforamentoDTO.getPrezzoMancatoConsumo()));
//            wlrOffersDAO.update(wlrOffers);
//        }
//    }
//
//    private void setSforamentoResultToTlcOffer(ResultSforamentoDTO resultSforamentoDTO) {
//        if (offer.getTlcOfferId() != null) {
//            tlcOffer.setCodicePmcPS(resultSforamentoDTO.getCodice());
//            tlcOffer.setPs(String.valueOf(resultSforamentoDTO.getPrezzoSforamento()));
//            tlcOffer.setPmc(String.valueOf(resultSforamentoDTO.getPrezzoMancatoConsumo()));
//            tlcOfferDAO.update(tlcOffer);
//        }
//    }

    private void updateOfferInfo() {
        MyApplication.set("offerEntity", offer);
        new OfferDAOImpl().update(offer);
//        Intent intent = new Intent(this, Canone.class);
//        intent.putExtra("ChoiceCode", 1);
//        startActivity(intent);
    }


    private boolean addAdslValidation(boolean isAnimated) {
        boolean result = checkSpinnerSelection(isAnimated, adsl.getId());
        if (result) {
            changeViewVisibility(R.id.addADSLDisabled, R.id.addADSLEnabled);
        } else {
            changeViewVisibility(R.id.addADSLEnabled, R.id.addADSLDisabled);
        }
        return result;
    }

    private boolean addTlcValidation(boolean isAnimated) {
        boolean result = (checkMode(CPS) && tlcVersoFissoAndMobileValidation(isAnimated)) || checkMode(VOIP);
        if (result) {
            changeViewVisibility(R.id.addTlcButtonDisabled, R.id.addTlcButtonEnabled);
        } else {
            changeViewVisibility(R.id.addTlcButtonEnabled, R.id.addTlcButtonDisabled);
        }
        return result;
    }

    private void addAdsl(boolean isModificaMod) {
        if (addAdslValidation(false)) {
            setInternetOfferToBaseIfItNeeded();
            setInternetOfferDetailsIfItNeeded();
            internetCalculation(isModificaMod);
        }
    }

    private void setInternetOfferDetailsIfItNeeded() {
        if (internetDetailOffers.getInternetDetailOfferId() == 0) {
            internetDetailOffers.setInternetOfferId(internetOffer.getInternetOfferId());
            internetDetailOffersDAO.insert(internetDetailOffers);
        }
    }

    private void setInternetOfferToBaseIfItNeeded() {
        if (internetOffer.getInternetOfferId() == 0) {
            internetOfferDAO.insert(internetOffer);
        }
    }

    private void cpsPartCalculation() {
        tlcOfferDAO.update(tlcOffer);
        MyApplication.set("TLC", tlcOffer);
        MyApplication.set("ArchivioTLCOffer", tlcOffer);
    }

    private void wlrCalculation() {
        wlrOffersDAO.update(wlrOffers);
        MyApplication.set("WlrOffer", wlrOffers);
    }

    private void internetOfferComputation() {
        internetOfferDAO.update(internetOffer);
        offer.setInternetOfferId(internetOffer.getInternetOfferId());
        MyApplication.set("Internet", internetOffer);
    }

    private void internetCalculation(boolean isModificaMod) {
        Bundle adslBundle = (Bundle) adsl.getSelectedItem();
        internetDetailOffers.setInternetOfferId(internetOffer.getInternetOfferId());
        internetDetailOffers.setBundleId(adslBundle.getBundleId());
//        internetDetailOffers.setRouter(router.isChecked() ? 1 : 0);
        internetDetailOffers.setRouter(0);
        List<Integer> servicesId = new ArrayList<>();

        if (checkSpinnerSelection(false, adslServicesSpinner.getId()))
            servicesId.add(((Services) adslServicesSpinner.getSelectedItem()).getServiceId());

        if ((lineaSoloDatiContainer.getVisibility() == View.VISIBLE && lineaSoloDatiCheckBox.isChecked()) ||
                lineaSoloDatiContainer.getVisibility() == View.GONE) {
            List<Services> lineaSoloDatiList = ServicesDAOImpl.getServicesByFollowingParams(idCampagnaADSL, ServicesDAOImpl.TipoOpzioni.LINEA_SOLO_DATI_LNA);
            if (!lineaSoloDatiList.isEmpty()) {
                servicesId.add(AbstractJarUtil.defineLineaSoloDati(lineaSoloDatiList, adslBundle));
            }
        }

//        List<Services> routerServicesList = ServicesDAOImpl.getServicesByFollowingParams(idCampagnaADSL, ServicesDAOImpl.TipoOpzioni.ROUTER);
//        if (!routerServicesList.isEmpty()) {
//            servicesId.add(routerServicesList.get(0).getServiceId());
//        }

        internetDetailOffers.setServiceId(TextUtils.join(",", servicesId));

        internetDetailOffersDAO.update(internetDetailOffers);
        if (!isModificaMod) {
            drawADSLPod(internetDetailOffers);
        } else {
            cleanTable(adslLayout);
            drawADSLTable();
        }
        internetDetailOffers = new InternetDetailOffers();
    }

    private void drawADSLTable() {
        List<InternetDetailOffers> internetDetailOfferses = internetDetailOffersDAO.getInternetDetailsOfferByInternetOfferId(internetOffer.getInternetOfferId());
        if (internetDetailOfferses.size() > 0) {
            existingClientId = 0;
            for (InternetDetailOffers internetDetailOffers1 : internetDetailOfferses) {
                drawADSLPod(internetDetailOffers1);
            }
        }
        serviceValidation();
    }

    private void cleanTlcTable() {
        bundleInfoLayout.setVisibility(View.GONE);
        bundleInfoLayout.setBackgroundColor(0000000);
        mainInfoLayout.removeAllViews();
        deleteLineLayout.removeAllViews();
        existingClientLayout.removeAllViews();
    }

    private void drawTlcTable() {
        cleanTlcTable();
//        List<TlcDetailsOffer> tlcExistingDetailsOffers = tlcDetailsOfferDAO.getExistingClientDetailsByTlcOfferId(tlcOffer.getTlcOfferId());
//        drawTlcRows(tlcExistingDetailsOffers);

//        List<WlrOfferDetails> wlrExistingOfferDetailses = wlrOfferDetailsDAO.getOldClientDetailsByOfferId(wlrOffers.getWlrOfferId(), true);
//        drawWlrRows(wlrExistingOfferDetailses);
        if (tlcOffer != null && tlcOffer.getTlcOfferId() != 0) {
            List<TlcDetailsOffer> tlcNewDetailsOffers = tlcDetailsOfferDAO.getNewClientDetailsByTlcOfferId(tlcOffer.getTlcOfferId());
            drawTlcRows(tlcNewDetailsOffers);
        }
        if (wlrOffers != null && wlrOffers.getWlrOfferId() != 0) {
            List<WlrOfferDetails> wlrNewOfferDetailses = wlrOfferDetailsDAO.getNewClientDetailsByOfferId(wlrOffers.getWlrOfferId(), true);
            drawWlrRows(wlrNewOfferDetailses);
        }
    }

    private void drawTlcRows(List<TlcDetailsOffer> offerList) {
        for (TlcDetailsOffer detailsOffer : offerList) {
            tlcDetailsOffer = detailsOffer;
            drawTlcTableRow(null);
        }
        tlcDetailsOffer = new TlcDetailsOffer();
        serviceValidation();
    }

    private void drawWlrRows(List<WlrOfferDetails> offerList) {
        for (WlrOfferDetails detailsOffer : offerList) {
            wlrOfferDetails = detailsOffer;
            drawTlcTableRow(LineTypeDAOImpl.getLineTypeByIdAndCampagnaId(idCampagnaVoce, detailsOffer.getLineId()));
        }
        wlrOfferDetails = new WlrOfferDetails();
        serviceValidation();
    }

    private void drawADSLPod(final InternetDetailOffers details) {
        adslLayout.setVisibility(View.VISIBLE);

        LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.internet_adsl_table_row, null);
        TextView adslName = (TextView) row.findViewById(R.id.adslName);
        TextView adslDesc = (TextView) row.findViewById(R.id.adslDesc);
        TextView attivazione = (TextView) row.findViewById(R.id.attivazione);
        TextView ipAggiuntivi = (TextView) row.findViewById(R.id.ipAggiungivi);
//        TextView routerDesc = (TextView) row.findViewById(R.id.router);
        final TextView adslId = (TextView) row.findViewById(R.id.adslId);

        final String name = "Linea dati " + (adslLayout.getChildCount() - existingClientId);

        adslName.setText(name);
        if (!details.isExistingClientOffer()) {
            final Bundle currentBundle = bundleDAO.getBundleById(details.getBundleId());
            adslDesc.setText(currentBundle.getBundleDesc());
//            routerDesc.setText(details.getRouter() == 1 ? "Si" : "No");
        }
        adslId.setText(String.valueOf(details.getInternetDetailOfferId()));

        List<Services> servicesList = ServicesDAOImpl.getServicesByIdRange(details.getServiceId());
        for (Services services : servicesList) {
            if (services.getFieldName().equals(ServicesDAOImpl.TipoOpzioni.IP_AGGIUNTIVI.getCtoId()))
                ipAggiuntivi.setText(services.getServiceDesc());
            if (services.getFieldName().equals(ServicesDAOImpl.TipoOpzioni.LINEA_SOLO_DATI_LNA.getCtoId()))
                attivazione.setText(services.getServiceDesc());
        }

        LinearLayout currentADSL = (LinearLayout) row.findViewById(R.id.currentADSL);
        LinearLayout deleteLayout = (LinearLayout) row.findViewById(R.id.deleteLayout);

        currentADSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isADSLChecked) {
                    currentAdslRow = (LinearLayout) v;
                    currentAdslRow.setBackgroundColor(getResources().getColor(R.color.item_background_blue));
                    internetDetailOffers = internetDetailOffersDAO.getInternetDetailsOfferById(Integer.valueOf(adslId.getText().toString().trim()));
                    addADSLButtonsLayout.setVisibility(View.GONE);
                    setPodValuesToView();
                }
            }
        });

        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isADSLChecked) {
                    LinearLayout deleteRow = (LinearLayout) v.getParent().getParent();
                    adslLayout.removeView(deleteRow);
                    if (!name.startsWith("Linea dati")) existingClientId--;
                    internetDetailOffersDAO.deleteInternetDetailslById(Integer.valueOf(adslId.getText().toString().trim()));
                    if (adslLayout.getChildCount() == 1) {
                        adslLayout.setVisibility(View.GONE);
                    }
                    cleanTable(adslLayout);
                    drawADSLTable();
                    serviceValidation();
                }
            }
        });

        if (details.isExistingClientOffer()) {
            currentADSL.setClickable(false);
            adslName.setText(details.getAdsl());
            existingClientId++;
        } else {
            internetDetailOffers.setAdsl(name);
        }

        new InternetDetailOffersDAOImpl().update(internetDetailOffers);
        adslLayout.addView(row);
        cleanInternetFields();
    }

    private void cleanInternetFields() {
        adsl.setSelection(0);
//        router.setChecked(false);
        lineaSoloDatiCheckBox.setChecked(false);
    }

    private void setPodValuesToView() {
        for (int i = 1; i < adsl.getCount(); i++) {
            Bundle currentBundle = (Bundle) adsl.getItemAtPosition(i);
            if (currentBundle.getBundleId() == internetDetailOffers.getBundleId()) {
                adsl.setSelection(i);
                break;
            }
        }

        List<String> lineaSoloDatiDescList = new ArrayList<>();
        lineaSoloDatiDescList.add(Constants.LINEA_SOLO_DATI_LNA);
        lineaSoloDatiDescList.add(Constants.LINEA_SOLO_DATI_PER_BEATSTREAM);
        lineaSoloDatiDescList.add(Constants.LINEA_SOLO_DATI_PER_VOIP);

        List<Services> servicesList = ServicesDAOImpl.getServicesByIdRange(internetDetailOffers.getServiceId());
        for (Services service : servicesList) {
            for (int i = 1; i < adslServicesSpinner.getCount(); i++) {
                if (lineaSoloDatiDescList.contains(service.getServiceDesc())) {
                    lineaSoloDatiCheckBox.setChecked(true);
                }
                Services currentServices = (Services) adslServicesSpinner.getItemAtPosition(i);
                if (service.getServiceId() == currentServices.getServiceId()) {
                    adslServicesSpinner.setSelection(i);
                    break;
                }
            }
        }

//        router.setChecked(internetDetailOffers.getRouter() == 1);
        modificaADSLLayout.setVisibility(View.VISIBLE);
        adslLayout.setClickable(false);
        isADSLChecked = true;
    }

//    private void mobileCalculation() {
//        String simStr = (String) numSimSpinner.getSelectedItem();
//        int sim = Integer.valueOf(simStr);
//        String minuteStr = (String) minutesMeseSpinner.getSelectedItem();
//        int minute = Integer.valueOf(minuteStr);
//        String smsStr = (String) smsMeseSpinner.getSelectedItem();
//        int sms = Integer.valueOf(smsStr);
//        String gygabiteStr = (String) gigabyteSpinner.getSelectedItem();
//        int gygabite = getIntGB(gygabiteStr);
//
//        Map<Integer, Double> map = MobileAlgorithm.getBestBundleByPrice(minute, sim, 0, 0);
//        double cost = MobileAlgorithm.getTotalCost(map, sms, gygabite);
//
//        mobileOffer.setMinutesNumber(minute);
//        mobileOffer.setMontlyDataNumber(gygabite);
//        mobileOffer.setMontlySmsNumber(sms);
//        mobileOffer.setSimNumber(sim);
//        mobileOffer.setOfferCost(cost);
//        setMobileToBase();
//
//        MyApplication.set("Mobile", mobileOffer);
//    }

    private int getIntGB(String str) {
        String intGB = str.substring(0, 2);
        return String.valueOf(intGB.charAt(1)).equals(" ") ? Integer.valueOf(String.valueOf(intGB.charAt(0))) : Integer.valueOf(intGB);
    }

    private void setMobileToBase() {
        if (MyApplication.get("ArchivioMobileOffer", MobileOffer.class) != null) {
            mobileOfferDAO.update(mobileOffer);
        } else {
            mobileOfferDAO.insert(mobileOffer);
        }
    }

    private void showListServices(Map navigationMap) {
        if (navigationMap.get(3) == null) {
            findViewById(R.id.mobileLinearLayout).setVisibility(View.GONE);
        } else {
            findViewById(R.id.mobileLinearLayout).setVisibility(View.VISIBLE);
            mobileFlag = true;
        }

        if (navigationMap.get(4) == null) {
            findViewById(R.id.internetLinearLayout).setVisibility(View.GONE);
        } else {
            findViewById(R.id.internetLinearLayout).setVisibility(View.VISIBLE);
            internetFlag = true;
        }

        if (navigationMap.get(5) == null) {
            findViewById(R.id.telefoniaFissaLinearLayout).setVisibility(View.GONE);
        } else {
            findViewById(R.id.telefoniaFissaLinearLayout).setVisibility(View.VISIBLE);
            tlcFlag = true;
        }
    }

    private void drawTlcServicesTable(String serviceType, String lineType, final LinearLayout serviceTable) {
        List<String> alreadyAddedSpinners = new ArrayList<>();
        serviceTable.setVisibility(View.VISIBLE);
        List<Services> services = ServicesDAOImpl.getServicesByFollowingParams(idCampagnaVoce, lineType);
        List<Integer> currentServiceList = serviceTable.getId() == servicesRtg.getId() ? servicesListRtg : servicesListISND;
        for (final Services service : services) {
            if (!alreadyAddedSpinners.contains(service.getFieldName())) {
                drawServiceItem(service, currentServiceList, serviceTable);
                if (service.getFieldName().equals(ServicesDAOImpl.TipoOpzioni.NUMERO_CANALI.getCtoId()))
                    alreadyAddedSpinners.add(service.getFieldName());
            }
        }
    }

    private void drawServiceWithCheckBox(Services service, final List serviceList, final LinearLayout servicesLayout) {
        LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.services_item_checkbox, null);
        final TextView serviceDesc = (TextView) row.findViewById(R.id.serviceDesc);
        CheckBox serviceCheckBox = (CheckBox) row.findViewById(R.id.serviceCheckBox);
        final Spinner numerazione = (Spinner) row.findViewById(R.id.numerazioneSpinner);
        final TextView serviceId = (TextView) row.findViewById(R.id.serviceId);

        serviceDesc.setText(service.getServiceDesc());
        serviceId.setText(String.valueOf(service.getServiceId()));
        serviceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Integer id = Integer.valueOf(serviceId.getText().toString().trim());
                if (isChecked) {
                    if (serviceDesc.getText().toString().trim().equals("Numerazione aggiuntiva")) {
                        numerazione.setVisibility(View.VISIBLE);
                        numerazioneRelatedWithService = numerazione;
                        List<AdditionalNumbers> additionalNumberses = AdditionalNumbersDAOImpl.getAdditionalNumbersByCampagnaId(idCampagnaVoce);
                        ArrayAdapter arrayAdapter = new ArrayAdapter(TLC.this, android.R.layout.simple_spinner_item, additionalNumberses.toArray());
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        numerazione.setAdapter(arrayAdapter);
                    }
                    List<Integer> currentList = servicesLayout.getId() == servicesRtg.getId() ? servicesListRtg : servicesListISND;
                    if (!currentList.contains(id)) currentList.add(id);
//                        if (serviceTable.getId() == R.id.rtgServices) servicesListRtg.add(id);
//                        else servicesListISND.add(id);
                } else {
                    if (serviceDesc.getText().toString().trim().equals("Numerazione aggiuntiva")) {
                        numerazioneRelatedWithService = null;
                        numerazione.setVisibility(View.GONE);
                    }
                    if (servicesLayout.getId() == R.id.rtgServices) servicesListRtg.remove(id);
                    else servicesListISND.remove(id);
                }
            }
        });

        if (serviceList.contains(service.getServiceId())) {
            serviceCheckBox.setChecked(true);
            if (serviceDesc.getText().toString().trim().equals("Numerazione aggiuntiva") && wlrOfferDetails.getServiceAddictionNumber() != null) {
                for (int i = 0; i < numerazione.getCount(); i++) {
                    AdditionalNumbers additionalNumbers = (AdditionalNumbers) numerazione.getItemAtPosition(i);
                    if (additionalNumbers.getValueAdditionalNumber() == wlrOfferDetails.getServiceAddictionNumber()) {
                        numerazione.setSelection(i);
                        break;
                    }
                }
            }
        }
        servicesLayout.addView(row);
    }

    private void drawServiceWithCombo(final Services service, final List serviceList, final LinearLayout servicesLayout, ServicesDAOImpl.TipoOpzioni numeroCanali) {
        LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.services_item_spinner, null);
        TextView serviceName = (TextView) row.findViewById(R.id.spinnerName);
        final TextView lastId = (TextView) row.findViewById(R.id.lastId);
        final Spinner serviceSpinner = (Spinner) row.findViewById(R.id.serviceSpinner);
        List spinnerContent = ServicesDAOImpl.getServicesByFollowingParams(idCampagnaVoce, service.getLineType(), numeroCanali);
        spinnerContent.add(0, "");

        final List<Services> allFieldsRelatedWithCurrentSpinner = ServicesDAOImpl.getServicesByFollowingParams(idCampagnaVoce, service.getLineType(), numeroCanali);
        for (Services serv : allFieldsRelatedWithCurrentSpinner) {
            if (serviceList.contains(serv.getServiceId())) {
                lastId.setText(String.valueOf(serv.getServiceId()));
                break;
            }
        }

        TipoOpzioni tipoOpzioni = TipoOpzioniDAOImpl.getTipoOpzioniById(numeroCanali);
        serviceName.setText(tipoOpzioni.getCtoDesc());
        setSpinner(serviceSpinner, spinnerContent);

        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<Integer> currentList = servicesLayout.getId() == servicesRtg.getId() ? servicesListRtg : servicesListISND;
                LinearLayout spinnerParent = (LinearLayout) view.getParent().getParent();
                TextView lastIdTextView = (TextView) spinnerParent.findViewById(R.id.lastId);
                Integer lastServiceId = Integer.valueOf(lastIdTextView.getText().toString().trim());
                if (position != 0) {
                    Integer idOfService = ((Services) parent.getAdapter().getItem(position)).getServiceId();
                    lastId.setText(String.valueOf(idOfService));
                    currentList.remove(lastServiceId);
                    currentList.add(idOfService);
                } else {
                    currentList.remove(lastServiceId);
                    lastId.setText(String.valueOf("0"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        for (Services serv : allFieldsRelatedWithCurrentSpinner) {
            if (serviceList.contains(serv.getServiceId()))
                for (int iter = 1; iter < serviceSpinner.getCount(); iter++) {
                    Services currentService = (Services) serviceSpinner.getItemAtPosition(iter);
                    if (serv.getServiceId() == currentService.getServiceId()) {
                        serviceSpinner.setSelection(iter);
                        break;
                    }
                }
        }
        servicesLayout.addView(row);
    }

    private void drawServiceItem(Services service, List serviceList, LinearLayout servicesLayout) {
        switch (service.getFieldName()) {
            case ServicesDAOImpl.NUMERO_CANALI_ID:
                drawServiceWithCombo(service, serviceList, servicesLayout, ServicesDAOImpl.TipoOpzioni.NUMERO_CANALI);
                break;
            default:
                drawServiceWithCheckBox(service, serviceList, servicesLayout);
                break;
        }
    }

    private void fillAdslServiceSpinner(ServicesDAOImpl.TipoOpzioni tipoOpzioni, LinearLayout serviceTable) {
        serviceTable.setVisibility(View.VISIBLE);
        List serviceses = ServicesDAOImpl.getServicesByFollowingParams(idCampagnaADSL, tipoOpzioni);
        serviceses.add(0, "");
        setSpinner(adslServicesSpinner, serviceses);
    }

    @Override
    public void run() {
        if (MyApplication.get("ArchivioTLCOffer", TLCOffer.class) != null) {
            tlcOffer = MyApplication.get("ArchivioTLCOffer", TLCOffer.class);
        } else {
            tlcOffer = new TLCOffer();
        }
        if (MyApplication.get("WlrOffer", WlrOffers.class) != null) {
            wlrOffers = MyApplication.get("WlrOffer", WlrOffers.class);
        } else {
            wlrOffers = new WlrOffers();
        }

        if (MyApplication.get("ArchivioTLCOffer", TLCOffer.class) == null && MyApplication.get("WlrOffer", WlrOffers.class) == null) {
            checkForTlcExistingClient();
            drawTlcTable();
        } else {
            drawTlcTable();
        }

        if (MyApplication.get("ArchivioInternetOffer", InternetOffer.class) != null) {
            internetOffer = MyApplication.get("ArchivioInternetOffer", InternetOffer.class);
            drawADSLTable();
        } else {
            internetOffer = new InternetOffer();
            checkForADSLExistingClient();
            drawADSLTable();
        }

//        if (Deamon.get(ARCHIVIO_MOBILE_OFFER, MobileOffer.class) != null) {
//            mobileOffer = Deamon.get(ARCHIVIO_MOBILE_OFFER, MobileOffer.class);
//            setMobileArchivioValue();
//        } else {
//            mobileOffer = new MobileOffer();
//        }
    }

    private void checkForTlcExistingClient() {
        if (offer.getOldTlcOfferId() != 0) {
            ClientServices clientServices = new ClientServicesDAOImpl().getClientServicesByClientId(offer.getOldTlcOfferId());
            ClientTlcOffers clientTlcOffers = new ClientTlcOffersDAOImpl().getClientTlcOfferById(clientServices.getTlcServiceId() != null ? clientServices.getTlcServiceId() : 0);
            List<ClientDetailTlcOffers> exsitingDetailsTlcOfferses = new ClientDetailsTlcOffersDAOImpl().getClientDetailTlcOffersById(clientTlcOffers.getClientTlcOfferId());
            if (exsitingDetailsTlcOfferses.size() > 0) {
//                addTlcOfferToBaseIfNeeded();
                addWlrOfferToBaseIfNeeded();
                String dialerPadre = "";
                for (ClientDetailTlcOffers details : exsitingDetailsTlcOfferses) {
                    saveAlreadyClientRow(details);
                    if (!dialerPadre.equals(details.getLine())) {
                        saveZiImportPricingVoceDett(details);
                        dialerPadre = details.getLine();
                    }
                }
            }
        }
    }

    private void saveZiImportPricingVoceDett(ClientDetailTlcOffers clientDetailTlcOffers) {
        List<ZiImportPricingVoceDett> ziImportPricingVoceDetts = new ZiImportPricingVoceDettDAOImpl().getZiImportPricingVoceDettByLine(clientDetailTlcOffers.getLine());
        if (ziImportPricingVoceDetts != null) {
            for (ZiImportPricingVoceDett ziImportPricingVoceDett : ziImportPricingVoceDetts) {
                saveAlreadyClientRow(ziImportPricingVoceDett, clientDetailTlcOffers);
            }
        }
    }

    private void saveAlreadyClientRow(ZiImportPricingVoceDett ziImportPricingVoceDett, ClientDetailTlcOffers clientDetailTlcOffers) {
        WlrOfferDetails wlrOfferDetails = new WlrOfferDetails();
        wlrOfferDetails.setModified(false);
        wlrOfferDetails.setExistingClientOffer(true);
        wlrOfferDetails.setLineId(Integer.valueOf(clientDetailTlcOffers.getTipologiaLinea()));
        wlrOfferDetails.setOwnLocalBundleId(clientDetailTlcOffers.getLocalMinutes());
        wlrOfferDetails.setOwnMobileBundleId(clientDetailTlcOffers.getMobileMinutes());
        wlrOfferDetails.setServiceAddictionNumber(ziImportPricingVoceDett.getNumAggiuntivi());
        wlrOfferDetails.setNumLines(ziImportPricingVoceDett.getNum());
        wlrOfferDetails.setWlrName(ziImportPricingVoceDett.getDialerPadre());
        wlrOfferDetails.setWlrOfferId(wlrOffers.getWlrOfferId());
        wlrOfferDetails.setRete(ziImportPricingVoceDett.getRete());
        wlrOfferDetails.setParent(false);
        wlrOfferDetailsDAO.insert(wlrOfferDetails);
    }

    private void saveAlreadyClientRow(ClientDetailTlcOffers clientDetailTlcOffers) {
        WlrOfferDetails wlrOfferDetails = new WlrOfferDetails();
        wlrOfferDetails.setModified(false);
        wlrOfferDetails.setExistingClientOffer(true);
        wlrOfferDetails.setLineId(Integer.valueOf(clientDetailTlcOffers.getTipologiaLinea()));
        wlrOfferDetails.setOwnLocalBundleId(clientDetailTlcOffers.getLocalMinutes());
        wlrOfferDetails.setOwnMobileBundleId(clientDetailTlcOffers.getMobileMinutes());
        wlrOfferDetails.setServiceAddictionNumber(clientDetailTlcOffers.getNumAggiuntivi());
        wlrOfferDetails.setWlrName(clientDetailTlcOffers.getLine());
        wlrOfferDetails.setWlrOfferId(wlrOffers.getWlrOfferId());
        wlrOfferDetails.setRete(clientDetailTlcOffers.getRete());
        wlrOfferDetails.setNumLines(1);
        if (clientDetailTlcOffers.getRete().equals(Constants.WLR)) {
            Carriers carriers = carriersDAO.getCarrierByDescAndConfiguratorType(Constants.OPTIMA, Constants.BUSINESS_CONFIGURATOR_FLAG);
            wlrOfferDetails.setCarrierId(carriers != null ? carriers.getCarrierId() : 0);
            Networks networks = networksDAO.getNetworksByDesc(Constants.WLR, carriers.getCarrierId());
            wlrOfferDetails.setNetworkId(networks != null ? networks.getNetworkId() : 0);
        }
//        if (wlrOfferDetails.getServiceAddictionNumber() != null) {
//            wlrOfferDetails.setServicesId(String.valueOf(
//                    servicesDAO.getServiceByLineTypeAndServiceDesc(Integer.valueOf(clientDetailTlcOffers.getTipologiaLinea())
//                            , NUMERAZIONE_AGGIUNTIVA).getServiceId()));
//        }
        wlrOfferDetailsDAO.insert(wlrOfferDetails);
    }

    private void checkForADSLExistingClient() {
        if (offer.getOldTlcOfferId() != 0) {
            ClientServices clientServices = new ClientServicesDAOImpl().getClientServicesByClientId(offer.getOldTlcOfferId());
            ClientAdslOffers clientAdslOffers = new ClientAdslOffersDAOImpl().getClientAdslOfferById(clientServices.getAdslServiceId() != null ? clientServices.getAdslServiceId() : 0);
            List<ClientDetailAdslOffers> existingClientsADSL = new ClientDetailAdslOffersDAOImpl().getClientDetailAdslOffersById(clientAdslOffers.getClientAdslOffers());
            if (existingClientsADSL.size() > 0) {
                setInternetOfferToBaseIfItNeeded();
                for (ClientDetailAdslOffers existingOffer : existingClientsADSL) {
                    InternetDetailOffers internetDetail = new InternetDetailOffers();
                    internetDetail.setInternetOfferId(internetOffer.getInternetOfferId());
                    internetDetail.setRouter(0);
                    internetDetail.setBundleId(existingOffer.getAdslType());
                    internetDetail.setAdsl(existingOffer.getLine());
                    internetDetail.setExistingClientOffer(true);
                    internetDetail.setCost(existingOffer.getCost());
                    internetDetail.setCostIVA(existingOffer.getCostIva());
                    internetDetail.setBundleCostVersion(existingOffer.getVersion());
                    List<Integer> serviceIds = new ArrayList<>();
                    if (existingOffer.getDataLine() != 0)
                        serviceIds.add(existingOffer.getDataLine());
                    if (existingOffer.getIp() != 0) serviceIds.add(existingOffer.getIp());
                    internetDetail.setServiceId(serviceIds.toString().substring(1, serviceIds.toString().length() - 1));
                    internetDetailOffersDAO.insert(internetDetail);
                }
            }
        }
    }

    public boolean serviceValidation() {
        boolean calcolaFlag;
        if (calcolaFlag = (!tlcFlag || ((mainInfoLayout.getChildCount() > 0 || existingClientLayout.getChildCount() > 0) & modificaTlcLayout.getVisibility() == View.GONE &
                modificaWlrLayout.getVisibility() == View.GONE)) &&
                (!mobileFlag || (numSimSpinner.getSelectedItemPosition() != 0 && minutesMeseSpinner.getSelectedItemPosition() != 0)) &&
                (!internetFlag || adslLayout.getChildCount() > 1) && modificaADSLLayout.getVisibility() == View.GONE) {
            setVisibilityToGroupOfViews(View.GONE, calcolaButtonDisabled, continuaButtonDisabled);
            setVisibilityToGroupOfViews(View.VISIBLE, calcolaButton, continuaButton);
        } else {
            setVisibilityToGroupOfViews(View.VISIBLE, calcolaButtonDisabled, continuaButtonDisabled);
            setVisibilityToGroupOfViews(View.GONE, calcolaButton, continuaButton);
        }
        return calcolaFlag;
    }

    private boolean tlcVersoFissoAndMobileValidation(boolean isAnimated) {
        return (isCheckBoxChecked(isAnimated, fisso.getId()) || (isCheckBoxChecked(isAnimated, mobile.getId()))) && isValid(isAnimated, lines.getId());
    }

    private boolean fissoAndMobileValidation(boolean isAnimated) {
        boolean result = (fisso.isChecked() || mobile.isChecked());
        if (!result && isAnimated) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            fisso.startAnimation(shake);
            mobile.startAnimation(shake);
        }
        return result;
    }

    private boolean tlcWLRValidation() {
        return checkMode(CPS) & podTable.getChildCount() > 1 & modificaWlrLayout.getVisibility() == View.GONE;
    }

//    private void setMobileArchivioValue() {
//        changeCalcolaVisibility();
//        if (looperNumber == 2) {
//            for (int iter = 0; iter < smsMeseSpinner.getCount(); iter++) {
//                String sms = (String) smsMeseSpinner.getItemAtPosition(iter);
//                if ((mobileOffer.getMontlySmsNumber()) == Integer.valueOf(sms) && mobileOffer.getMontlySmsNumber() != 0) {
//                    smsMeseSpinner.setSelection(iter);
//                    CheckBox dataCheckBox = (CheckBox) findViewById(R.id.smscheck);
//                    dataCheckBox.setChecked(true);
//                    smsMeseSpinner.setSelection(iter);
//                    smsMeseSpinner.setEnabled(true);
//                    break;
//                }
//            }
//
//            for (int iter = 0; iter < gigabyteSpinner.getCount(); iter++) {
//                String gigabyte = (String) gigabyteSpinner.getItemAtPosition(iter);
//                if (mobileOffer.getMontlyDataNumber() == getIntGB(gigabyte) && mobileOffer.getMontlyDataNumber() != 0) {
//                    CheckBox smsCheckBox = (CheckBox) findViewById(R.id.gigabytecheck);
//                    smsCheckBox.setChecked(true);
//                    gigabyteSpinner.setSelection(iter);
//                    gigabyteSpinner.setEnabled(true);
//                    break;
//                }
//            }
//        }
//        if (looperNumber == 1) {
//            for (int iter = 1; iter < minutesMeseSpinner.getCount(); iter++) {
//                String minute = (String) minutesMeseSpinner.getItemAtPosition(iter);
//                if ((mobileOffer.getMinutesNumber()) == Integer.valueOf(minute)) {
//                    minutesMeseSpinner.setSelection(iter);
//                    break;
//                }
//            }
//            new Handler().postDelayed(TLC.this, 500);
//            looperNumber = 2;
//        }
//        if (looperNumber == 0) {
//            for (int iter = 1; iter < numSimSpinner.getCount(); iter++) {
//                String sim = (String) numSimSpinner.getItemAtPosition(iter);
//                if ((mobileOffer.getSimNumber()) == Integer.valueOf(sim)) {
//                    numSimSpinner.setSelection(iter);
//                    break;
//                }
//            }
//
//            new Handler().postDelayed(TLC.this, 1000);
//            looperNumber = 1;
//        }
//    }

    private void changeCalcolaVisibility() {
        findViewById(R.id.calcola6Disabled).setVisibility(View.GONE);
        findViewById(R.id.calcola6).setVisibility(View.VISIBLE);
    }

    private void changeRTGServiceVisibility() {
        boolean flag = checkSpinnerSelection(false, tipologiaDiLineaRTG.getId());
        if (flag) {
            drawTlcServicesTable(Constants.WLR, String.valueOf(((LineTypes) tipologiaDiLineaRTG.getSelectedItem()).getLineId()), servicesRtg);
        }
    }

    private void changeISDNServiceVisibility() {
        boolean flag = checkSpinnerSelection(false, tipologiaDiLineaISDN.getId());
        if (flag) {
            drawTlcServicesTable(Constants.WLR, String.valueOf(((LineTypes) tipologiaDiLineaISDN.getSelectedItem()).getLineId()), servicesIdsn);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        serviceValidation();
        addTlcValidation(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        serviceValidation();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redrawTable();
            }
        }, TIME_DELAY_CONFIGURATION_CHANGED);
    }

    private boolean checkMode(String mode) {
        return voceModeSpinner.getSelectedItem().equals(mode);
    }

    private void redrawBundleInVoipMode() {
        if (offer != null) {
            String configuratoreType = offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR
                    ? Constants.BUSINESS_CONFIGURATOR_FLAG : Constants.CONSUMER_CONFIGURATOR_FLAG;

            new VoceVoipModeSpinner(this, "", VOCE_FISSO_BUNDLE_TYPE, new AbstractService.OnTaskCompleted<VoceVoipModeSpinner>() {
                @Override
                public void onTaskCompleted(VoceVoipModeSpinner service) {
                    setSpinner(fissoSpinner, service.getResult());
                }
            }).execute();

            new VoceVoipModeSpinner(this, configuratoreType, VOCE_MOBILE_BUNDLE_TYPE, new AbstractService.OnTaskCompleted<VoceVoipModeSpinner>() {
                @Override
                public void onTaskCompleted(VoceVoipModeSpinner service) {
                    setSpinner(mobileSpinner, service.getResult());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkForCommonWlrBundle();
                        }
                    }, 400);
                }
            }).execute();
        }
    }

    private void redrawTlcBundleLayout() {
        fillTlcBundleSpinner(false);
    }

    private void fillTlcBundleSpinner(boolean isFirstTime) {
        if (offer != null) {
            String configuratoreType = getConfiguratorType(offer.getConfiguratorType());
            new VersoFissoSpinner(this, new AbstractService.OnTaskCompleted<VersoFissoSpinner>() {
                @Override
                public void onTaskCompleted(VersoFissoSpinner service) {
                    setSpinner(fissoSpinner, service.getResult());
                }
            }).execute(configuratoreType);

            new VersoMobileSpinner(this, new AbstractService.OnTaskCompleted<VersoMobileSpinner>() {
                @Override
                public void onTaskCompleted(VersoMobileSpinner service) {
                    setSpinner(mobileSpinner, service.getResult());
                    if (isFirstTime) {
                        new Handler().postDelayed(TLC.this, 700);
                    } else {
                        checkBundleAlreadyChoosed();
                    }
                }
            }).execute(configuratoreType);
        }
    }

    private boolean isManuallSoloDatiMode(String adslModeCode) {
        return (wlrOffers == null || wlrOffers.getWlrOfferId() == 0 || !wlrOfferDetailsDAO.isVoipLinesUsedForOfferId(wlrOffers.getWlrOfferId()))
                && !adslModeCode.equalsIgnoreCase(Constants.FIBRA_CODE);
    }
}