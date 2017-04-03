package iomango.com.forestdirect.mvp.model.request;

import java.util.List;

import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnNetworkResponseListener;
import iomango.com.forestdirect.mvp.common.utilities.Logger;
import iomango.com.forestdirect.mvp.model.SearchActivityModel;
import iomango.com.forestdirect.mvp.model.data.AirportLocationModel;
import iomango.com.forestdirect.mvp.model.netwotk.Client;
import iomango.com.forestdirect.mvp.model.netwotk.NetworkRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by clelia_arch on 3/8/17
 */

public class QueryAirport<M>
        implements NetworkRequest {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private Logger logger = new Logger(TAG);
    private M model;
    private OnNetworkResponseListener listener = null;


    public QueryAirport(M model, OnNetworkResponseListener listener) {
        this.model = model;
        this.listener = listener;
    }

    @Override
    public void performNetworkRequest() {
        Call<List<AirportLocationModel>> call = Client.getRestAPIService()
                .getAirports(((SearchActivityModel)model).getLocation());

        call.enqueue(new Callback<List<AirportLocationModel>>() {
            @Override
            public void onResponse(Call<List<AirportLocationModel>> call, Response<List<AirportLocationModel>> response) {
                if (response.isSuccessful()) {
                    if (listener != null)
                        listener.processResponse(response.body());
                } else
                    logger.log("Something went wrong! \n" + response.message());
            }

            @Override
            public void onFailure(Call<List<AirportLocationModel>> call, Throwable throwable) {
                try {
                    logger.log(throwable.getCause().toString());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}
