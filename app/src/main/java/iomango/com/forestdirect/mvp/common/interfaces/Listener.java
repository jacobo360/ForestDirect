package iomango.com.forestdirect.mvp.common.interfaces;

import iomango.com.forestdirect.mvp.common.utilities.Date;
import iomango.com.forestdirect.mvp.model.AdvancedOptionsModel;
import iomango.com.forestdirect.mvp.view.custom.CustomEditText;

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

    interface OnLocationSelectedListener {
        <T> void updateLocation(T location);
    }

    interface OnMultiCityActionListener {
        void onSearchButtonClicked();
        void onFromEditTextClicked(CustomEditText editText);
        void onToEditTextClicked(CustomEditText editText);
    }

    interface OnDateSetListener {
        void updateDate(Date date, int id);
    }

    interface OnAdapterErrorListener {
        void reportError();
    }

    interface ExecutorListener {
        void execute(String name);
    }
}
