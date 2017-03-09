package iomango.com.forestdirect.mvp.model;

/**
 * Created by clelia_arch on 3/8/17
 */

public class MainActivityModel {

    /**
     * Attributes
     */
    private String location;


    public MainActivityModel(String location) {
        this.location = location.trim();
    }

    public String getLocation() {
        return location;
    }
}
