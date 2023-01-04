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
    public List<Order> getOrders(int userId) {
        return orderRepository.getByUserId(userId);
    }

    @Override
    public List<OrderDetail> getOrderDetail(int orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.getByOrder_Id(orderId);
        return orderDetails;
    }
}
