package controller;

import dao.AutoDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Auto;
import org.apache.commons.lang.StringUtils;
import org.controlsfx.dialog.Dialogs;
import sample.ServiceLocator;

import java.util.List;

public class AutoController extends BaseFxController {
    @FXML
    private TableView<Auto> tableAuto;
    @FXML
    private TableColumn<Auto, String> idColumn;
    @FXML
    private TableColumn<Auto, String> modelColumn;
    @FXML
    private TableColumn<Auto, String> numberColumn;
    @FXML
    private TableColumn<Auto, String> gosNumberColumn;
    @FXML
    private TextField modelSearchText;

    private AutoDao autoDao = ServiceLocator.getAutoDaoInstance();

    private ObservableList<Auto> result = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        gosNumberColumn.setCellValueFactory(new PropertyValueFactory<>("gosNumber"));

        refreshTable();
    }

    @FXML
    private void updateTable() {
        refreshTable();
    }

    private void refreshTable() {
        List<Auto> results = autoDao.findAll();
        refreshTable(results);
    }

    private void refreshTable(List<Auto> results) {
        result.setAll(results);
        tableAuto.setItems(result);
    }

    @FXML
    private void searchClick() {
        String model = modelSearchText.getText();
        if (StringUtils.isEmpty(model)) {
            Dialogs.create()
                    .message("Введите id для поиска!\n")
                    .showWarning();
        } else {
            refreshTable(autoDao.findByModel(model));
        }
    }

}
