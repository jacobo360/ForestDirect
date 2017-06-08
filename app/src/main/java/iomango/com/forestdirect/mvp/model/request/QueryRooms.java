package iomango.com.forestdirect.mvp.model.request;

import java.util.ArrayList;

import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnNetworkResponseListener;
import iomango.com.forestdirect.mvp.common.utilities.Logger;
import iomango.com.forestdirect.mvp.model.data.RoomModel;
import iomango.com.forestdirect.mvp.model.data.HotelSearchModel;
import iomango.com.forestdirect.mvp.model.netwotk.Client;
import iomango.com.forestdirect.mvp.model.netwotk.NetworkRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Clelia López on 6/7/17
 */
public class QueryRooms<M>
        implements NetworkRequest {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private Logger logger = new Logger(TAG);
    private M model;
    private OnNetworkResponseListener listener = null;


    public QueryRooms(M model, OnNetworkResponseListener listener) {
        this.model = model;
        this.listener = listener;
    }

    @Override
    public void performNetworkRequest() {
        HotelSearchModel hotelSearchModel = (HotelSearchModel) model;

        // Process rooms distribution
        String rooms = "";
        ArrayList<RoomModel> guests = ((HotelSearchModel) model).getGuests();
        ArrayList<Integer> children;
        for (RoomModel model : guests) {
            rooms = rooms + model.getAdults() + "!";
            children = model.getChildren();
            for (int age : children)
                rooms = rooms + age + "-";
        }

        if (guests.size() > 1) {
            int begin = rooms.lastIndexOf("!");
            rooms = rooms.substring(0, begin - 1);
            rooms = rooms + rooms.substring(begin + 1, rooms.length() - 1);
        } else if (guests.size() == 1) {
            int position = rooms.lastIndexOf("-");
            rooms = rooms.substring(0, position - 1);
        }

        Call<ResponseBody> call = Client.getTestAPIService().getRHotelRooms(
            hotelSearchModel.getCityCode(),
            hotelSearchModel.getCheckIn(),
            hotelSearchModel.getCheckOut(),
            rooms);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Client.returnToBaseHost();
                if (response.isSuccessful()) {
                    if (listener != null)
                        listener.processResponse(response.body());
                } else
                    logger.log("Something went wrong! \n" + response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                try {
                    logger.log(throwable.getCause().toString());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}
