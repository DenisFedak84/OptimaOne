package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.net.AbstractUrlConnectionService;
import com.aimprosoft.android.optima.centralizedApp.service.net.SendSDCDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EsportaDialog extends BaseEvent {
    private LinearLayout llEmail;
    private Dialog dialog;
    private Offer offer;
    private CheckBox cartaceo;
    private OfferDAOImpl offerDAO;
    private ImageView plusButton;

    public EsportaDialog(BaseActivity activity, Offer offer) {
        this.offer = offer;
        this.activity = activity;
        createDialog();
    }

    private void createDialog() {
        if (!isActivityAlive()) {
            return;
        }
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.espotra_dialog_layout);

        llEmail = (LinearLayout) dialog.findViewById(R.id.llEmail);
        cartaceo = (CheckBox) dialog.findViewById(R.id.cartaceo);
        plusButton = (ImageView) dialog.findViewById(R.id.plusButtonImageView);

        addListeners();
    }

    public void showDialog() {
        if (isActivityAlive() && dialog != null) {
            dialog.show();
            cartaceo.setChecked(offer.isCartaceo());
            llEmail.removeAllViews();
            addEmailRow();
        }
    }

    private void addListeners() {
        final List<String> emailList = new ArrayList<>();

        final Button btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && isActivityAlive()) {
                    if (!cartaceo.isChecked()) {
                        if (llEmail.getChildCount() == 1) {
                            EditText et = activity.getEmailTextField(llEmail, 0);
                            if (activity.checkMail(et, llEmail)) {
                                String email = et.getText().toString();
                                emailList.add(email);
                                sendJson(emailList);
                            }
                        } else {
                            if (activity.isLastEmailValid(llEmail)) {
                                for (int i = 0; i < llEmail.getChildCount(); i++) {
                                    emailList.add(activity.getEmailTextField(llEmail, i).getText().toString());
                                }
                                sendJson(emailList);
                            }
                        }
                    } else {
                        offer.setPdfSendingRequired(false);
                        saveAdditionalParams();
                        dialog.dismiss();
                    }
                }
            }
        });

//        cartaceo.setChecked(offer.isCartaceo());
        cartaceo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (dialog != null && isActivityAlive()) {
                    plusButton.setBackgroundResource(isChecked ? R.drawable.plus : R.drawable.plus_enabled);
                    llEmail.removeAllViews();
                    addEmailRow();
                    llEmail.setClickable(isChecked);
                }
            }
        });

        LinearLayout btnPlus = (LinearLayout) dialog.findViewById(R.id.plusButton);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && isActivityAlive() && !cartaceo.isChecked() && (activity.isLastEmailValid(llEmail))) {
                    addEmailRow();
                }
            }
        });

        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && isActivityAlive()) {
                    dialog.dismiss();
                }
            }
        });
    }

    private void addEmailRow() {
        final RelativeLayout ll = (RelativeLayout) ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.email_edittext_layout, null);
        ll.findViewById(R.id.removeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llEmail.removeView((View) v.getParent());
            }
        });
        EditText tv = (EditText) ll.findViewById(R.id.etEmail);
        if (cartaceo.isChecked()) {
            tv.clearFocus();
        } else {
            tv.requestFocus();
        }
        tv.setEnabled(!cartaceo.isChecked());
        llEmail.addView(ll);
    }

    private void sendJson(final List<String> emailAddressList) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(SendSDCDataService.OFFER, offer);
        objectMap.put(SendSDCDataService.EMAILS, emailAddressList);

        SendSDCDataService sendSDCDataService = new SendSDCDataService(activity, new AbstractService.OnTaskCompleted<SendSDCDataService>() {
            @Override
            public void onTaskCompleted(SendSDCDataService service) {
                if (dialog != null && isActivityAlive()) {
                    boolean result = !service.hasError() && service.getResult().equals(AbstractUrlConnectionService.SC_OK);
                    offer.setPdfSendingRequired(!result);
                    String resultMessage = result ?
                            activity.getResources().getString(R.string.json_send_yes) : activity.getResources().getString(R.string.json_send_no);
                    saveAdditionalParams();
                    Toast.makeText(activity, resultMessage, Toast.LENGTH_LONG).show();
                    emailAddressList.clear();
                    dialog.dismiss();
                }
            }
        });
        sendSDCDataService.execute(objectMap);
    }

    private void saveAdditionalParams() {
        offer.setCartaceo(cartaceo.isChecked());
        if (offerDAO == null) {
            offerDAO = new OfferDAOImpl();
        }
        offerDAO.update(offer);
        MyApplication.set(Constants.OFFER_ENTITY, offer);
    }
}
