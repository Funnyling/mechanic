package util;

import model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static final String PASS = "12345";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DIALECT = "org.hibernate.dialect.PostgreSQL94Dialect";

    private static SessionFactory sessionFactory;

    public static void setUpSession() {
        try {
            Configuration cfg = new Configuration()
                    .addAnnotatedClass(Bus.class)
                    .addAnnotatedClass(Auto.class)
                    .addAnnotatedClass(Trip.class)
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Accumulator.class)
                    .addAnnotatedClass(AccumulatorAuto.class)
                    .addAnnotatedClass(BusAuto.class)
                    .setProperty("hibernate.dialect", DIALECT)
                    .setProperty("hibernate.connection.driver_class", DRIVER)
                    .setProperty("hibernate.connection.url", URL)
                    .setProperty("hibernate.connection.username", USERNAME)
                    .setProperty("hibernate.connection.password", PASS)
                    .setProperty("hibernate.show_sql", "true")
                    .setProperty("hibernate.format_sql", "true");

            sessionFactory = cfg.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}
