package net.codejava.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.codejava.Model.User;
import net.codejava.dto.UserRespDTO;
import net.codejava.request.RegisterRequest;

@Service
public interface IManageUserService {


	 void updatePassword(User user, String newPassword) ;
	
	 User findUserByEmail( String email);
	 
	 void registerUser(RegisterRequest request ,  MultipartFile file);
	 
	 UserRespDTO getUserProfile(int id);
	
}
