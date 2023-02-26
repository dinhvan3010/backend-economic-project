package net.codejava.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderRespDTO {
	private Integer id;
	private String userName;
	private String deliveryName;
	private String deliveryPhoneNum;
	private String deliveryAddress;
	private int paymentMethod;
	private String notes;
	private int status;
	private Date createdDate;
	private double total;
}
