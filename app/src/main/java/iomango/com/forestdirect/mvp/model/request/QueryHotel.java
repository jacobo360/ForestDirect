package iomango.com.forestdirect.mvp.model.request;

import java.util.List;

import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnNetworkResponseListener;
import iomango.com.forestdirect.mvp.common.utilities.Logger;
import iomango.com.forestdirect.mvp.model.SearchActivityModel;
import iomango.com.forestdirect.mvp.model.data.HotelLocationModel;
import iomango.com.forestdirect.mvp.model.netwotk.Client;
import iomango.com.forestdirect.mvp.model.netwotk.NetworkRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by clelia_arch on 3/8/17
 */

public class QueryHotel<M>
        implements NetworkRequest {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private Logger logger = new Logger(TAG);
    private M model;
    private OnNetworkResponseListener listener = null;


    public QueryHotel(M model, OnNetworkResponseListener listener) {
        this.model = model;
        this.listener = listener;
    }

    @Override
    public void performNetworkRequest() {
        Client.changeBaseHost("https://test.forestdirect.com/hotels/");

        Call<List<HotelLocationModel>> call = Client.getTestAPIService()
            .getHotels(((SearchActivityModel)model).getLocation());

        call.enqueue(new Callback<List<HotelLocationModel>>() {
            @Override
            public void onResponse(Call<List<HotelLocationModel>> call, Response<List<HotelLocationModel>> response) {
                Client.returnToBaseHost();
                if (response.isSuccessful()) {
                    if (listener != null)
                        listener.processResponse(response.body());
                } else
                    logger.log("Something went wrong! \n" + response.message());
            }

            @Override
            public void onFailure(Call<List<HotelLocationModel>> call, Throwable throwable) {
                try {
                    logger.log(throwable.getCause().toString());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}
