package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Елена on 17.12.2015.
 */
public class MenuController {
    private Stage stage = new Stage();
    @FXML
    private void openBus(ActionEvent event) {
        buildScene(event, "../view/busView.fxml", "Информация о шинах");
    }

    @FXML
    private void openBattery(ActionEvent event) {
        buildScene(event, "../view/batteryView.fxml", "Информация об аккумуляторах");
    }

    @FXML
    private void openAuto(ActionEvent event) {
        buildScene(event, "../view/autoView.fxml", "Информация об автомобилях");
    }

    @FXML
    private void openTrip(ActionEvent event) {
        buildScene(event, "../view/tripView.fxml", "Информация о рейсах");
    }

    @FXML
    private void openEmployee(ActionEvent event) {
        buildScene(event, "../view/personView.fxml", "Информация о сотрудниках");
    }

    @FXML
    private void openBusAuto(ActionEvent event) {
        buildScene(event, "../view/busAutoView.fxml", "Шины на авто");
    }

    @FXML
    private void openBatteryAuto(ActionEvent event) {
        buildScene(event, "../view/batteryAutoView.fxml", " Аккамуляторы на авто");
    }

    private void buildScene(ActionEvent event, String fxmlPath, String stageTitle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            stage.setTitle(stageTitle);
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exitClick(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
