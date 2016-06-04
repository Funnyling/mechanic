package controller;

import dao.EmployeeDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Employee;
import org.apache.commons.lang.StringUtils;
import org.controlsfx.dialog.Dialogs;
import sample.ServiceLocator;

import java.util.List;

public class EmployeeController extends BaseFxController {
    @FXML
    private TableView<Employee> tableEmployee;
    @FXML
    private TableColumn<Employee, String> idColumn;
    @FXML
    private TableColumn<Employee, String> positionColumn;
    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TextField searchText;

    private EmployeeDao employeeDao = ServiceLocator.getEmployeeDaoInstance();

    private ObservableList<Employee> result = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        refreshTable();
    }

    private void refreshTable() {
        List<Employee> employees = employeeDao.findAll();
        refreshTable(employees);
    }

    private void refreshTable(List<Employee> employees) {
        result.setAll(employees);
        tableEmployee.setItems(result);
    }

    @FXML
    private void updateTable() {
        refreshTable();
    }

    @FXML
    private void searchEmployee() {
        String fullName = searchText.getText();
        if (StringUtils.isEmpty(fullName)) {
            Dialogs.create()
                    .message("Введите фамилию для поиска!\n")
                    .showWarning();
        } else {
            refreshTable(employeeDao.findByFullName(fullName));
        }
    }
}
