package net.codejava.dto;

import java.util.List;

import lombok.*;
import net.codejava.model.QuantityBySize;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRespDTO {
	private int id;
	private String name;
	private int unitPrice;
	private String description;
	private float discount;
	private List<ImageRespDTO> imgs;


}
