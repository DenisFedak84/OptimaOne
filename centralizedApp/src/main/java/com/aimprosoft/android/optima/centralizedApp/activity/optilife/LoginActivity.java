package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.app.receiver.LogoutTimeReceiver;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.net.LoginService;
import com.aimprosoft.android.optima.centralizedApp.util.AlarmUtils;
import com.aimprosoft.android.optima.centralizedApp.util.LocalSharedPreferencesManager;
import com.aimprosoft.android.optima.centralizedApp.util.LogoutUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {

    private TextView loginError;
    private TextView connectionError;
    private EditText nome;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        checkUserAutorization();
        initializeFields();

        findViewById(R.id.accedi).setOnClickListener(v -> {
            if (isValid(true, R.id.nome, R.id.password)) {
                hideErrors();
                login();
            }
//            startChildActivity(MainActivity.class);
        });
    }

    private void checkUserAutorization() {
        boolean isUserLoggined = LocalSharedPreferencesManager.getInstance().getSharedPreferencesBooleanValue(this, LocalSharedPreferencesManager.IS_USER_LOGGINED);
        boolean isSessionExpired = LogoutUtils.isLogoutNeeded();
        if (!isSessionExpired && isUserLoggined) {
            startChildActivity(MainActivity.class);
//            MyApplication.setUpLogoutReceiver();
        }
//        else {
//            LocalSharedPreferencesManager.getInstance().storeSharedPreferencesBooleanValue(this, LocalSharedPreferencesManager.IS_USER_LOGGINED, false);
//            LocalSharedPreferencesManager.getInstance().storeSharedPreferencesLongValue(this, LocalSharedPreferencesManager.LOGIN_TIMESTAMP, 0);
//        }
    }

    private void initializeFields() {
        loginError = (TextView) findViewById(R.id.loginError);
        connectionError = (TextView) findViewById(R.id.loginErrorConnection);
        nome = (EditText) findViewById(R.id.nome);
        password = (EditText) findViewById(R.id.password);
    }

    private void hideErrors() {
        loginError.setVisibility(View.INVISIBLE);
        connectionError.setVisibility(View.INVISIBLE);
    }

    private void login() {
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("login", editTextGetTextString(R.id.nome));
        loginMap.put("password", editTextGetTextString(R.id.password));

        LoginService loginService = new LoginService(this, new AbstractService.OnTaskCompleted<LoginService>() {
            @Override
            public void onTaskCompleted(LoginService service) {
                if (service.hasError()) {
                    Animation showAnimation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.show_from_top);
                    Throwable throwableError = service.getError();
                    boolean isClassCastException = throwableError instanceof ClassCastException;
                    if (isClassCastException) {
                        loginError.startAnimation(showAnimation);
                        loginError.setVisibility(View.VISIBLE);
                    } else {
                        connectionError.startAnimation(showAnimation);
                        connectionError.setVisibility(View.VISIBLE);
                    }
                } else {
                    LocalSharedPreferencesManager.getInstance().storeSharedPreferencesLongValue(LoginActivity.this,
                            LocalSharedPreferencesManager.LOGIN_TIMESTAMP,
                            Calendar.getInstance().getTimeInMillis());
                    LocalSharedPreferencesManager.getInstance().storeSharedPreferencesBooleanValue(LoginActivity.this,
                            LocalSharedPreferencesManager.IS_USER_LOGGINED,
                            true);
                    LocalSharedPreferencesManager.getInstance().storeSharedPreferencesStringValue(LoginActivity.this,
                            LocalSharedPreferencesManager.ID_SOGGETTO,
                            service.getResult().getIdSoggetto());
                    AlarmUtils.setupAlarm(LoginActivity.this, LogoutTimeReceiver.class);
                    startChildActivity(MainActivity.class);
                }
            }
        });
        loginService.execute(loginMap);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode != 4 && super.onKeyDown(keyCode, event);
    }
}
