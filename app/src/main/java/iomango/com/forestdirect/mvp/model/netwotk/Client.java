package iomango.com.forestdirect.mvp.model.netwotk;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Clelia López on 9/28/16
 */
public class Client {

    /**
     * Attributes
     */
    private static final String host = "https://forestdirect.com/";
    private static RestAPIService restAPIService = null;


    private Client() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(host)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        restAPIService = retrofit.create(RestAPIService.class);
    }

    public static RestAPIService getRestAPIService() {
        if (restAPIService == null)
            new Client();
        return restAPIService;
    }
}
