package net.openid.appauth;

import android.os.AsyncTask;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Creates {@link javax.net.ssl.HttpsURLConnection} instances using the default for verification
 * link/
 */

public class VerifyLink extends AsyncTask<String, Void, Boolean> {

    protected Boolean doInBackground(String... params) {

        HttpsURLConnection conn = null;
        boolean isValidLink = true;
        try {
            final URL myUrl = new URL(params[0]);
            conn = (HttpsURLConnection)
                myUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
        } catch (Exception e) {
            isValidLink = false;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return isValidLink;
    }
}
