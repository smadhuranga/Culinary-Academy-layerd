package lk.ijse.culinaryacademy.bo.custom.impl;

import lk.ijse.culinaryacademy.bo.custom.StudentBO;
import lk.ijse.culinaryacademy.dao.DAOFactory;
import lk.ijse.culinaryacademy.dao.custom.StudentDAO;
import lk.ijse.culinaryacademy.dto.StudentDTO;
import lk.ijse.culinaryacademy.entity.Enrolment;
import lk.ijse.culinaryacademy.entity.Payment;
import lk.ijse.culinaryacademy.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.STUDENT);

    private List<Enrolment> enrolment;
    private List<Payment> payment;

    @Override
    public boolean addStudent(StudentDTO dto) throws Exception {
        return studentDAO.add(new Student(
                dto.getStudentId(),
                dto.getName(),
                dto.getEmail(),
                dto.getContact(),
                dto.getAddress(),
                dto.getEnrolledDate(),
                null,
                null
        ));
    }

    @Override
    public boolean updateStudent(StudentDTO dto) throws Exception {
        return studentDAO.update(new Student(
                dto.getStudentId(),
                dto.getName(),
                dto.getEmail(),
                dto.getContact(),
                dto.getAddress(),
                dto.getEnrolledDate(),
                null,
                null
        ));
    }

    @Override
    public boolean deleteStudent(String studentId) throws Exception {
        return studentDAO.delete(studentId);
    }

    @Override
    public String currentStudentId() throws Exception {
        return studentDAO.currentId();
    }

    @Override
    public List<StudentDTO> getAllStudents() throws Exception {
        List<StudentDTO> allStudents = new ArrayList<>();
        List<Student> all = studentDAO.getAll();

        for (Student s : all) {
            allStudents.add(new StudentDTO(s.getStudentId(),
                    s.getName(),
                    s.getEmail(),
                    s.getContact(),
                    s.getAddress(),
                    s.getEnrolledDate(),
                    null,
                    null
            ));
        }
        return allStudents;
    }

    @Override
    public StudentDTO searchByStudentId(String studentId) throws Exception {
        Student s = studentDAO.searchById(studentId);
        return new StudentDTO(
                s.getStudentId(),
                s.getName(),
                s.getEmail(),
                s.getContact(),
                s.getAddress(),
                s.getEnrolledDate(),
                null,
                null
        );
    }

    @Override
    public List<String> getStudentIds() throws Exception {
        return studentDAO.getIds();
    }

    @Override
    public int getStudentCount() throws Exception {
        return studentDAO.getCount();
    }
}
