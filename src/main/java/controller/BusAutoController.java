package controller;

import dao.BusAutoDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.BusAuto;
import sample.ServiceLocator;

public class BusAutoController extends BaseFxController {
    @FXML
    private TableView<BusAuto> tableBusAuto;
    @FXML
    private TableColumn<BusAuto, String> idColumn;
    @FXML
    private TableColumn<BusAuto, String> startDateColumn;
    @FXML
    private TableColumn<BusAuto, String> endDateColumn;
    @FXML
    private TableColumn<BusAuto, String> reasonColumn;
    @FXML
    private TableColumn<BusAuto, String> conditionColumn;
    @FXML
    private TableColumn<BusAuto, String> autoNumberColumn;
    @FXML
    private TableColumn<BusAuto, String> busFactoryNumberColuumn;

    private BusAutoDao busAutoDao = ServiceLocator.getBusAutoDaoInstance();

    private ObservableList<BusAuto> result = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        autoNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAuto().getNumber()));
        busFactoryNumberColuumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBus().getFactoryNumber()));

        refreshTable();
    }

    private void refreshTable() {
        result.setAll(busAutoDao.findAll());
        tableBusAuto.setItems(result);
    }

    @FXML
    private void updateTable() {
        refreshTable();
    }
}
