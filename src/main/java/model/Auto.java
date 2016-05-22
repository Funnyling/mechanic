package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "auto")
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "number", length = 10)
    private String number;

    @Column(name = "gos_number", length = 10)
    private String gosNumber;

    @Column(name = "model", length = 25)
    private String model;

    @OneToMany(mappedBy = "auto", fetch = FetchType.EAGER)
    private List<Trip> trips;

    @OneToMany(mappedBy = "auto", fetch = FetchType.EAGER)
    private List<BusAuto> busAutoList;

    @OneToMany(mappedBy = "auto", fetch = FetchType.EAGER)
    private List<AccumulatorAuto> accumulatorAutoList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGosNumber() {
        return gosNumber;
    }

    public void setGosNumber(String gosNumber) {
        this.gosNumber = gosNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<BusAuto> getBusAutoList() {
        return busAutoList;
    }

    public void setBusAutoList(List<BusAuto> busAutoList) {
        this.busAutoList = busAutoList;
    }

    public List<AccumulatorAuto> getAccumulatorAutoList() {
        return accumulatorAutoList;
    }

    public void setAccumulatorAutoList(List<AccumulatorAuto> accumulatorAutoList) {
        this.accumulatorAutoList = accumulatorAutoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auto)) return false;

        Auto auto = (Auto) o;

        return id.equals(auto.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
