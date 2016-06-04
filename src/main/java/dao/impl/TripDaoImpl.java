package dao.impl;

import dao.TripDao;
import model.Trip;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtils;

import java.util.List;

public class TripDaoImpl implements TripDao {
    private SessionFactory sessionFactory;

    public TripDaoImpl() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Trip> findAll() {
        Session session = sessionFactory.openSession();
        List<Trip> result = session.createQuery("from model.Trip as trip left join fetch trip.auto left join fetch trip.employee").list();
        session.close();
        return result;
    }
}
