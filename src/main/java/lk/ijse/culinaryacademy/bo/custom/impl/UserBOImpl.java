package lk.ijse.culinaryacademy.bo.custom.impl;

import lk.ijse.culinaryacademy.bo.custom.UserBO;
import lk.ijse.culinaryacademy.dao.DAOFactory;
import lk.ijse.culinaryacademy.dao.custom.UserDAO;
import lk.ijse.culinaryacademy.dto.UserDTO;
import lk.ijse.culinaryacademy.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    public static String userName;
    public static String role;

    UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean addUser(UserDTO dto) throws Exception {
        return userDAO.add(new User(
                dto.getUsername(),
                dto.getName(),
                dto.getEmail(),
                dto.getRole(),
                dto.getPassword())
        );
    }

    public boolean updateUser(UserDTO dto) throws Exception {
        return userDAO.update(new User(
                dto.getUsername(),
                dto.getName(),
                dto.getEmail(),
                dto.getRole(),
                dto.getPassword())
        );
    }

    @Override
    public boolean deleteUser(String id) throws Exception {
        return userDAO.delete(id);
    }

    @Override
    public List<UserDTO> getAllUsers() throws Exception {
        List<UserDTO> allUsers = new ArrayList<>();
        List<User> all = userDAO.getAll();

        for (User u : all) {
            allUsers.add(new UserDTO(
                    u.getUsername(),
                    u.getName(),
                    u.getEmail(),
                    u.getRole(),
                    u.getPassword())
            );
        }
        return allUsers;
    }

    public boolean changeUsername(String currentUsername, String newUsername, String confirmUsername) throws Exception {
        return userDAO.changeUsername(currentUsername, newUsername, confirmUsername);
    }

    @Override
    public boolean changePassword(String currentPassword, String newPassword) throws Exception {
        // Get the current user's username or ID (assuming you have a method to get the logged-in user)
        String username = UserBOImpl.userName; // or however you manage the logged-in user

        // First, retrieve the user from the database
        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        // Verify the current password
        if (!BCrypt.checkpw(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }

        return userDAO.changePassword(username, newPassword);
    }

    @Override
    public UserDTO searchByUsername(String username) throws Exception {
        User u = userDAO.searchByUsername(username);
        return new UserDTO(
                u.getUsername(),
                u.getName(),
                u.getEmail(),
                u.getRole(),
                u.getPassword()
        );
    }

    @Override
    public User checkLoginCredential(String username) throws Exception {
        return userDAO.checkLogin(username);
    }

    @Override
    public boolean checkRegisterCredential(String username, String name, String email, String hashedPassword, String role) throws Exception {
        return userDAO.checkRegister(username, name, email, hashedPassword, role);
    }
}
