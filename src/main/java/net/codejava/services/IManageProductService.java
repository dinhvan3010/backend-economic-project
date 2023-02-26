package net.codejava.services;

import net.codejava.dto.DataPagingResp;
import net.codejava.dto.ProductRespDTO;
import net.codejava.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IManageProductService {

	DataPagingResp<ProductRespDTO> getProduct(int page, int size, Integer brandId, Integer categoryId, String name);

	Product getProductDetail(int productId);

	List<Product> Top5Discount();

	void deleteProduct(int productId);

}
