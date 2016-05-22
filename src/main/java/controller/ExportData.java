package controller;

public class ExportData {
    private static ExportData myInstance = new ExportData();

    public static ExportData getInstance() {
        return myInstance;
    }

    private ExportData() {

    }

    public Object myObject = null;
    public boolean editFlag = false;
}
