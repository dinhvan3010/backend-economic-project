package net.codejava.Model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "oders")
public class Oder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(updatable = false)
	private String deliveryName;
	private String deliveryPhoneNum;
	private String deliveryAddress;
	private String paymentMethod;
	private String notes;
	private int status;
	private Date createdDate;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "oder")
	private List<OderDetail> oderDetails;
}
