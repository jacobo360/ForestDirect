package iomango.com.forestdirect.mvp.view.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.ExecutorListener;
import iomango.com.forestdirect.mvp.common.utilities.AppBrowser;


/**
 * Created by Clelia López on 3/21/2017
 */
public class WebViewActivity
        extends AppCompatActivity
        implements ExecutorListener {


    /**
     * Attributes
     */
    private RelativeLayout relativeLayoutDialog;
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

        relativeLayoutDialog = (RelativeLayout) findViewById(R.id.linear_dialog_dialog);
        WebView webView = (WebView) findViewById(R.id.web_view);
        relativeLayoutDialog.setVisibility(View.VISIBLE);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        // WebView set up
        WebSettings settings = webView.getSettings();
        settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new AppBrowser(this));

        String url = "https://forestdirect.com/flights/search";
        if (source != null)
            webView.loadDataWithBaseURL(url, source, "text/html", "UTF-8", null);
        else
            finish();
    }

    @Override
    public void execute(String name) {
        relativeLayoutDialog.setVisibility(View.GONE);
    }
}
