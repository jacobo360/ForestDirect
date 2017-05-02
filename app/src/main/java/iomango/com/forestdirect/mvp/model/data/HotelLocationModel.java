package iomango.com.forestdirect.mvp.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by clelia_arch on 4/3/17
 */

public class HotelLocationModel implements Parcelable {

    private String title;

    @SerializedName("city_code")
    private String cityCode;

    @SerializedName("hotel_code")
    private String hotelCode;

    @SerializedName("is_child")
    private int isChild;

    @SerializedName("has_child")
    private int hasChild;


    public String getTitle() {
        return title;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public int isChild() {
        return isChild;
    }

    public int isHasChild() {
        return hasChild;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.cityCode);
        dest.writeString(this.hotelCode);
        dest.writeInt(this.isChild);
        dest.writeInt(this.hasChild);
    }

    public HotelLocationModel() {
    }

    protected HotelLocationModel(Parcel in) {
        this.title = in.readString();
        this.cityCode = in.readString();
        this.hotelCode = in.readString();
        this.isChild = in.readInt();
        this.hasChild = in.readInt();
    }

    public static final Creator<HotelLocationModel> CREATOR = new Creator<HotelLocationModel>() {
        @Override
        public HotelLocationModel createFromParcel(Parcel source) {
            return new HotelLocationModel(source);
        }

        @Override
        public HotelLocationModel[] newArray(int size) {
            return new HotelLocationModel[size];
        }
    };
}
