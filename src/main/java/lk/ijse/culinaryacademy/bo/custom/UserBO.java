package lk.ijse.culinaryacademy.bo.custom;

import lk.ijse.culinaryacademy.bo.SuperBO;
import lk.ijse.culinaryacademy.dto.UserDTO;
import lk.ijse.culinaryacademy.entity.User;

import java.util.List;

public interface UserBO extends SuperBO {
    boolean addUser(UserDTO dto) throws Exception;

    boolean updateUser(UserDTO dto) throws Exception;

    boolean deleteUser(String id) throws Exception;

    List<UserDTO> getAllUsers() throws Exception;

    boolean changeUsername(String currentUsername, String newUsername, String confirmUsername) throws Exception;

    boolean changePassword(String currentPassword, String newPassword) throws Exception;

    UserDTO searchByUsername(String username) throws Exception;

    User checkLoginCredential(String username) throws Exception;

    boolean checkRegisterCredential(String username, String name, String email, String hashedPassword, String role) throws Exception;

}
