package lk.ijse.culinaryacademy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaymentDTO {

    private String paymentId;
    private String studentId;
    private String courseId;
    private LocalDateTime paymentDate;
    private double fee;
    private String status;
}
