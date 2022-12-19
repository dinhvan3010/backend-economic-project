package net.codejava.controller;

import net.codejava.model.Brand;
import net.codejava.services.IListConverter;
import net.codejava.converter.BrandConverter;
import net.codejava.dto.BrandRespDTO;
import net.codejava.repository.BrandRepository;
import net.codejava.response.StatusResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController extends AbstractRestController {

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    IListConverter converter;

    @GetMapping("/list")
    public StatusResp getProductsFavourite() {
        StatusResp resp = new StatusResp();
        List<Brand> list = brandRepository.findAll();
        List<BrandRespDTO> brandRespDTOS = converter.toListResponse(list, BrandConverter::toRespDTO);
        resp.setData(brandRespDTOS);
        return resp;
    }


}
