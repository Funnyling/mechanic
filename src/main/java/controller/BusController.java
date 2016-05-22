package controller;

import dao.BusDao;
import dao.impl.BusDaoImpl;
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
import org.controlsfx.dialog.Dialogs;

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

    private String test;

    private BusDao busDao;

    private ExportData exportData = ExportData.getInstance();

    @FXML
    private void initialize() {
        busDao = new BusDaoImpl();
        idColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("id"));
        factoryColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("factory"));
        costColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("cost"));
        normColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("norm"));
        dateCreateColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("dateCreate"));
        factoryNumberColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("factoryNumber"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("model"));
        indicationColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("indication"));
        test = busDao.echo();
    }

    @FXML
    private void updateTableClick() {
        tableBus.getItems().clear();
        // заполняем таблицу данными
        tableBus.setItems(dbControl.selectBus("SELECT Id_шина,Cast( Стоимость_комплекта as int) as Стоимость_комплекта, Cast(Дата_изготовления as Date) as Дата_изготовления, " +
                "Завод_изготовитель, Обозначение, Модель, Заводской_номер, Норма_слойности FROM Шина"));
    }

    //удаление записи из таблицы
    @FXML
    private void deleteBusClick() {
        int selectedIndex = tableBus.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            Integer id = tableBus.getItems().get(selectedIndex).getId();
            String sql = "DELETE FROM Шина WHERE Id_шина='" + id + "'";
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

    //===========================================================================================================
    @FXML
    private void editBusClick() {
        Bus selectedBus = tableBus.getSelectionModel().getSelectedItem();

        if (selectedBus != null) {
            try {

                exportData.myObject = selectedBus;
                exportData.editFlag = true;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/busEditAdd.fxml"));
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
    private void searchClick() {
        String id = "";
        if (txtId.getText() == null || txtId.getText().length() == 0) {
            Dialogs.create()
                    .message("Введите id для поиска!\n")
                    .showWarning();
        } else {
            tableBus.getItems().clear();
            id = txtId.getText();
            tableBus.setItems(dbControl.selectBus("SELECT Id_шина, Cast( Стоимость_комплекта as int) as Стоимость_комплекта, Cast(Дата_изготовления as Date) as Дата_изготовления, " +
                    "Завод_изготовитель, Обозначение, Модель, Заводской_номер, Норма_слойности " +
                    "FROM Шина WHERE Id_шина='" + id + "'"));
        }
    }
}
