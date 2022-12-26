package net.codejava.converter;

import net.codejava.dto.BrandRespDTO;
import net.codejava.dto.OrderRespDTO;
import net.codejava.model.Brand;
import net.codejava.model.Order;

public class OrderConverter {
	public static OrderRespDTO toRespDTO(Order entity) {
		OrderRespDTO orderRespDTO = OrderRespDTO.builder().id(entity.getId()).deliveryName(entity.getDeliveryName())
						.deliveryPhoneNum(entity.getDeliveryPhoneNum())
						.deliveryAddress(entity.getDeliveryAddress())
						.paymentMethod(entity.getPaymentMethod())
						.notes(entity.getNotes())
						.status(entity.getStatus())
						.createdDate(entity.getCreatedDate())
						.build();
		return  orderRespDTO;
	}
}
