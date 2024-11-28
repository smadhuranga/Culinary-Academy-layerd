package lk.ijse.culinaryacademy.bo.custom.impl;

import lk.ijse.culinaryacademy.bo.custom.EnrolmentBO;
import lk.ijse.culinaryacademy.dao.DAOFactory;
import lk.ijse.culinaryacademy.dao.custom.EnrolmentDAO;
import lk.ijse.culinaryacademy.dto.EnrolmentDTO;
import lk.ijse.culinaryacademy.entity.Course;
import lk.ijse.culinaryacademy.entity.Enrolment;
import lk.ijse.culinaryacademy.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class EnrolmentBOImpl implements EnrolmentBO {

    EnrolmentDAO enrolmentDAO = (EnrolmentDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ENROLMENT);
    private Student student;
    private Course course;

    @Override
    public boolean addEnrolment(EnrolmentDTO dto) throws Exception {
        return enrolmentDAO.add(new Enrolment(
                dto.getEnrolmentId(),
                dto.getStudentId(),
                dto.getCourseId(),
                dto.getEnrolledDate(),
                student,
                course
        ));
    }

    @Override
    public boolean updateEnrolment(EnrolmentDTO dto) throws Exception {
        return enrolmentDAO.update(new Enrolment(
                dto.getEnrolmentId(),
                dto.getStudentId(),
                dto.getCourseId(),
                dto.getEnrolledDate(),
                student,
                course
        ));
    }

    @Override
    public boolean deleteEnrolment(String enrolmentId) throws Exception {
        return enrolmentDAO.delete(enrolmentId);
    }

    @Override
    public EnrolmentDTO searchByEnrolmentId(String enrolmentId) throws Exception {
        Enrolment e = enrolmentDAO.searchById(enrolmentId);

        if (e == null) {
            return null;
        }

        return new EnrolmentDTO(
                e.getEnrolmentId(),
                e.getStudent().getStudentId(),
                e.getStudent().getName(),
                e.getCourse().getCourseId(),
                e.getCourse().getCourseName(),
                e.getEnrolledDate()
        );
    }

    @Override
    public String currentEnrolmentId() throws Exception {
        return enrolmentDAO.currentId();
    }

    @Override
    public List<EnrolmentDTO> getAllEnrolments() {
        List<EnrolmentDTO> enrolmentsList = new ArrayList<>();
        try {
            List<Enrolment> enrolments = enrolmentDAO.getAll();
            for (Enrolment e : enrolments) {
                enrolmentsList.add(new EnrolmentDTO(
                        e.getEnrolmentId(),
                        e.getStudent().getStudentId(),
                        e.getStudent().getName(),
                        e.getCourse().getCourseId(),
                        e.getCourse().getCourseName(),
                        e.getEnrolledDate()
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return enrolmentsList;
    }
}
