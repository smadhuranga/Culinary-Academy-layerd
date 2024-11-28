package lk.ijse.culinaryacademy.util;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.paint.Paint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static boolean isTextFiledValid(TextField textField, String text) {
        String field = switch (textField) {
            case STUDENTID -> "^S([0-9]{3})$";

            case CONTACT -> "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";

            case ADDRESS -> "^[A-Za-z0-9,\\s/-]{3,}$";

            case ENROLMENTID -> "^E([0-9]{3})$";

            case COURSEID -> "^C([0-9]{3})$";

            case DURATION -> "^([0-9]{1,2})$";

            case PAYMENTID -> "^P([0-9]{3})$";

            case USERNAME -> "^[a-zA-Z0-9_-]{3,16}$";

            case NAME -> "^[A-z|\\s]{3,}$";

            case EMAIL -> "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

            case PASSWORD -> "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$";

            case DATE ->
                    "^(?:19|20)\\d{2}-(?:0[1-9]|1[0-2])-(?:(?:0[1-9]|[12][0-9]|3[01])|(?:0[1-9]|[12][0-9]|30(?=(?:-01|-03|-05|-07|-08|-10|-12)))|(?:0[1-9]|[12][0-9]|29(?<=-02(?<=-(?:0[1-9]|1[0-9]|2[0-9]|3[0-1]|3[1-9]|2[0-2]|25)))))$";

            case DESCRIPTION -> "^([\\w\\s.,!?()-]+)$";

            case FEE -> "^(\\d+(\\.\\d{1,8})?)$";

        };

        Pattern pattern = Pattern.compile(field);

        if (text != null) {
            if (text.trim().isEmpty()) {
                return false;
            }
        } else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }

    public static boolean setTextColor(TextField location, JFXTextField field) {
        if (Regex.isTextFiledValid(location, field.getText())) {
            field.setFocusColor(Paint.valueOf("Green"));
            field.setUnFocusColor(Paint.valueOf("Green"));
            return true;
        } else {
            field.setFocusColor(Paint.valueOf("Red"));
            field.setUnFocusColor(Paint.valueOf("Red"));
            return false;
        }
    }

    public static boolean setTextColor(TextField location, JFXPasswordField field) {
        if (Regex.isTextFiledValid(location, field.getText())) {
            field.setFocusColor(Paint.valueOf("Green"));
            field.setUnFocusColor(Paint.valueOf("Green"));
            return true;
        } else {
            field.setFocusColor(Paint.valueOf("Red"));
            field.setUnFocusColor(Paint.valueOf("Red"));
            return false;
        }
    }

    public static boolean setTextColor(TextField location, MFXTextField field) {
        if (Regex.isTextFiledValid(location, field.getText())) {
            field.getStyleClass().removeAll("invalid-field");
            field.getStyleClass().add("valid-field");
            return true;
        } else {
            // Apply invalid field CSS class
            field.getStyleClass().removeAll("valid-field");
            field.getStyleClass().add("invalid-field");
            return false;
        }
    }

    public static boolean setTextColor(TextField location, MFXPasswordField field) {

        if (Regex.isTextFiledValid(location, field.getText())) {
            field.getStyleClass().removeAll("invalid-field");
            field.getStyleClass().add("valid-field");
            return true;
        } else {
            field.getStyleClass().removeAll("valid-field");
            field.getStyleClass().add("invalid-field");
            return false;
        }
    }
}
