package iomango.com.forestdirect.mvp.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clelia_arch on 3/23/17
 */

public class MultiCityModel {

    private String type;

    @SerializedName("from_multi")
    private List<String> from;

    @SerializedName("include_from_multi")
    private List<String> includeFrom;

    @SerializedName("to_multi")
    private List<String> to;

    @SerializedName("include_to_multi")
    private List<String> includeTo;

    private List<String> dates;

    private String cabin;

    private String adult;

    private String senior;

    private String child;

    @SerializedName("lap_infant")
    private String lapInfant;


    public MultiCityModel() {
        type = "Multi";
        from = new ArrayList<>();
        includeFrom = new ArrayList<>();
        to = new ArrayList<>();
        includeTo = new ArrayList<>();
        dates = new ArrayList<>();
    }

    public void addFrom(String value) {
        from.add(value);
    }

    public void addIncludeFrom(String value) {
        includeFrom.add(value);
    }

    public void addTo(String value) {
        to.add(value);
    }

    public void addIncludeTo(String value) {
        includeTo.add(value);
    }

    public void addDate(String value) {
        dates.add(value);
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public void setSenior(String senior) {
        this.senior = senior;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public void setLapInfant(String lapInfant) {
        this.lapInfant = lapInfant;
    }

    public boolean isValid() {
        if (from.size() < 2)
            return false;
        if (to.size() < 2)
            return false;
        if (dates.size() < 2)
            return false;
        if (adult.isEmpty() || senior.isEmpty())
            return false;

        return true;
    }

    public int getTotalPassengers() {
        int total = 0;
        if (!adult.equals(""))
            total = total + Integer.parseInt(adult);
        if (!senior.equals(""))
            total = total + Integer.parseInt(senior);
        if (!child.equals(""))
            total = total + Integer.parseInt(child);
        if (!lapInfant.equals(""))
            total = total + Integer.parseInt(lapInfant);
        return total;
    }
}
