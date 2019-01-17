package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.Gas;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.CategorieUso;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ClassePrelievoGas;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;

public class RigmeFiscaleDialog extends BaseEvent {

    private Offer offer;
    private Spinner categoriaDusoSpinner;
    private Spinner classeDiPrelievoSpinner;
    private Button btn_yes;
    private int categoriaDusoId;
    private int classeDePrelievoId;

    public RigmeFiscaleDialog(BaseActivity activity, GasDetailOffers details, Offer offer) {
        this.activity = activity;
        this.offer = offer;
        showDialog(details);
    }

    private void showDialog(final GasDetailOffers gasDetailOffer) {
        if (!isActivityAlive()) {
            return;
        }
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.regime_fiscale_dialog);

        final RadioButton civile = (RadioButton) dialog.findViewById(R.id.rf_civile);
        RadioButton industriale = (RadioButton) dialog.findViewById(R.id.rf_industriale);
        categoriaDusoSpinner = (Spinner) dialog.findViewById(R.id.categoria_di_utilizzo);
        classeDiPrelievoSpinner = (Spinner) dialog.findViewById(R.id.classe_di_prelievo);
        btn_yes = (Button) dialog.findViewById(R.id.rf_yes);

        final int townId = offer.getTownId();
        btn_yes.setEnabled(false);
        classeDiPrelievoSpinner.setEnabled(false);
        civile.setChecked(gasDetailOffer.getFiscalClass() == 1);
        industriale.setChecked(gasDetailOffer.getFiscalClass() == 2);
        ((Gas) activity).fillCategoriaDusoSpinner(((Gas) activity).getSystem(), categoriaDusoSpinner, gasDetailOffer);
        ((Gas) activity).setDataPrelievoSpinner(gasDetailOffer, classeDiPrelievoSpinner);
        categoriaDusoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (dialog != null && isActivityAlive()) {
                    if (position != 0 && townId != 0) {
                        categoriaDusoId = ((CategorieUso) parent.getSelectedItem()).getIdCategoriaUso();
                        ((Gas) activity).fillClassePrelievoSpinner(townId, categoriaDusoId, classeDiPrelievoSpinner);
                        classeDiPrelievoSpinner.setEnabled(true);
                    } else {
                        classeDiPrelievoSpinner.setEnabled(false);
                        if (classeDiPrelievoSpinner.getChildCount() != 0)
                            classeDiPrelievoSpinner.setSelection(0);
                    }
                    validationBtnOk();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        classeDiPrelievoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (dialog != null & isActivityAlive()) {
                    if (position != 0) {
                        classeDePrelievoId = ((ClassePrelievoGas) parent.getSelectedItem()).getIdClassePrelievo();
                    }
                    if (validationBtnOk()) {
                        btn_yes.setEnabled(true);
                        btn_yes.setTextColor(activity.getResources().getColor(R.color.regime_fiscale_btn_ok));
                    } else {
                        btn_yes.setEnabled(false);
                        btn_yes.setTextColor(activity.getResources().getColor(R.color.btn_inactive));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && isActivityAlive()) {
                    gasDetailOffer.setFiscalClass(civile.isChecked() ? 1 : 2);
                    gasDetailOffer.setUserTypeId(categoriaDusoId);
                    gasDetailOffer.setDayClassId(classeDePrelievoId);
                    new GasDetailOffersDAOImpl().update(gasDetailOffer);
                    ((Gas) activity).cleanPdrTable();
                    ((Gas) activity).drawPdrTable();
                    dialog.dismiss();
                }
            }
        });

        dialog.findViewById(R.id.rf_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && isActivityAlive()) {
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private boolean validationBtnOk() {
        return categoriaDusoSpinner.getSelectedItemPosition() != 0 &&
                categoriaDusoSpinner.getSelectedItemPosition() != -1 &&
                classeDiPrelievoSpinner.getSelectedItemPosition() != 0 &&
                classeDiPrelievoSpinner.getSelectedItemPosition() != -1;
    }
}
