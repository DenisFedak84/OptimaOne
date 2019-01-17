package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.Archivio;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;

import java.util.HashSet;
import java.util.Set;

public class EmailSendEvent extends BaseEvent<Archivio> {

    public static final String PREFERENCES = "emailPreferences";

    public EmailSendEvent(Archivio activity, final String filePath) {
        this.activity = activity;
        showDialog(activity, filePath);
    }

    private void showDialog(BaseActivity activity, String filePath) {
        if (!isActivityAlive()) {
            return;
        }
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.send_file_dialog);
        Button positiveButton = (Button) dialog.findViewById(R.id.invia_contratto_btn);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && isActivityAlive()) {
                    final SharedPreferences preferences = activity.getSharedPreferences(PREFERENCES, 0);
                    String subject = "Archivio B2B TestRev";
                    Set<String> emails = preferences.getStringSet("email", new HashSet<String>());

                    if (emails.size() == 0) {
                        emails.add("lcipriani@optimaitalia.com");
                        emails.add("nlippiello@optimaitalia.com");
                    }

                    Intent i = new Intent(Intent.ACTION_SEND);

                    String[] strs = emails.toArray(new String[emails.size()]);

                    i.setType("text/xml");
                    i.putExtra(Intent.EXTRA_EMAIL, strs);
                    i.putExtra(Intent.EXTRA_SUBJECT, subject);
                    i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + R.id.filePath));
                    activity.startActivity(Intent.createChooser(i, "Send with"));
                    dialog.dismiss();
                }
            }
        });

        Button negativeButton = (Button) dialog.findViewById(R.id.annulla_btn);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && isActivityAlive()) {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}
