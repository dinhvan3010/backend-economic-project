package net.codejava.converter;

import net.codejava.dto.ImageRespDTO;
import net.codejava.dto.ProductDetailRespDTO;
import net.codejava.dto.QuantityBySizeDTO;
import net.codejava.model.*;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailConverter {
    public static ProductDetailRespDTO toRespDTO(Product entity) {
        Brand brand = entity.getBrand();
        Category catelory = entity.getCategory();
        List<ProductImage> pi = entity.getImages();
        List<QuantityBySize> qbs = entity.getQuantityBySizes();

        ProductDetailRespDTO productDetailRespDTO = ProductDetailRespDTO.builder().id(entity.getId()).name(entity.getName())
                .unitPrice(entity.getUnitPrice()).description(entity.getDescription())
                .discount(entity.getDiscount()).imgs(new ArrayList<>()).quantityBySizes(new ArrayList<>()).build();

        if (brand != null) {
            productDetailRespDTO.setBrand_name(brand.getName());
        }
        if (catelory != null) {
            productDetailRespDTO.setCategory_name(catelory.getName());
        }
        if (pi != null) {
            for (int i = 0; i < pi.size(); i++) {
                ImageRespDTO ird = new ImageRespDTO();
                ProductImage productImage = pi.get(i);
                ird.setImageAlt(productImage.getAlt());
                ird.setImageUrl(productImage.getUrl());

                productDetailRespDTO.getImgs().add(ird);
            }
        }
        if (qbs != null) {
            for (int i = 0; i < qbs.size(); i++) {
                QuantityBySizeDTO qbsDTO = new QuantityBySizeDTO();
                QuantityBySize quantityBySize = qbs.get(i);
                qbsDTO.setSize(quantityBySize.getSize());
                qbsDTO.setQuantity(quantityBySize.getQuantity());

                productDetailRespDTO.getQuantityBySizes().add(qbsDTO);
            }
        }
        return productDetailRespDTO;
    }
}
