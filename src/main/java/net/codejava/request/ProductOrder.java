package net.codejava.request;

import lombok.Data;
import net.codejava.model.QuantityOrder;

import java.util.List;

@Data
public class ProductOrder {
    private int productId;
    List<QuantityOrder> quantityOrder;


}
