package com.aimprosoft.android.optima.centralizedApp.service;


import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.service.net.AbstractUrlConnectionService;
import com.aimprosoft.android.optima.centralizedApp.util.URLs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckForUpdatesService extends AbstractUrlConnectionService<Void, Integer> {

    public static final String LOGIN = "android";
    public static final String PASS = "android$$1";

    public CheckForUpdatesService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected Integer doStuff(Void... voids) {
        Integer result = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(openURLForInput(new URL(URLs.UPDATE_URL), LOGIN, PASS)));
            String line;
            StringBuilder buffer;
            buffer = new StringBuilder();
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }

            Pattern pattern = Pattern.compile("([Oo]ptima[Oo]ne)(\\d+)");
            Matcher matcher = pattern.matcher(buffer);

            while (matcher.find()) {
                result = Integer.valueOf(matcher.group(2));
            }

        } catch (IOException e) {
            Log.e(TAG, "error while executing CheckForUpdatesService");
            result = 0;
        }

        return result;
    }
}
