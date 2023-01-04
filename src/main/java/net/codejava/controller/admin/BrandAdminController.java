package net.codejava.controller.admin;

import net.codejava.controller.AbstractRestController;
import net.codejava.converter.BrandConverter;
import net.codejava.dto.BrandRespDTO;
import net.codejava.model.Brand;
import net.codejava.repository.BrandRepository;
import net.codejava.response.StatusResp;
import net.codejava.services.IListConverter;
import net.codejava.services.IManageBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/brand")
public class BrandAdminController extends AbstractRestController {

    @Autowired
    IManageBrandService manageBrandService;

    @Autowired
    IListConverter converter;

    @GetMapping("/list")
    public StatusResp getProductsFavourite() {
        StatusResp resp = new StatusResp();
        List<Brand> list = manageBrandService.getListBrand();
        List<BrandRespDTO> brandRespDTOS = converter.toListResponse(list, BrandConverter::toRespDTO);
        resp.setData(brandRespDTOS);
        return resp;
    }

    @GetMapping("/delete")
    public StatusResp deleteBrand(@RequestParam int brandId) {
        StatusResp resp = new StatusResp();
        manageBrandService.deleteBrand(brandId);
        return resp;
    }


}
