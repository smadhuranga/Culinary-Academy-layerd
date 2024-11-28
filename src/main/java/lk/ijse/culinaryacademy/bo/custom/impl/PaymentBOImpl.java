package lk.ijse.culinaryacademy.bo.custom.impl;

import javafx.scene.chart.LineChart;
import lk.ijse.culinaryacademy.bo.custom.PaymentBO;
import lk.ijse.culinaryacademy.dao.DAOFactory;
import lk.ijse.culinaryacademy.dao.custom.PaymentDAO;
import lk.ijse.culinaryacademy.dto.PaymentDTO;
import lk.ijse.culinaryacademy.entity.Course;
import lk.ijse.culinaryacademy.entity.Payment;
import lk.ijse.culinaryacademy.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    private Student student;
    private Course course;


    @Override
    public boolean addPayment(PaymentDTO dto) throws Exception {
        return paymentDAO.add(new Payment(
                dto.getPaymentId(),
                dto.getStudentId(),
                dto.getCourseId(),
                dto.getPaymentDate(),
                dto.getFee(),
                dto.getStatus(),
                student,
                course
        ));
    }

    @Override
    public boolean updatePayment(PaymentDTO paymentDTO) throws Exception {
        return paymentDAO.update(new Payment(
                paymentDTO.getPaymentId(),
                paymentDTO.getStudentId(),
                paymentDTO.getCourseId(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getFee(),
                paymentDTO.getStatus(),
                student,
                course
        ));
    }

    @Override
    public boolean deletePayment(String paymentId) throws Exception {
        return paymentDAO.delete(paymentId);
    }

    @Override
    public List<PaymentDTO> getAllPayments() throws Exception {
        List<PaymentDTO> allPayments = new ArrayList<>();
        List<Payment> all = paymentDAO.getAll();

        for (Payment s : all) {
            allPayments.add(new PaymentDTO(
                    s.getPaymentId(),
                    s.getStudentId(),
                    s.getCourseId(),
                    s.getPaymentDate(),
                    s.getFee(),
                    s.getStatus())
            );
        }
        return allPayments;
    }

    @Override
    public String currentPaymentId() throws Exception {
        return paymentDAO.currentId();
    }

    @Override
    public PaymentDTO searchByPaymentId(String paymentId) throws Exception {
        Payment payment = paymentDAO.searchById(paymentId);

        if (payment == null) {
            return null;
        }

        return new PaymentDTO(
                payment.getPaymentId(),
                payment.getStudentId(),
                payment.getCourseId(),
                payment.getPaymentDate(),
                payment.getFee(),
                payment.getStatus()
        );
    }

    @Override
    public double getTotalPayments() throws Exception {
        return paymentDAO.getTotalPayments();
    }

    @Override
    public void monthlyFeeChart(LineChart<String, Double> paymentLineChart) throws Exception {
        paymentDAO.monthlyFeeChart(paymentLineChart);
    }
}
