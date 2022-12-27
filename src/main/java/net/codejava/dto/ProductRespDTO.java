package net.codejava.dto;

import lombok.*;

import java.util.List;

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
