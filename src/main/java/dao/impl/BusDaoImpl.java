package dao.impl;

import dao.BusDao;
import model.Bus;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtils;

import java.util.List;

public class BusDaoImpl implements BusDao {

    private SessionFactory sessionFactory;

    public BusDaoImpl() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    @SuppressWarnings("unchecked")
    public List<Bus> findAll() {
        Session session = sessionFactory.openSession();
        List<Bus> buses = session.createQuery("from model.Bus").list();
        session.close();
        return buses;
    }

    @SuppressWarnings("unchecked")
    public List<Bus> findByFactoryNumber(String factoryNumber) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Bus.class);
        criteria.add(Restrictions.like("factoryNumber", "%" + factoryNumber + "%"));
        List<Bus> result = criteria.list();
        session.close();
        return result;
    }

    public void delete(Integer busId) {
        Session session = sessionFactory.openSession();
        Bus bus = findById(busId);
        Transaction transaction = session.beginTransaction();
        try {
            transaction.begin();
            session.delete(bus);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        }
        session.flush();
        session.close();
    }

    public Bus findById(Integer busId) {
        Session session = sessionFactory.openSession();
        Bus bus = session.get(Bus.class, busId);
        session.close();
        return bus;
    }

    public Integer deleteQuery(Integer busId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("delete model.Bus where id = :id");
        query.setInteger("id", busId);

        Transaction transaction = session.beginTransaction();
        try {
            transaction.begin();
            int rows = query.executeUpdate();
            transaction.commit();
            return rows;
        } catch (HibernateException e) {
            transaction.rollback();
        }
        session.flush();
        session.close();
        return -1;
    }

    public void save(Bus bus) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            transaction.begin();
            session.save(bus);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        }
        session.close();
    }

    public void update(Bus bus) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("update model.Bus set cost = :cost, dateCreate = :dateCreate, factory = :factory, " +
                " factoryNumber = :factoryNumber, indication = :indication, model = :model, norm = :norm where id = :id");
        query.setInteger("id", bus.getId());
        query.setDouble("cost", bus.getCost());
        query.setString("dateCreate", bus.getDateCreate());
        query.setString("factory", bus.getFactory());
        query.setString("factoryNumber", bus.getFactoryNumber());
        query.setString("indication", bus.getIndication());
        query.setString("model", bus.getModel());
        query.setString("norm", bus.getNorm());

        Transaction transaction = session.beginTransaction();
        try {
            transaction.begin();
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        }
        session.flush();
        session.close();
    }
}
