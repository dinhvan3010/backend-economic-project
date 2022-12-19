package net.codejava.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Data
@Entity
public class QuantityBySize {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	@ManyToOne
	private Product product;
	@Column
	private int Size;
	@Column
	private int quantity;
}