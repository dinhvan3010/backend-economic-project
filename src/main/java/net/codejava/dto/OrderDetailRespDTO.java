package net.codejava.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.codejava.model.QuantityOrder;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRespDTO {
    private String productName;
    private int unitPrice;
    private String description;
    private float discount;
    private List<ImageRespDTO> imgs;
    List<QuantityOrder> quantityOrders;
    private double subTotal;


}
