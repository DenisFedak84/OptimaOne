package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;

import java.util.HashSet;
import java.util.Set;

public class ConfigActivity extends BaseActivity {

    public static final String PREFERENCES = "emailPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_activity);

        final SharedPreferences preferences = getSharedPreferences(PREFERENCES, 0);
        Set<String> current;
        current = preferences.getStringSet("email", new HashSet<>());
        current.add("lcipriani@optimaitalia.com");
        current.add("nlippiello@optimaitalia.com");
        EditText emailField = (EditText) findViewById(R.id.email);
        emailField.setText(current.toString().substring(1, current.toString().length() - 1));

        Button save_btn = (Button) findViewById(R.id.ok_btn);
        save_btn.setOnClickListener(v -> {

            String email = editTextGetTextString(R.id.email);
            String[] mails = email.split(", ");

            if (isMailArrayValid(mails)) {
                Set<String> mailSet = new HashSet<>();
                for (String mail : mails) {
                    mailSet.add(mail.trim());
                }

                SharedPreferences.Editor editor = preferences.edit();
                editor.putStringSet("email", mailSet);
                editor.apply();
                finish();
            }
        });

        Button cancel_btn = (Button) findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(v -> finish());
    }

    private boolean isMailArrayValid(String[] strs) {
        for (String mail : strs) {
            if (!isMailAdressValid(mail)) {
                Toast.makeText(this, "Inserire un valore valido in E-mail", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
