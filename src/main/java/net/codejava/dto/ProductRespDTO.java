package net.codejava.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRespDTO {
	private int id;
	private String name;
	private int quantity;
	private int unitPrice;
	private String brand_name;
	private String catelory_name;
	private String description;
	private float discount;
	private List<ImageRespDTO> imgs;

}
