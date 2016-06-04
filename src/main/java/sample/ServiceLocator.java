package sample;

import dao.*;
import dao.impl.*;

public class ServiceLocator {

    private final static ServiceLocatorHolder instance = new ServiceLocatorHolder();

    public static BusDao getBusDaoInstance() {
        return instance.busDao;
    }
    public static AccumulatorDao getAccumulatorDaoInstance() {
        return instance.accumulatorDao;
    }

    public static AutoDao getAutoDaoInstance() {
        return instance.autoDao;
    }

    public static TripDao getTripDaoInstance() {
        return instance.tripDao;
    }

    public static EmployeeDao getEmployeeDaoInstance() {
        return instance.employeeDao;
    }

    public static BusAutoDao getBusAutoDaoInstance() {
        return instance.busAutoDao;
    }

    public static AccumulatorAutoDao getAccumulatorAutoDaoInstance() {
        return instance.accumulatorAutoDao;
    }

    private static class ServiceLocatorHolder {
        BusDao busDao;
        AccumulatorDao accumulatorDao;
        AutoDao autoDao;
        TripDao tripDao;
        EmployeeDao employeeDao;
        BusAutoDao busAutoDao;
        AccumulatorAutoDao accumulatorAutoDao;

        private ServiceLocatorHolder() {
            busDao = new BusDaoImpl();
            accumulatorDao = new AccumulatorDaoImpl();
            autoDao = new AutoDaoImpl();
            tripDao = new TripDaoImpl();
            employeeDao = new EmployeeDaoImpl();
            busAutoDao = new BusAutoDaoImpl();
            accumulatorAutoDao = new AccumulatorAutoDaoImpl();
        }
    }
}
