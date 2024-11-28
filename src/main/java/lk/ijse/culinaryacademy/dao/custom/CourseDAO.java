package lk.ijse.culinaryacademy.dao.custom;


import lk.ijse.culinaryacademy.dao.CrudDAO;
import lk.ijse.culinaryacademy.dto.CourseDTO;
import lk.ijse.culinaryacademy.entity.Course;

import java.util.List;

public interface CourseDAO extends CrudDAO<Course> {

    Course searchByName(String courseId) throws Exception;

    int getCount() throws Exception;
}
