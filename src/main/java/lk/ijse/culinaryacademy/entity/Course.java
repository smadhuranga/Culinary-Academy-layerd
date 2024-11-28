package lk.ijse.culinaryacademy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "course_id" , length = 100)
    private String courseId;

    @Column(name = "course_name" , length = 100)
    private String courseName;

    @Column(name = "description" , length = 100)
    private String description;

    @Column(name = "duration" , length = 100)
    private int duration;

    @Column(name = "fee" , length = 100)
    private double fee;

    @Column(name = "course_level" , length = 100)
    private String courseLevel;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrolment> enrolment;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payment;
}