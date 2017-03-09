package iomango.com.forestdirect.mvp.model.netwotk;

import java.util.List;

import iomango.com.forestdirect.mvp.model.data.LocationModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Clelia López on 9/28/16
 */
public interface RestAPIService {

    @GET("/getCities")
    Call<List<LocationModel>> getLocations(@Query("q") String name);
}
