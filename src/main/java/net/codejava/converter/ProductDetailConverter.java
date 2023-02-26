package net.codejava.converter;

import net.codejava.dto.ImageRespDTO;
import net.codejava.dto.InventoryDTO;
import net.codejava.dto.ProductDetailRespDTO;
import net.codejava.model.*;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailConverter {
    public static ProductDetailRespDTO toRespDTO(Product entity) {
        Brand brand = entity.getBrand();
        Category category = entity.getCategory();
        List<ProductImage> pi = entity.getImages();
        List<Inventory> inventoryList = entity.getInventories();

        ProductDetailRespDTO productDetailRespDTO = ProductDetailRespDTO.builder().id(entity.getId()).name(entity.getName())
                .unitPrice(entity.getUnitPrice()).description(entity.getDescription())
                .discount(entity.getDiscount()).imgs(new ArrayList<>()).inventoryDTOS(new ArrayList<>()).build();

        if (brand != null) {
            productDetailRespDTO.setBrand_name(brand.getName());
        }
        if (category != null) {
            productDetailRespDTO.setCategory_name(category.getName());
        }
        if (pi != null) {
            for (int i = 0; i < pi.size(); i++) {
                ImageRespDTO ird = new ImageRespDTO();
                ProductImage productImage = pi.get(i);
                ird.setImageUrl(productImage.getUrl());
                productDetailRespDTO.getImgs().add(ird);
            }
        }
        if (inventoryList != null) {
            for (int i = 0; i < inventoryList.size(); i++) {
                InventoryDTO inventoryDTO = new InventoryDTO();
                Inventory quantityBySize = inventoryList.get(i);
                inventoryDTO.setSize(quantityBySize.getSize());
                inventoryDTO.setQuantity(quantityBySize.getQuantity());
                productDetailRespDTO.getInventoryDTOS().add(inventoryDTO);
            }
        }
        return productDetailRespDTO;
    }
}
