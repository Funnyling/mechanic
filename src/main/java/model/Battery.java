package model;

/**
 * Created by Елена on 17.12.2015.
 */
public class Battery {
    private String id;
    private String cost;
    private String dateCreate;
    private String factory;
    private String factoryNumber;
    private String type;
    private String garageNumber;


    public Battery(String id, String cost,String dateCreate, String factory, String factoryNumber, String type, String garageNumber) {
        this.id = id;
        this.cost = cost;
        this.dateCreate = dateCreate;
        this.factory = factory;
        this.factoryNumber=factoryNumber;
        this.type=type;
        this.garageNumber = garageNumber;
    }
    public Battery(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getFactoryNumber() {
        return factoryNumber;
    }

    public void setFactoryNumber(String factoryNumber) {
        this.factoryNumber = factoryNumber;
    }

    public String getGarageNumber() {
        return garageNumber;
    }

    public void setGarageNumber(String garageNumber) {
        this.garageNumber = garageNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
