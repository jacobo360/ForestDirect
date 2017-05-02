package iomango.com.forestdirect.mvp.model.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Clelia López on 3/8/17
 */

public class AirportLocationModel
        implements Parcelable {

    /**
     * Attributes
     */
    private String city;
    private String code;
    private String country;
    private String municipality;
    private boolean isChild;
    private boolean hasChild;


    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getCountry() {
        return country;
    }

    public String getMunicipality() {
        return municipality;
    }

    public boolean getIsChild() {
        return isChild;
    }

    public boolean getHasChild() {
        return hasChild;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.code);
        dest.writeString(this.country);
        dest.writeString(this.municipality);
        dest.writeByte(this.isChild ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasChild ? (byte) 1 : (byte) 0);
    }

    public AirportLocationModel() {
    }

    public AirportLocationModel(Parcel in) {
        this.city = in.readString();
        this.code = in.readString();
        this.country = in.readString();
        this.municipality = in.readString();
        this.isChild = in.readByte() != 0;
        this.hasChild = in.readByte() != 0;
    }

    public static final Parcelable.Creator<AirportLocationModel> CREATOR = new Parcelable.Creator<AirportLocationModel>() {
        @Override
        public AirportLocationModel createFromParcel(Parcel source) {
            return new AirportLocationModel(source);
        }

        @Override
        public AirportLocationModel[] newArray(int size) {
            return new AirportLocationModel[size];
        }
    };
}
