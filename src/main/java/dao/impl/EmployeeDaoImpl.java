package dao.impl;

import dao.EmployeeDao;
import model.Employee;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtils;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    private SessionFactory sessionFactory;

    public EmployeeDaoImpl() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> findAll() {
        Session session = sessionFactory.openSession();
        List<Employee> result = session.createQuery("from model.Employee").list();
        session.close();
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> findByFullName(String fullName) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.add(Restrictions.like("fullName", "%" + fullName + "%"));
        List<Employee> result = criteria.list();
        session.close();
        return result;
    }
}
