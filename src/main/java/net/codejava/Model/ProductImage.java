package net.codejava.Model;

import lombok.*;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Data
@Indexed
@Entity
public class ProductImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private Product product;

	private String alt;

	private String url;
	
	private String pid;
}
