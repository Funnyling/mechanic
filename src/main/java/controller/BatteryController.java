package controller;

import dao.AccumulatorDao;
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
import model.Accumulator;
import model.Battery;
import model.DBCotroller;
import org.apache.commons.lang.StringUtils;
import org.controlsfx.dialog.Dialogs;
import sample.ServiceLocator;

import java.util.List;

/**
 * Created by Елена on 20.12.2015.
 */
public class BatteryController {
    @FXML
    private TableView<Accumulator> tableBattery;
    @FXML
    private TableColumn<Accumulator, String> idColumn;
    @FXML
    private TableColumn<Accumulator, String> factoryColumn;
    @FXML
    private TableColumn<Accumulator, String> costColumn;
    @FXML
    private TableColumn<Accumulator, String> typeColumn;
    @FXML
    private TableColumn<Accumulator, String> dateCreateColumn;
    @FXML
    private TableColumn<Accumulator, String> factoryNumberColumn;
    @FXML
    private TableColumn<Accumulator, String> garageNumberColumn;
    @FXML
    private TextField txtId;

    private AccumulatorDao accumulatorDao = ServiceLocator.getAccumulatorDaoInstance();

    private ObservableList<Accumulator> result = FXCollections.observableArrayList();

    private ExportData exportData = ExportData.getInstance();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        factoryColumn.setCellValueFactory(new PropertyValueFactory<>("factory"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateCreateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        factoryNumberColumn.setCellValueFactory(new PropertyValueFactory<>("factoryNumber"));
        garageNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        refreshTable();
    }

    @FXML
    private void updateTableClick() {
        refreshTable();
    }

    @FXML
    private void deleteBatteryClick() {
        int selectedIndex = tableBattery.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            Integer id = tableBattery.getItems().get(selectedIndex).getId();
            accumulatorDao.deleteQuery(id);
            refreshTable();
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
    private void addBatteryClick() {
        try {
            exportData.editFlag = false;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/batteryEditAdd.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            BatteryEditAddController controller = fxmlLoader.getController();
            controller.setEditable(Boolean.FALSE);
            Stage stage = new Stage();
            stage.setTitle("Добавить аккумулятор");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTable();
        } catch (Exception e) {
            Dialogs.create()
                    .message(e.getMessage())
                    .showWarning();
            e.printStackTrace();
        }
    }

    @FXML
    private void editBatteryClick() {
        Accumulator selected = tableBattery.getSelectionModel().getSelectedItem();

        if (selected != null) {
            try {
                exportData.myObject = selected;
                exportData.editFlag = true;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/batteryEditAdd.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                BatteryEditAddController controller = fxmlLoader.getController();
                controller.setEditable(Boolean.TRUE);
                controller.initScene(selected);
                Stage stage = new Stage();
                stage.setTitle("Изменить");
                stage.setScene(new Scene(root));
                stage.showAndWait();
                refreshTable();
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
                    .message("Пожалуйста, выберите аккумулятор из таблицы")
                    .showWarning();
        }
    }

    @FXML
    private void createBatteryCardClick() {

        Accumulator selected = tableBattery.getSelectionModel().getSelectedItem();

        if (selected != null) {
            try {
                exportData.myObject = selected;
                exportData.editFlag = true;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/batteryCard.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                BatteryCardController controller = fxmlLoader.getController();
                controller.initScene(selected);
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
                    .message("Пожалуйста, выберите аккумулятор из таблицы")
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
    //todo
    private void searchClick() {
        String id = txtId.getText();
        if (StringUtils.isEmpty(id)) {
            Dialogs.create()
                    .message("Введите id для поиска!\n")
                    .showWarning();
        } else {
            refreshTable(accumulatorDao.findByFactoryNumber(id));
        }
    }

    private void refreshTable(List<Accumulator> results) {
        result.setAll(results);
        tableBattery.setItems(result);
    }

    private void refreshTable() {
        refreshTable(accumulatorDao.findAll());
    }
}
