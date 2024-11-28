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
import lk.ijse.culinaryacademy.bo.custom.impl.UserBOImpl;

import java.io.IOException;
import java.net.URL;

public class AdminMainFormController {

    @FXML
    private JFXButton btnCourse;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnSettings;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnUser;

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



    // ---------------------------- Button Actions ----------------------------
    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnHome);

        URL resource = getClass().getResource("/view/dashboardForm.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        subPane.getChildren().clear();
        subPane.getChildren().add(load);

        pageTransition(load);
    }

    @FXML
    void btnStudentOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnStudent);

        URL resource = getClass().getResource("/view/studentsForm.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        subPane.getChildren().clear();
        subPane.getChildren().add(load);

        pageTransition(load);
    }

    @FXML
    void btnEnrolmentOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnEnrolment);

        URL resource = getClass().getResource("/view/enrolmentForm.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        subPane.getChildren().clear();
        subPane.getChildren().add(load);

        pageTransition(load);
    }

    @FXML
    void btnCourseOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnCourse);

        URL resource = getClass().getResource("/view/coursesForm.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        subPane.getChildren().clear();
        subPane.getChildren().add(load);

        pageTransition(load);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent actionEvent) throws IOException {
        setButtonActive(btnPayment);

        URL resource = getClass().getResource("/view/paymentsForm.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        subPane.getChildren().clear();
        subPane.getChildren().add(load);

        pageTransition(load);
    }

    @FXML
    void btnUserOnAction(ActionEvent actionEvent) throws IOException {
        setButtonActive(btnUser);

        URL resource = getClass().getResource("/view/userForm.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        subPane.getChildren().clear();
        subPane.getChildren().add(load);

        pageTransition(load);
    }

    @FXML
    void btnSettingsOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnSettings);

        URL resource = getClass().getResource("/view/settingsForm.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        subPane.getChildren().clear();
        subPane.getChildren().add(load);

        pageTransition(load);
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

    // ---------------------------- Active Button Styles ----------------------------
    private void setButtonActive(JFXButton button) {
        btnHome.getStyleClass().removeAll("jfx-active-button");
        btnCourse.getStyleClass().removeAll("jfx-active-button");
        btnStudent.getStyleClass().removeAll("jfx-active-button");
        btnEnrolment.getStyleClass().removeAll("jfx-active-button");
        btnUser.getStyleClass().removeAll("jfx-active-button");
        btnPayment.getStyleClass().removeAll("jfx-active-button");
        btnSettings.getStyleClass().removeAll("jfx-active-button");
        btnLogOut.getStyleClass().removeAll("jfx-active-button");

        // Add Style
        button.getStyleClass().add("jfx-active-button");
    }

}
