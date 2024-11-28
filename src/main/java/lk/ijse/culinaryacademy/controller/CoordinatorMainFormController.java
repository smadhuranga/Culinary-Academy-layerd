package lk.ijse.culinaryacademy.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class CoordinatorMainFormController {

    @FXML
    private JFXButton btnCourse;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnSettings;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private JFXButton btnEnrolment;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Pane subPane;


    @FXML
     void initialize() throws IOException {
        setButtonActive(btnHome);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboardForm.fxml"));

        Pane registerPane = fxmlLoader.load();
        subPane.getChildren().clear();
        subPane.getChildren().add(registerPane);
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {

    }

    @FXML
    void btnStudentOnAction(ActionEvent event) {

    }

    public void btnEnrolmentOnAction(ActionEvent actionEvent) {

    }

    public void btnCourseOnAction(ActionEvent actionEvent) {

    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {

    }

    @FXML
    void btnSettingsOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/credentialForm.fxml"));
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

    // ---------------------------- Pane Transition ----------------------------
    private void pageTransition(Parent load) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), subPane);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    // Set Active Button style
    private void setButtonActive(JFXButton button) {
        btnHome.getStyleClass().removeAll("jfx-active-button");
        btnCourse.getStyleClass().removeAll("jfx-active-button");
        btnEnrolment.getStyleClass().removeAll("jfx-active-button");
        btnPayment.getStyleClass().removeAll("jfx-active-button");
        btnStudent.getStyleClass().removeAll("jfx-active-button");
        btnSettings.getStyleClass().removeAll("jfx-active-button");
        btnLogOut.getStyleClass().removeAll("jfx-active-button");

        // Add Style
        button.getStyleClass().add("jfx-active-button");
    }

}
