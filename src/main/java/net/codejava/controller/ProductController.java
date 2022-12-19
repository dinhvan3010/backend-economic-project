package net.codejava.controller;

import net.codejava.model.Product;
import net.codejava.converter.ProductConverter;
import net.codejava.services.IListConverter;
import net.codejava.services.IManageProductService;
import net.codejava.dto.DataPagingResp;
import net.codejava.dto.ProductRespDTO;
import net.codejava.repository.BrandRepository;
import net.codejava.repository.ProductRepository;
import net.codejava.repository.UserRepository;
import net.codejava.response.StatusResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/product")
public class ProductController extends AbstractRestController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private IManageProductService productService;

    @Autowired
    private IListConverter listConverter;

    @GetMapping("/list")
    public StatusResp getProducts(@RequestParam ( required = false)int brandId,
                                  @RequestParam ( required = false) int cateloryId,
                                  @RequestParam ( required = false) String name,
                                  @RequestParam  int pageNum,
                                  @RequestParam int pageSize) {
        StatusResp resp = new StatusResp();
        Pageable paging = PageRequest.of(pageNum, pageSize);
        DataPagingResp<ProductRespDTO> dataPagingResp = productService.getProduct(pageNum, pageSize,brandId ,cateloryId, name);
        resp.setData(dataPagingResp);
        return resp;
    }

    @GetMapping("/top5Discount")
    public StatusResp getProductTop5Discount() {
        StatusResp resp = new StatusResp();
        List<Product> products = productService.Top5Discount();
        List<ProductRespDTO> productRespDTOS =listConverter.toListResponse(products, ProductConverter::toRespDTO);
        resp.setData(productRespDTOS);
        return resp;
    }


}
