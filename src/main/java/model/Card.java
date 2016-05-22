package model;

/**
 * Created by Елена on 18.12.2015.
 */
public class Card {
    private String id;
    private String auto;
    private String dateAdd;
    private String dateDel;
    private String reason;
    private String state;
    private int milage;

    public Card(String id, String auto, String dateAdd, String dateDel, String reason, String state, int milage) {
        this.id = id;
        this.auto = auto;
        this.dateDel = dateDel;
        this.dateAdd = dateAdd;
        this.milage = milage;
        this.reason = reason;
        this.state = state;
    }

    public Card(String auto, String dateAdd, String dateDel, String reason, String state, int milage) {

        this.auto = auto;
        this.dateDel = dateDel;
        this.dateAdd = dateAdd;
        this.milage = milage;
        this.reason = reason;
        this.state = state;
    }

    public Card() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getDateDel() {
        return dateDel;
    }

    public void setDateDel(String dateDel) {
        this.dateDel = dateDel;
    }

    public int getMilage() {
        return milage;
    }

    public void setMilage(int milage) {
        this.milage = milage;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
