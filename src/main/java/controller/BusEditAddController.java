package controller;

import dao.BusDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Bus;
import model.DBCotroller;
import org.controlsfx.dialog.Dialogs;
import sample.DaoUtils;

/**
 * Created by Елена on 17.12.2015.
 */
public class BusEditAddController {
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtFactory;
    @FXML
    private TextField txtCost;
    @FXML
    private TextField txtNorm;
    @FXML
    private TextField txtDateCreate;
    @FXML
    private TextField txtFactoryNumber;
    @FXML
    private TextField txtModel;
    @FXML
    private TextField txtIndication;

    private Bus bus = new Bus();

    private BusDao busDao = DaoUtils.getBusDaoInstance();

    @FXML
    private void initialize() {
        if (ExportData.getInstance().editFlag == true) {
            setBus();
//        else {
//            DBCotroller dbCotrol = new DBCotroller();
//            txtId.setText("" + (dbCotrol.maxId() + 1) + "");
        }
    }

    private void setBus() {
        bus = (Bus) ExportData.getInstance().myObject;

        txtId.setText(bus.getId().toString());
        txtFactory.setText(bus.getFactory());
        txtCost.setText(bus.getCost().toString());
        txtNorm.setText(bus.getNorm());
        txtDateCreate.setText(bus.getDateCreate().toString());
        txtFactoryNumber.setText(bus.getFactoryNumber());
        txtModel.setText(bus.getModel());
        txtIndication.setText(bus.getIndication());
    }

    @FXML
    private void cancelClick(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void saveClick(ActionEvent event) {
        boolean editFlag = ExportData.getInstance().editFlag;

        if (isInputValid()) {

            bus.setFactory(txtFactory.getText());
            bus.setCost(Double.valueOf(txtCost.getText()));
            bus.setNorm(txtNorm.getText());
            bus.setDateCreate(txtDateCreate.getText());
            bus.setFactoryNumber(txtFactoryNumber.getText());
            bus.setModel(txtModel.getText());
            bus.setIndication(txtIndication.getText());

            DBCotroller dbCotrol = new DBCotroller();
            if (editFlag == true) {
                bus.setId(Integer.valueOf(txtId.getText()));
//                String sql = "UPDATE Шина SET Стоимость_комплекта=" + bus.getCost() +
//                        ", Дата_изготовления=cast('" + bus.getDateCreate() + "' as Date), Завод_изготовитель='" + bus.getFactory() +
//                        "', Обозначение='" + bus.getIndication() + "', Модель='" + bus.getModel() +
//                        "', Заводской_номер='" + bus.getFactoryNumber() + "', Норма_слойности='" + bus.getNorm() + "' WHERE Id_шина =" + bus.getId();
//                dbCotrol.update(sql);
                busDao.update(bus);
            } else {
                busDao.save(bus);
            }
            ((Node) (event.getSource())).getScene().getWindow().hide();

        }
    }


    // проверка корректности ввода

    private boolean isInputValid() {

        String errorMessage = "";

        if (txtModel.getText() == null || txtModel.getText().length() == 0) {
            errorMessage += "Введите модель!\n";
        }
        if (txtFactory.getText() == null || txtFactory.getText().length() == 0) {
            errorMessage += "Введите завод изготовител!\n";
        }
        if (txtFactoryNumber.getText() == null || txtFactoryNumber.getText().length() == 0) {
            errorMessage += "Введите заводской номер!\n";
        }
        if (txtDateCreate.getText() == null || txtDateCreate.getText().length() == 0) {
            errorMessage += "Введите дату изготовления!\n";
        }
        if (txtNorm.getText() == null || txtNorm.getText().length() == 0) {
            errorMessage += "Введите норму слойности!\n";
        }
        if (txtCost.getText() == null || txtCost.getText().length() == 0) {
            errorMessage += "Введите стоимость комплекта!\n";
        }
        if (txtIndication.getText() == null || txtIndication.getText().length() == 0) {
            errorMessage += "Введите обозначение!\n";
        }

        if (errorMessage.length() != 0) {
            // Show the error message.
            Dialogs.create()
                    .title("Предупреждение, сэр")
                    .masthead("Пожалуйста, заполните поля верно.")
                    .message(errorMessage)
                    .showError();
            return false;
        }
        return true;
    }
}
