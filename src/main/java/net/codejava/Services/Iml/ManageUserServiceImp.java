package net.codejava.Services.Iml;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.codejava.Model.Profile;
import net.codejava.Model.User;
import net.codejava.Services.CloudinaryService;
import net.codejava.Services.IManageUserService;
import net.codejava.converter.UserConverter;
import net.codejava.dto.UserRespDTO;
import net.codejava.enums.Gender;
import net.codejava.enums.UserRole;
import net.codejava.exceptions.MyAppException;
import net.codejava.repository.UserRepository;
import net.codejava.request.RegisterRequest;
import net.codejava.utils.DateUtil;
import net.codejava.utils.StaticData;

@Service
public class ManageUserServiceImp implements IManageUserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	CloudinaryService cloudinaryService;

	public void updatePassword(User user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);
		userRepo.save(user);
	}

	public User findUserByEmail(String email) {
		User user = userRepo.findOneByEmail(email);
		return user;
	}

	@Override
	public void registerUser(RegisterRequest request, MultipartFile file) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(file.getInputStream());
			if (bi == null) {
				throw new MyAppException(StaticData.ERROR_CODE.WRONG_FORMAT.getMessage(),
						StaticData.ERROR_CODE.WRONG_FORMAT.getCode());
			}
			Profile profile = new Profile();
			profile.setFirstName(request.getFirstName());
			profile.setLastName(request.getLastName());
			profile.setImage(request.getImage());
			profile.setGender(Gender.valueOf(request.getGender()));
			Map result = cloudinaryService.upload(file);
			profile.setImage((String) result.get("url"));
			User user = new User();
			user.setEmail(request.getEmail());
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			user.setProfile(profile);
			user.setRole(UserRole.USER);
			user.setCreatedDate(DateUtil.now());
			user.setEnabled(true);
			userRepo.save(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserRespDTO getUserProfile(int id) {
		User user = userRepo.getById(id);
		UserRespDTO userRespDTO = UserConverter.toRespDTO(user);
		return userRespDTO;
	}

}
