package net.codejava.controller;

import net.bytebuddy.utility.RandomString;
import net.codejava.model.Profile;
import net.codejava.model.User;
import net.codejava.services.CloudinaryService;
import net.codejava.services.IManageUserService;
import net.codejava.services.MailService;
import net.codejava.dto.UserRespDTO;
import net.codejava.exceptions.MyAppException;
import net.codejava.repository.UserRepository;
import net.codejava.request.ChangePasswordRequest;
import net.codejava.request.ForgetPasswordRequest;
import net.codejava.request.RegisterRequest;
import net.codejava.request.UpdateUserRequest;
import net.codejava.response.StatusResp;
import net.codejava.utils.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractRestController {

	@Autowired
	IManageUserService userService;
	@Autowired
	MailService mail;
	@Autowired
	CloudinaryService cloudinaryService;
	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/forgot_password")
	public StatusResp processForgotPassword(@Valid @RequestBody ForgetPasswordRequest request,
			BindingResult bindingResult) {
		checkBindingResult(bindingResult);
		StatusResp statusResp = new StatusResp();
		String email = request.getRecipientEmail();
		String newPw = RandomString.make(6);
		User user = userService.findUserByEmail(email);
		if (user == null) {
			throw new MyAppException(StaticData.ERROR_CODE.NOT_FOUND_EMAIL.getMessage(),
					StaticData.ERROR_CODE.NOT_FOUND_EMAIL.getCode());
		}
		mail.sendMail(email, newPw);
		userService.updatePassword(user, newPw);
		return statusResp;
	}

	@PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public StatusResp registerUser(@RequestPart("user") @Valid RegisterRequest request, @RequestPart MultipartFile file,
			BindingResult bindingResult) {
		checkBindingResult(bindingResult);
		StatusResp statusResp = new StatusResp();
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new MyAppException(StaticData.ERROR_CODE.NEW_PASSWORD_CONFIRMATION_NOT_MATCH.getMessage(),
					StaticData.ERROR_CODE.NEW_PASSWORD_CONFIRMATION_NOT_MATCH.getCode());
		}
		if (userRepo.existsByEmail(request.getEmail())) {
			throw new MyAppException(StaticData.ERROR_CODE.CUSTOMER_EXIST_EMAIL.getMessage(),
					StaticData.ERROR_CODE.CUSTOMER_EXIST_EMAIL.getCode());
		}
		userService.registerUser(request, file);
		return statusResp;
	}

	@PostMapping("/checkExistEmail")
	public StatusResp checkExistEmail(@RequestBody RegisterRequest request) {
		StatusResp resp = new StatusResp();
		if (userRepo.existsByEmail(request.getEmail())) {
			throw new MyAppException(StaticData.ERROR_CODE.CUSTOMER_EXIST_EMAIL.getMessage(),
					StaticData.ERROR_CODE.CUSTOMER_EXIST_EMAIL.getCode());
		}
		return resp;
	}

	@GetMapping("/whoami")
	public StatusResp whoami() {
		StatusResp resp = new StatusResp();
		User user = getUserSession();
		UserRespDTO userRespDTO = userService.getUserProfile(user.getId());
		resp.setData(userRespDTO);
		return resp;
	}

	@PutMapping(value = "/update", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public StatusResp updateUser(@RequestPart("user") @Valid UpdateUserRequest request, @RequestPart MultipartFile file,
			BindingResult bindingResult) {
		checkBindingResult(bindingResult);
		StatusResp resp = new StatusResp();
		BufferedImage bi;
		Map result = null;
		User userSession = getUserSession();
		User user = userRepo.getById(userSession.getId());
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
		try {
			bi = ImageIO.read(file.getInputStream());
			if (bi != null) {
				cloudinaryService.delete(profile.getPhotoId());
				 result = cloudinaryService.upload(file);
				user.getProfile().setImage((String) result.get("url"));
				user.getProfile().setPhotoId(result.get("public_id").toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		userRepo.save(user);
		return resp;
	}

	@PutMapping("/changePassword")
	public StatusResp changePassword(@RequestBody @Valid ChangePasswordRequest request, BindingResult bindingResult) {
		checkBindingResult(bindingResult);
		User userSession = getUserSession();
		if (!passwordEncoder.matches(request.getOldPassword(), userSession.getPassword())) {
			throw new MyAppException(StaticData.ERROR_CODE.NEW_PASSWORD_SAME_CURRENT_PASSWORD.getMessage(),
					StaticData.ERROR_CODE.NEW_PASSWORD_SAME_CURRENT_PASSWORD.getCode());
		}

		if (!request.getNewPassword().equals(request.getConfirmPassword())) {
			throw new MyAppException(StaticData.ERROR_CODE.NEW_PASSWORD_CONFIRMATION_NOT_MATCH.getMessage(),
					StaticData.ERROR_CODE.NEW_PASSWORD_CONFIRMATION_NOT_MATCH.getCode());
		}

		StatusResp resp = new StatusResp();
		userService.changePassword(userSession.getId(), request.getNewPassword());
		return resp;
	}

}
