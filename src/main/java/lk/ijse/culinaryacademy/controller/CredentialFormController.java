package lk.ijse.culinaryacademy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CredentialFormController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Pane subPane;

    public void initialize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/loginForm.fxml"));

        Pane pane = fxmlLoader.load();
        subPane.getChildren().clear();
        subPane.getChildren().add(pane);
    }

}
