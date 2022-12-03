package net.codejava.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import net.codejava.Model.User;
import net.codejava.Services.IManageUserService;
import net.codejava.exceptions.MyAppException;
import net.codejava.jwt.JwtTokenUtil;
import net.codejava.repository.UserRepository;
import net.codejava.utils.StaticData;

public abstract class AbstractRestController {
	Logger logger = LoggerFactory.getLogger(AbstractRestController.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	protected IManageUserService userServiceImpl;
	
	@Autowired
	UserRepository userRepo;

	protected void checkBindingResult(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder erroMsg = new StringBuilder();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				erroMsg.append(" " + fieldError.getField() + ": " + fieldError.getDefaultMessage());
				erroMsg.append("\r\n");
			}

			throw new MyAppException(
					StaticData.ERROR_CODE.INVALID_SUBMIT_DATA.getMessage() + "\r\n Error:\r\n" + erroMsg.toString(),
					StaticData.ERROR_CODE.INVALID_SUBMIT_DATA.getCode());
		}
	}

	protected User getUserSession() {
		User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepo.getById(userSession.getId());
		if (user == null) {
			throw new MyAppException(StaticData.ERROR_CODE.NOT_FOUND_USER_SESSION.getMessage(),
					StaticData.ERROR_CODE.NOT_FOUND_USER_SESSION.getCode());
		}
		return user;
	}
}
