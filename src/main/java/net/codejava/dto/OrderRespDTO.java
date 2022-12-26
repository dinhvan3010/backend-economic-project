package net.codejava.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.codejava.model.OrderDetail;
import net.codejava.model.User;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRespDTO {
	private Integer id;
	private String deliveryName;
	private String deliveryPhoneNum;
	private String deliveryAddress;
	private int paymentMethod;
	private String notes;
	private int status;
	private Date createdDate;


}
