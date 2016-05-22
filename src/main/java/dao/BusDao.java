package dao;

import model.Bus;

import java.util.List;

public interface BusDao {

    List<Bus> findAll();

    void delete(Integer busId);

    Integer deleteQuery(Integer busId);

    void save(Bus bus);

    void update(Bus bus);

    List<Bus> findByFactoryNumber(String factoryNumber);

    Bus findById(Integer busId);
}
