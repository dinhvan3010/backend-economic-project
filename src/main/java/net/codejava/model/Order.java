package net.codejava.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Data
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(updatable = false)
	private String deliveryName;
	private String deliveryPhoneNum;
	private String deliveryAddress;
	private int paymentMethod;
	private String notes;
	private int status;
	private Date createdDate;
	@ManyToOne
	private User user;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	private List<OrderDetail> orderDetails  = new ArrayList<>();
}
