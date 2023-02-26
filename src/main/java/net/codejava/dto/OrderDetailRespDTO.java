package net.codejava.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.codejava.model.QuantityOrder;

import java.util.List;

@Data
@Builder
public class OrderDetailRespDTO {
    private String productName;
    private int unitPrice;
    private String description;
    private float discount;
    private List<ImageRespDTO> imgs;
    List<QuantityOrderRespDTO> quantityOrders;
    private double subTotal;


}
