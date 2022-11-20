package net.codejava.request;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import net.codejava.enums.Gender;

public class RegisterRequest {
	@NotNull
	@Email
	@Length(min = 5, max = 50)
	private String email;
	@Length(min = 5, max = 50)
	private String password;
	@Length(min = 5, max = 50)
	private String confirmPassword;
	@NotNull
	private String image;
	@NotNull
	private Gender gender;
	
	private Date birthday;

}
