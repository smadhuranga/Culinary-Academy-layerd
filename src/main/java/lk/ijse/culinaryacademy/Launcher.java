package lk.ijse.culinaryacademy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) { Application.launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/credentialForm.fxml"))));
        stage.setTitle("Credential Form");
        stage.centerOnScreen();
        stage.show();
    }

}