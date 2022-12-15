package net.codejava.converter;

import net.codejava.Model.Category;
import net.codejava.dto.CategoryRespDTO;

public class CategoryConverter {
    public static CategoryRespDTO toRespDTO(Category entity) {
        CategoryRespDTO categoryRespDTO = CategoryRespDTO.builder().id(entity.getId()).name(entity.getName()).build();
        return categoryRespDTO;
    }
}
