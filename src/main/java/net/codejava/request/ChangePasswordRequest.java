package net.codejava.request;

import org.hibernate.validator.constraints.Length;

public class ChangePasswordRequest {

	@Length(min = 5, max = 20)
	private String oldPassword;
	@Length(min = 5, max = 20)
	private String newPassword;
	@Length(min = 5, max = 20)
	private String confirmPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
