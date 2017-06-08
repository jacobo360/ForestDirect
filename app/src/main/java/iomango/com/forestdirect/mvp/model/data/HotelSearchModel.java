package iomango.com.forestdirect.mvp.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Clelia López on 4/3/17
 */
public class HotelSearchModel {

    @SerializedName("city_code")
    private String cityCode;

    private String checkIn;

    private String checkOut;

    private ArrayList<GuestModel> guests;


    public String getCityCode() {
        return cityCode;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public ArrayList<GuestModel> getGuests() {
        return guests;
    }
}
