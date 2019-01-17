package com.aimprosoft.android.optima.centralizedApp.activity.itus;

import android.content.Intent;
import android.os.Bundle;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.MainActivity;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;

public class IntegrationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integration_screen);

        Intent itusIntent = getIntent();
        if(itusIntent!=null && !itusIntent.getExtras().isEmpty()) {
            String name = itusIntent.getStringExtra(Constants.NOME);
            String cognome = itusIntent.getStringExtra(Constants.COGNOME_OR_RAGIONE_SOCIALE);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(Constants.NOME, name);
            intent.putExtra(Constants.COGNOME_OR_RAGIONE_SOCIALE, cognome);
            startActivity(intent);
        }
    }
}
