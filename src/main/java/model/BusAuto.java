package model;

import javax.persistence.*;

/**
 * @author ntishkevich
 * @version 22.05.2016
 */
@Entity
@Table(name = "bus_auto")
public class BusAuto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "reason")
    private String reason;

    @Column(name = "condition")
    private String condition;

    @ManyToOne
    @JoinColumn(name = "auto_id", referencedColumnName = "id")
    private Auto auto;

    @ManyToOne
    @JoinColumn(name = "Bus_id", referencedColumnName = "id")
    private Bus bus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusAuto)) return false;

        BusAuto busAuto = (BusAuto) o;

        return id.equals(busAuto.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
