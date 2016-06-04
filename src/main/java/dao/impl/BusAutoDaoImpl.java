package dao.impl;

import dao.BusAutoDao;
import model.BusAuto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtils;

import java.util.List;

public class BusAutoDaoImpl implements BusAutoDao {
    private SessionFactory sessionFactory;

    public BusAutoDaoImpl() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BusAuto> findAll() {
        Session session = sessionFactory.openSession();
        List<BusAuto> result = session.createQuery("from model.BusAuto as busAuto left join fetch busAuto.auto left join fetch busAuto.bus").list();
        session.close();
        return result;
    }
}
