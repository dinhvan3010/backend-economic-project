package net.codejava.utils;

public class StaticData {

	public enum ERROR_CODE {
		// Role 403
		InvalidRoleId(403001, "Invalid role id"),
		// User 404
		UserExisted(404001, "User existed"), NOT_FOUND_USER(404002, "Not found user"),
		INVALID_USER_ADMIN(404003, "Invalid user admin"), INVALID_CREDENTIALS(404004, "Invalid credentials"),
		USER_DISABLED(404005, "User disabled"),
		NEW_PASSWORD_CONFIRMATION_NOT_MATCH(404006, "New password confirmation does not match"),
		NEW_PASSWORD_SAME_CURRENT_PASSWORD(404007, "New password be same as current password"),
		INVALID_VERIFY_PASSWORD(404008, "Invalid verify password"),

		NOT_FOUND_USER_SESSION(404009, "Not found user session"), NOT_FOUND_CUSTOMER(404010, "Not found customer"),
		INVALID_SUBMIT_DATA(405001, "Invalid submit data"),
		WRONG_FORMAT(405002, "WRONG FORMAT"),
		NOT_FOUND_EMAIL(406001, "Not found email"),
		CUSTOMER_EXIST_EMAIL(406002, "This email already exists"),
		PRODUCT_EXIST_FAVORITE(406003, "The product already exists in the wishlist"),
		PRODUCT_NOT_FOUND(406003, "Product not found")

		;

		private final int code;
		private final String message;

		private ERROR_CODE(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}
}
