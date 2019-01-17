package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Dialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.Archivio;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.SetOptilifeCodiceToBase;
import com.aimprosoft.android.optima.centralizedApp.util.ActionItem;
import com.aimprosoft.android.optima.centralizedApp.util.QuickAction;

import java.util.HashMap;

public class AddCodiceOptilife extends BaseEvent<Archivio> implements ActionItem.ActionItemCallBack {

    private View view;
    private int offerId;
    private EditText codiceFiscaleOrPiva;
    private Offer offer;
    private final int CF_LENGTH = 16;
    private final int PIVA_LENGTH = 11;

    public AddCodiceOptilife(Archivio activity, View view, ListView listView, int offerId) {
        this.activity = activity;
        this.view = view;
        this.listView = listView;
        this.offerId = offerId;
        dialog = new Dialog(activity);
    }

     public void showDialog() {
        if(!isActivityAlive()){
            return;
        }

        offer = new OfferDAOImpl().getOfferById(offerId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.optilife_codice_dialog);
        codiceFiscaleOrPiva = (EditText) dialog.findViewById(R.id.codiceFiscale);

        TextView codiceLable = (TextView) dialog.findViewById(R.id.cf_lable);
        String lable;

        if(offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR) {
            lable = activity.getResources().getString(R.string.codice_fiscale);
            codiceFiscaleOrPiva.setFilters(new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(CF_LENGTH)});
        } else {
            lable = activity.getResources().getString(R.string.codice_fiscale);
            codiceFiscaleOrPiva.setFilters(new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(PIVA_LENGTH)});
        }
        codiceLable.setText(lable);
        codiceFiscaleOrPiva.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                activity.colorLengthValidation(codiceFiscaleOrPiva, s.toString().trim(),
                        offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR ? CF_LENGTH : PIVA_LENGTH);
            }
        });

        if (offer.getPiva().length() > 0) {
            codiceFiscaleOrPiva.setText(offer.getPiva());
            codiceFiscaleOrPiva.setTextColor(activity.getResources().getInteger(R.integer.text_black));
        }

        dialog.findViewById(R.id.salva).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codiceFiscaleOrPiva.getCurrentTextColor() == activity.getResources().getInteger(R.integer.text_black)) {
                    HashMap<String, String> param = new HashMap<>();
                    param.put("offerId", String.valueOf(offerId));
                    param.put("fiscale", EditTextGetTextString(R.id.codiceFiscale));
                    new SetOptilifeCodiceToBase(activity, service -> {
                        loadData();
                        dialog.dismiss();
                    }).execute(param);
                }
            }
        });
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void OnActionClickListener(ActionItem item, QuickAction source, int pos, int actionId) {
        showDialog();
    }

    public String EditTextGetTextString(int resourseId) {
        EditText editText = (EditText) dialog.findViewById(resourseId);
        return editText.getText().toString().trim();
    }

    public boolean isValid(int id) {
        boolean result = true;
        if (EditTextGetTextString(id) == null || EditTextGetTextString(id).trim().length() == 0) {
            dialog.findViewById(id).requestFocus();
            Animation shake = AnimationUtils.loadAnimation(activity, R.anim.shake);
            dialog.findViewById(id).startAnimation(shake);
            result = false;
        }
        return result;
    }
}
