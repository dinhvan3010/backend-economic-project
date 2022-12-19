package net.codejava.converter;

import net.codejava.model.Brand;
import net.codejava.dto.BrandRespDTO;

public class BrandConverter {
	public static BrandRespDTO toRespDTO(Brand entity) {
		BrandRespDTO brandRespDTO = BrandRespDTO.builder().id(entity.getId()).name(entity.getName()).build();
		return  brandRespDTO;
	}
}
