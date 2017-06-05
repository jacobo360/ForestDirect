package iomango.com.forestdirect.mvp.view.fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericFragment;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.ExecutorListener;
import iomango.com.forestdirect.mvp.common.managers.FutureTaskManager;
import iomango.com.forestdirect.mvp.presenter.EmptyPresenterFragment;

/**
 * Created by Clelia López on 03/10/2017
 */
public class WebViewFragment
        extends GenericFragment<MVP.RequiredFragmentMethods, MVP.ProvidedPresenterMethodsFragment, EmptyPresenterFragment>
        implements MVP.RequiredFragmentMethods, ExecutorListener {


    /**
     * Attributes
     */
    private RelativeLayout relativeLayoutDialog;
    private RelativeLayout relativeContainer;
    private ExecutorListener listener;
    private String urlPage;


    public static WebViewFragment newInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        WebViewFragment webViewFragment = new WebViewFragment();
        webViewFragment.setArguments(bundle);
        return webViewFragment;
    }

    /**
     * Hook method called to set up the fragment's user interface. It returns a View object,
     * that is given to the hosting activity to install it into its view hierarchy.
     *
     * @param container view parent of the fragment in the activity, therefore its container
     * @param savedInstanceState object that contains saved state information.
     * @return View object returned by the inflation process
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        relativeContainer = (RelativeLayout) inflater.inflate(R.layout.activity_web_view, container, false);

        // Initialize retained fragment state
        isRetainedFragment = false;

        // Initialize the view components defined in the fragment's layout
        initializeViews();

        return relativeContainer;
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initializeViews() {
        relativeLayoutDialog = (RelativeLayout) relativeContainer.findViewById(R.id.linear_dialog_dialog);
        relativeLayoutDialog.setVisibility(View.GONE);

        WebView webView = (WebView) relativeContainer.findViewById(R.id.web_view);
        ProgressBar progressBar = (ProgressBar) relativeContainer.findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        listener = this;

        // WebView set up
        WebSettings settings = webView.getSettings();
        settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());

        if (getArguments() != null) {
            urlPage = getArguments().getString("url", "");
            webView.loadUrl(urlPage);
        }
    }

    private class AppBrowser
            extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(urlPage);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            FutureTaskManager.executeAfter(listener, "hide", 3, false);
        }
    }

    @Override
    public void execute(String name) {
        relativeLayoutDialog.setVisibility(View.GONE);
    }
}
