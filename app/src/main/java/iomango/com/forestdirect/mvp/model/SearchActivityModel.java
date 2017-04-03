package iomango.com.forestdirect.mvp.model;

/**
 * Created by clelia_arch on 3/8/17
 */

public class SearchActivityModel {

    /**
     * Attributes
     */
    private String location;
    private boolean lookingHotels = false;


    public SearchActivityModel(String location) {
        this.location = location;
    }

    public SearchActivityModel(String location, boolean lookingHotels) {
        this.location = location;
        this.lookingHotels = lookingHotels;
    }

    public String getLocation() {
        return location;
    }

    public boolean isLookingHotels() {
        return lookingHotels;
    }
}
