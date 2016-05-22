package sample;

import dao.BusDao;
import dao.impl.BusDaoImpl;

/**
 * @author ntishkevich
 * @version 22.05.2016
 */
public class DaoUtils {

    private final static BusDao busDao = new BusDaoImpl();

    private DaoUtils() {

    }

    public static BusDao getBusDaoInstance() {
        return busDao;
    }
}
