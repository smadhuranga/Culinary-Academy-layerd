package lk.ijse.culinaryacademy.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.culinaryacademy.bo.BOFactory;
import lk.ijse.culinaryacademy.bo.custom.CourseBO;
import lk.ijse.culinaryacademy.bo.custom.PaymentBO;
import lk.ijse.culinaryacademy.bo.custom.StudentBO;
import lk.ijse.culinaryacademy.bo.custom.impl.UserBOImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardFormController {

    @FXML
    private Text lblPaymentsTotal;

    @FXML
    private Label lblCoursesCount;

    @FXML
    private Label lblStudentsCount;

    @FXML
    private Text txtDate;

    @FXML
    private Text txtTime;

    @FXML
    private ImageView imgUser;

    @FXML
    private Label userName;

    // Objects
    StudentBO studentBO = (StudentBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.STUDENT);
    CourseBO courseBO = (CourseBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.COURSE);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PAYMENT);

    private int studentCount;
    private int courseCount;
    private double paymentsTotal;

    public void initialize() {
        updateTime();
        updateDate();

        try {
            studentCount = studentBO.getStudentCount();
            courseCount = courseBO.getCourseCount();
            paymentsTotal = paymentBO.getTotalPayments();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setStudentsCount(studentCount);
        setCoursesCount(courseCount);
        setPayments(paymentsTotal);
        setUserName();
    }

    private void updateTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            txtTime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        txtDate.setText(formattedDate);
    }


    // ---------------------------- COUNT FUNCTIONS ----------------------------
    public void setPayments(double total) {
        lblPaymentsTotal.setText(String.valueOf(total));
    }

    public void setCoursesCount(int count) {
        lblCoursesCount.setText(String.valueOf(count));
    }

    public void setStudentsCount(int count) {
        lblStudentsCount.setText(String.valueOf(count));
    }


    // ---------------------------- USER FUNCTIONS ----------------------------
    public void setUserName() {
        userName.setText(UserBOImpl.userName);
    }

}
