package lk.ijse.culinaryacademy.bo.custom;

import lk.ijse.culinaryacademy.bo.SuperBO;
import lk.ijse.culinaryacademy.dto.PaymentDTO;

import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean addPayment(PaymentDTO paymentDTO) throws Exception;

    boolean updatePayment(PaymentDTO paymentDTO) throws Exception;

    boolean deletePayment(String paymentId) throws Exception;

    List<PaymentDTO> getAllPayments() throws Exception;

    String currentPaymentId() throws Exception;

    PaymentDTO searchByPaymentId(String paymentId) throws Exception;

    double getTotalPayments() throws Exception;
}
