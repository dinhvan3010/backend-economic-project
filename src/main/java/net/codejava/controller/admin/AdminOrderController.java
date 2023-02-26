package net.codejava.controller.admin;

import net.codejava.controller.AbstractRestController;
import net.codejava.converter.AdminOrderConverter;
import net.codejava.converter.OrderDetailConverter;
import net.codejava.dto.AdminOrderRespDTO;
import net.codejava.dto.OrderDetailRespDTO;
import net.codejava.exceptions.MyAppException;
import net.codejava.model.*;
import net.codejava.repository.OrderDetailRepository;
import net.codejava.repository.OrderRepository;
import net.codejava.repository.ProductRepository;
import net.codejava.repository.QuantityOrderRepository;
import net.codejava.request.AdminUpdateOrderRequest;
import net.codejava.request.CreateOrderRequest;
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
@RequestMapping("/api/admin/order")
public class AdminOrderController extends AbstractRestController {

    @Autowired
    IListConverter listConverter;

    @Autowired
    IManageOrderService manageOrderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    QuantityOrderRepository quantityOrderRepository;

    @GetMapping("/list")
    public StatusResp getListOrder() {
        StatusResp resp = new StatusResp();
        List<Order> orders = manageOrderService.getAllOrder();
        List<AdminOrderRespDTO> adminOrderRespDTOS = listConverter.toListResponse(orders, AdminOrderConverter::toRespDTO);
        resp.setData(adminOrderRespDTOS);
        return resp;
    }

    @GetMapping("/detail")
    public StatusResp getOrderDetail(@RequestParam int orderId) {
        StatusResp resp = new StatusResp();
        List<OrderDetail> orderDetails = manageOrderService.getOrderDetail(orderId);
        if (orderDetails.isEmpty()) {
            throw new MyAppException(StaticData.ERROR_CODE.ORDER_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.ORDER_NOT_FOUND.getCode());
        }
        List<OrderDetailRespDTO> orderDetailRespDTOS = listConverter.toListResponse(orderDetails, OrderDetailConverter::toRespDTO);
        resp.setData(orderDetailRespDTOS);
        return resp;
    }

    @PostMapping("/create")
    public StatusResp createOrder(@RequestBody CreateOrderRequest request) {
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
                if (map.get(size) < quantity) {
                    throw new MyAppException(StaticData.ERROR_CODE.NOT_ENOUGH_QUANTITY.getMessage(), StaticData.ERROR_CODE.NOT_ENOUGH_QUANTITY.getCode());
                }
                for (int k = 0; k < inventories.size(); k++) {
                    if (inventories.get(k).getSize() == quantityOrders.get(j).getSize()) {
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
        manageOrderService.createOrder(order);
        orderDetailRepository.saveAll(orderDetails);
        return resp;
    }

    @PutMapping("/update")
    public StatusResp updateOrder(@RequestBody AdminUpdateOrderRequest request) {
        StatusResp resp = new StatusResp();
        Order order = manageOrderService.getOrderById(request.getOrderId());
        if (order == null) {
            throw new MyAppException(StaticData.ERROR_CODE.ORDER_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.ORDER_NOT_FOUND.getCode());
        }
        if (request.getDeliveryName() != null) {
            order.setDeliveryName(request.getDeliveryName());
        }
        if (request.getDeliveryAddress() != null) {
            order.setDeliveryAddress(request.getDeliveryAddress());
        }
        if (request.getDeliveryPhoneNum() != null) {
            order.setDeliveryPhoneNum(request.getDeliveryPhoneNum());
        }
        if (request.getNotes() != null) {
            order.setNotes(request.getNotes());
        }
        if (Integer.toString(request.getStatus()) != null) {
            order.setStatus(request.getStatus());
        }
        orderRepository.save(order);
        return resp;
    }

}
