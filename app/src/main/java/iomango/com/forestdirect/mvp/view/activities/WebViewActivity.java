package iomango.com.forestdirect.mvp.view.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import iomango.com.forestdirect.R;

/**
 * Created by Clelia López on 3/21/2017
 */
public class WebViewActivity
        extends AppCompatActivity {

    /**
     * Attributes
     */
    private String source;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        source = getIntent().getStringExtra("source");

        initializeViews();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initializeViews() {
        final WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new MyBrowser());

        if (source != null)
            webView.loadDataWithBaseURL("https://forestdirect.com/flights/search", source, "text/html", "UTF-8", null);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.loadDataWithBaseURL("https://forestdirect.com/flights/search", source, "text/html", "UTF-8", null);
            }
        }, 5000);

    }

    // Manages the behavior when URLs are loaded
    private class MyBrowser
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
