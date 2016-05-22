package controller;

import dao.BusDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Bus;
import org.apache.commons.lang.StringUtils;
import org.controlsfx.dialog.Dialogs;
import sample.ServiceLocator;

import java.io.IOException;
import java.util.List;

/**
 * Created by Елена on 17.12.2015.
 */
public class BusController extends BaseFxController {
    @FXML
    private TableView<Bus> tableBus;
    @FXML
    private TableColumn<Bus, String> idColumn;
    @FXML
    private TableColumn<Bus, String> factoryColumn;
    @FXML
    private TableColumn<Bus, String> costColumn;
    @FXML
    private TableColumn<Bus, String> normColumn;
    @FXML
    private TableColumn<Bus, String> dateCreateColumn;
    @FXML
    private TableColumn<Bus, String> factoryNumberColumn;
    @FXML
    private TableColumn<Bus, String> modelColumn;
    @FXML
    private TableColumn<Bus, String> indicationColumn;
    @FXML
    private TextField txtFactoryNumber;

    private ObservableList<Bus> result = FXCollections.observableArrayList();

    private BusDao busDao = ServiceLocator.getBusDaoInstance();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        factoryColumn.setCellValueFactory(new PropertyValueFactory<>("factory"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        normColumn.setCellValueFactory(new PropertyValueFactory<>("norm"));
        dateCreateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreate"));
        factoryNumberColumn.setCellValueFactory(new PropertyValueFactory<>("factoryNumber"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        indicationColumn.setCellValueFactory(new PropertyValueFactory<>("indication"));
        refreshTable();
    }

    @FXML
    private void updateTableClick() {
        refreshTable();
    }

    //удаление записи из таблицы
    @FXML
    private void deleteBusClick() {
        int selectedIndex = tableBus.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            Integer id = tableBus.getItems().get(selectedIndex).getId();
            busDao.deleteQuery(id);
        } else {
            // нет выбранных записей, сэр.
            Dialogs.create()
                    .title("Предупреждение")
                    .masthead("Не выбрана запись для удаления")
                    .message("Пожалуйста, выберите сотрудника из таблицы")
                    .showWarning();
        }
    }

    @FXML
    private void addBusClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/busEditAdd.fxml"));
            Parent root = fxmlLoader.load();
            BusEditAddController controller = fxmlLoader.getController();
            controller.setEditable(Boolean.FALSE);
            Stage stage = new Stage();
            stage.setTitle("Добавить шину");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTable(busDao.findAll());
        } catch (Exception e) {
            Dialogs.create()
                    .message(e.getMessage())
                    .showWarning();
            e.printStackTrace();
        }
    }

    @FXML
    private void editBusClick() {
        Bus selectedBus = tableBus.getSelectionModel().getSelectedItem();

        if (selectedBus != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/busEditAdd.fxml"));
                Parent root = fxmlLoader.load();
                BusEditAddController controller = fxmlLoader.getController();
                controller.setEditable(Boolean.TRUE);
                controller.initScene(selectedBus);
                Stage stage = new Stage();
                stage.setTitle("Изменить");
                stage.setScene(new Scene(root));
                stage.showAndWait();
                refreshTable(busDao.findAll());
            } catch (Exception e) {
                Dialogs.create()
                        .message(e.getMessage())
                        .showWarning();
            }
        } else {
            // Nothing selected.
            Dialogs.create()
                    .title("Предупреждение")
                    .masthead("Не выбрана запись для изменения")
                    .message("Пожалуйста, выберите шину из таблицы")
                    .showWarning();
        }
    }

    @FXML
    private void createBusCardClick() throws IOException {
        Bus selectedBus = tableBus.getSelectionModel().getSelectedItem();

        if (selectedBus != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/busCard.fxml"));
                Parent root = fxmlLoader.load();
                BusCardController controller = fxmlLoader.getController();
                controller.initScene(selectedBus);
                Stage stage = new Stage();
                stage.setTitle("Карточка учета");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (Exception e) {
                Dialogs.create()
                        .message(e.getMessage())
                        .showWarning();
            }
        } else {
            // Nothing selected.
            Dialogs.create()
                    .title("Предупреждение")
                    .masthead("Не выбрана запись для формирования карточки учета")
                    .message("Пожалуйста, выберите шину из таблицы")
                    .showWarning();
        }
    }

    @FXML
    private void searchClick() {
        String factoryNumber = txtFactoryNumber.getText();
        if (StringUtils.isEmpty(factoryNumber)) {
            Dialogs.create()
                    .message("Введите заводской номер для поиска!\n")
                    .showWarning();
        } else {
            refreshTable(busDao.findByFactoryNumber(factoryNumber));
        }
    }

    private void refreshTable(List<Bus> buses) {
        result.setAll(buses);
        tableBus.setItems(result);
    }

    private void refreshTable() {
        refreshTable(busDao.findAll());
    }

}
