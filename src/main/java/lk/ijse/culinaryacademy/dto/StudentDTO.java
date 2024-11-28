package lk.ijse.culinaryacademy.dto;

import lk.ijse.culinaryacademy.entity.Enrolment;
import lk.ijse.culinaryacademy.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StudentDTO {

    private String studentId;
    private String name;
    private String email;
    private String contact;
    private String address;
    private Date enrolledDate;
    private Enrolment enrolment;
    private Payment payment;
}