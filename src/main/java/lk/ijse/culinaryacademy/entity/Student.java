package lk.ijse.culinaryacademy.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "student_id" , length = 100)
    private String studentId;

    @Column(name = "name" , length = 100)
    private String name;

    @Column(name = "email" , length = 100)
    private String email;

    @Column(name = "contact" , length = 100)
    private String contact;

    @Column(name = "address" , length = 100)
    private String address;

    @Column(name = "enrolled_date" , length = 100)
    private Date enrolledDate;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrolment> enrollment;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payment;
}