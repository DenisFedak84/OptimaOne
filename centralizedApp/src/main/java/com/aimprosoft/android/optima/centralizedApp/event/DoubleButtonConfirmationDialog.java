package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.event.listener.OnDialogSubmitListener;

public class DoubleButtonConfirmationDialog extends BaseEvent {

    private static final float DIALOG_TAKEN_AREA_PERCENT_Y = 0.8f;
    private static final float DIALOG_TAKEN_AREA_PERCENT_X = 0.55f;
    private OnDialogSubmitListener onDialogSubmitListener;

    public DoubleButtonConfirmationDialog(BaseActivity activity, String message) {
        this.activity = activity;
        setUpDialog(message);
    }

    private void setUpDialog(String message) {
        if (!isActivityAlive()) {
            return;
        }

        dialog = new Dialog(activity, R.style.AppDialogStyle);
        calculateDimensions();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        dialog.setContentView(R.layout.double_button_dialog);

        TextView mesasgeView = (TextView) dialog.findViewById(R.id.message);
        mesasgeView.setText(message);

        dialog.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCallback(true);
            }
        });

        dialog.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCallback(false);
            }
        });
    }

    private void sendCallback(boolean result) {
        if (onDialogSubmitListener != null && isActivityAlive()) {
            onDialogSubmitListener.onDialogSubmit(result);
        }
        dialog.dismiss();
    }

    public void showDialog() {
        if (isActivityAlive() && dialog!=null) {
            dialog.show();
        }
    }

    public OnDialogSubmitListener getOnDialogSubmitListener() {
        return onDialogSubmitListener;
    }

    public void setOnDialogSubmitListener(OnDialogSubmitListener onDialogSubmitListener) {
        this.onDialogSubmitListener = onDialogSubmitListener;
    }

    private void calculateDimensions() {
        WindowManager wm = (WindowManager) dialog.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = (int) (size.y * DIALOG_TAKEN_AREA_PERCENT_Y);
        int width = (int) (size.x * DIALOG_TAKEN_AREA_PERCENT_X);
        dialog.getWindow().setLayout(width, height);
    }
}
