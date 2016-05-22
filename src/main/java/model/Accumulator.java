package model;

import javax.persistence.*;
import java.util.List;

/**
 * @author ntishkevich
 * @version 22.05.2016
 */
@Entity
@Table(name = "accumulator")
public class Accumulator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "factory", length = 50)
    private String factory;

    @Column(name = "number", length = 10)
    private String number;

    @Column(name = "type", length = 25)
    private String type;

    @Column(name = "factoryNumber", length = 10)
    private String factoryNumber;

    @Column(name = "cost")
    private Double cost;

    @OneToMany(mappedBy = "accumulator", fetch = FetchType.EAGER)
    private List<AccumulatorAuto> autoList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFactoryNumber() {
        return factoryNumber;
    }

    public void setFactoryNumber(String factoryNumber) {
        this.factoryNumber = factoryNumber;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public List<AccumulatorAuto> getAutoList() {
        return autoList;
    }

    public void setAutoList(List<AccumulatorAuto> autoList) {
        this.autoList = autoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accumulator)) return false;

        Accumulator that = (Accumulator) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}