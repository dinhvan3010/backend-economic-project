package net.codejava.services.iml;

import net.codejava.model.Order;
import net.codejava.model.OrderDetail;
import net.codejava.repository.OrderDetailRepository;
import net.codejava.repository.OrderRepository;
import net.codejava.services.IManageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageOrderServiceImp implements IManageOrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUerId(int userId) {
        return orderRepository.getByUserId(userId);
    }

    @Override
    public List<OrderDetail> getOrderDetail(int orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        return orderDetails;
    }

    @Override
    public Order getOrderById(int orderId) {
        return orderRepository.findOneById(orderId);
    }

    @Override
    public Order getOrderByIdAndUserId(int orderId, int userId) {
        return orderRepository.getByIdAndUserId(orderId, userId);
    }

    @Override
    public void createOrder(Order order) {
        orderRepository.save(order);
    }
}
