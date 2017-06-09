package iomango.com.forestdirect.mvp.model.data;

/**
 * Created by Clelia López on 3/15/17
 */

public class AirlineModel {

    /**
     * Attributes
     */
    private int id;
    private String AirLineCode;
    private String AirLineName;
    private String ProviderType;
    private boolean Active;


    public int getId() {
        return id;
    }

    public String getAirLineCode() {
        return AirLineCode;
    }

    public String getAirLineName() {
        return AirLineName;
    }

    public String getProviderType() {
        return ProviderType;
    }

    public boolean isActive() {
        return Active;
    }
}
