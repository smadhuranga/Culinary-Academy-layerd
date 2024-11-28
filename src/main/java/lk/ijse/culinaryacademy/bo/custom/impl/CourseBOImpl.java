package lk.ijse.culinaryacademy.bo.custom.impl;

import lk.ijse.culinaryacademy.bo.custom.CourseBO;
import lk.ijse.culinaryacademy.dao.DAOFactory;
import lk.ijse.culinaryacademy.dao.custom.CourseDAO;
import lk.ijse.culinaryacademy.dto.CourseDTO;
import lk.ijse.culinaryacademy.dto.StudentDTO;
import lk.ijse.culinaryacademy.entity.Course;
import lk.ijse.culinaryacademy.entity.Enrolment;
import lk.ijse.culinaryacademy.entity.Payment;
import lk.ijse.culinaryacademy.entity.Student;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class CourseBOImpl implements CourseBO {

    CourseDAO courseDAO = (CourseDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.COURSE);

    private List<Enrolment> enrolment;

    private List<Payment> payment;

    @Override
    public boolean addCourse(CourseDTO dto) throws Exception {
        return courseDAO.add(new Course(
                dto.getCourseId(),
                dto.getCourseName(),
                dto.getDescription(),
                dto.getDuration(),
                dto.getFee(),
                dto.getCourseLevel(),
                enrolment,
                payment
        ));
    }

    @Override
    public boolean updateCourse(CourseDTO dto) throws Exception {
        return courseDAO.update(new Course(
                dto.getCourseId(),
                dto.getCourseName(),
                dto.getDescription(),
                dto.getDuration(),
                dto.getFee(),
                dto.getCourseLevel(),
                enrolment,
                payment
        ));
    }

    @Override
    public boolean deleteCourse(String id) throws Exception {
        return courseDAO.delete(id);
    }

    @Override
    public String currentCourseId() throws Exception {
        return courseDAO.currentId();
    }

    @Override
    public List<CourseDTO> getAllCourses() throws Exception {
        List<CourseDTO> allCourses = new ArrayList<>();
        List<Course> all = courseDAO.getAll();

        for (Course c : all) {
            allCourses.add(new CourseDTO(
                    c.getCourseId(),
                    c.getCourseName(),
                    c.getDescription(),
                    c.getDuration(),
                    c.getFee(),
                    c.getCourseLevel()
            )
            );
        }
        return allCourses;
    }

    @Override
    public CourseDTO searchByCourseId(String courseId) throws Exception {
        Course c = courseDAO.searchById(courseId);

        if (c == null) {
            return null;
        }

        return new CourseDTO(
                c.getCourseId(),
                c.getCourseName(),
                c.getDescription(),
                c.getDuration(),
                c.getFee(),
                c.getCourseLevel()
        );
    }

    @Override
    public List<String> getCourseIds() throws Exception {
        return courseDAO.getIds();
    }

    @Override
    public int getCourseCount() throws Exception {
        return courseDAO.getCount();
    }
}
