package net.codejava.services.iml;

import net.codejava.converter.UserConverter;
import net.codejava.dto.UserRespDTO;
import net.codejava.enums.UserRole;
import net.codejava.model.Profile;
import net.codejava.model.User;
import net.codejava.repository.UserRepository;
import net.codejava.request.RegisterRequest;
import net.codejava.request.UpdateUserRequest;
import net.codejava.services.IManageUserService;
import net.codejava.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageUserServiceImp implements IManageUserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;


	public void updatePassword(User user, String newPassword) {;
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);
		userRepository.save(user);
	}

	public User findUserByEmail(String email) {
		User user = userRepository.findOneByEmail(email);
		return user;
	}

	@Override
	public void registerUser(RegisterRequest request) {

			Profile profile = new Profile();
			profile.setFirstName(request.getFirstName());
			profile.setLastName(request.getLastName());
			profile.setImage(request.getImage());
			profile.setGender(request.getGender());
			User user = new User();
			user.setEmail(request.getEmail());
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			user.setProfile(profile);
			user.setRole(UserRole.USER);
			user.setCreatedDate(DateUtil.now());
			user.setEnabled(true);
		userRepository.save(user);
	}

	@Override
	public UserRespDTO getUserProfile(int id) {
		User user = userRepository.getById(id);
		UserRespDTO userRespDTO = UserConverter.toRespDTO(user);
		return userRespDTO;
	}

	@Override
	public void changePassword(int id, String password) {
		User user = userRepository.getById(id);
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}

	@Override
	public void updateUserInfo(User user,  UpdateUserRequest request ) {
		Profile profile = user.getProfile();
		if (request.getFirstName() != null) {
			profile.setFirstName(request.getFirstName());
		}
		if (request.getLastName() != null) {
			profile.setLastName(request.getLastName());
		}
		if (request.getGender() != null) {
			profile.setGender(request.getGender());
		}
		if (request.getImage() != null) {
			profile.setImage(request.getImage());
		}
		if (request.getBirthday() != null) {
			profile.setBirthday(request.getBirthday());
		}
		userRepository.save(user);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User findUserById(int userId) {
		return userRepository.findOneById(userId);
	}

}
