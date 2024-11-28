package lk.ijse.culinaryacademy.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import lk.ijse.culinaryacademy.bo.BOFactory;
import lk.ijse.culinaryacademy.bo.custom.CourseBO;
import lk.ijse.culinaryacademy.bo.custom.PaymentBO;
import lk.ijse.culinaryacademy.bo.custom.StudentBO;
import lk.ijse.culinaryacademy.dto.PaymentDTO;
import lk.ijse.culinaryacademy.util.Regex;
import lk.ijse.culinaryacademy.util.TextField;
import lk.ijse.culinaryacademy.view.tdm.PaymentTm;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class PaymentsFormController {

    @FXML
    private JFXComboBox<String> cmbCourseId;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private JFXComboBox<String> cmbStudentId;

    @FXML
    private TableColumn<?, ?> colCourseId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private JFXTextField txtFee;

    @FXML
    private JFXTextField txtPaymentDate;

    @FXML
    private JFXTextField txtPaymentId;

    @FXML
    private JFXTextField txtSearch;

    private List<PaymentDTO> paymentList = new ArrayList<>();

    // Objects
    PaymentBO paymentBO = (PaymentBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PAYMENT);
    StudentBO studentBO = (StudentBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.STUDENT);
    CourseBO courseBO = (CourseBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.COURSE);


    // ------------------------------------ INITIALIZATION ------------------------------------
    @FXML
    void initialize() throws Exception {
        loadNextPaymentId();
        setPaymentDate();
        loadStudentIds();
        loadCourseIds();
        loadStatus();
        this.paymentList = getAllPayments();
        loadPaymentTable();
        setCellValueFactory();
    }

    // ------------------------------------ CRUD OPERATIONS ------------------------------------
    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String paymentId = txtPaymentId.getText();
        String studentId = cmbStudentId.getValue();
        String courseId = cmbCourseId.getValue();
        String feeText = txtFee.getText();
        String status = cmbStatus.getValue();


        LocalDateTime paymentDate = LocalDateTime.now();
        double fee = 0;


        try {
            fee = Double.parseDouble(feeText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Fee Amount. Please enter a valid number.").show();
            return;
        }

        if (courseId == null || status == null) {
            new Alert(Alert.AlertType.ERROR, "Please select both Course and Payment status.").show();
            return;
        }


        PaymentDTO paymentDTO = new PaymentDTO(paymentId, studentId, courseId, paymentDate, fee, status);


        String errorMessage = isValid();
        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }


        try {
            boolean isAdded = paymentBO.addPayment(paymentDTO);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Added Successfully.").show();
                clearField();
                loadNextPaymentId();
                setPaymentDate();
                refreshTable();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String paymentId = txtPaymentId.getText();
        String studentId = cmbStudentId.getValue();
        String courseId = cmbCourseId.getValue();
        String paymentDateText = txtPaymentDate.getText();
        String feeText = txtFee.getText();
        String status = cmbStatus.getValue();

        LocalDateTime paymentDate;
        double fee;

        try {
            paymentDate = LocalDateTime.parse((paymentDateText));
            fee = Double.parseDouble(feeText);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
            return;
        }

        PaymentDTO paymentDTO = new PaymentDTO(paymentId, studentId, courseId, paymentDate, fee, status);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isAdded = paymentBO.updatePayment(paymentDTO);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Updated Successfully.").show();
                clearField();
                loadNextPaymentId();
                setPaymentDate();
                refreshTable();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String paymentId = txtPaymentId.getText();

        try {
            boolean isDeleted = paymentBO.deletePayment(paymentId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Deleted Successfully.").show();
                clearField();
                loadNextPaymentId();
                setPaymentDate();
                refreshTable();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws Exception {
        clearField();
    }

    private List<PaymentDTO> getAllPayments() throws Exception {
        List<PaymentDTO> paymentList = null;
        try {
            paymentList = paymentBO.getAllPayments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paymentList;
    }


    // ------------------------------------ OTHER OPERATIONS ------------------------------------
    private void clearField() throws Exception {
        txtPaymentId.clear();
        cmbStudentId.getSelectionModel().clearSelection();
        cmbCourseId.getSelectionModel().clearSelection();
        txtPaymentDate.clear();
        txtFee.clear();
        cmbStatus.getSelectionModel().clearSelection();

        loadNextPaymentId();
        setPaymentDate();
    }

    private void refreshTable() throws Exception {
        this.paymentList = getAllPayments();
        loadPaymentTable();
    }

    @FXML
    private void txtSearchOnAction(ActionEvent event) {
        String paymentId = txtSearch.getText();

        try {
            PaymentDTO dto = paymentBO.searchByPaymentId(paymentId);

            if (dto != null) {

                txtPaymentId.setText(dto.getPaymentId());
                cmbStudentId.setValue(dto.getStudentId());
                cmbCourseId.setValue(dto.getCourseId());
                txtPaymentDate.setText(dto.getPaymentDate().toString());
                txtFee.setText(String.valueOf(dto.getFee()));
                cmbStatus.setValue(dto.getStatus());
                txtSearch.clear();
            } else {
                showAlert("Search Result", "Payment Not Found",
                        "No Payment with ID \"" + paymentId + "\" was found.");
            }
        } catch (Exception e) {
            showAlert("Error", "Database Error",
                    "An error occurred while searching for the payment. " +
                            "Please try again later.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void loadNextPaymentId() throws Exception {
        try {
            String currentId = paymentBO.currentPaymentId();
            String nextId = nextId(currentId);

            txtPaymentId.setText(nextId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("P");
            int id = Integer.parseInt(split[1]);
            return "P" + String.format("%03d", ++id);
        }
        return "P001";
    }

    private void setPaymentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            txtPaymentDate.setText(LocalDateTime.now().format(formatter));
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadPaymentTable() {
        ObservableList<PaymentTm> tmList = FXCollections.observableArrayList();

        for (PaymentDTO payment : paymentList) {
            PaymentTm paymentTm = new PaymentTm(
                    payment.getPaymentId(),
                    payment.getStudentId(),
                    payment.getCourseId(),
                    payment.getPaymentDate(),
                    payment.getFee(),
                    payment.getStatus()
            );

            tmList.add(paymentTm);
        }
        tblPayment.setItems(tmList);
        tblPayment.getSelectionModel().getSelectedItem();
    }

    private void loadStudentIds() throws Exception {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = studentBO.getStudentIds();
            for (String id : idList) {
                obList.add(id);
            }
            cmbStudentId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCourseIds() throws Exception {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = courseBO.getCourseIds();
            for (String id : idList) {
                obList.add(id);
            }
            cmbCourseId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadStatus() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Upfront Payment");
        obList.add("Full Payment");
        cmbStatus.setItems(obList);
    }

    @FXML
    private void cmbStatusOnAction(ActionEvent event) {

        if (cmbStatus.getValue() != null && cmbCourseId.getValue() != null) {
            setPaymentFee();
        } else {
            txtFee.clear();
        }
    }

    private static final String INVALID_UPFRONT_MESSAGE = "Invalid upfront payment amount. Please enter a valid number.";
    private static final String UPFRONT_TOO_LOW_MESSAGE = "Upfront payment must be %.2f or more.";
    private static final String UPFRONT_TOO_HIGH_MESSAGE = "Upfront payment exceeds total fee. Please check.";
    private static final String EMPTY_UPFRONT_MESSAGE = "Please enter an upfront payment amount.";
    private static final String ERROR_COURSE_FEE_MESSAGE = "Error retrieving course fee: ";

    private void setPaymentFee() {
        String paymentStatus = cmbStatus.getValue();
        String courseId = cmbCourseId.getValue();


        if (paymentStatus == null || paymentStatus.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment status.").show();
            return;
        }
        if (courseId == null || courseId.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a course.").show();
            return;
        }

        try {
            double fee = courseBO.searchByCourseId(courseId).getFee();


            if (paymentStatus.equals("Full Payment")) {
                txtFee.setText(String.valueOf(fee));
            } else {
                String upfrontFeeText = txtFee.getText().trim();


                if (upfrontFeeText.isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, EMPTY_UPFRONT_MESSAGE).show();
                    return;
                }

                try {
                    double upfrontFee = Double.parseDouble(upfrontFeeText);


                    if (upfrontFee < fee / 10) {
                        new Alert(Alert.AlertType.ERROR, String.format(UPFRONT_TOO_LOW_MESSAGE, fee / 10)).show();
                    } else if (upfrontFee < 0) {
                        new Alert(Alert.AlertType.ERROR, INVALID_UPFRONT_MESSAGE).show();
                    } else if (upfrontFee <= fee) {

                        txtFee.setText(String.valueOf(fee - upfrontFee));
                    } else {
                        new Alert(Alert.AlertType.ERROR, UPFRONT_TOO_HIGH_MESSAGE).show();
                    }
                } catch (NumberFormatException e) {
                    new Alert(Alert.AlertType.ERROR, INVALID_UPFRONT_MESSAGE).show();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, ERROR_COURSE_FEE_MESSAGE + e.getMessage()).show();
        }
    }



    // ------------------------------------ ON KEY RELEASED ------------------------------------
    @FXML
    void txtPaymentIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.PAYMENTID, txtPaymentId);
    }

    @FXML
    void txtFeeOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.FEE, txtFee);
    }

    // ------------------------------------ VALIDATION ------------------------------------
    private String isValid() {
        String message = "";

        if (!Regex.setTextColor(TextField.PAYMENTID, txtPaymentId))
            message += "Payment ID must be starts with 'P' and exactly three digits.\n\n";

        if (!Regex.setTextColor(TextField.FEE, txtFee))
            message += "Fee must be a positive number.\n\n";

        return message.isEmpty() ? null : message;
    }
}
