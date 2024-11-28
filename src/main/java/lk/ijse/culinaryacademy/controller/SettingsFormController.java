package lk.ijse.culinaryacademy.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import lk.ijse.culinaryacademy.bo.BOFactory;
import lk.ijse.culinaryacademy.bo.custom.UserBO;
import lk.ijse.culinaryacademy.util.Regex;
import lk.ijse.culinaryacademy.util.TextField;

import java.util.Optional;

public class SettingsFormController {

    @FXML
    private TitledPane titledPaneUsername;

    @FXML
    private TitledPane titledPanePassword;

    @FXML
    private JFXTextField txtCurrentUsername;

    @FXML
    private JFXTextField txtNewUsername;

    @FXML
    private JFXTextField txtConfirmUsername;

    @FXML
    private JFXPasswordField txtCurrentPassword;

    @FXML
    private JFXPasswordField txtNewPassword;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    UserBO userBO = (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);


    // ------------------------------------ Initialize Method ------------------------------------
    @FXML
    public void initialize() {
        // Set the expanded property for both TitledPanes
        titledPaneUsername.setExpanded(false);
        titledPanePassword.setExpanded(false);

        // Add listeners to the expanded property of both TitledPanes
        titledPaneUsername.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                titledPanePassword.setExpanded(false);
            }
        });

        titledPanePassword.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                titledPaneUsername.setExpanded(false);
            }
        });
    }


    // ------------------------------------ Username & PASSWORD CHANGE BUTTONS ------------------------------------
    @FXML
    void btnUsernameChangeOnAction(ActionEvent event) {
        String currentUsername = txtCurrentUsername.getText();
        String newUsername = txtNewUsername.getText();
        String confirmUsername = txtConfirmUsername.getText();

        String errorMessage = isValid("Username");

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        // Confirmation Alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Username Change");
        confirmationAlert.setHeaderText("Are you sure you want to change your Username?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isAdded = userBO.changeUsername(currentUsername, newUsername, confirmUsername);

                if (isAdded) {
                    new Alert(Alert.AlertType.INFORMATION, "Username Changed Successfully.").show();
                    clearField();
                }
            } catch (IllegalArgumentException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred while changing the Username.").show();
                e.printStackTrace();
            }
        } else {
            // User chooses CANCEL or closed the dialogue
            new Alert(Alert.AlertType.INFORMATION, "Username change cancelled.").show();
        }
    }

    @FXML
    void btnPWChangeOnAction(ActionEvent event) throws Exception {
        String currentPassword = txtCurrentPassword.getText();
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (!newPassword.equals(confirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Password Mismatched.").show();
            return;
        }

        String errorMessage = isValid("Password");

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        // Confirmation Alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Password Change");
        confirmationAlert.setHeaderText("Are you sure you want to change your password?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isChanged = userBO.changePassword(currentPassword, newPassword);

                if (isChanged) {
                    new Alert(Alert.AlertType.INFORMATION, "Password Changed Successfully.").show();
                    clearField();
                }
            } catch (IllegalArgumentException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred while changing the password.").show();
                e.printStackTrace();
            }
        } else {
            // User chose CANCEL or closed the dialogue
            new Alert(Alert.AlertType.INFORMATION, "Password change cancelled.").show();
        }
    }


    // ------------------------------------ OTHER METHODS ------------------------------------
    private void clearField() {
        txtCurrentUsername.clear();
        txtNewUsername.clear();
        txtConfirmUsername.clear();
        txtCurrentPassword.clear();
        txtNewPassword.clear();
        txtConfirmPassword.clear();
    }

    // ------------------------------------ ON KEY RELEASE ------------------------------------
    @FXML
    void txtNewUsernameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.USERNAME, txtNewUsername);
    }

    @FXML
    void txtNewPasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.PASSWORD, txtNewPassword);
    }

    // ------------------------------------ VALIDATION ------------------------------------
    public String isValid(String type) {
        String message = "";

        if ("username".equals(type)) {
            if (!Regex.setTextColor(TextField.USERNAME, txtNewUsername))
                message += "Enter valid username.\n\n";
        }

        if ("password".equals(type)) {
            if (!Regex.setTextColor(TextField.PASSWORD, txtNewPassword))
                message += """
                    Please enter a password following these rules:
                    \t* Contains at least one alphabetic character and one digit.
                    \t* Includes special characters such as @$!%*?&.
                    \t* Password must be at least 8 characters long.""";
        }

        return message.isEmpty() ? null : message;
    }

}
