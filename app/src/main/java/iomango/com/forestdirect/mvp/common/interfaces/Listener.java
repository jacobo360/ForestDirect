package iomango.com.forestdirect.mvp.common.interfaces;

import iomango.com.forestdirect.mvp.model.AdvancedOptionsModel;

/**
 * Created by clelia_arch on 3/8/17
 */

public interface Listener {

    interface OnNetworkResponseListener {
        <T> void processResponse(T response);
    }

    interface OnAdvanceOptionsListener {
        void updateTextView(AdvancedOptionsModel model);
    }

    interface OnAmountChangeListener {
        void amountIncrease(int viewId, int amount);
        void amountDecrease(int viewId, int amount);
    }
}
