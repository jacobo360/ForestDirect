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
    private static Retrofit retrofit;
    private static boolean hasHostChanged = false;


    private Client(Retrofit retrofit) {
        restAPIService = retrofit.create(RestAPIService.class);
    }

    public static void changeBaseHost(String newHost) {
        hasHostChanged = true;
        retrofit = new Retrofit.Builder()
            .baseUrl(newHost)
            .client(getOkHttpBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    public static void returnToBaseHost() {
        hasHostChanged = false;
    }

    private static OkHttpClient.Builder getOkHttpBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(getInterceptor());
        return builder;
    }

    private static HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    private static Retrofit getDefaultRetrofitBuilder () {
        return new Retrofit.Builder()
            .baseUrl(host)
            .client(getOkHttpBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    public static RestAPIService getRestAPIService() {
        if (restAPIService == null)
            if (hasHostChanged)
                new Client(retrofit);
            else
                new Client(getDefaultRetrofitBuilder());
        return restAPIService;
    }
}
