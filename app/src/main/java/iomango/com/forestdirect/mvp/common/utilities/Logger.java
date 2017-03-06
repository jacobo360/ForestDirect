package iomango.com.forestdirect.mvp.common.utilities;

import android.util.Log;

/**
 * Created by Clelia López on 6/5/16
 */
public class Logger {

    private String TAG;
    private final String separator = "------- ";

    public Logger(String tag) {
        TAG = tag;
    }

    public void log(String message) {
        Log.d(TAG, separator + message);
    }

    public void logError(String message) {
        Log.e(TAG, separator + message);
    }
}