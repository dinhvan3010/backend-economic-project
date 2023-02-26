package net.codejava.utils;

import net.codejava.model.OrderDetail;
import net.codejava.model.Product;
import net.codejava.model.QuantityOrder;

import java.util.HashMap;
import java.util.List;
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
		NOT_FOUND_USER_SESSION(404009, "Not found user session"),
		NOT_FOUND_CUSTOMER(404010, "Not found customer"),
		INVALID_SUBMIT_DATA(405001, "Invalid submit data"),
		WRONG_FORMAT(405002, "WRONG FORMAT"),
		NOT_FOUND_EMAIL(406001, "Not found email"),
		CUSTOMER_EXIST_EMAIL(406002, "This email already exists"),
		PRODUCT_EXIST_FAVORITE(406003, "The product already exists in the wishlist"),
		PRODUCT_NOT_FOUND(406003, "Product not found"),
		CART_IS_NULL(406004, "Cart is null"),
		YOUR_ORDERS_IS_NULL(406006, "Your order is null"),
		ORDER_NOT_FOUND(406007, "Order not found"),
		CANNOT_CANCEL_ORDER(406008, "Can't cancel order"),
		NOT_ENOUGH_QUANTITY(406009, "not enough quantity"),
		BRAND_NOT_FOUND(406010, "Brand not found"),
		USER_NOT_FOUND(406011, "User not found"),
		BRAND_EXISTED(406012, "Brand Exist"),
		CATEGORY_NOT_FOUND (40613, "Category not found"),
		CATEGORY_NAME_EXISTED(406012, "Category Name Exist"),
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

	public static double getSubTotal(OrderDetail entity) {
		List<QuantityOrder> quantityOrders = entity.getQuantityOrders();
		Product product = entity.getProduct();
		double subtotal = 0;
		int totalQuantity = 0;
		for (int i = 0; i < quantityOrders.size(); i++) {
			totalQuantity += quantityOrders.get(i).getQuantity();
		}
		subtotal = totalQuantity * product.getUnitPrice()*(1- product.getDiscount()/100);
		return subtotal;
	}
}
