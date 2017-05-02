package iomango.com.forestdirect.mvp.common.managers;

import android.os.Handler;

import iomango.com.forestdirect.mvp.common.interfaces.Listener;

/**
 * Created by Clelia López on 4/27/17
 */

public class FutureTaskManager {

    /**
     * Attributes
     */
    private static Handler handler;


    private FutureTaskManager() {
        if (handler == null)
            handler = new Handler();
    }

    public static void executeAfter(final Listener.ExecutorListener listener, final String name, int time) {
        new FutureTaskManager();
        handler.postDelayed(new Runnable() {
            public void run() {
               listener.execute(name);
            }
        }, time);
    }
}
