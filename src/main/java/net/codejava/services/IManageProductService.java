package net.codejava.services;

import org.springframework.stereotype.Service;

import net.codejava.dto.DataPagingResp;
import net.codejava.dto.ProductRespDTO;

@Service
public interface IManageProductService {

	DataPagingResp<ProductRespDTO> getProduct(int page, int size, String name , int brandId, int cateloryId);

}
