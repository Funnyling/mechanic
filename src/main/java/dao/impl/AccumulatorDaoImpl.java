package dao.impl;

import dao.AccumulatorDao;
import org.hibernate.SessionFactory;
import util.HibernateUtils;

/**
 * Created by Елена on 22.05.2016.
 */
public class AccumulatorDaoImpl implements AccumulatorDao {

    private SessionFactory sessionFactory;

    public AccumulatorDaoImpl() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }
}
