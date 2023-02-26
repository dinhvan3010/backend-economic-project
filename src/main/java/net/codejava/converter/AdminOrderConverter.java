package net.codejava.converter;

import net.codejava.dto.AdminOrderRespDTO;
import net.codejava.dto.OrderRespDTO;
import net.codejava.model.Order;
import net.codejava.model.OrderDetail;
import net.codejava.model.User;
import net.codejava.utils.StaticData;

import java.util.List;

public class AdminOrderConverter {
    public static AdminOrderRespDTO toRespDTO(Order entity) {

        double total = 0;
        double subtotal = 0;
        List<OrderDetail> orderDetails = entity.getOrderDetails();
        for (int i = 0; i < orderDetails.size(); i++) {
            subtotal = StaticData.getSubTotal(orderDetails.get(i));
            total += subtotal;
        }


        AdminOrderRespDTO adminOrderRespDTO = AdminOrderRespDTO.builder().id(entity.getId())
                .deliveryName(entity.getDeliveryName())
                .deliveryPhoneNum(entity.getDeliveryPhoneNum())
                .deliveryAddress(entity.getDeliveryAddress())
                .paymentMethod(entity.getPaymentMethod())
                .notes(entity.getNotes())
                .status(entity.getStatus())
                .createdDate(entity.getCreatedDate())
                .total(total)
                .build();

        User user = entity.getUser();
        if(user!= null){
            adminOrderRespDTO.setUserName(user.getUsername());
        }
        return adminOrderRespDTO;
    }
}
