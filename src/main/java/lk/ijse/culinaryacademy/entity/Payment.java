
package lk.ijse.culinaryacademy.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @Column(name = "payment_id" , length = 100)
    private String paymentId;

    @Column(name = "student_id" , length = 100)
    private String studentId;

    @Column(name = "course_id" , length = 100)
    private String courseId;

    @Column(name = "payment_date" , length = 100)
    private LocalDateTime paymentDate;

    @Column(name = "fee" , length = 100)
    private double fee;

    @Column(name = "status" , length = 100)
    private String status;

    // Many-to-One relationship with Student
    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student student;

    // Many-to-One relationship with Course
    @ManyToOne
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;

}
