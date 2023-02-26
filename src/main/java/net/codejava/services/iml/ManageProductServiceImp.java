package net.codejava.services.iml;

import net.codejava.converter.ProductConverter;
import net.codejava.dto.DataPagingResp;
import net.codejava.dto.ProductRespDTO;
import net.codejava.model.Product;
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
    public DataPagingResp<ProductRespDTO> getProduct(int num, int size, Integer brandId, Integer categoryId, String name) {

        Page<Product> pageResult = null;
        PageRequest pageable = PageRequest.of(num, size);
        if (categoryId == null && brandId != null) {
            pageResult = productRepository.findAllByBrandIdAndNameContaining(brandId, name, pageable);
        }
        if (categoryId != null && brandId == null) {
            pageResult = productRepository.findAllByCategoryIdAndNameContaining(categoryId, name, pageable);
        }
        if (categoryId != null && brandId != null) {
            pageResult = productRepository.findAllByCategoryIdAndBrandIdAndNameContaining(brandId, categoryId, name, pageable);
        }
        if (categoryId == null && brandId == null) {
            pageResult = productRepository.findByNameContaining(name, pageable);
        }
        return listConverter.toPageResponse(pageResult, ProductConverter::toRespDTO);
    }

    @Override
    public Product getProductDetail(int productId) {
        return productRepository.findOneById(productId);
    }

    @Override
    public List<Product> Top5Discount() {
        return productRepository.findTop5ByOrderByDiscountDesc();
    }

    @Override
    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }

}
