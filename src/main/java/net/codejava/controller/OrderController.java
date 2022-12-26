package net.codejava.controller;

import net.codejava.converter.OrderConverter;
import net.codejava.converter.ProductConverter;
import net.codejava.dto.OrderRespDTO;
import net.codejava.dto.ProductRespDTO;
import net.codejava.exceptions.MyAppException;
import net.codejava.model.Order;
import net.codejava.model.OrderDetail;
import net.codejava.model.User;
import net.codejava.repository.OrderDetailRepository;
import net.codejava.repository.OrderRepository;
import net.codejava.repository.ProductRepository;
import net.codejava.request.OrderRequest;
import net.codejava.request.ProductOrder;
import net.codejava.request.QuantitySize;
import net.codejava.response.StatusResp;
import net.codejava.services.IListConverter;
import net.codejava.utils.DateUtil;
import net.codejava.utils.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController extends AbstractRestController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    IListConverter listConverter;

    @PostMapping("/create")
    public StatusResp createOrder(@RequestBody OrderRequest request) {
        StatusResp resp = new StatusResp();
        Order order = new Order();
        order.setDeliveryName(request.getDeliveryName());
        order.setDeliveryPhoneNum(request.getDeliveryPhoneNum());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setNotes(request.getNotes());
        order.setCreatedDate(DateUtil.now());
        order.setStatus(StaticData.statusOrder.OPEN);
        User user = getUserSession();
        order.setUser(user);

        orderRepository.save(order);

        List<ProductOrder> productOrderList = request.getProductOrdersList();
        List<OrderDetail> orderDetails = new ArrayList<>();
        if (productOrderList.size() == 0) {
            throw new MyAppException(StaticData.ERROR_CODE.CART_IS_NULL.getMessage(), StaticData.ERROR_CODE.CART_IS_NULL.getCode());
        }
        for (int i = 0; i < productOrderList.size(); i++) {
            ProductOrder productOrder = productOrderList.get(i);
            List<QuantitySize> quantitySizes = productOrder.getQuantitySizes();

            if (quantitySizes.size() == 0) {
                throw new MyAppException(StaticData.ERROR_CODE.SIZE_OR_QUANTITY_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.SIZE_OR_QUANTITY_NOT_FOUND.getCode());
            }

            for (int j = 0; j < quantitySizes.size(); j++) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(productRepository.getById(productOrder.getProductId()));
                orderDetail.setSize(quantitySizes.get(j).getSize());
                orderDetail.setQuantity(quantitySizes.get(j).getQuantity());
                orderDetails.add(orderDetail);
            }
        }
        return resp;
    }

    @GetMapping("/list")
    public StatusResp list() {
        StatusResp resp = new StatusResp();
        User user = getUserSession();
        List<Order> orders = orderRepository.getByUserId(user.getId());
        if(orders.size() == 0){
            throw new MyAppException(StaticData.ERROR_CODE.YOUR_ORDERS_IS_NULL.getMessage(), StaticData.ERROR_CODE.YOUR_ORDERS_IS_NULL.getCode());
        }
        List<OrderRespDTO> orderRespDTOs = listConverter.toListResponse(orders, OrderConverter::toRespDTO);
        resp.setData(orderRespDTOs);
        return resp;
    }


    @GetMapping("/detail")
    public StatusResp orderDetail(@RequestParam int orderId) {
        StatusResp resp = new StatusResp();
        User user = getUserSession();
        List<OrderDetail> orderDetails = orderDetailRepository.getByOrder_Id(orderId);
        resp.setData(orderDetails);
        return resp;
    }

}
