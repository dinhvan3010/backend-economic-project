package net.codejava.utils;

import java.util.HashMap;
import java.util.Map;

public class StaticData {

	public interface Gender
	{
		public final static String	NONE			= "N";
		public final static String	MALE			= "M";
		public final static String	FEMALE			= "F";
	}

	public interface statusOrder
	{
		public final static int	OPEN					= 10001;
		public final static int	CONFIRMED				= 10002;
		public final static int	COMPLETE				= 10003;
		public final static int	CANCELLED				= 10004;
	}

	public interface paymentMethod
	{
		public final static int	CASH					= 10011;
		public final static int	BANK					= 10012;
	}

	public enum ERROR_CODE {

		NEW_PASSWORD_CONFIRMATION_NOT_MATCH(404006, "New password confirmation does not match"),
		NEW_PASSWORD_SAME_CURRENT_PASSWORD(404007, "New password be same as current password"),
		NOT_FOUND_USER_SESSION(404009, "Not found user session"), NOT_FOUND_CUSTOMER(404010, "Not found customer"),
		INVALID_SUBMIT_DATA(405001, "Invalid submit data"),
		WRONG_FORMAT(405002, "WRONG FORMAT"),
		NOT_FOUND_EMAIL(406001, "Not found email"),
		CUSTOMER_EXIST_EMAIL(406002, "This email already exists"),
		PRODUCT_EXIST_FAVORITE(406003, "The product already exists in the wishlist"),
		PRODUCT_NOT_FOUND(406003, "Product not found"),
		CART_IS_NULL(406004, "Cart is null"),
		SIZE_OR_QUANTITY_NOT_FOUND(406005, "Size or quantity not found"),
		YOUR_ORDERS_IS_NULL(406006, "Your order is null"),
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
