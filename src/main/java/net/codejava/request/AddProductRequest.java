package net.codejava.request;


import lombok.Data;

import java.util.List;

@Data
public class AddProductRequest {
    private String name;
    private int unitPrice;
    private String description;
    private float discount;
    private int brandId;
    private int categoryId;
    private List<ProductImageRequest> images;
    private List<InventoryRequest> inventories;
}
