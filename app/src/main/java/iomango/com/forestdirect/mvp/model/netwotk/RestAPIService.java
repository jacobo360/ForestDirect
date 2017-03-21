package iomango.com.forestdirect.mvp.model.netwotk;

import java.util.HashMap;
import java.util.List;

import iomango.com.forestdirect.mvp.model.data.LocationModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Clelia López on 9/28/16
 */
public interface RestAPIService {

    @GET("flights/getCities")
    Call<List<LocationModel>> getLocations(@Query("q") String name);

    @FormUrlEncoded
    @POST("flights/search")
    Call<ResponseBody> getFlights(@FieldMap HashMap<String,String> data);
}
