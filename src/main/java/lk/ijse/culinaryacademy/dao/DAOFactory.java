package lk.ijse.culinaryacademy.dao;

import lk.ijse.culinaryacademy.dao.custom.Impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDAOFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        COURSE, CREDENTIAL, PAYMENT, STUDENT, ENROLMENT, USER
    }

    //Object Creation Logic

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case COURSE:
                return new CourseDAOImpl();

            case PAYMENT:
                return new PaymentDAOImpl();

            case STUDENT:
                return new StudentDAOImpl();

            case ENROLMENT:
                return new EnrolmentDAOImpl();

            case USER:
                return new UserDAOImpl();

            default:
                return null;
        }
    }
}
