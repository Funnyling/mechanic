package model;

import java.util.Date;

public class Bus {
    private String id;
    private String cost;
    private String dateCreate;
    private String factory;
    private String factoryNumber;
    private String indication;
    private String model;
    private String norm;


    public Bus(String id, String cost, String dateCreate, String factory, String factoryNumber, String indication, String model, String norm) {
        this.id = id;
        this.cost = cost;
        this.dateCreate = dateCreate;
        this.factory = factory;
        this.factoryNumber = factoryNumber;
        this.indication = indication;
        this.model = model;
        this.norm = norm;
    }

    public Bus() {

    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }
}
