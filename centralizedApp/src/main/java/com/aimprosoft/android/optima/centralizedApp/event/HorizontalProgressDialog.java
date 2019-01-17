package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnCancelClickListener;

public class HorizontalProgressDialog extends BaseEvent<BaseActivity> {

    private static final float DIALOG_TAKEN_AREA_PERCENT_Y = 0.8f;
    private static final float DIALOG_TAKEN_AREA_PERCENT_X = 0.55f;
    private ProgressBar progressBar;
    private TextView progressMessage;
    private TextView progressValue;
    private Button okButton;
    private Button cancelButton;
    private OnCancelClickListener onCancelClickListener;
    private int maxValue;

    public HorizontalProgressDialog(BaseActivity activity) {
        this.activity = activity;
        setUpDialog(activity);
    }

    private void setUpDialog(BaseActivity activity) {
        if (!isActivityAlive()) {
            return;
        }
        dialog = new Dialog(activity, R.style.AppDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setOwnerActivity(activity);
        calculateDimensions();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        dialog.setContentView(R.layout.horizontal_progress_dialog);

        progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);
        progressMessage = (TextView) dialog.findViewById(R.id.progressMessage);
        progressValue = (TextView) dialog.findViewById(R.id.progressValue);

        okButton = (Button) dialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && isActivityAlive()) {
                    okAction();
                }
            }
        });

        cancelButton = (Button) dialog.findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && isActivityAlive()) {
                    cancelAction();
                }
            }
        });
    }

    private void cancelAction() {
        onCancelClickListener.onCancelClick();
        progressValue.setVisibility(View.GONE);
        progressBar.setIndeterminate(true);
        progressMessage.setText(dialog.getContext().getString(R.string.canceling));
        cancelButton.setVisibility(View.GONE);
    }

    private void okAction() {
        dismissDialog();
        okButton.setVisibility(View.GONE);
        progressValue.setVisibility(View.GONE);
        progressBar.setIndeterminate(true);
        maxValue = 0;
    }

    public void complete(String message) {
        if (dialog != null && isActivityAlive()) {
            okButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.GONE);
            setMessage(message);
        }
    }

    public void setMax(int maxValue) {
        progressBar.setMax(maxValue);
        this.maxValue = maxValue;
    }

    public void setIndeterminate(boolean isIndeterminate) {
        progressBar.setIndeterminate(isIndeterminate);
    }

    public void setMessage(final String message) {
        getOwnerActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog != null & isActivityAlive()) {
                    progressMessage.setText(message);
                }
            }
        });
    }

    public void dismissDialog() {
        if (dialog != null && isActivityAlive()) {
            dialog.dismiss();
        }
    }

    public void setProgress(int progress) {
        progressBar.setProgress(progress);
        progressValue.setText(dialog.getContext().getString(R.string.progress_value, progress, maxValue));
    }

    public void showDialog() {
        if (isActivityAlive() && dialog != null) {
            dialog.show();
        }
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

    public void setProgressIndicatorVisibility(boolean isVisible) {
        progressValue.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public OnCancelClickListener getOnCancelClickListener() {
        return onCancelClickListener;
    }

    public void setOnCancelClickListener(OnCancelClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
    }

    public Activity getOwnerActivity() {
        return dialog.getOwnerActivity();
    }
}
