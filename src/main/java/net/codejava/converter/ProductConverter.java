package net.codejava.converter;

import net.codejava.dto.ImageRespDTO;
import net.codejava.dto.ProductRespDTO;
import net.codejava.model.Product;
import net.codejava.model.ProductImage;

import java.util.ArrayList;
import java.util.List;

public class ProductConverter {
	public static ProductRespDTO toRespDTO(Product entity) {
		List<ProductImage> pi = entity.getImages();

		ProductRespDTO productRespDTO = ProductRespDTO.builder().id(entity.getId()).name(entity.getName())
				.unitPrice(entity.getUnitPrice()).description(entity.getDescription())
				.discount(entity.getDiscount()).imgs(new ArrayList<>()).build();

		if (pi != null) {
			for (int i = 0; i < pi.size(); i++) {
				ImageRespDTO ird = new ImageRespDTO();
				ProductImage productImage = pi.get(i);
				ird.setImageAlt(productImage.getAlt());
				ird.setImageUrl(productImage.getUrl());

				productRespDTO.getImgs().add(ird);
			}
		}
		return productRespDTO;
	}
}
