package controller;

import dao.BusDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import model.Bus;
import model.DBCotroller;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.controlsfx.dialog.Dialogs;
import sample.ServiceLocator;

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

    private BusDao busDao = ServiceLocator.getBusDaoInstance();

    private Boolean editable;

    @FXML
    private void cancelClick(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void saveClick(ActionEvent event) {
        if (isInputValid()) {

            bus.setFactory(txtFactory.getText());
            bus.setCost(Double.valueOf(txtCost.getText()));
            bus.setNorm(txtNorm.getText());
            bus.setDateCreate(txtDateCreate.getText());
            bus.setFactoryNumber(txtFactoryNumber.getText());
            bus.setModel(txtModel.getText());
            bus.setIndication(txtIndication.getText());

            if (editable) {
                bus.setId(Integer.valueOf(txtId.getText()));
                busDao.update(bus);
            } else {
                busDao.save(bus);
            }
            ((Node) (event.getSource())).getScene().getWindow().hide();

        }
    }

    // проверка корректности ввода
    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        if (StringUtils.isEmpty(txtModel.getText())) {
            errorMessage.append("Введите модель!\n");
        }
        if (StringUtils.isEmpty(txtFactory.getText())) {
            errorMessage.append("Введите завод изготовител!\n");
        }
        if (StringUtils.isEmpty(txtFactoryNumber.getText())) {
            errorMessage.append("Введите заводской номер!\n");
        }
        if (StringUtils.isEmpty(txtDateCreate.getText())) {
            errorMessage.append("Введите дату изготовления!\n");
        }
        if (StringUtils.isEmpty(txtNorm.getText())) {
            errorMessage.append("Введите норму слойности!\n");
        }
        if (StringUtils.isEmpty(txtCost.getText())) {
            errorMessage.append("Введите стоимость комплекта!\n");
        }
        if (StringUtils.isEmpty(txtIndication.getText())) {
            errorMessage.append("Введите обозначение!\n");
        }

        if (errorMessage.length() != 0) {
            // Show the error message.
            Dialogs.create()
                    .title("Предупреждение, сэр")
                    .masthead("Пожалуйста, заполните поля верно.")
                    .message(errorMessage.toString())
                    .showError();
            return false;
        }
        return true;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public void initScene(Bus bus) {
        if (this.editable) {
            this.bus = bus;
            txtId.setText(this.bus.getId().toString());
            txtFactory.setText(this.bus.getFactory());
            txtCost.setText(this.bus.getCost().toString());
            txtNorm.setText(this.bus.getNorm());
            txtDateCreate.setText(this.bus.getDateCreate());
            txtFactoryNumber.setText(this.bus.getFactoryNumber());
            txtModel.setText(this.bus.getModel());
            txtIndication.setText(this.bus.getIndication());
        }
    }
}
