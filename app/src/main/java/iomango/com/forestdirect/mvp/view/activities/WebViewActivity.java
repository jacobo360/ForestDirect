package iomango.com.forestdirect.mvp.view.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.ExecutorListener;
import iomango.com.forestdirect.mvp.common.managers.FutureTaskManager;


/**
 * Created by Clelia López on 3/21/2017
 */
public class WebViewActivity
        extends AppCompatActivity
        implements ExecutorListener {


    /**
     * Attributes
     */
    private LinearLayout linearLayoutDialog;
    private String source;
    private ExecutorListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        source = getIntent().getStringExtra("source");
        listener = this;

        initializeViews();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initializeViews() {

        linearLayoutDialog = (LinearLayout)findViewById(R.id.linear_dialog_dialog);
        WebView webView = (WebView) findViewById(R.id.web_view);
        linearLayoutDialog.setVisibility(View.VISIBLE);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        // WebView set up
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
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            FutureTaskManager.executeAfter(listener, "hide", 3, false);
        }
    }

    @Override
    public void execute(String name) {
        linearLayoutDialog.setVisibility(View.GONE);
    }
}
