package net.codejava.controller;

import net.codejava.converter.OrderConverter;
import net.codejava.converter.OrderDetailConverter;
import net.codejava.dto.OrderDetailRespDTO;
import net.codejava.dto.OrderRespDTO;
import net.codejava.exceptions.MyAppException;
import net.codejava.model.*;
import net.codejava.repository.*;
import net.codejava.request.OrderRequest;
import net.codejava.request.ProductOrder;
import net.codejava.response.StatusResp;
import net.codejava.services.IListConverter;
import net.codejava.services.IManageOrderService;
import net.codejava.utils.DateUtil;
import net.codejava.utils.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    QuantityOrderRepository quantityOrderRepository;

    @Autowired
    IManageOrderService manageOrderService;

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

        List<ProductOrder> productOrderList = request.getProductOrdersList();
        List<OrderDetail> orderDetails = new ArrayList<>();
        if (productOrderList.isEmpty()) {
            throw new MyAppException(StaticData.ERROR_CODE.CART_IS_NULL.getMessage(), StaticData.ERROR_CODE.CART_IS_NULL.getCode());
        }
        List<QuantityOrder> quantityOrderList = null;
        for (int i = 0; i < productOrderList.size(); i++) {
            List<Inventory> inventories = productRepository.getById(productOrderList.get(i).getProductId()).getInventories();
            // convert list to Map
            Map<Integer, Integer> map = new HashMap<>();
            for (Inventory inventory : inventories) {
                map.put(inventory.getSize(), inventory.getQuantity());
            }
            ProductOrder productOrder = productOrderList.get(i);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            Product product = productRepository.getById(productOrder.getProductId());
            orderDetail.setProduct(product);
            orderDetails.add(orderDetail);
            List<QuantityOrder> quantityOrders = productOrder.getQuantityOrder();
            quantityOrderList = new ArrayList<>();
            for (int j = 0; j < quantityOrders.size(); j++) {
                QuantityOrder quantityOrder = new QuantityOrder();
                int size = quantityOrders.get(j).getSize();
                int quantity = quantityOrders.get(j).getQuantity();
                if(map.get(size) < quantity){
                    throw new MyAppException(StaticData.ERROR_CODE.NOT_ENOUGH_QUANTITY.getMessage(), StaticData.ERROR_CODE.NOT_ENOUGH_QUANTITY.getCode());
                }
                for (int k = 0; k <inventories.size() ; k++) {
                    if(inventories.get(k).getSize() == quantityOrders.get(j).getSize()){
                        inventories.get(k).setQuantity(inventories.get(k).getQuantity() - quantityOrders.get(j).getQuantity());
                    }
                }
                quantityOrder.setOrderDetail(orderDetail);
                quantityOrder.setSize(size);
                quantityOrder.setQuantity(quantity);
                quantityOrderList.add(quantityOrder);
            }
            quantityOrderRepository.saveAll(quantityOrderList);
        }
        orderRepository.save(order);
        orderDetailRepository.saveAll(orderDetails);
        return resp;
    }

    @GetMapping("/list")
    public StatusResp list() {
        StatusResp resp = new StatusResp();
        User user = getUserSession();
        List<Order> orders = manageOrderService.getOrders(user.getId());
        if (orders.isEmpty()) {
            throw new MyAppException(StaticData.ERROR_CODE.YOUR_ORDERS_IS_NULL.getMessage(), StaticData.ERROR_CODE.YOUR_ORDERS_IS_NULL.getCode());
        }
        List<OrderRespDTO> orderRespDTOs = listConverter.toListResponse(orders, OrderConverter::toRespDTO);
        resp.setData(orderRespDTOs);
        return resp;
    }


    @GetMapping("/detail")
    public StatusResp orderDetail(@RequestParam int orderId) {
        StatusResp resp = new StatusResp();
        List<OrderDetail> orderDetails =manageOrderService.getOrderDetail(orderId);
        if(orderDetails.isEmpty()){
            throw new MyAppException(StaticData.ERROR_CODE.ORDER_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.ORDER_NOT_FOUND.getCode());
        }
        List<OrderDetailRespDTO> orderDetailRespDTOS = listConverter.toListResponse(orderDetails, OrderDetailConverter::toRespDTO);
        resp.setData(orderDetailRespDTOS);
        return resp;
    }

    @PutMapping("/cancel")
    public StatusResp cancelOrder(@RequestParam int orderId) {
        StatusResp resp = new StatusResp();
        User user = getUserSession();
        Order order = orderRepository.getByIdAndUserId(orderId, user.getId());
        if(order == null){
            throw new MyAppException(StaticData.ERROR_CODE.ORDER_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.ORDER_NOT_FOUND.getCode());
        }
        int statusOrder = order.getStatus();
        if(statusOrder != StaticData.statusOrder.OPEN){
            throw new MyAppException(StaticData.ERROR_CODE.CANNOT_CANCEL_ORDER.getMessage(), StaticData.ERROR_CODE.CANNOT_CANCEL_ORDER.getCode());
        }
        order.setStatus(StaticData.statusOrder.CANCELLED);
        orderRepository.save(order);
        return resp;
    }

}
