package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDeviceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferDevice;
import com.aimprosoft.android.optima.centralizedApp.event.ConfirmationDialog;
import com.aimprosoft.android.optima.centralizedApp.event.JarInputOutputResultDialog;
import com.aimprosoft.android.optima.centralizedApp.event.listener.OnDialogDismissedListener;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.TotalCostJarService;
import com.aimprosoft.android.optima.centralizedApp.util.LocalSharedPreferencesManager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Canone extends BaseActivity implements OnDialogDismissedListener {
    private LinearLayout offertaUnicaLayout;
    private LinearLayout costoAttivazioneLayout;
    private LinearLayout canoneMensileIvaLayout;
    private LinearLayout mobileCostLayout;

    private int year;
    private int month;
    private int day;
    private Offer offer = MyApplication.get("offerEntity", Offer.class);
    private DecimalFormat decimalFormat;
    private ImageButton canoneArrowButton;
    private ImageButton canoneArrowBackButton;
    private ImageButton attivazioneArrowButton;
    private ImageButton attivazioneArrowBackButton;
    private ImageButton contoRelaxArrowButton;
    private ImageButton contoRelaxArrowBackButton;
    private ImageButton mobileArrowButton;
    private ImageButton mobileArrowBackButton;
    private String defaultRelaxCost;
    private ConfirmationDialog confirmationDialog;
    private MobileOffer mobileOffer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canone_layout);
        String pattern = "#####0.00";
        decimalFormat = new DecimalFormat(pattern);
        offertaUnicaLayout = (LinearLayout) findViewById(R.id.offertaUnicaLayout);
        costoAttivazioneLayout = (LinearLayout) findViewById(R.id.costoAttivazioneLayout);
        canoneMensileIvaLayout = (LinearLayout) findViewById(R.id.canoneMensileIvaLayout);
        mobileCostLayout = (LinearLayout) findViewById(R.id.mobileCostLayout);
        canoneArrowButton = (ImageButton) findViewById(R.id.canoneArrowButton);
        canoneArrowBackButton = (ImageButton) findViewById(R.id.canoneArrowBackButton);
        attivazioneArrowButton = (ImageButton) findViewById(R.id.attivazioneArrowButton);
        mobileArrowButton = (ImageButton) findViewById(R.id.mobileArrowButton);
        mobileArrowBackButton = (ImageButton) findViewById(R.id.mobileArrowBackButton);
        attivazioneArrowBackButton = (ImageButton) findViewById(R.id.attivazioneArrowBackButton);
        contoRelaxArrowButton = (ImageButton) findViewById(R.id.contoRelaxArrowButton);
        contoRelaxArrowBackButton = (ImageButton) findViewById(R.id.contoRelaxArrowBackButton);
        defaultRelaxCost = decimalFormat.format(0.00);
        setTextToTextView(R.id.relaxCost, defaultRelaxCost);

        showConfirmDialog();

        Button detaglio = (Button) findViewById(R.id.dettaglio);
        detaglio.setOnClickListener(v -> {
            saveData();
            startChildActivity(Dettaglio.class);
        });

        Button modifica = (Button) findViewById(R.id.modifica);
        modifica.setOnClickListener(v -> {
            saveData();
            if (checkForLogout()) {
                startChildActivity(Modifica.class);
            }
        });

        findViewById(R.id.archivia).setOnClickListener(v -> {
            saveData();
            if (checkForLogout()) {
                MyApplication.set("ModificaPoint", true);
                startChildActivity(Archivio.class);
            }
        });

        findViewById(R.id.tornaItus).setOnClickListener(v -> callItusApp());

        canoneArrowButton.setOnClickListener(v -> {
            canoneArrowBackButton.setVisibility(View.VISIBLE);
            canoneArrowButton.setVisibility(View.GONE);
            if (offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR) {
                canoneMensileIvaLayout.setVisibility(View.VISIBLE);
            }
            setTextToTextView(R.id.canoneMensile, decimalFormat.format(offer.getOfferCost()));
            setTextToTextView(R.id.canoneMensileIva, decimalFormat.format(offer.getOfferCostIVA()));
        });

        canoneArrowBackButton.setOnClickListener(v -> {
            canoneArrowBackButton.setVisibility(View.GONE);
            canoneArrowButton.setVisibility(View.VISIBLE);
            setTextToTextView(R.id.canoneMensile, decimalFormat.format(offer.getOfferCost().doubleValue() * 1.15));
            setTextToTextView(R.id.canoneMensileIva, decimalFormat.format((offer.getOfferCostIVA().doubleValue() * 1.15)));
            canoneMensileIvaLayout.setVisibility(View.INVISIBLE);
        });


        attivazioneArrowButton.setOnClickListener(v -> {
            attivazioneArrowButton.setVisibility(View.GONE);
            attivazioneArrowBackButton.setVisibility(View.VISIBLE);
            costoAttivazioneLayout.setVisibility(View.VISIBLE);
        });

        attivazioneArrowBackButton.setOnClickListener(v -> {
            attivazioneArrowButton.setVisibility(View.VISIBLE);
            attivazioneArrowBackButton.setVisibility(View.GONE);
            costoAttivazioneLayout.setVisibility(View.INVISIBLE);
        });

        contoRelaxArrowButton.setOnClickListener(v -> {
            setTextToTextView(R.id.relaxCost, decimalFormat.format(offer.getConto_relax()));
            contoRelaxArrowButton.setVisibility(View.GONE);
            contoRelaxArrowBackButton.setVisibility(View.VISIBLE);

        });

        contoRelaxArrowBackButton.setOnClickListener(v -> {
            setTextToTextView(R.id.relaxCost, defaultRelaxCost);
            contoRelaxArrowButton.setVisibility(View.VISIBLE);
            contoRelaxArrowBackButton.setVisibility(View.GONE);
        });

        mobileArrowButton.setOnClickListener(v -> {
            if (mobileOffer != null) {
                setTextToTextView(R.id.mobileCost, decimalFormat.format(mobileOffer.getOfferCostConPromo()));
            }
            mobileArrowButton.setVisibility(View.GONE);
            mobileArrowBackButton.setVisibility(View.VISIBLE);
        });

        mobileArrowBackButton.setOnClickListener(v -> {
            if (mobileOffer != null) {
                setTextToTextView(R.id.mobileCost, decimalFormat.format(mobileOffer.getOfferCostIva()));
            }
            mobileArrowButton.setVisibility(View.VISIBLE);
            mobileArrowBackButton.setVisibility(View.GONE);
        });
    }

    @Override
    public void onDialogDismiss() {
        showPrices();
    }

    private void runCalculations() {
        if (offer != null) {
            new TotalCostJarService(this, new AbstractService.OnTaskCompleted<TotalCostJarService>() {
                @Override
                public void onTaskCompleted(TotalCostJarService service) {
                    showTestDialog(service.getResult());
                    MyApplication.set("offerEntity", offer);
                    setData();
                    saveData();
                }
            }).execute(offer);
        }
    }

    private void showConfirmDialog() {
        if (offer != null) {
            List<OfferDevice> offerDeviceList = new OfferDeviceDAOImpl().getDevicesByOfferId(offer.getOfferId());
            if (!offerDeviceList.isEmpty()) {
                if (confirmationDialog == null) {
                    confirmationDialog = new ConfirmationDialog(this, getString(R.string.device_confirmation_dialog_text));
                }
                confirmationDialog.setOnDialogDismissedListener(this);
                confirmationDialog.showDialog();
            } else {
                showPrices();
            }
        }
    }

    private void showPrices() {
        if (!getIntent().getBooleanExtra(Constants.IS_FROM_ARCHIVIO, false)) {
            runCalculations();
        } else {
            setData();
            saveData();
        }
    }

    private void callItusApp() {
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage("wachipi.optima");
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            if (offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR) {
                intent.putExtra(Constants.NOME, offer.getName());
            } else {
                intent.putExtra(Constants.NOME, offer.getNome());
                intent.putExtra(Constants.COGNOME_OR_RAGIONE_SOCIALE, offer.getSurname());
            }
            intent.putExtra(Constants.CANONE_MENSILE_IVA_ESCLUSA, decimalFormat.format(offer.getOfferCost()));
            intent.putExtra(Constants.CANONE_MENSILE_IVA_INCLUSA, decimalFormat.format(offer.getOfferCostIVA()));
            intent.putExtra(Constants.CONTO_RELAX, decimalFormat.format(offer.getConto_relax()));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(Canone.this, "ITUS app doesn't found", Toast.LENGTH_SHORT).show();
        }
    }

    private void showTestDialog(Map<String, Object> map) {
        if (getResources().getBoolean(R.bool.is_test_version)) {
            new JarInputOutputResultDialog(Canone.this).showDialog(map);
        }
    }

    private void setData() {
//        switch (choiceCode) {
//            case 0:
//                showOffertaPerServizio();
//                break;
//            case 1:
        showOffertaUnica();
//                break;
//            case 2:
//                showComponiOfferta();
//                break;
//        }
    }

    private void saveData() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        offer = MyApplication.get("offerEntity", Offer.class);
        if (offer != null) {
            updateDisplayDate(offer);
            OfferDAOImpl offerDAO = new OfferDAOImpl();

            if (offer.getOfferId() != 0) {
                offerDAO.update(offer);
            } else {
                offerDAO.insert(offer);
            }
            MyApplication.set("offerEntity", offer);
        }
    }

    private void showOffertaUnica() {
        offertaUnicaLayout.setVisibility(View.VISIBLE);
        TextView activationCostlable = (TextView) findViewById(R.id.activationCostLable);
        if (offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR) {
            findViewById(R.id.canoneMensileIvaLayoutBusiness).setVisibility(View.VISIBLE);
            activationCostlable.setText(getString(R.string.activation_cost_lable_consumer));
            canoneMensileIvaLayout.setVisibility(View.INVISIBLE);
            setTextToTextView(R.id.unaTantum, decimalFormat.format(offer.getOfferCostExtra()));
        } else {
            activationCostlable.setText(getString(R.string.activation_cost_lable_business));
            setTextToTextView(R.id.unaTantum, decimalFormat.format(offer.getOfferCostExtra().multiply(new BigDecimal(4))));
        }
        setTextToTextView(R.id.canoneMensile, decimalFormat.format(offer.getOfferCost().doubleValue() * 1.15));
        setTextToTextView(R.id.canoneMensileIva, decimalFormat.format((offer.getOfferCostIVA().doubleValue() * 1.15)));
        setTextToTextView(R.id.canoneMensileIvaConsumer, decimalFormat.format(offer.getOfferCostIVA()));

        if (offer != null && offer.getMobileOfferId() != null) {
            mobileOffer = new MobileOfferDAOImpl().getMobileOfferById(offer.getMobileOfferId());
            mobileCostLayout.setVisibility(View.VISIBLE);
            setTextToTextView(R.id.mobileCost, decimalFormat.format(mobileOffer.getOfferCostIva()));
        }
    }

    private void updateDisplayDate(Offer offer) {
        String creationDate = pad(day) + "/" + pad(month + 1) + "/" + pad(year);
        if (offer != null) {
            offer.setCreationDate(creationDate);
        }
    }

    private String pad(Integer d) {
        return d.toString().length() == 1 ? "0" + d.toString() : d.toString();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode != 4 && super.onKeyDown(keyCode, event);
    }

    @Override
    public void closeAll(View v) {
        super.closeAll(v);
        saveData();
        LocalSharedPreferencesManager.getInstance().storeSharedPreferencesBooleanValue(this, LocalSharedPreferencesManager.IS_APP_CHECKED_FOR_UPDATE, false);
    }
}