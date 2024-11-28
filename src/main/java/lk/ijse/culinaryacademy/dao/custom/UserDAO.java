package lk.ijse.culinaryacademy.dao.custom;

import lk.ijse.culinaryacademy.dao.CrudDAO;
import lk.ijse.culinaryacademy.entity.User;

public interface UserDAO extends CrudDAO<User> {

    boolean changeUsername(String currentUsername, String newUsername, String confirmUsername) throws Exception;

    User getUserByUsername(String username) throws Exception;

    boolean changePassword(String username, String newPassword) throws Exception;

    User checkLogin(String username) throws Exception;

    boolean checkRegister(String username, String name, String email, String password, String role) throws Exception;

    User searchByUsername(String name) throws Exception;
}
