package iomango.com.forestdirect.mvp.model.request;

import java.util.List;

import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnNetworkResponseListener;
import iomango.com.forestdirect.mvp.common.utilities.Logger;
import iomango.com.forestdirect.mvp.model.SearchActivityModel;
import iomango.com.forestdirect.mvp.model.data.LocationModel;
import iomango.com.forestdirect.mvp.model.netwotk.Client;
import iomango.com.forestdirect.mvp.model.netwotk.NetworkRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by clelia_arch on 3/8/17
 */

public class QueryLocation<M>
        implements NetworkRequest {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private Logger logger = new Logger(TAG);
    private M model;
    private OnNetworkResponseListener listener = null;


    public QueryLocation(M model, OnNetworkResponseListener listener) {
        this.model = model;
        this.listener = listener;
    }

    @Override
    public void performNetworkRequest() {
        Call<List<LocationModel>> call = Client.getRestAPIService()
                .getLocations(((SearchActivityModel)model).getLocation());

        call.enqueue(new Callback<List<LocationModel>>() {
            @Override
            public void onResponse(Call<List<LocationModel>> call, Response<List<LocationModel>> response) {
                if (response.isSuccessful()) {
                    if (listener != null)
                        listener.processResponse(response.body());
                } else
                    logger.log("Something went wrong! \n" + response.message());
            }

            @Override
            public void onFailure(Call<List<LocationModel>> call, Throwable throwable) {
                if (throwable != null)
                    logger.log(throwable.getCause().toString());
            }
        });
    }
}
