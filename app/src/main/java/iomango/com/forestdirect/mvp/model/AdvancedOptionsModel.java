package iomango.com.forestdirect.mvp.model;

/**
 * Created by Clelia López on 3/14/17
 */

public class AdvancedOptionsModel {

    /**
     * Attributes
     */
    private int adult;
    private int senior;
    private int children;
    private int infant;
    private String cabin;


    public AdvancedOptionsModel() { }

    public AdvancedOptionsModel(int adult, int senior, int children, int infant, String cabin) {
        this.adult = adult;
        this.senior = senior;
        this.children = children;
        this.infant = infant;
        this.cabin = cabin;
    }

    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public int getSenior() {
        return senior;
    }

    public void setSenior(int senior) {
        this.senior = senior;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public int getInfant() {
        return infant;
    }

    public void setInfant(int infant) {
        this.infant = infant;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }
}
