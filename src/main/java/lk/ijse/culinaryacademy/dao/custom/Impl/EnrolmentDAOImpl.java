package lk.ijse.culinaryacademy.dao.custom.Impl;

import lk.ijse.culinaryacademy.config.SessionFactoryConfig;
import lk.ijse.culinaryacademy.dao.custom.EnrolmentDAO;
import lk.ijse.culinaryacademy.entity.Enrolment;
import lk.ijse.culinaryacademy.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class EnrolmentDAOImpl implements EnrolmentDAO {
    @Override
    public boolean add(Enrolment enrolment) throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(enrolment);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Enrolment enrolment) throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(enrolment);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(searchById(id));
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String currentId() throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT e.enrolmentId FROM Enrolment e ORDER BY e.enrolmentId DESC";
            Query<String> query = session.createQuery(hql, String.class);
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Enrolment searchById(String enrolmentId) throws Exception {
        try(Session session = SessionFactoryConfig.getInstance().getSession()){
            System.out.println(session.get(Student.class, enrolmentId));
            return session.get(Enrolment.class, enrolmentId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Enrolment> getAll() throws Exception {
        List<Enrolment> enrolments = new ArrayList<>();
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            // Using a JOIN FETCH to load related entities
            enrolments = session.createQuery(
                    "SELECT e FROM Enrolment e " +
                            "JOIN FETCH e.student s " +
                            "JOIN FETCH e.course c " +
                            "ORDER BY e.enrolmentId", Enrolment.class
            ).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enrolments;
    }
    @Override
    public List<String> getIds() throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT e.enrolmentId FROM Enrolment e ORDER BY e.enrolmentId";
            Query<String> query = session.createQuery(hql, String.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
