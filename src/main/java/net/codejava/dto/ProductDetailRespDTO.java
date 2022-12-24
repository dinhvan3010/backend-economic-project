package net.codejava.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailRespDTO {
	private int id;
	private String name;
	private int unitPrice;
	private String brand_name;
	private String category_name;
	private String description;
	private float discount;
	private List<ImageRespDTO> imgs;
	private List<QuantityBySizeDTO> quantityBySizes;

}
