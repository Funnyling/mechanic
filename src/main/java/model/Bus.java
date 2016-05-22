package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bus")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "date_create")
    private String dateCreate;

    @Column(name = "factory", length = 50)
    private String factory;

    @Column(name = "factoryNumber", length = 10)
    private String factoryNumber;

    @Column(name = "indication")
    private String indication;

    @Column(name = "model", length = 30)
    private String model;

    @Column(name = "norm", length = 20)
    private String norm;

    @OneToMany(mappedBy = "bus")
    private List<BusAuto> busAutoList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
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

    public List<BusAuto> getBusAutoList() {
        return busAutoList;
    }

    public void setBusAutoList(List<BusAuto> busAutoList) {
        this.busAutoList = busAutoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bus)) return false;

        Bus bus = (Bus) o;

        return id.equals(bus.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
