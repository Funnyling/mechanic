package controller;

import dao.AccumulatorDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import model.Accumulator;
import org.apache.commons.lang.StringUtils;
import org.controlsfx.dialog.Dialogs;
import sample.ServiceLocator;

public class BatteryEditAddController {
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtFactory;
    @FXML
    private TextField txtCost;
    @FXML
    private TextField txtNumber;
    @FXML
    private TextField txtCreateDate;
    @FXML
    private TextField txtFactoryNumber;
    @FXML
    private TextField txtType;

    private Accumulator accumulator = new Accumulator();

    private AccumulatorDao accumulatorDao = ServiceLocator.getAccumulatorDaoInstance();

    private Boolean editable;

    @FXML
    private void cancelClick(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void saveClick(ActionEvent event) {
        if (isInputValid()) {

            accumulator.setFactory(txtFactory.getText());
            accumulator.setCost(Double.valueOf(txtCost.getText()));
            accumulator.setCreateDate(txtCreateDate.getText());
            accumulator.setType(txtType.getText());
            accumulator.setFactoryNumber(txtFactoryNumber.getText());
            accumulator.setNumber(txtNumber.getText());

            if (editable) {
                accumulatorDao.update(accumulator);
            } else {
                accumulatorDao.save(accumulator);
            }
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        if (StringUtils.isEmpty(txtType.getText())) {
            errorMessage.append("������� ���!\n");
        }
        if (StringUtils.isEmpty(txtFactory.getText())) {
            errorMessage.append("������� ����� �����������!\n");
        }
        if (StringUtils.isEmpty(txtFactoryNumber.getText())) {
            errorMessage.append("������� ��������� �����!\n");
        }
        if (StringUtils.isEmpty(txtCreateDate.getText())) {
            errorMessage.append("������� ���� ������������!\n");
        }
        if (StringUtils.isEmpty(txtCost.getText())) {
            errorMessage.append("������� ��������� ���������!\n");
        }
        if (StringUtils.isEmpty(txtNumber.getText())) {
            errorMessage.append("������� �������� �����!\n");
        }

        if (errorMessage.length() != 0) {
            // Show the error message.
            Dialogs.create()
                    .title("��������������, ���")
                    .masthead("����������, ��������� ���� �����.")
                    .message(errorMessage.toString())
                    .showError();
            return false;
        }
        return true;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public void initScene(Accumulator accumulator) {
        if (this.editable) {
            this.accumulator = accumulator;
            txtId.setText(this.accumulator.getId().toString());
            txtFactory.setText(this.accumulator.getFactory());
            txtCost.setText(this.accumulator.getCost().toString());
            txtCreateDate.setText(this.accumulator.getCreateDate());
            txtFactoryNumber.setText(this.accumulator.getFactoryNumber());
            txtNumber.setText(this.accumulator.getCreateDate());
            txtType.setText(this.accumulator.getType());
        }
    }
}
