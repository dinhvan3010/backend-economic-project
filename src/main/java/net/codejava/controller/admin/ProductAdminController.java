package net.codejava.controller.admin;

import net.codejava.controller.AbstractRestController;
import net.codejava.converter.ProductConverter;
import net.codejava.converter.ProductDetailConverter;
import net.codejava.dto.DataPagingResp;
import net.codejava.dto.ProductDetailRespDTO;
import net.codejava.dto.ProductRespDTO;
import net.codejava.exceptions.MyAppException;
import net.codejava.model.Product;
import net.codejava.response.StatusResp;
import net.codejava.services.IListConverter;
import net.codejava.services.IManageProductService;
import net.codejava.utils.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/admin/product")
public class ProductAdminController extends AbstractRestController {

    @Autowired
    IManageProductService productService;

    @GetMapping("/list")
    public StatusResp getProducts(@RequestParam ( required = false)int brandId,
                                  @RequestParam ( required = false) int categoryId,
                                  @RequestParam ( required = false) String name,
                                  @RequestParam  int pageNum,
                                  @RequestParam int pageSize) {
        StatusResp resp = new StatusResp();
        Pageable paging = PageRequest.of(pageNum, pageSize);
        DataPagingResp<ProductRespDTO> dataPagingResp = productService.getProduct(pageNum, pageSize,brandId ,categoryId, name);
        resp.setData(dataPagingResp);
        return resp;
    }

    @GetMapping("/detail")
    public StatusResp getProductDetail(@RequestParam ( required = false)int productId) {
        StatusResp resp = new StatusResp();
        Product product = productService.getProductDetail(productId);
        if(product == null){
            throw new MyAppException(StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getMessage(),
                    StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getCode());
        }
        ProductDetailRespDTO productDetailRespDTO = ProductDetailConverter.toRespDTO(product);
        resp.setData(productDetailRespDTO);
        return resp;
    }

    @GetMapping("/delete")
    public StatusResp deleteProduct(@RequestParam ( required = false)int productId) {
        StatusResp resp = new StatusResp();
        Product product = productService.getProductDetail(productId);
        if(product == null){
            throw new MyAppException(StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getMessage(),
                    StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getCode());
        }
        productService.deleteProduct(productId);
        return resp;
    }



}
