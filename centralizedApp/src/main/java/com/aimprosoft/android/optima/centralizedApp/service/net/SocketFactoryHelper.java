package com.aimprosoft.android.optima.centralizedApp.service.net;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class SocketFactoryHelper {

    private static SocketFactoryHelper instance;

    public SocketFactoryHelper() {
    }

    public static SocketFactoryHelper getInstance() {
        if (instance == null)
            synchronized (SocketFactoryHelper.class) {
                if (instance == null)
                    instance = new SocketFactoryHelper();
            }
        return instance;
    }

    public javax.net.ssl.SSLSocketFactory buildSslSocketFactory(Context context, String certificatePath) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream is = context.getAssets().open(certificatePath);
            InputStream caInput = new BufferedInputStream(is);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
            } finally {
                caInput.close();
            }

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext con = SSLContext.getInstance("TLS");
            con.init(null, tmf.getTrustManagers(), null);
            return con.getSocketFactory();

        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | CertificateException | IOException e) {
            Log.e(getClass().getSimpleName(), "error while creating Keystore from certificate", e);
        }
        return null;
    }
}
