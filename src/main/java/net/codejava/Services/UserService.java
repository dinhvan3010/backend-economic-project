package net.codejava.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.codejava.Model.User;
import net.codejava.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	public void updatePassword(User user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);
		userRepo.save(user);
	}
	
	public User findUserByEmail( String email) {
		User user = userRepo.findUserByEmail(email);
		return user;
	}
}
