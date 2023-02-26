package net.codejava.services;

import net.codejava.model.Order;
import net.codejava.model.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IManageOrderService {

    List<Order> getAllOrder();

    List<Order> getOrdersByUerId(int userId);

    List<OrderDetail> getOrderDetail(int orderId);

    Order getOrderById(int orderId);

    Order getOrderByIdAndUserId(int orderId, int userId);

    void createOrder(Order order);


}
