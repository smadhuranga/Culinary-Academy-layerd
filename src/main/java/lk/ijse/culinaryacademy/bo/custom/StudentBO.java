package lk.ijse.culinaryacademy.bo.custom;

import lk.ijse.culinaryacademy.bo.SuperBO;
import lk.ijse.culinaryacademy.dto.StudentDTO;

import java.util.List;

public interface StudentBO extends SuperBO {

    boolean addStudent(StudentDTO dto) throws Exception;

    boolean updateStudent(StudentDTO dto) throws Exception;

    boolean deleteStudent(String id) throws Exception;

    String currentStudentId() throws Exception;

    List<StudentDTO> getAllStudents() throws Exception;

    StudentDTO searchByStudentId(String studentId) throws Exception;

    List<String> getStudentIds() throws Exception;

    int getStudentCount() throws Exception;
}
