package controller;

import dao.TripDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Trip;
import sample.ServiceLocator;

import java.util.List;

public class TripController extends BaseFxController {
    @FXML
    private TableView<Trip> tableTrip;
    @FXML
    private TableColumn<Trip, String> idColumn;
    @FXML
    private TableColumn<Trip, String> startDateColumn;
    @FXML
    private TableColumn<Trip, String> endDateColumn;
    @FXML
    private TableColumn<Trip, String> mileageColumn;
    @FXML
    private TableColumn<Trip, String> autoNumberColumn;
    @FXML
    private TableColumn<Trip, String> nameColumn;

    private TripDao tripDao = ServiceLocator.getTripDaoInstance();

    private ObservableList<Trip> result = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        mileageColumn.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        autoNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAuto().getNumber()));
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEmployee().getFullName()));

        refreshTable();
    }

    private void refreshTable() {
        List<Trip> trips = tripDao.findAll();
        result.setAll(trips);
        tableTrip.setItems(result);
    }

    @FXML
    private void updateTable() {
        refreshTable();
    }
}
