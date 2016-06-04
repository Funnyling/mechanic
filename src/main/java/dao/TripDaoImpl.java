package dao;

import model.Trip;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtils;

import javax.persistence.Table;
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
        List<Trip> result = session.createQuery("from model.Trip").list();
        session.close();
        return result;
    }
}
