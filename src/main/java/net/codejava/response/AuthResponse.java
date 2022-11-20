package net.codejava.response;

public class AuthResponse {
	private String email;
	private String accessToken;
	private String role ;

	public AuthResponse() { }
	
	public AuthResponse(String email, String accessToken, String role) {
		this.email = email;
		this.accessToken = accessToken;
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	

}
