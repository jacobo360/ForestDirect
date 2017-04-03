package iomango.com.forestdirect.mvp.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by clelia_arch on 4/3/17
 */

public class HotelLocationModel implements Parcelable {

    @SerializedName("city_code")
    private String code;

    @SerializedName("city_name")
    private String city;

    @SerializedName("country_name")
    private String country;

    @SerializedName("municipality")
    private String municipality;


    public String getCode() {
        return code;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getMunicipality() {
        return municipality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeString(this.municipality);
    }

    public HotelLocationModel() {
    }

    protected HotelLocationModel(Parcel in) {
        this.code = in.readString();
        this.city = in.readString();
        this.country = in.readString();
        this.municipality = in.readString();
    }

    public static final Parcelable.Creator<HotelLocationModel> CREATOR = new Parcelable.Creator<HotelLocationModel>() {
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
