package net.codejava.converter;

import net.codejava.Model.Product;
import net.codejava.Model.WishList;
import net.codejava.dto.ProductRespDTO;

public class WishListConverter {
	public static ProductRespDTO toRespDTO(WishList entity) {
		Product product = entity.getProduct();
		ProductRespDTO productRespDTO = ProductConverter.toRespDTO(product);
		return productRespDTO;
	}
}
