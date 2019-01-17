package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasUtilizationProfileClassDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;

public class GasQuestionario extends BaseActivity {

    private final int CIV = 1;
    private int pageNumber = 2;
    private GasOffer gasOffer;
    private String validationCode;
    private Offer offer = MyApplication.get("offerEntity", Offer.class);
    private GasDetailOffers gasDetailOffers;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gas_layout_questionario);

        checkContinuaButton(R.id.continuaLinearLayout5, R.id.calcolaLinearLayout5, Constants.GAS);
        checkRicalcolaButton();

        if (MyApplication.get("ArchivioGasOffer", GasOffer.class) != null) {
            setEntityValues();
        } else {
            gasOffer = new GasOffer();
            gasDetailOffers = new GasDetailOffers();
        }
        MyApplication.set("gas", true);

        ImageButton continua = (ImageButton) findViewById(R.id.continua5);
        continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    calculateOffer(null);
                    isLastPage(pageNumber);
                }
            }
        });

        findViewById(R.id.ricalcolaGas5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    calculateOffer(Modifica.class);
                }
            }
        });

        findViewById(R.id.calcola5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    calculateOffer(Canone.class);
                }
            }
        });
    }

    private void calculateOffer(Class clas) {
        setGasOfferToBaseIfItNeeded();
        setGasOfferDetailsIfItNeeded();
        gasDetailOffers.setValidationMap(validationCode);
        gasDetailOffers.setTipoContratto(Constants.DOMESTICO);
        gasDetailOffers.setFiscalClass(CIV);
        gasDetailOffers.setYearlySmc(0);
        gasDetailOffers.setTownId(offer.getTownId());
        gasDetailOffers.setProfiloDiUtilizzo(getProfileStr(validationCode));
        updateGasOfferDetailsInBase();
        updateGasOfferInBase();
        offer.setGasOfferId(gasOffer.getGasOfferId());
        new OfferDAOImpl().update(offer);
        MyApplication.set(Constants.OFFER_ENTITY, offer);
//        Deamon.set("offerEntity", offer); ///not used
        if (clas != null) {
            startChildActivity(clas);
        }
    }

    public boolean isLastPage(int currentPage) {
        if (!startNextActivity(currentPage)) {
            Intent intent = new Intent(this, Canone.class);
            intent.putExtra("ChoiceCode", 1);
            startActivity(intent);
            return true;
        }
        return false;
    }

    private boolean validation() {
        boolean flag = true;
        validationCode = getCodePart(R.id.riscaldamentoGas) + getCodePart(R.id.cotturacibiGas)
                + getCodePart(R.id.produzioneAcquaCaldaGas) + getCodePart(R.id.condizionamentoGas);
        if (!new GasUtilizationProfileClassDAOImpl().getIsClassCode(validationCode)) {
            Toast.makeText(this, "La combinazionedeiprofili di utilizzoinseriti non è valida", Toast.LENGTH_LONG).show();
            flag = false;
        }
        return flag;
    }

    private void setGasOfferToBaseIfItNeeded() {
        GasOfferDAOImpl gasOfferDAO = new GasOfferDAOImpl();
        if (gasOffer.getGasOfferId() == 0) {
            gasOffer.setQuestionarioUsing(true); /// isQuestionarioUsing
            gasOfferDAO.insert(gasOffer);
        }
    }

    private void updateGasOfferInBase() {
        GasOfferDAOImpl gasOfferDAO = new GasOfferDAOImpl();
        if (gasOffer.getGasOfferId() != 0) {
            gasOfferDAO.update(gasOffer);
        }
    }

    private void setGasOfferDetailsIfItNeeded() {
        GasDetailOffersDAOImpl gasDetailOffersDAO = new GasDetailOffersDAOImpl();
        if (gasDetailOffers.getGasDetailsOfferId() == 0) {
            gasDetailOffers.setGasOfferId(gasOffer.getGasOfferId());
            gasDetailOffersDAO.insert(gasDetailOffers);
        }
    }

    private void updateGasOfferDetailsInBase() {
        GasDetailOffersDAOImpl gasDetailOffersDAO = new GasDetailOffersDAOImpl();
        if (gasDetailOffers.getGasDetailsOfferId() != 0) {
            gasDetailOffersDAO.update(gasDetailOffers);
        }
    }

    private void checkRicalcolaButton() {
        if (MyApplication.get("modificaGasLable", Boolean.class) != null) {
            findViewById(R.id.continuaLinearLayout5).setVisibility(View.GONE);
            findViewById(R.id.calcolaLinearLayout5).setVisibility(View.GONE);
            findViewById(R.id.ricalcolaGasLayout).setVisibility(View.VISIBLE);
        }
    }

    private String getCodePart(int id) {
        return isCheckBoxChecked(false, id) ? "1" : "0";
    }

    private void setEntityValues() {
        gasOffer = MyApplication.get("ArchivioGasOffer", GasOffer.class);//
        gasDetailOffers = new GasDetailOffersDAOImpl().getGasOfferDetailsByGasOfferId(gasOffer.getGasOfferId()).get(0);//
        validationCode = gasDetailOffers.getValidationMap();
        checkProfile(gasDetailOffers.getValidationMap());
        changeViewVisibility(R.id.ricalcolaGas5Disabled, R.id.ricalcolaGas5);
    }

    private void checkProfile(String code) {
        CheckBox riscaldamento = (CheckBox) findViewById(R.id.riscaldamentoGas);
        CheckBox condizionamento = (CheckBox) findViewById(R.id.condizionamentoGas);
        CheckBox cotturacibi = (CheckBox) findViewById(R.id.cotturacibiGas);
        CheckBox produzioneAcquaCalda = (CheckBox) findViewById(R.id.produzioneAcquaCaldaGas);

        if (code.substring(0, 1).equals("1")) riscaldamento.setChecked(true);
        if (code.substring(1, 2).equals("1")) cotturacibi.setChecked(true);
        if (code.substring(2, 3).equals("1")) produzioneAcquaCalda.setChecked(true);
        if (code.substring(3, 4).equals("1")) condizionamento.setChecked(true);
    }

    private String getProfileStr(String code) {
        String codeDesc = ((code.substring(0, 1).equals("1") ? "riscaldamento, " : "") +
                (code.substring(1, 2).equals("1") ? "cottura_cibo, " : "") +
                (code.substring(2, 3).equals("1") ? "acqua_calda, " : "") +
                (code.substring(3, 4).equals("1") ? "condizionamento, " : "")).trim();
        return codeDesc.substring(0, codeDesc.length() - 1);
    }
}