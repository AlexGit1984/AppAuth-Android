package net.openid.appauth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.support.annotation.NonNull;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.concurrent.ExecutionException;

import static net.openid.appauth.AuthorizationException.NETWORK_ERROR;
import static net.openid.appauth.AuthorizationRequest.PARAM_CODE;
import static net.openid.appauth.AuthorizationRequest.PARAM_STATE;

/**
 * Class {@link CustomWebView} encapsulates logic for working with {@link WebViewClient} for SSO
 * flow.
 */

class CustomWebView extends WebViewClient {

    private static final String SSO = "sso";
    private final Context context;
    private WebView webView;

    CustomWebView(@NonNull final Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(@NonNull final WebView view,
        @NonNull final WebResourceRequest request) {
        Uri url = request.getUrl();
        String stateParam = url.getQueryParameter(PARAM_STATE);
        String codeParam = url.getQueryParameter(PARAM_CODE);

        if (url.getPath() != null && url.getPath().contains(SSO) && stateParam != null
            && codeParam != null) {
            navigateToRederictActivity(url);
            return true;
        }
        return super.shouldOverrideUrlLoading(view, request);
    }

    /**
     * Attach {@link WebView} to {@link WebViewClient} and initialize it.
     *
     * @param webView - view
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void configureWebView(@NonNull final WebView webView) {
        this.webView = webView;
        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(this);
        webView.setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * Navigate to specific url in {@link WebView}
     *
     * @param url url to open.
     */
    public void navigateTo(@NonNull final String url) {
       VerifyLink authServer =  new VerifyLink();
        try {
            if(authServer.execute(url).get()){
                webView.loadUrl(url);
            }else {
                navigateToRederictActivity(Uri.parse(NETWORK_ERROR));
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            navigateToRederictActivity(Uri.parse(NETWORK_ERROR));
        }
    }

    private void navigateToRederictActivity(@NonNull final Uri url) {
        Intent intent = new Intent(context, RedirectUriReceiverActivity.class);
        intent.setData(url);
        context.startActivity(intent);
    }
}
