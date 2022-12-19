package net.codejava.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.LastModifiedDate;

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
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private int unitPrice;
	private String description;
	private float discount;
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails;
	@OneToMany(mappedBy = "product")
	private List<ProductImage> images;
	@OneToMany(mappedBy = "product")
	private List<QuantityBySize> quantityBySizes;
	@Column(updatable = false)
	private Date createdDate;
	@LastModifiedDate
	private Date modifiedDate;

}
