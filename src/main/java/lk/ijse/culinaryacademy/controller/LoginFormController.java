package lk.ijse.culinaryacademy.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.culinaryacademy.bo.BOFactory;
import lk.ijse.culinaryacademy.bo.custom.UserBO;
import lk.ijse.culinaryacademy.bo.custom.impl.UserBOImpl;
import lk.ijse.culinaryacademy.entity.User;
import lk.ijse.culinaryacademy.util.CustomException;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;

public class LoginFormController {

    @FXML
    private AnchorPane adminLoginPane;


    @FXML
    private MFXTextField txtUsername;

    @FXML
    private MFXPasswordField txtPassword;


    UserBO userBO = (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);


    public void initialize() {

        txtUsername.requestFocus();
    }

    // ---------------------------- MAIN FUNCTIONS ----------------------------
    @FXML
    void btnLoginOnAction(ActionEvent event) throws Exception {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields.").show();
            return;
        }

        try {
            User user = userBO.checkLoginCredential(username);
            if (user != null) {
                // Verify the password using BCrypt
                if (BCrypt.checkpw(password, user.getPassword())) {
                    UserBOImpl.userName = user.getUsername();
                    UserBOImpl.role = user.getRole(); // get the username of the logged-in user
                    String role = user.getRole(); // get the user's role for which dashboard to navigate

                    // Navigate based on the user's role
                    if ("Admin".equalsIgnoreCase(role)) {
                        navigateToDashboard("/view/adminMainForm.fxml"); // navigate to the admin dashboard
                    } else if ("Coordinator".equalsIgnoreCase(role)) {
                        navigateToDashboard("/view/adminMainForm.fxml"); // navigate to the coordinator dashboard
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Unknown role: " + role).show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid username or password. Please try again.").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid username or password. Please try again.").show();
            }
        } catch (SQLException | IOException e) {
            CustomException.handleException(new CustomException("An error occurred while checking login details: ", e));
            e.printStackTrace();
        }
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/registerForm.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        adminLoginPane.getChildren().clear();
        adminLoginPane.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), adminLoginPane);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }


    // ---------------------------- OTHER METHODS ----------------------------
    private void navigateToDashboard(String url) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource(url));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.adminLoginPane.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }



    // ---------------------------- ON ACTION ----------------------------
    @FXML
    void txUsernameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        try {
            btnLoginOnAction(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
