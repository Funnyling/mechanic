package dao.impl;

import dao.AccumulatorAutoDao;
import model.AccumulatorAuto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtils;

import java.util.List;

public class AccumulatorAutoDaoImpl implements AccumulatorAutoDao {

    private SessionFactory sessionFactory;

    public AccumulatorAutoDaoImpl() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AccumulatorAuto> findAll() {
        Session session = sessionFactory.openSession();
        List<AccumulatorAuto> result = session.createQuery("from model.AccumulatorAuto as accAuto left join fetch accAuto.auto left join fetch accAuto.accumulator").list();
        session.close();
        return result;
    }
}
