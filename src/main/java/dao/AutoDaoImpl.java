package dao;

import model.Auto;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtils;

import java.util.List;

public class AutoDaoImpl implements AutoDao {

    private SessionFactory sessionFactory;

    public AutoDaoImpl() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Auto> findAll() {
        Session session = sessionFactory.openSession();
        List<Auto> result = session.createQuery("from model.Auto").list();
        session.close();
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Auto> findByModel(String model) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Auto.class);
        criteria.add(Restrictions.like("model", "%" + model + "%"));
        List<Auto> result = criteria.list();
        session.close();
        return result;
    }
}
