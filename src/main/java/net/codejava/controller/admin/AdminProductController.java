package net.codejava.controller.admin;

import net.codejava.controller.AbstractRestController;
import net.codejava.converter.ProductDetailConverter;
import net.codejava.dto.DataPagingResp;
import net.codejava.dto.ProductDetailRespDTO;
import net.codejava.dto.ProductRespDTO;
import net.codejava.exceptions.MyAppException;
import net.codejava.model.*;
import net.codejava.repository.InventoryRepository;
import net.codejava.repository.ProductImageRepository;
import net.codejava.repository.ProductRepository;
import net.codejava.request.AddProductRequest;
import net.codejava.request.InventoryRequest;
import net.codejava.request.ProductImageRequest;
import net.codejava.request.updateProductRequest;
import net.codejava.response.StatusResp;
import net.codejava.services.IManageBrandService;
import net.codejava.services.IManageCategoryService;
import net.codejava.services.IManageProductService;
import net.codejava.utils.DateUtil;
import net.codejava.utils.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/admin/product")
public class AdminProductController extends AbstractRestController {

    @Autowired
    IManageProductService productService;

    @Autowired
    IManageBrandService brandService;

    @Autowired
    IManageCategoryService categoryService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @GetMapping("/list")
    public StatusResp getProducts(@RequestParam(required = false) Integer brandId, @RequestParam(required = false) Integer categoryId, @RequestParam(required = false, defaultValue = "") String name, @RequestParam(required = true, defaultValue = "0") int pageNum, @RequestParam(required = true, defaultValue = "10") int pageSize) {
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
    public StatusResp getProductDetail(@RequestParam(required = false) int productId) {
        StatusResp resp = new StatusResp();
        Product product = productService.getProductDetail(productId);
        if (product == null) {
            throw new MyAppException(StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getCode());
        }
        ProductDetailRespDTO productDetailRespDTO = ProductDetailConverter.toRespDTO(product);
        resp.setData(productDetailRespDTO);
        return resp;
    }

    @DeleteMapping("/delete")
    public StatusResp deleteProduct(@RequestParam(required = false) int productId) {
        StatusResp resp = new StatusResp();
        Product product = productService.getProductDetail(productId);
        if (product == null) {
            throw new MyAppException(StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getCode());
        }
        productService.deleteProduct(productId);
        return resp;
    }

    @PostMapping("/add")
    public StatusResp addProduct(@RequestBody AddProductRequest request) {
        StatusResp resp = new StatusResp();
        Brand brand = brandService.findById(request.getBrandId());
        if (brand == null) {
            throw new MyAppException(StaticData.ERROR_CODE.BRAND_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.BRAND_NOT_FOUND.getCode());
        }
        Category category = categoryService.findById(request.getCategoryId());
        if (category == null) {
            throw new MyAppException(StaticData.ERROR_CODE.CATEGORY_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.CATEGORY_NOT_FOUND.getCode());
        }
        Product product = new Product();
        product.setName(request.getName());
        product.setUnitPrice(request.getUnitPrice());
        product.setDiscount(request.getDiscount());
        product.setDescription(request.getDescription());
        product.setBrand(brand);
        product.setCategory(category);
        product.setCreatedDate(DateUtil.now());
        List<ProductImageRequest> productImageRequests = request.getImages();
        if (productImageRequests.size() != 3) {
            return null;
        }
        Product productSaved = productRepository.save(product);

        List<ProductImage> productImages = new ArrayList<>();
        for (int i = 0; i < productImageRequests.size(); i++) {
            ProductImage productImage = new ProductImage();
            productImage.setUrl(productImageRequests.get(i).getUrl());
            productImage.setProduct(productSaved);
            productImages.add(productImage);
        }
        productImageRepository.saveAll(productImages);

        List<InventoryRequest> inventoryRequests = request.getInventories();
        List<Inventory> inventoryList = new ArrayList<>();
        for (int i = 0; i < inventoryRequests.size(); i++) {
            Inventory inventory = new Inventory();
            inventory.setSize(inventoryRequests.get(i).getSize());
            inventory.setQuantity(inventoryRequests.get(i).getQuantity());
            inventory.setProduct(productSaved);
            inventoryList.add(inventory);
        }
        inventoryRepository.saveAll(inventoryList);

        return resp;
    }


    @PutMapping("/update")
    public StatusResp updateProduct(@RequestBody updateProductRequest request) {
        StatusResp resp = new StatusResp();
        Product product = productRepository.findOneById(request.getId());
        if (product == null) {
            throw new MyAppException(StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getCode());
        }
        Brand brand = brandService.findById(request.getBrandId());
        if (brand == null) {
            throw new MyAppException(StaticData.ERROR_CODE.BRAND_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.BRAND_NOT_FOUND.getCode());
        }
        Category category = categoryService.findById(request.getCategoryId());
        if (category == null) {
            throw new MyAppException(StaticData.ERROR_CODE.CATEGORY_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.CATEGORY_NOT_FOUND.getCode());
        }

        if (!request.getName().equals("")) {
            product.setName(request.getName());
        }
        if (!Integer.toString(request.getUnitPrice()).equals("")) {
            product.setUnitPrice(request.getUnitPrice());
        }
        if (!Float.toString(request.getDiscount()).equals("")) {
            product.setDiscount(request.getDiscount());
        }
        if (!request.getDescription().equals("")) {
            product.setDescription(request.getDescription());
        }

        product.setBrand(brand);
        product.setCategory(category);

        List<ProductImageRequest> productImageRequests = request.getImages();
        Product productSaved = productRepository.save(product);

//        List<ProductImage> productImages = new ArrayList<>();
//        for (int i = 0; i < productImageRequests.size(); i++) {
//            ProductImage productImage = new ProductImage();
//            productImage.setUrl(productImageRequests.get(i).getUrl());
//            productImage.setProduct(productSaved);
//            productImages.add(productImage);
//        }
//        productImageRepository.saveAll(productImages);
//
//        List<InventoryRequest> inventoryRequests = request.getInventories();
//        List<Inventory> inventoryList = new ArrayList<>();
//        for (int i = 0; i < inventoryRequests.size(); i++) {
//            Inventory inventory = new Inventory();
//            inventory.setSize(inventoryRequests.get(i).getSize());
//            inventory.setQuantity(inventoryRequests.get(i).getQuantity());
//            inventory.setProduct(productSaved);
//            inventoryList.add(inventory);
//        }
//        inventoryRepository.saveAll(inventoryList);

        return resp;
    }


}
