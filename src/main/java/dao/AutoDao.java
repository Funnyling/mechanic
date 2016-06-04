package dao;

import model.Auto;

import java.util.List;

public interface AutoDao {

    List<Auto> findAll();

    List<Auto> findByModel(String model);
}
