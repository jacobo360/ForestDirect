package iomango.com.forestdirect.mvp.model.request;

import java.io.IOException;

import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnNetworkResponseListener;
import iomango.com.forestdirect.mvp.common.utilities.Logger;
import iomango.com.forestdirect.mvp.model.data.SearchModel;
import iomango.com.forestdirect.mvp.model.netwotk.Client;
import iomango.com.forestdirect.mvp.model.netwotk.NetworkRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by clelia_arch on 3/21/17
 */

public class QueryFlight <M>
        implements NetworkRequest {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private Logger logger = new Logger(TAG);
    private M model;
    private OnNetworkResponseListener listener = null;


    public QueryFlight(M model, OnNetworkResponseListener listener) {
        this.model = model;
        this.listener = listener;
    }

    @Override
    public void performNetworkRequest() {
        Call<ResponseBody> call = Client.getRestAPIService()
                .getFlights(((SearchModel)model).getFieldMap());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (listener != null)
                        try {
                            listener.processResponse(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                } else
                    logger.log("Something went wrong! \n" + response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                logger.log(throwable.getCause().toString());
            }
        });
    }
}
