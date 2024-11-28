package lk.ijse.culinaryacademy.dao.custom;


import lk.ijse.culinaryacademy.dao.CrudDAO;
import lk.ijse.culinaryacademy.entity.Payment;

import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {

    double getTotalPayments() throws Exception;
}