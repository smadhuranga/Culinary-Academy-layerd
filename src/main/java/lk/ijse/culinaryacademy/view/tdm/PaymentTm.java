package lk.ijse.culinaryacademy.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaymentTm {

    private String paymentId;
    private String studentId;
    private String courseId;
    private LocalDateTime paymentDate;
    private double fee;
    private String status;

}
