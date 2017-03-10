package iomango.com.forestdirect.mvp.model.data;

/**
 * Created by clelia_arch on 3/8/17
 */

public class LocationModel {

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
}
