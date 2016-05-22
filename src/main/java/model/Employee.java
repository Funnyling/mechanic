package model;

import javax.persistence.*;
import java.util.List;

/**
 * @author ntishkevich
 * @version 22.05.2016
 */
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "full_name", length = 50)
    private String fullName;

    @Column(name = "position", length = 30)
    private String position;

    @OneToMany(mappedBy = "employee")
    private List<Trip> trips;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        return id.equals(employee.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
