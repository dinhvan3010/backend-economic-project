package net.codejava.controller;

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
    IListConverter converter;

    @GetMapping("/list")
    public StatusResp getProducts(@RequestParam(required = false) String name,
                                  @RequestParam ( required = false)int brandId,
                                  @RequestParam ( required = false) int cateloryId,
                                  @RequestParam  int pageNum,
                                  @RequestParam int pageSize) {
        StatusResp resp = new StatusResp();
        Pageable paging = PageRequest.of(pageNum, pageSize);
        DataPagingResp<ProductRespDTO> dataPagingResp = productService.getProduct(pageNum, pageSize, name, brandId ,cateloryId);
        resp.setData(dataPagingResp);
        return resp;
    }
}
