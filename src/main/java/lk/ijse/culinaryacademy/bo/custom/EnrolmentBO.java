package lk.ijse.culinaryacademy.bo.custom;

import lk.ijse.culinaryacademy.bo.SuperBO;
import lk.ijse.culinaryacademy.dto.EnrolmentDTO;

import java.util.List;

public interface EnrolmentBO extends SuperBO {

    boolean addEnrolment(EnrolmentDTO dto) throws Exception;

    boolean updateEnrolment(EnrolmentDTO dto) throws Exception;

    boolean deleteEnrolment(String enrolmentId) throws Exception;

    EnrolmentDTO searchByEnrolmentId(String enrolmentId) throws Exception;

    String currentEnrolmentId() throws Exception;

    List<EnrolmentDTO> getAllEnrolments() throws Exception;
}
