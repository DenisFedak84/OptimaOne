package com.aimprosoft.android.optima.centralizedApp.service.net;

import android.app.Activity;
import android.util.Base64;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.util.URLs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public abstract class AbstractUrlConnectionService<Params, Result> extends AbstractService<Params, Result> {
    public static final int SERVICE_NOT_STARTED = -1;
    private static int CONNECTION_TIMEOUT = 10000;
    public static final int SC_OK = 200;

    public static final String ACCEPT_PROPERTY = "Accept";
    public static final String USER_AGENT_PROPERTY = "User-Agent";
    public static final String CONTENT_TYPE_PROPERTY = "Content-Type";
    public static final String AUTHORIZATION_PROPERTY = "Authorization";
    public static final String POST = "POST";
    public static final String GET = "GET";

    private int responseCode = SERVICE_NOT_STARTED;
    public static final String TAG = "UrlConnectionService";

    public AbstractUrlConnectionService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    public AbstractUrlConnectionService(OnTaskCompleted onTaskCompleted) {
        super(onTaskCompleted);
    }

    public void callHttpUrlConnectionGet(String url) {
        try {
            URL urlAddress = new URL(url);
            HttpURLConnection urlConnection =
                    (HttpURLConnection) urlAddress.openConnection();
            urlConnection.connect();
            responseCode = urlConnection.getResponseCode();
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "error while sending request", e);
            responseCode = SERVICE_NOT_STARTED;
        }
    }

    public String callHttpUrlConnectionPostForResult(String url, String json) {
        String response;
        URL destinationUrl;
        HttpURLConnection urlConnection = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader in = null;
        try {
            destinationUrl = new URL(url);
            urlConnection =
                    (HttpURLConnection) destinationUrl.openConnection();
            urlConnection.setRequestMethod(POST);
            urlConnection.setRequestProperty(ACCEPT_PROPERTY, "application/json");
            urlConnection.setRequestProperty(USER_AGENT_PROPERTY, URLs.USER_AGENT);
            urlConnection.setRequestProperty(CONTENT_TYPE_PROPERTY, "application/json");
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.connect();

            outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStreamWriter.write(json);
            outputStreamWriter.flush();

            responseCode = urlConnection.getResponseCode();

            in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuilder responseStrBuilder = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                responseStrBuilder.append(inputLine);
            }
            response = responseStrBuilder.toString();
            responseCode = urlConnection.getResponseCode();
        } catch (Exception e) {
            Log.e(TAG, "Error while executing POST request", e);
            e.printStackTrace();
            response = Constants.EMPTY_STRING;
            responseCode = SERVICE_NOT_STARTED;
        } finally {
            closeConnection(urlConnection);
            closeOutputStreamWriter(outputStreamWriter);
            closeBufferedReader(in);
        }
        return response;
    }

    public String callHttpsUrlConnectionPostForResult(String url, String json, SSLSocketFactory sslSocketFactory) {
        String response;
        URL destinationUrl;
        HttpsURLConnection urlConnection = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader in = null;
        try {
            destinationUrl = new URL(url);
            urlConnection =
                    (HttpsURLConnection) destinationUrl.openConnection();
            urlConnection.setRequestMethod(POST);
            urlConnection.setRequestProperty(ACCEPT_PROPERTY, "application/json");
            urlConnection.setRequestProperty(USER_AGENT_PROPERTY, URLs.USER_AGENT);
            urlConnection.setRequestProperty(CONTENT_TYPE_PROPERTY, "application/json");
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.setSSLSocketFactory(sslSocketFactory);
            urlConnection.connect();

            outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStreamWriter.write(json);
            outputStreamWriter.flush();

            responseCode = urlConnection.getResponseCode();

            in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuilder responseStrBuilder = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                responseStrBuilder.append(inputLine);
            }
            response = responseStrBuilder.toString();
            responseCode = urlConnection.getResponseCode();
        } catch (Exception e) {
            Log.e(TAG, "Error while executing GET request", e);
            response = Constants.EMPTY_STRING;
            responseCode = SERVICE_NOT_STARTED;
        } finally {
            closeConnection(urlConnection);
            closeOutputStreamWriter(outputStreamWriter);
            closeBufferedReader(in);
        }
        return response;
    }

    public void callHttpsUrlConnectionGet(String url, SSLSocketFactory sslSocketFactory) {
        try {
            URL urlAddress = new URL(url);
            HttpsURLConnection urlConnection =
                    (HttpsURLConnection) urlAddress.openConnection();
            urlConnection.setSSLSocketFactory(sslSocketFactory);
            urlConnection.connect();
            responseCode = urlConnection.getResponseCode();
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "error while sending request", e);
            responseCode = SERVICE_NOT_STARTED;
        }
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    private static void closeConnection(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    private static void closeOutputStreamWriter(OutputStreamWriter outputStreamWriter) {
        if (outputStreamWriter != null) {
            try {
                outputStreamWriter.close();
            } catch (IOException e) {
                Log.e(TAG, "error while closing output stream", e);
            }
        }
    }

    private static void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "error while closing input stream", e);
            }
        }
    }

    private static void closeBufferedReader(BufferedReader bufferedReader) {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                Log.e(TAG, "error while closing buffered reader", e);
            }
        }
    }

    public InputStream openURLForInput
            (URL url, String uname, String password)
            throws IOException {
        URLConnection conn = url.openConnection();
        conn.setDoInput(true);
        conn.setRequestProperty(AUTHORIZATION_PROPERTY, userNamePasswordBase64(uname, password));
        conn.connect();
        return conn.getInputStream();
    }

    public static String userNamePasswordBase64
            (String username, String password) {
        return "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.DEFAULT).trim();
    }
}
