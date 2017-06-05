package iomango.com.forestdirect.mvp.common.utilities;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import iomango.com.forestdirect.mvp.common.interfaces.Listener.ExecutorListener;
import iomango.com.forestdirect.mvp.common.managers.FutureTaskManager;

/**
 * Created by Clelia López on 5/29/17
 */

public class AppBrowser
        extends WebViewClient {


    /**
     * Attributes
     */
    private ExecutorListener listener;


    public AppBrowser(ExecutorListener listener) {
        this.listener = listener;
    }

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