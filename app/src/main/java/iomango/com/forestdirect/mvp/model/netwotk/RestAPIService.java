package iomango.com.forestdirect.mvp.model.netwotk;

import java.util.HashMap;
import java.util.List;

import iomango.com.forestdirect.mvp.model.data.AirportLocationModel;
import iomango.com.forestdirect.mvp.model.data.HotelLocationModel;
import iomango.com.forestdirect.mvp.model.data.MultiCitySearchModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Clelia López on 9/28/16
 */
public interface RestAPIService {

    @GET("flights/getCities")
    Call<List<AirportLocationModel>> getAirports(@Query("q") String name);

    @GET("locations/{name}")
    Call<List<HotelLocationModel>> getHotels(@Path("name") String name);

    @FormUrlEncoded
    @POST("flights/search")
    Call<ResponseBody> getFlights(@FieldMap HashMap<String,String> data);

    @POST("flights/search")
    Call<ResponseBody> getFlights(@Body MultiCitySearchModel model);

    @POST("hotels/search/{city_code}/{check_in}/{check_out}/{rooms}")
    Call<ResponseBody> getRHotelRooms(
        @Path("city_code") String cityCode,
        @Path("check_in") String checkIn,
        @Path("check_out") String checkOut,
        @Path("rooms") String rooms
    );
}
