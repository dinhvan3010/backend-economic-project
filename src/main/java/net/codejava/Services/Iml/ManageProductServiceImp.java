package net.codejava.Services.Iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import net.codejava.Model.Product;
import net.codejava.Services.IListConverter;
import net.codejava.Services.IManageProductService;
import net.codejava.converter.ProductConverter;
import net.codejava.dto.DataPagingResp;
import net.codejava.dto.ProductRespDTO;
import net.codejava.repository.ProductRepository;
import org.springframework.data.domain.Sort;

@Service
public class ManageProductServiceImp implements IManageProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private IListConverter listConverter;

	@Override
	public DataPagingResp<ProductRespDTO> getProduct(int page, int size, String name, String softBy, int brandId, int cateloryId) {

		Page<Product> pageResult;
		Sort sort = Sort.by(softBy).ascending();
		PageRequest pageable = PageRequest.of(page, size, sort);

		if (name == null) {
			pageResult = productRepository.findAll(pageable);
		} else {
			pageResult = productRepository.findByNameContaining(name, pageable);
		}
		

			return listConverter.toPageResponse(pageResult, ProductConverter::toRespDTO);
	}

}
