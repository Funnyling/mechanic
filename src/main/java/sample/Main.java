package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.HibernateUtils;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        super.init();
        HibernateUtils.setUpSession();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/menu.fxml"));
        primaryStage.setTitle("Меню");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        HibernateUtils.closeSessionFactory();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
