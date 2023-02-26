package net.codejava.controller.admin;

import net.codejava.controller.AbstractRestController;
import net.codejava.converter.BrandConverter;
import net.codejava.dto.BrandRespDTO;
import net.codejava.exceptions.MyAppException;
import net.codejava.model.Brand;
import net.codejava.repository.BrandRepository;
import net.codejava.request.AddBrandRequest;
import net.codejava.request.UpdateBrandRequest;
import net.codejava.response.StatusResp;
import net.codejava.services.IListConverter;
import net.codejava.services.IManageBrandService;
import net.codejava.utils.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/brand")
public class AdminBrandController extends AbstractRestController {

    @Autowired
    IManageBrandService manageBrandService;

    @Autowired
    IListConverter converter;

    @Autowired
    BrandRepository brandRepository;

    @GetMapping("/list")
    public StatusResp getListBrand() {
        StatusResp resp = new StatusResp();
        List<Brand> list = manageBrandService.getListBrand();
        List<BrandRespDTO> brandRespDTOS = converter.toListResponse(list, BrandConverter::toRespDTO);
        resp.setData(brandRespDTOS);
        return resp;
    }

    @PostMapping("/add")
    public StatusResp addBrand(@RequestBody AddBrandRequest request) {
        StatusResp resp = new StatusResp();
        if (brandRepository.existsByName(request.getName())) {
            throw new MyAppException(StaticData.ERROR_CODE.BRAND_EXISTED.getMessage(), StaticData.ERROR_CODE.BRAND_EXISTED.getCode());
        }
        Brand brand = new Brand();
        brand.setName(request.getName());
        brandRepository.save(brand);
        return resp;
    }

    @DeleteMapping("/delete")
    public StatusResp deleteBrand(@RequestParam int brandId) {
        StatusResp resp = new StatusResp();
        manageBrandService.deleteBrand(brandId);
        return resp;
    }

    @GetMapping("/detail")
    public StatusResp brandDetail(@RequestParam int brandId) {
        StatusResp resp = new StatusResp();
        Brand brand = manageBrandService.getDetail(brandId);
        if (brand == null) {
            throw new MyAppException(StaticData.ERROR_CODE.BRAND_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.BRAND_NOT_FOUND.getCode());
        }
        BrandRespDTO brandRespDTO = BrandConverter.toRespDTO(brand);
        resp.setData(brandRespDTO);
        return resp;
    }

    @PutMapping("/update")
    public StatusResp updateBrand(@RequestBody UpdateBrandRequest request) {
        StatusResp resp = new StatusResp();
        Brand brand = brandRepository.findOneById(request.getId());
        if (brand == null) {
            throw new MyAppException(StaticData.ERROR_CODE.BRAND_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.BRAND_NOT_FOUND.getCode());
        }
        if (brandRepository.existsByName(request.getName())) {
            throw new MyAppException(StaticData.ERROR_CODE.BRAND_EXISTED.getMessage(), StaticData.ERROR_CODE.BRAND_EXISTED.getCode());
        }
        if (!request.getName().equals("")) {
            brand.setName(request.getName());
        }
        brandRepository.save(brand);
        return resp;
    }
}
