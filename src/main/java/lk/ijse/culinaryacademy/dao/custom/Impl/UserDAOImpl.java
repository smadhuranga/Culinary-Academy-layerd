package lk.ijse.culinaryacademy.dao.custom.Impl;

import lk.ijse.culinaryacademy.config.SessionFactoryConfig;
import lk.ijse.culinaryacademy.dao.custom.UserDAO;
import lk.ijse.culinaryacademy.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean add(User user) throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(User user) throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(user);
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
    public boolean changeUsername(String currentUsername, String newUsername, String confirmUsername) throws Exception {
        if (!newUsername.equals(confirmUsername)) {
            throw new IllegalArgumentException("Username Mismatch.");
        }

        if (newUsername == null || newUsername.isEmpty()) {
            throw new IllegalArgumentException("New username cannot be empty.");
        }

        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                String hql = "UPDATE User u SET u.username = :newUsername WHERE u.username = :currentUsername";
                Query query = session.createQuery(hql);
                query.setParameter("newUsername", newUsername);
                query.setParameter("currentUsername", currentUsername);

                int result = query.executeUpdate();

                if (result > 0) {
                    transaction.commit();
                    return true;
                } else {
                    transaction.rollback();
                    throw new IllegalArgumentException("Current Username does not exist.");
                }
            } catch (HibernateException e) {
                e.printStackTrace();
                transaction.rollback();
                throw new Exception("Error updating Username", e);
            }
        }
    }

    @Override
    public User getUserByUsername(String username) throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }

    @Override
    public boolean changePassword(String username, String newPassword) throws Exception {
        // Hash the new password before saving
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                String hql = "UPDATE User u SET u.password = :newPassword WHERE u.username = :username";
                Query query = session.createQuery(hql);
                query.setParameter("newPassword", hashedNewPassword);
                query.setParameter("username", username);

                int result = query.executeUpdate();

                if (result > 0) {
                    transaction.commit();
                    return true;
                } else {
                    transaction.rollback();
                    throw new IllegalArgumentException("Failed to update password.");
                }
            } catch (HibernateException e) {
                e.printStackTrace();
                transaction.rollback();
                throw new Exception("Error updating password", e);
            }
        }
    }

    @Override
    public User checkLogin(String username) throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }

    @Override
    public boolean checkRegister(String username, String name, String email, String hashedPassword, String role) throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                // Check if an admin already exists in the database
                if ("Admin".equalsIgnoreCase(role)) {
                    String hql = "SELECT COUNT(*) FROM User WHERE role = :adminRole";
                    Long adminCount = (Long) session.createQuery(hql)
                            .setParameter("adminRole", "admin")
                            .uniqueResult();    // Use Long data type to avoid NullPointerException in Hibernate

                    if (adminCount != null && adminCount > 0) {
                        throw new Exception("An admin user already exists. Only one admin can be registered.");
                    }
                }

                // Proceed with user registration
                User user = new User();
                user.setUsername(username);
                user.setName(name);
                user.setEmail(email);
                user.setPassword(hashedPassword);
                user.setRole(role);

                session.save(user);
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
                throw new Exception("Error occurred while registering the user: " + e.getMessage(), e);
            }
        }
    }


    @Override
    public User searchByUsername(String username) throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT u FROM User u WHERE u.username = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getAll() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        try(Session session = SessionFactoryConfig.getInstance().getSession()){
            users = (ArrayList<User>) session.createQuery("FROM User").list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<String> getIds() throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT u.username FROM User u ORDER BY u.username";
            Query<String> query = session.createQuery(hql, String.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User searchById(String id) throws Exception {
        return null;
    }

    @Override
    public String currentId() throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT u.username FROM User u ORDER BY u.username DESC";
            Query<String> query = session.createQuery(hql, String.class);
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
