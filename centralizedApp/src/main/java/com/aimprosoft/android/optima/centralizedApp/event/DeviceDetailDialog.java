package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;

public class DeviceDetailDialog extends BaseEvent {

    public final double WIDTH_COEFFICIENT_PORTRAIT = 0.85;
    public final double WIDTH_COEFFICIENT_LANDSCAPE = 1.3;
    public final double HEIGHT_COEFFICIENT_PORTRAIT = 0.77;
    public final double HEIGHT_COEFFICIENT_LANDSCAPE = 0.7;
    private WebView webView;

    public DeviceDetailDialog(BaseActivity activity) {
        this.activity = activity;
        setUpDialog();
    }

    public void showDeviceDialog(int deviceId) {
        if (isActivityAlive() && dialog != null) {
            calculateDimensions();
            setData(deviceId);
            dialog.show();
        }
    }

    private void clearZoom() {
        while (webView.zoomOut()) {
        }
    }

    private void setData(int deviceId) {
        String html = dialog.getContext().getResources().getString(R.string.device_image_path, deviceId);
        webView.loadDataWithBaseURL("", html, "text/html", "utf-8", "");
    }

    private void setUpDialog() {
        if (!isActivityAlive()) {
            return;
        }

        dialog = new Dialog(activity, R.style.DeviceDetailDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.device_detail);
        setUpWebView();
        calculateDimensions();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (dialog != null && isActivityAlive()) {
                    clearZoom();
                }
            }
        });

//        dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
    }

    private void setUpWebView() {
        webView = (WebView) dialog.findViewById(R.id.webView);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
    }

    private void calculateDimensions() {
        WindowManager wm = (WindowManager) dialog.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width;
        int height;
        if (dialog.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            width = (int) (size.x * WIDTH_COEFFICIENT_PORTRAIT);
            height = (int) (width * HEIGHT_COEFFICIENT_PORTRAIT);
        } else {
            height = (int) (size.y * HEIGHT_COEFFICIENT_LANDSCAPE);
            width = (int) (height * WIDTH_COEFFICIENT_LANDSCAPE);
        }
        dialog.getWindow().setLayout(width, height);
    }
}
