package net.codejava.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.bytebuddy.utility.RandomString;
import net.codejava.Model.User;
import net.codejava.Services.MailService;
import net.codejava.Services.UserService;
import net.codejava.exceptions.MyAppException;
import net.codejava.repository.UserRepository;
import net.codejava.request.ForgetPasswordRequest;
import net.codejava.request.RegisterRequest;
import net.codejava.response.StatusResp;
import net.codejava.utils.StaticData;

@RestController
public class UserController extends AbstractRestController {
	
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepo;
	@Autowired
	MailService mail;

	@PostMapping("/forgot_password")
	public StatusResp processForgotPassword(@Valid @RequestBody ForgetPasswordRequest request,  BindingResult bindingResult) {
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
	
	@PostMapping("/register")
	public StatusResp registerUser(@Valid @RequestBody RegisterRequest request) {
		StatusResp statusResp = new StatusResp();
		
		return statusResp;
	}

}
