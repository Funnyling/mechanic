package dao.impl;

import dao.AccumulatorDao;
import model.Accumulator;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtils;

import java.util.List;

/**
 * Created by ����� on 22.05.2016.
 */
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
        } catch (Exception e) {
            transaction.rollback();
        }
        session.flush();
        session.close();
    }

    @Override
    public Integer deleteQuery(Integer accumulatorId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("delete model.Accumulator where id = :id");
        query.setInteger("id", accumulatorId);

        Transaction transaction = session.beginTransaction();
        try {
            transaction.begin();
            int rows = query.executeUpdate();
            transaction.commit();
            return rows;
        } catch (Exception e) {
            transaction.rollback();
        }
        session.flush();
        session.close();
        return -1;
    }

    @Override
    public void save(Accumulator accumulator) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            transaction.begin();
            session.save(accumulator);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        session.close();
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
        } catch (Exception e) {
            transaction.rollback();
        }
        session.flush();
        session.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Accumulator> findByFactoryNumber(String factoryNumber) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Accumulator.class);
        criteria.add(Restrictions.like("factoryNumber", "%" + factoryNumber + "%"));
        List<Accumulator> result = criteria.list();
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
