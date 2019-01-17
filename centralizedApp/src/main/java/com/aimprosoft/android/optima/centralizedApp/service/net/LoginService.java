package com.aimprosoft.android.optima.centralizedApp.service.net;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.entity.LoginEntity;
import com.aimprosoft.android.optima.centralizedApp.db.entity.User;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.util.Json;
import com.aimprosoft.android.optima.centralizedApp.util.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Map;

public class LoginService extends AbstractUrlConnectionService<Map<String, String>, User> {

    private Throwable error;

    public LoginService(Activity activity, AbstractService.OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected User doStuff(Map<String, String>... maps) {
        Map<String, String> loginMap = maps[0];
        return processLogin(loginMap.get("login"), loginMap.get("password"));
    }

    private User processLogin(String login, String password) {
        User user = new User();
        String json = Json.inJSON(toJson(login, password));
        String responce = null;
        try {
            responce = callHttpUrlConnectionPostForResult(MyApplication.getContext().getResources().getBoolean(R.bool.is_test_version) ? URLs.LOGIN_URL_TEST : URLs.LOGIN_URL, json);
            if (responce != null) {
                User[] users = Json.outJSON(Json.outJSON(responce).toString(), User[].class);
                user = users[0];
                LoginEntity loginEntity = new LoginEntity();
                loginEntity.setLogin(login);
                loginEntity.setPassword(password);
                loginEntity.setDateOfLogin(Calendar.getInstance().getTime());
//                new LoginEntityDAOImpl().update(loginEntity);
            }
        } catch (Exception e) {
            error = e;
            Log.e(this.getClass().getName(), "error duaring login");
        }
        return user;
    }

    private String toJson(String login, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", login);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            Log.e(this.getClass().getName(), "can't create JsonObject", e);
        }
        return jsonObject.toString();
    }

    public Throwable getError() {
        return error;
    }

    public boolean hasError() {
        return error!=null;
    }
}
