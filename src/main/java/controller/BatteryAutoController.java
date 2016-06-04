package controller;

import dao.AccumulatorAutoDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.AccumulatorAuto;
import sample.ServiceLocator;

public class BatteryAutoController extends BaseFxController{
    @FXML
    private TableView<AccumulatorAuto> tableAccumulatorAuto;
    @FXML
    private TableColumn<AccumulatorAuto, String> idColumn;
    @FXML
    private TableColumn<AccumulatorAuto, String> startDateColumn;
    @FXML
    private TableColumn<AccumulatorAuto, String> endDateColumn;
    @FXML
    private TableColumn<AccumulatorAuto, String> reasonColumn;
    @FXML
    private TableColumn<AccumulatorAuto, String> conditionColumn;
    @FXML
    private TableColumn<AccumulatorAuto, String> autoNumberColumn;
    @FXML
    private TableColumn<AccumulatorAuto, String> accumulatorFactoryNumberColuumn;

    private AccumulatorAutoDao accumulatorAutoDao = ServiceLocator.getAccumulatorAutoDaoInstance();

    private ObservableList<AccumulatorAuto> result = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        autoNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAuto().getNumber()));
        accumulatorFactoryNumberColuumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAccumulator().getFactoryNumber()));

        refreshTable();
    }

    private void refreshTable() {
        result.setAll(accumulatorAutoDao.findAll());
        tableAccumulatorAuto.setItems(result);
    }

    @FXML
    private void updateTable() {
        refreshTable();
    }
}
