package net.codejava.services.iml;

import net.codejava.Model.Product;
import net.codejava.services.IListConverter;
import net.codejava.services.IManageProductService;
import net.codejava.converter.ProductConverter;
import net.codejava.dto.DataPagingResp;
import net.codejava.dto.ProductRespDTO;
import net.codejava.repository.BrandRepository;
import net.codejava.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ManageProductServiceImp implements IManageProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private IListConverter listConverter;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public DataPagingResp<ProductRespDTO> getProduct(int num, int size, String name, int brandId, int categoryId) {

        Page<Product> pageResult = null;
        PageRequest pageable = PageRequest.of(num, size);
        if (categoryId == 0) {
           pageResult = productRepository.findAllByBrand_Id(pageable,brandId);
        }
        if (brandId == 0) {
           pageResult = productRepository.findAllByCategory_Id(pageable,categoryId);
        }
        return listConverter.toPageResponse(pageResult, ProductConverter::toRespDTO);
    }

}
