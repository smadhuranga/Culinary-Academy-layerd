package lk.ijse.culinaryacademy.util;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomAlert {

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnOK;

    @FXML
    private static ImageView imgAlertType;

    @FXML
    private static Text txtAlertMessage;

    @FXML
    private static Text txtAlertType;


    public void initialize() {

    }

    public void btnOKOnAction(ActionEvent actionEvent) {

    }

    public void btnCancelOnAction(ActionEvent actionEvent) {

    }

    public void showAlert(String s) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/alertForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Alert Form");
            stage.centerOnScreen();
            stage.showAndWait();

            txtAlertType.setText(s);
            txtAlertMessage.setText(s);
        } catch (IOException e) {
            CustomException.handleException(new CustomException("Failed to load alert form", e));
        }
    }
}
