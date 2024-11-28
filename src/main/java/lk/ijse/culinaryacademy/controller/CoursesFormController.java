package lk.ijse.culinaryacademy.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.culinaryacademy.bo.BOFactory;
import lk.ijse.culinaryacademy.bo.custom.CourseBO;
import lk.ijse.culinaryacademy.bo.custom.UserBO;
import lk.ijse.culinaryacademy.dto.CourseDTO;
import lk.ijse.culinaryacademy.util.Regex;
import lk.ijse.culinaryacademy.util.TextField;
import lk.ijse.culinaryacademy.view.tdm.CourseTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursesFormController {

    @FXML
    private JFXComboBox<String> cmbCourseLevel;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colCourseLevel;

    @FXML
    private TableView<CourseTm> tblCourse;

    @FXML
    private JFXTextField txtCourseId;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtFee;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtDuration;

    @FXML
    private JFXTextField txtSearch;

    private List<CourseDTO> courseList = new ArrayList<>();

    // Objects
    CourseBO courseBO = (CourseBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.COURSE);
    UserBO userBO = (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void initialize() throws Exception {
        loadNextCourseId();
        loadCourseLevels();
        this.courseList = getAllCourses();
        loadCourseTable();
        setCellValueFactory();
    }

    //    ------------------------------------ CRUD OPERATIONS ------------------------------------
    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        String courseId = txtCourseId.getText();
        String courseName = txtName.getText();
        String description = txtDescription.getText();
        String durationText = txtDuration.getText();
        String feeText = txtFee.getText();
        String level = cmbCourseLevel.getValue();

        int duration;
        double fee;

        try {
            duration = Integer.parseInt(durationText);
            fee = Double.parseDouble(feeText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Invalid Fields").show();
            return;
        }

        CourseDTO dto = new CourseDTO(courseId, courseName, description, duration, fee, level);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isAdded = courseBO.addCourse(dto);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Course Added Successfully.").show();
                clearField();
                refreshTable();
                loadNextCourseId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws Exception {
        String courseId = txtCourseId.getText();
        String courseName = txtName.getText();
        String description = txtDescription.getText();
        String durationText = txtDuration.getText();
        String feeText = txtFee.getText();
        String level = cmbCourseLevel.getValue();

        int duration;
        double fee;

        try {
            duration = Integer.parseInt(durationText);
            fee = Double.parseDouble(feeText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Invalid Fields").show();
            return;
        }

        CourseDTO dto = new CourseDTO(courseId, courseName, description, duration, fee, level);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isUpdated = courseBO.updateCourse(dto);

            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Course Updated Successfully.").show();
                clearField();
                refreshTable();
                loadNextCourseId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws Exception {
        String courseId = txtCourseId.getText();

        try {
            boolean isDeleted = courseBO.deleteCourse(courseId);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Course Deleted Successfully.").show();
                clearField();
                refreshTable();
                loadNextCourseId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws Exception {
        clearField();
    }

    private List<CourseDTO> getAllCourses() {
        List<CourseDTO> courseList = null;
        try {
            courseList = courseBO.getAllCourses();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return courseList;
    }


    // ------------------------------------ OTHER OPERATIONS ------------------------------------
    private void clearField() throws Exception {
        txtCourseId.clear();
        txtName.clear();
        txtDescription.clear();
        txtDuration.clear();
        txtFee.clear();
        cmbCourseLevel.getSelectionModel().clearSelection();

        loadNextCourseId();
    }

    private void refreshTable() {
        this.courseList = getAllCourses();
        loadCourseTable();
    }

    @FXML
    private void txtSearchOnAction(ActionEvent event) throws Exception {
        String courseId = txtSearch.getText();

        try {
            CourseDTO dto = courseBO.searchByCourseId(courseId);

            if (dto != null) {
                txtCourseId.setText(dto.getCourseId());
                txtName.setText(dto.getCourseName());
                txtDuration.setText(String.valueOf(dto.getDuration()));
                txtFee.setText(String.valueOf(dto.getFee()));
                txtDescription.setText(dto.getDescription());
                cmbCourseLevel.setValue(dto.getCourseLevel());

                txtSearch.clear();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Course not found.").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the course.").show();
            e.printStackTrace();
        }
    }


    private void loadNextCourseId() throws Exception {
        try {
            String currentId = courseBO.currentCourseId();
            String nextId = nextId(currentId);

            txtCourseId.setText(nextId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("C");
            int id = Integer.parseInt(split[1]);
            return "C" + String.format("%03d", ++id);
        }
        return "C001";
    }

    private void loadCourseLevels() throws Exception {
        ObservableList<String> courseLevels = FXCollections.observableArrayList("Advanced", "Expert", "Intermediate", "Beginner");
        cmbCourseLevel.setItems(courseLevels);
    }

    private void loadCourseTable() {
        ObservableList<CourseTm> tmList = FXCollections.observableArrayList();

        for (CourseDTO dto : courseList) {
            CourseTm courseTm = new CourseTm(
                    dto.getCourseId(),
                    dto.getCourseName(),
                    dto.getDescription(),
                    dto.getDuration(),
                    dto.getFee(),
                    dto.getCourseLevel()
            );

            tmList.add(courseTm);
        }

        tblCourse.setItems(tmList);
        tblCourse.getSelectionModel().getSelectedItem();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCourseLevel.setCellValueFactory(new PropertyValueFactory<>("courseLevel"));
    }


    // ------------------------------------ ON KEY RELEASED ------------------------------------
    @FXML
    void txtCourseIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.COURSEID, txtCourseId);
    }

    @FXML
    void txtCourseNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.NAME, txtName);
    }

    @FXML
    void txtDescriptionOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.DESCRIPTION, txtDescription);
    }

    @FXML
    void txtDurationOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.DURATION, txtDuration);
    }

    @FXML
    void txtFeeOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.FEE, txtFee);
    }


    // ------------------------------------ VALIDATION ------------------------------------
    public String isValid() {
        String message = "";

        if (!Regex.setTextColor(TextField.COURSEID, txtCourseId))
            message += "Course ID must be starts with 'C' and exactly three digits.\n\n";

        if (!Regex.setTextColor(TextField.NAME, txtName))
            message += "Course Name must be at least 3 letters.\n\n";

        if (!Regex.setTextColor(TextField.DESCRIPTION, txtDescription))
            message += "Description must be at least 10 letters.\n\n";

        if (!Regex.setTextColor(TextField.DURATION, txtDuration))
            message += "Duration must be a positive number.\n\n";

        if (!Regex.setTextColor(TextField.FEE, txtFee))
            message += "Fee must be a positive number.\n\n";

        return message.isEmpty() ? null : message;
    }

}
