package sample;

import dao.*;
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

    public static AutoDao getAutoDaoInstance() {
        return instance.autoDao;
    }

    public static TripDao getTripDaoInstance() {
        return instance.tripDao;
    }

    private static class ServiceLocatorHolder {
        BusDao busDao;
        AccumulatorDao accumulatorDao;
        AutoDao autoDao;
        TripDao tripDao;

        private ServiceLocatorHolder() {
            busDao = new BusDaoImpl();
            accumulatorDao = new AccumulatorDaoImpl();
            autoDao = new AutoDaoImpl();
            tripDao = new TripDaoImpl();
        }
    }
}
