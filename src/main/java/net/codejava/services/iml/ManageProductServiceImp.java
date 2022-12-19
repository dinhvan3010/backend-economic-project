package net.codejava.services.iml;

import net.codejava.model.Product;
import net.codejava.converter.ProductConverter;
import net.codejava.dto.DataPagingResp;
import net.codejava.dto.ProductRespDTO;
import net.codejava.repository.ProductRepository;
import net.codejava.services.IListConverter;
import net.codejava.services.IManageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageProductServiceImp implements IManageProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    private IListConverter listConverter;

    @Override
    public DataPagingResp<ProductRespDTO> getProduct(int num, int size, int brandId, int categoryId, String name) {

        Page<Product> pageResult = null;
        PageRequest pageable = PageRequest.of(num, size);
        if (categoryId == 0 && brandId != 0) {
           pageResult = productRepository.findAllByBrand_Id(pageable,brandId);
        }
        if (brandId == 0 && categoryId != 0 ) {
           pageResult = productRepository.findAllByCategory_Id(pageable,categoryId);
        }
        if (brandId == 0 && categoryId == 0 ) {
            pageResult = productRepository.findByNameContaining(name , pageable);
        }
        return listConverter.toPageResponse(pageResult, ProductConverter::toRespDTO);
    }

    @Override
    public List<Product> Top5Discount() {
        return productRepository.findTop5ByOrderByDiscountDesc();
    }

}
