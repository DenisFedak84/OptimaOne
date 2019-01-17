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
import com.aimprosoft.android.optima.centralizedApp.event.listener.OnDialogDismissedListener;

public class ConfirmationDialog extends BaseEvent {

    private static final float DIALOG_TAKEN_AREA_PERCENT_Y = 0.8f;
    private static final float DIALOG_TAKEN_AREA_PERCENT_X = 0.55f;
    private OnDialogDismissedListener onDialogDismissedListener;

    public ConfirmationDialog(BaseActivity activity, String text) {
        this.activity = activity;
        setUpDialog(text);
    }

    private void setUpDialog(String text) {
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
        dialog.setContentView(R.layout.confirmation_dialog);

        TextView textContainer = (TextView) dialog.findViewById(R.id.text);
        textContainer.setText(text);

        dialog.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDialogDismissedListener != null && isActivityAlive()) {
                    onDialogDismissedListener.onDialogDismiss();
                }
                dialog.dismiss();
            }
        });
    }

    public void showDialog() {
        if (isActivityAlive() && dialog != null) {
            dialog.show();
        }
    }

    public OnDialogDismissedListener getOnDialogDismissedListener() {
        return onDialogDismissedListener;
    }

    public void setOnDialogDismissedListener(OnDialogDismissedListener onDialogDismissedListener) {
        this.onDialogDismissedListener = onDialogDismissedListener;
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
