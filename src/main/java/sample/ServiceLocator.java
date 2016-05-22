package sample;

import dao.BusDao;
import dao.impl.BusDaoImpl;

public class ServiceLocator {

    private final static ServiceLocatorHolder instance = new ServiceLocatorHolder();

    private ServiceLocator() {
        instance.busDao = new BusDaoImpl();
    }

    public static BusDao getBusDaoInstance() {
        return instance.busDao;
    }

    private static class ServiceLocatorHolder {
        static BusDao busDao;
    }
}
