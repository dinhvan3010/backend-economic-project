package net.codejava.converter;

import net.codejava.Model.Product;
import net.codejava.Model.ProductFavoutite;
import net.codejava.dto.ProductRespDTO;

public class FavoutiteConverter {
	public static ProductRespDTO toRespDTO(ProductFavoutite entity) {
		Product product = entity.getProduct();
		ProductRespDTO productRespDTO = ProductConverter.toRespDTO(product);

//		Brand brand = entity.getBrand();
//		Catelory catelory = entity.getCatelory();
//		List<ProductImage> pi = entity.getImages();

//		ProductRespDTO productRespDTO = ProductRespDTO.builder().id(entity.getId()).build();
//		if (product != null) {
//			productRespDTO.setName(product.getName());
//			productRespDTO.setQuantity(product.getQuantity());
//			productRespDTO.setUnitPrice(product.getUnitPrice());
//			productRespDTO.setDescription(product.getDescription());
//			productRespDTO.setDiscount(product.getDiscount());
//		}
//		if (catelory != null) {
//			productRespDTO.setCatelory_name(catelory.getName());
//		}
//		if (pi != null) {
//			for (int i = 0; i < pi.size(); i++) {
//				ImageRespDTO ird = new ImageRespDTO();
//				ProductImage productImage = pi.get(i);
//				ird.setImageAlt(productImage.getAlt());
//				ird.setImageUrl(productImage.getUrl());
//
//				productRespDTO.getImgs().add(ird);
//			}
//		}
		return productRespDTO;
	}
}
