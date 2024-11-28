package lk.ijse.culinaryacademy.dao.custom;


import lk.ijse.culinaryacademy.dao.CrudDAO;
import lk.ijse.culinaryacademy.entity.Student;

public interface StudentDAO extends CrudDAO<Student> {

    int getCount() throws Exception;
}
