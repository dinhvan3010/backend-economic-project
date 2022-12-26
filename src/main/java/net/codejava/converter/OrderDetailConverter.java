package net.codejava.converter;

import net.codejava.dto.ImageRespDTO;
import net.codejava.dto.OrderDetailRespDTO;
import net.codejava.dto.OrderRespDTO;
import net.codejava.model.*;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailConverter {
	public static OrderDetailRespDTO toRespDTO(OrderDetail entity) {
		Product product = entity.getProduct();
		List<ProductImage> pi = product.getImages();

		OrderDetailRespDTO orderDetailRespDTO = OrderDetailRespDTO.builder().productName(product.getName()).unitPrice(product.getUnitPrice())
						.description(product.getDescription())
						.discount(product.getDiscount())
						.quantity(entity.getQuantity())
						.size(entity.getSize())
						.imgs(new ArrayList<>())
						.build();

		if (pi != null) {
			for (int i = 0; i < pi.size(); i++) {
				ImageRespDTO ird = new ImageRespDTO();
				ProductImage productImage = pi.get(i);
				ird.setImageAlt(productImage.getAlt());
				ird.setImageUrl(productImage.getUrl());

				orderDetailRespDTO.getImgs().add(ird);
			}
		}
		return  orderDetailRespDTO;
	}
}
