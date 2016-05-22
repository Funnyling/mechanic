package sample;

import dao.AccumulatorDao;
import dao.BusDao;
import dao.impl.AccumulatorDaoImpl;
import dao.impl.BusDaoImpl;
import model.Accumulator;

public class ServiceLocator {

    private final static ServiceLocatorHolder instance = new ServiceLocatorHolder();

    public static BusDao getBusDaoInstance() {
        return instance.busDao;
    }
    public static AccumulatorDao getAccumulatorDaoInstance() {
        return instance.accumulatorDao;
    }

    private static class ServiceLocatorHolder {
        static BusDao busDao;
        static AccumulatorDao accumulatorDao;

        private ServiceLocatorHolder() {
            busDao = new BusDaoImpl();
            accumulatorDao = new AccumulatorDaoImpl();
        }
    }
}
