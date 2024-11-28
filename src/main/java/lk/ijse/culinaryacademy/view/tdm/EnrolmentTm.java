package lk.ijse.culinaryacademy.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EnrolmentTm {

    private String enrolmentId;
    private String studentId;
    private String studentName;
    private String courseId;
    private String courseName;
    private Date enrolledDate;

}
