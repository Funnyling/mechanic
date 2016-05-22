package sample;

import dao.AccumulatorDao;
import dao.BusDao;
import dao.impl.AccumulatorDaoImpl;
import dao.impl.BusDaoImpl;

public class ServiceLocator {

    private final static ServiceLocatorHolder instance = new ServiceLocatorHolder();

    public static BusDao getBusDaoInstance() {
        return instance.busDao;
    }
    public static AccumulatorDao getAccumulatorDaoInstance() {
        return instance.accumulatorDao;
    }

    private static class ServiceLocatorHolder {
        BusDao busDao;
        AccumulatorDao accumulatorDao;

        private ServiceLocatorHolder() {
            busDao = new BusDaoImpl();
            accumulatorDao = new AccumulatorDaoImpl();
        }
    }
}
