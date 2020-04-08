package net.openid.appauth;

import android.os.AsyncTask;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Creates {@link javax.net.ssl.HttpsURLConnection} instances using the default, platform-provided
 * mechanism, with sensible production defaults.
 */

public class VerifyLink extends AsyncTask<String, Void, Boolean> {

    protected Boolean doInBackground(String... params) {
        try {
            URL myUrl = new URL(params[0]);
            HttpsURLConnection conn = (HttpsURLConnection)
                myUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
