package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.DownloadFileService;

import java.io.File;

public class AutoUpdateDialog extends BaseEvent {

    public AutoUpdateDialog(BaseActivity activity, int apkVersion) {
        this.activity = activity;
        showDialog(apkVersion);
    }

    private void showDialog(final int apkVersion) {
        if (!isActivityAlive()) {
            return;
        }
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.auto_update_dialog);

        dialog.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadFileService(activity, new AbstractService.OnTaskCompleted<DownloadFileService>() {
                    @Override
                    public void onTaskCompleted(DownloadFileService service) {
                        String fileName = service.getResult();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
                        activity.startActivity(intent);
                    }
                }).execute(apkVersion);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
