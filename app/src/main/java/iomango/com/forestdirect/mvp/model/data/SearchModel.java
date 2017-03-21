package iomango.com.forestdirect.mvp.model.data;

import com.google.gson.annotations.SerializedName;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by clelia_arch on 3/20/17
 */

public class SearchModel {

    private String type;

    private String from;

    @SerializedName("include_from")
    private String includeFrom;

    private String to;

    @SerializedName("include_to")
    private String includeTo;

    @SerializedName("dep_date")
    private String departureDate;

    @SerializedName("arr_date")
    private String arriveDate;

    private String cabin;

    private String adult;

    private String senior;

    private String child;

    @SerializedName("lap_infant")
    private String lapInfant;

    @SerializedName("dep_time")
    private String departureTime;

    @SerializedName("arr_time")
    private String arriveTime;

    private String airline;


    public SearchModel() {
        includeFrom = "0";
        includeTo = "0";
        arriveDate = "";
        departureTime = "";
        arriveTime = "";
        airline = "";
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setIncludeFrom(String includeFrom) {
        this.includeFrom = includeFrom;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setIncludeTo(String includeTo) {
        this.includeTo = includeTo;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate;
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

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public HashMap<String,String> getFieldMap() {
        HashMap<String,String> data = new HashMap<>();
        data.put("type", type);
        data.put("from", from);
        data.put("include_from", includeFrom);
        data.put("to", to);
        data.put("include_to", includeTo);
        data.put("dep_date", departureDate);
        data.put("cabin", cabin);
        data.put("adult", adult);
        data.put("senior", senior);
        data.put("child", child);
        data.put("lap_infant", lapInfant);
        data.put("dep_time", departureTime);
        data.put("airline", airline);
        return data;
    }

    public String getEncodedParams() {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("type", type));
        nameValuePairs.add(new BasicNameValuePair("from", from));
        nameValuePairs.add(new BasicNameValuePair("include_from", includeFrom));
        nameValuePairs.add(new BasicNameValuePair("to", to));
        nameValuePairs.add(new BasicNameValuePair("include_to", includeTo));
        nameValuePairs.add(new BasicNameValuePair("dep_date", departureDate));
        nameValuePairs.add(new BasicNameValuePair("cabin", cabin));
        nameValuePairs.add(new BasicNameValuePair("adult", adult));
        nameValuePairs.add(new BasicNameValuePair("senior", senior));
        nameValuePairs.add(new BasicNameValuePair("child", child));
        nameValuePairs.add(new BasicNameValuePair("lap_infant", lapInfant));
        nameValuePairs.add(new BasicNameValuePair("dep_time", departureTime));
        nameValuePairs.add(new BasicNameValuePair("airline", airline));
        return URLEncodedUtils.format(nameValuePairs, "UTF-8").replaceAll("\\+", "%20");
    }
}
