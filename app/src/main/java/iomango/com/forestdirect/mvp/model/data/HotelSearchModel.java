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

    private ArrayList<RoomModel> guests;


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public ArrayList<RoomModel> getGuests() {
        return guests;
    }

    public void setGuests(ArrayList<RoomModel> guests) {
        this.guests = guests;
    }

    public boolean isValid() {
        if (cityCode.isEmpty())
            return false;
        if (checkIn.isEmpty())
            return false;
        if (checkOut.isEmpty())
            return false;
        if (guests == null)
            return false;

        return true;
    }

    public int getTotalPassengers() {
        /*return guestModel.getAdults() + guestModel.getChildren().size();

        int total = 0;
        for (GuestModel model : guests) {
            total
        }*/

        return 0;
    }
}
