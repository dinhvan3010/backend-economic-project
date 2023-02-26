package net.codejava.converter;

import net.codejava.dto.ImageRespDTO;
import net.codejava.dto.OrderDetailRespDTO;
import net.codejava.dto.QuantityOrderRespDTO;
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

        double subtotal = StaticData.getSubTotal(entity);


        OrderDetailRespDTO orderDetailRespDTO = OrderDetailRespDTO.builder().productName(product.getName()).unitPrice(product.getUnitPrice()).description(product.getDescription()).discount(product.getDiscount()).imgs(new ArrayList<>()).quantityOrders(new ArrayList<>()).subTotal(subtotal).build();

        List<ProductImage> pi = product.getImages();
        if (pi != null) {
            for (int i = 0; i < pi.size(); i++) {
                ImageRespDTO ird = new ImageRespDTO();
                ProductImage productImage = pi.get(i);
                ird.setImageUrl(productImage.getUrl());
                orderDetailRespDTO.getImgs().add(ird);
            }
        }
        List<QuantityOrder> quantityOrders = entity.getQuantityOrders();
        if (quantityOrders != null) {
            for (int i = 0; i < quantityOrders.size(); i++) {
                QuantityOrderRespDTO orderRespDTO = new QuantityOrderRespDTO();
                QuantityOrder order = quantityOrders.get(i);
                orderRespDTO.setSize(order.getSize());
                orderRespDTO.setQuantity(order.getQuantity());
                orderDetailRespDTO.getQuantityOrders().add(orderRespDTO);
            }
        }
        return orderDetailRespDTO;
    }
}
