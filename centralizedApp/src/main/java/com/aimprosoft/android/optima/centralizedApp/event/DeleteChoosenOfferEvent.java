package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.event.listener.OnEventClosed;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.DeleteAllOfferInfo;
import com.aimprosoft.android.optima.centralizedApp.util.ActionItem;
import com.aimprosoft.android.optima.centralizedApp.util.QuickAction;


public class DeleteChoosenOfferEvent extends BaseEvent implements ActionItem.ActionItemCallBack {

    private View view;
    private int offerId;
    private Dialog dialog;
    private OnEventClosed onEventClosed;

    public DeleteChoosenOfferEvent(BaseActivity activity, View view, int offerId, OnEventClosed onEventClosed) {
        this.activity = activity;
        this.view = view;
        this.offerId = offerId;
        this.onEventClosed = onEventClosed;
    }

    private void showDialog(View v) {
        if (!isActivityAlive()) {
            return;
        }
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delete_dialog);
        dialog.findViewById(R.id.del_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteAllOfferInfo(activity, new AbstractService.OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(AbstractService service) {
                        Toast completeMessage = Toast.makeText(activity, "Dati è stato rimosso", Toast.LENGTH_LONG);
                        completeMessage.setGravity(Gravity.CENTER, 0, 0);
                        completeMessage.show();
                        if (onEventClosed != null && isActivityAlive()) {
                            onEventClosed.onClosed();
                        }
                    }
                }).execute(offerId);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.del_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && isActivityAlive())
                    dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void OnActionClickListener(ActionItem item, QuickAction source, int pos, int actionId) {
        showDialog(view);
    }
}
