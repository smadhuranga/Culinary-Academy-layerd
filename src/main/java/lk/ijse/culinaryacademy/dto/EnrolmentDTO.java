package lk.ijse.culinaryacademy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EnrolmentDTO {

    private String enrolmentId;
    private String studentId;
    private String studentName;
    private String courseId;
    private String courseName;
    private Date enrolledDate;

    public EnrolmentDTO(String enrolmentId, String studentId, String courseId, Date enrolledDate) {
        this.enrolmentId = enrolmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrolledDate = enrolledDate;

    }
}
