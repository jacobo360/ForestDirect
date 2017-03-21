package iomango.com.forestdirect.mvp.view.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by Clelia López on 3/21/2017
 */
public class WebViewActivity
        extends AppCompatActivity {

    /**
     * Attributes
     */
    private WebView webView;
    private String source;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);

        source = getIntent().getStringExtra("source");

        initializeViews();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initializeViews() {
        WebSettings settings = webView.getSettings();
        settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new AppBrowser());

        String url = "https://forestdirect.com/flights/search";
        if (source != null)
            webView.loadDataWithBaseURL(url, source, "text/html", "UTF-8", null);
        else
            finish();
    }


    private class AppBrowser
            extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }
}
