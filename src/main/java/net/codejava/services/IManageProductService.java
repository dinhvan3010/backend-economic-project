package net.codejava.services;

import net.codejava.model.Product;
import org.springframework.stereotype.Service;

import net.codejava.dto.DataPagingResp;
import net.codejava.dto.ProductRespDTO;

import java.util.List;

@Service
public interface IManageProductService {

	DataPagingResp<ProductRespDTO> getProduct(int page, int size, int brandId, int cateloryId, String name);

	List<Product> Top5Discount();

}
