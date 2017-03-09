package iomango.com.forestdirect.mvp.common.interfaces;

/**
 * Created by clelia_arch on 3/8/17
 */

public interface Listener {

    interface OnNetworkResponseListener {
        <T> void processResponse(T response);
    }
}
