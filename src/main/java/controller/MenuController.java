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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/busView.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            BusController controller = fxmlLoader.<BusController>getController();
            stage.setTitle("Информация о шинах");
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void openBattery(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/batteryView.fxml"));
            Parent root = fxmlLoader.load();
            stage.setTitle("Информация об аккумуляторах");
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
