package dao;

import model.Bus;

import java.util.List;

public interface BusDao {

    List<Bus> findAll();

    void delete(Integer id);

    Integer deleteQuery(Integer id);

    void save(Bus bus);

    void update(Bus bus);
}
