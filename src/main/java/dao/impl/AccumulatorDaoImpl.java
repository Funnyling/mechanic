package dao.impl;

import dao.AccumulatorDao;
import model.Accumulator;
import org.hibernate.*;
import util.HibernateUtils;

import java.util.List;

public class AccumulatorDaoImpl implements AccumulatorDao {

    private SessionFactory sessionFactory;

    public AccumulatorDaoImpl() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Accumulator> findAll() {
        Session session = sessionFactory.openSession();
        List<Accumulator> result = session.createQuery("from model.Accumulator").list();
        session.close();
        return result;
    }

    @Override
    public void delete(Integer accumulatorId) {
        Session session = sessionFactory.openSession();
        Accumulator accumulator = findById(accumulatorId);
        Transaction transaction = session.beginTransaction();
        try {
            transaction.begin();
            session.delete(accumulator);
            transaction.commit();
            session.flush();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public Integer deleteQuery(Integer accumulatorId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("delete model.Accumulator where id = :id");
        query.setInteger("id", accumulatorId);
        int rows = -1;
        Transaction transaction = session.beginTransaction();
        try {
            transaction.begin();
            rows = query.executeUpdate();
            transaction.commit();
            session.flush();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return rows;
    }

    @Override
    public void save(Accumulator accumulator) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            transaction.begin();
            session.save(accumulator);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Accumulator accumulator) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("update model.Accumulator set cost = :cost, createDate = :createDate, factory = :factory, " +
                " factoryNumber = :factoryNumber, number = :number, type = :type where id = :id");
        query.setInteger("id", accumulator.getId());
        query.setDouble("cost", accumulator.getCost());
        query.setString("createDate", accumulator.getCreateDate());
        query.setString("factory", accumulator.getFactory());
        query.setString("factoryNumber", accumulator.getFactoryNumber());
        query.setString("number", accumulator.getNumber());
        query.setString("type", accumulator.getType());

        Transaction transaction = session.beginTransaction();
        try {
            transaction.begin();
            query.executeUpdate();
            transaction.commit();
            session.flush();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Accumulator> findByFactoryNumber(String factoryNumber) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from model.Accumulator where factoryNumber like concat('%', :factoryNumber, '%')");
        query.setString("factoryNumber", factoryNumber);
        List<Accumulator> result = query.list();
        session.close();
        return result;
    }

    @Override
    public Accumulator findById(Integer accumulatorId) {
        Session session = sessionFactory.openSession();
        Accumulator accumulator = session.get(Accumulator.class, accumulatorId);
        session.close();
        return accumulator;
    }
}
