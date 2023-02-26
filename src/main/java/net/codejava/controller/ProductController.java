package net.codejava.controller;

import net.codejava.converter.ProductConverter;
import net.codejava.converter.ProductDetailConverter;
import net.codejava.dto.DataPagingResp;
import net.codejava.dto.ProductDetailRespDTO;
import net.codejava.dto.ProductRespDTO;
import net.codejava.exceptions.MyAppException;
import net.codejava.model.Product;
import net.codejava.response.StatusResp;
import net.codejava.services.IListConverter;
import net.codejava.services.IManageBrandService;
import net.codejava.services.IManageCategoryService;
import net.codejava.services.IManageProductService;
import net.codejava.utils.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/product")
public class ProductController extends AbstractRestController {

    @Autowired
     IManageProductService productService;

    @Autowired
    IManageBrandService brandService;

    @Autowired
    IManageCategoryService categoryService;

    @Autowired
     IListConverter listConverter;

    @GetMapping("/list")
    public StatusResp getProducts(@RequestParam(required = false) Integer brandId,
                                  @RequestParam(required = false) Integer categoryId,
                                  @RequestParam(required = false, defaultValue = "") String name,
                                  @RequestParam(required = true, defaultValue = "0") int pageNum,
                                  @RequestParam(required = true, defaultValue = "10") int pageSize) {
        StatusResp resp = new StatusResp();
        if (brandId != null && brandService.findById(brandId) == null) {
            throw new MyAppException(StaticData.ERROR_CODE.BRAND_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.BRAND_NOT_FOUND.getCode());
        }
        if (categoryId != null && categoryService.findById(categoryId) == null) {
            throw new MyAppException(StaticData.ERROR_CODE.CATEGORY_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.CATEGORY_NOT_FOUND.getCode());
        }
        DataPagingResp<ProductRespDTO> dataPagingResp = productService.getProduct(pageNum, pageSize, brandId, categoryId, name);
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

    @GetMapping("/top5Discount")
    public StatusResp getProductTop5Discount() {
        StatusResp resp = new StatusResp();
        List<Product> products = productService.Top5Discount();
        List<ProductRespDTO> productRespDTOS =listConverter.toListResponse(products, ProductConverter::toRespDTO);
        resp.setData(productRespDTOS);
        return resp;
    }


}
