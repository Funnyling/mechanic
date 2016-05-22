package dao;

import model.Accumulator;

import java.util.List;

public interface AccumulatorDao {
    List<Accumulator> findAll();

    void delete(Integer accumulatorId);

    Integer deleteQuery(Integer accumulatorId);

    void save(Accumulator accumulator);

    void update(Accumulator accumulator);

    List<Accumulator> findByFactoryNumber(String factoryNumber);

    Accumulator findById(Integer accumulatorId);
}
