package net.codejava.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductOrder {
    private int productId;
    List<QuantitySize> quantitySizes;


}
