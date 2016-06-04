package dao;

import model.Trip;

import java.util.List;

public interface TripDao {
    List<Trip> findAll();
}
