package iomango.com.forestdirect.mvp.model.data;

import java.util.ArrayList;

/**
 * Created by Clelia López on 5/31/17
 */

public class RoomModel {

    /**
     * Attributes
     */
    private int adults;
    private ArrayList<Integer> children;


    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public ArrayList<Integer> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Integer> children) {
        this.children = children;
    }
}
