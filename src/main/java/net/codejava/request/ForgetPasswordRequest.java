package net.codejava.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ForgetPasswordRequest {
	@NotNull @Email  @Length(min = 5, max = 50)
	private String recipientEmail;

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
	
}
