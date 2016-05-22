package dao.impl;

import dao.BusDao;
import org.hibernate.Session;
import sample.HibernateUtils;

public class BusDaoImpl implements BusDao {

    private Session session;

    public BusDaoImpl() {
        this.session = HibernateUtils.getSession();
    }

    public String echo() {
        return "text";
    }
}
