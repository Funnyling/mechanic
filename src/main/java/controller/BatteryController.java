package controller;

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
import model.Battery;
import model.DBCotroller;
import org.controlsfx.dialog.Dialogs;

/**
 * Created by Елена on 20.12.2015.
 */
public class BatteryController {
    private DBCotroller dbControl = new DBCotroller();


    @FXML
    private TableView<Battery> tableBattery;
    @FXML
    private TableColumn<Battery, String> idColumn;
    @FXML
    private TableColumn<Battery, String> factoryColumn;
    @FXML
    private TableColumn<Battery, String> costColumn;
    @FXML
    private TableColumn<Battery, String> typeColumn;
    @FXML
    private TableColumn<Battery, String> dateCreateColumn;
    @FXML
    private TableColumn<Battery, String> factoryNumberColumn;
    @FXML
    private TableColumn<Battery, String> garageNumberColumn;
    @FXML
    private TextField txtId;


    private ExportData exportData = ExportData.getInstance();

    @FXML
    private void initialize() {
        // устанавливаем тип и значение которое должно хранится в колонке
        idColumn.setCellValueFactory(new PropertyValueFactory<Battery, String>("id"));
        factoryColumn.setCellValueFactory(new PropertyValueFactory<Battery, String>("factory"));
        costColumn.setCellValueFactory(new PropertyValueFactory<Battery, String>("cost"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Battery, String>("type"));
        dateCreateColumn.setCellValueFactory(new PropertyValueFactory<Battery, String>("dateCreate"));
        factoryNumberColumn.setCellValueFactory(new PropertyValueFactory<Battery, String>("factoryNumber"));
        garageNumberColumn.setCellValueFactory(new PropertyValueFactory<Battery, String>("garageNumber"));


        updateTableClick();
    }

    @FXML
    private void updateTableClick() {
        tableBattery.getItems().clear();
        // заполняем таблицу данными
        tableBattery.setItems(dbControl.selectBattery("SELECT Id_Аккумулятор, Cast( Стоимость as int) as Стоимость, Cast(Дата_изготовления as Date) as Дата_изготовления, " +
                "Завод_изготовитель, Тип, Заводской_номер, Гаражный_номер FROM Аккумулятор"));
    }

    //удаление записи из таблицы
    @FXML
    private void deleteBatteryClick() {
        int selectedIndex = tableBattery.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            String id = tableBattery.getItems().get(selectedIndex).getId();
            String sql = "DELETE FROM Аккумулятор WHERE Id_Аккумулятор='" + id + "'";
            dbControl.delete(sql);
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
            Stage stage = new Stage();
            stage.setTitle("Добавить аккумулятор");
            stage.setScene(new Scene(root));
            stage.showAndWait();


        } catch (Exception e) {
            Dialogs.create()
                    .message(e.getMessage())
                    .showWarning();
            e.printStackTrace();
        }
    }

    //===========================================================================================================
    @FXML
    private void editBatteryClick() {
        Battery selectedBattery = tableBattery.getSelectionModel().getSelectedItem();

        if (selectedBattery != null) {
            try {

                exportData.myObject = selectedBattery;
                exportData.editFlag = true;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/batteryEditAdd.fxml"));
                Parent root = (Parent) fxmlLoader.load();
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
                    .message("Пожалуйста, выберите аккумулятор из таблицы")
                    .showWarning();
        }
    }

    @FXML
    private void createBatteryCardClick() {

        Battery selectedBattery = tableBattery.getSelectionModel().getSelectedItem();

        if (selectedBattery != null) {
            try {
                exportData.myObject = selectedBattery;
                exportData.editFlag = true;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/batteryCard.fxml"));
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
    private void searchClick() {
        String id = "";
        if (txtId.getText() == null || txtId.getText().length() == 0) {
            Dialogs.create()
                    .message("Введите id для поиска!\n")
                    .showWarning();
        } else {
            tableBattery.getItems().clear();
            id = txtId.getText();
            tableBattery.setItems(dbControl.selectBattery("SELECT Id_Аккумулятор, Cast( Стоимость as int) as Стоимость, Cast(Дата_изготовления as Date) as Дата_изготовления, " +
                    "Завод_изготовитель, Тип, Заводской_номер, Гаражный_номер " +
                    "FROM Аккумулятор WHERE Id_Аккумулятор='" + id + "'"));
        }
    }
}
