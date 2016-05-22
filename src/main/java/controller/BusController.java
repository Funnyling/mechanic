package controller;

import dao.BusDao;
import dao.impl.BusDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Bus;
import model.DBCotroller;
import org.apache.commons.lang.StringUtils;
import org.controlsfx.dialog.Dialogs;
import sample.DaoUtils;

/**
 * Created by Елена on 17.12.2015.
 */
public class BusController {
    private DBCotroller dbControl = new DBCotroller();

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
    private TextField txtId;

    private ObservableList<Bus> result = FXCollections.observableArrayList();

    private BusDao busDao = DaoUtils.getBusDaoInstance();

    private ExportData exportData = ExportData.getInstance();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("id"));
        factoryColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("factory"));
        costColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("cost"));
        normColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("norm"));
        dateCreateColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("dateCreate"));
        factoryNumberColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("factoryNumber"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("model"));
        indicationColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("indication"));
        result.setAll(busDao.findAll());
        tableBus.setItems(result);
    }

    @FXML
    private void updateTableClick() {
        tableBus.getItems().clear();
        result.setAll(busDao.findAll());
        tableBus.setItems(result);
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
            exportData.editFlag = false;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/busEditAdd.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Добавить шину");
            stage.setScene(new Scene(root));
            stage.showAndWait();


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

                exportData.myObject = selectedBus;
                exportData.editFlag = true;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/busEditAdd.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Изменить");
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
                    .masthead("Не выбрана запись для изменения")
                    .message("Пожалуйста, выберите шину из таблицы")
                    .showWarning();
        }
    }

    @FXML
    private void createBusCardClick() {

        Bus selectedBus = tableBus.getSelectionModel().getSelectedItem();

        if (selectedBus != null) {
            try {
                exportData.myObject = selectedBus;
                exportData.editFlag = true;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/busCard.fxml"));
                Parent root = (Parent) fxmlLoader.load();
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
    private void backToMenu(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/menu.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Главное меню");
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            Dialogs.create()
                    .message(e.getMessage())
                    .showWarning();
        }
    }

    @FXML
    //todo поиск по заводскому номеру: поменять название на xml
    private void searchClick() {
        String factoryNumber = txtId.getText();
        if (StringUtils.isEmpty(factoryNumber)) {
            Dialogs.create()
                    .message("Введите id для поиска!\n")
                    .showWarning();
        } else {
            tableBus.getItems().clear();
            result.setAll(busDao.findByFactoryNumber(factoryNumber));
            tableBus.setItems(result);
        }
    }
}
