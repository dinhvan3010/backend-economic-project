package net.codejava.services;

import net.codejava.request.UpdateUserRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.codejava.model.User;
import net.codejava.dto.UserRespDTO;
import net.codejava.request.RegisterRequest;

import java.util.List;

@Service
public interface IManageUserService {

    void updatePassword(User user, String newPassword);

    User findUserByEmail(String email);

    User findUserById(int userId);

    void registerUser(RegisterRequest request);

    UserRespDTO getUserProfile(int id);

    void changePassword(int id, String password);

    void updateUserInfo(User user, UpdateUserRequest request);

    Boolean existsByEmail(String email);

    List<User> getAllUser();



}
