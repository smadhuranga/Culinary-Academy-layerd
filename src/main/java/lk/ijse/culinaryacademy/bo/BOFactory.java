package lk.ijse.culinaryacademy.bo;

import lk.ijse.culinaryacademy.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    private  BOFactory() {
    }

    public static BOFactory getBOFactory() { return (boFactory == null) ? (boFactory = new BOFactory()) : (boFactory); }

    public enum BOTypes{
        COURSE, PAYMENT, STUDENT, ENROLMENT , USER
    }

    public SuperBO getBO(BOTypes types) {
        switch (types) {
            case COURSE:
                return new CourseBOImpl();

            case PAYMENT:
                return new PaymentBOImpl();

            case STUDENT:
                return new StudentBOImpl();

            case ENROLMENT:
                return new EnrolmentBOImpl();

            case USER:
                return new UserBOImpl();

            default:
                return null;
        }
    }
}
