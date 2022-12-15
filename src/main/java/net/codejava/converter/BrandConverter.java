package net.codejava.converter;

import net.codejava.Model.Brand;
import net.codejava.dto.BrandRespDTO;
import net.codejava.dto.ProductRespDTO;
import net.codejava.dto.UserRespDTO;

public class BrandConverter {
	public static BrandRespDTO toRespDTO(Brand entity) {
		BrandRespDTO brandRespDTO = BrandRespDTO.builder().id(entity.getId()).name(entity.getName()).build();
		return  brandRespDTO;
	}
}
