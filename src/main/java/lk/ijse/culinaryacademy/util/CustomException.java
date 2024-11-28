package lk.ijse.culinaryacademy.util;

import javafx.scene.control.Alert;

public class CustomException extends Exception {

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }


    public static void handleException(Exception e) {
        if (e instanceof CustomException) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } else {
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage()).show();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); // You can set a header if you like
        alert.setContentText(message);
        alert.showAndWait();  // Display the alert and wait for the user to close it
    }
}
