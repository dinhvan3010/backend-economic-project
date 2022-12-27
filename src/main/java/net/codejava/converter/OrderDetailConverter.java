package net.codejava.converter;

import net.codejava.dto.ImageRespDTO;
import net.codejava.dto.OrderDetailRespDTO;
import net.codejava.model.OrderDetail;
import net.codejava.model.Product;
import net.codejava.model.ProductImage;
import net.codejava.model.QuantityOrder;
import net.codejava.utils.StaticData;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailConverter {
	public static OrderDetailRespDTO toRespDTO(OrderDetail entity) {
		Product product = entity.getProduct();
		List<QuantityOrder> quantityOrders = entity.getQuantityOrders();
		double subtotal = StaticData.getSubTotal(entity);

		List<ProductImage> pi = product.getImages();
		OrderDetailRespDTO orderDetailRespDTO = OrderDetailRespDTO.builder().productName(product.getName()).unitPrice(product.getUnitPrice())
				.description(product.getDescription())
				.discount(product.getDiscount())
				.imgs(new ArrayList<>())
				.quantityOrders(quantityOrders)
				.subTotal(subtotal)
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

		return orderDetailRespDTO;
	}
}
