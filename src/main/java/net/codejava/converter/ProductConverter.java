package net.codejava.converter;

import java.util.ArrayList;
import java.util.List;


import net.codejava.dto.QuantityBySizeDTO;
import net.codejava.model.*;
import net.codejava.dto.ImageRespDTO;
import net.codejava.dto.ProductRespDTO;

public class ProductConverter {
	public static ProductRespDTO toRespDTO(Product entity) {
		Brand brand = entity.getBrand();
		Category catelory = entity.getCategory();
		List<ProductImage> pi = entity.getImages();
		List<QuantityBySize> qbs = entity.getQuantityBySizes();

		ProductRespDTO productRespDTO = ProductRespDTO.builder().id(entity.getId()).name(entity.getName())
				.unitPrice(entity.getUnitPrice()).description(entity.getDescription())
				.discount(entity.getDiscount()).imgs(new ArrayList<>()).quantityBySizes(new ArrayList<>()).build();

		if (brand != null) {
			productRespDTO.setBrand_name(brand.getName());
		}
		if (catelory != null) {
			productRespDTO.setCategory_name(catelory.getName());
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
		if (qbs != null) {
			for (int i = 0; i < qbs.size(); i++) {
				QuantityBySizeDTO qbsDTO = new QuantityBySizeDTO();
				QuantityBySize quantityBySize = qbs.get(i);
				qbsDTO.setSize(quantityBySize.getSize());
				qbsDTO.setQuantity(quantityBySize.getQuantity());

				productRespDTO.getQuantityBySizes().add(qbsDTO);
			}
		}
		return productRespDTO;
	}
}
