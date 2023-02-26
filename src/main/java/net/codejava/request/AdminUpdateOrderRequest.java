package net.codejava.request;


import lombok.Data;

import java.util.List;

@Data
public class AdminUpdateOrderRequest {

    private int orderId;
    private String deliveryName;
    private String deliveryPhoneNum;
    private String deliveryAddress;
    private int paymentMethod;
    private String notes;
    private int status;

}
