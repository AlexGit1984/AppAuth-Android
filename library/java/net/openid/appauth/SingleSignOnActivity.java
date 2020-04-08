package net.openid.appauth;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.webkit.WebView;

/**
 * {@link SingleSignOnActivity} via WebView provides single sign-on process by user.
 */
public class SingleSignOnActivity extends Activity {

  @Override
  protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    setContentView(R.layout.custom_web);
    final WebView mWebView = findViewById(R.id.web_view);
    final CustomWebView mCustomWebView = new CustomWebView(this);
    mCustomWebView.configureWebView(mWebView);
    mCustomWebView.navigateTo(getIntent().getData().toString());
  }
}
