package net.codejava.converter;

import java.util.ArrayList;
import java.util.List;


import net.codejava.Model.Brand;
import net.codejava.Model.Catelory;
import net.codejava.Model.Product;
import net.codejava.Model.ProductImage;
import net.codejava.dto.ImageRespDTO;
import net.codejava.dto.ProductRespDTO;

public class ProductConverter {
	public static ProductRespDTO toRespDTO(Product entity) {
		Brand brand = entity.getBrand();
		Catelory catelory = entity.getCatelory();
		List<ProductImage> pi = entity.getImages();

		ProductRespDTO productRespDTO = ProductRespDTO.builder().id(entity.getId()).name(entity.getName())
				.quantity(entity.getQuantity()).unitPrice(entity.getUnitPrice()).description(entity.getDescription())
				.discount(entity.getDiscount()).imgs(new ArrayList<>()).build();

		if (brand != null) {
			productRespDTO.setBrand_name(brand.getName());
		}
		if (catelory != null) {
			productRespDTO.setCatelory_name(catelory.getName());
		}
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
