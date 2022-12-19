package net.codejava.controller;

import net.codejava.model.Category;
import net.codejava.services.IListConverter;
import net.codejava.converter.CategoryConverter;
import net.codejava.dto.CategoryRespDTO;
import net.codejava.repository.CategoryRepository;
import net.codejava.response.StatusResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController extends AbstractRestController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    IListConverter converter;

    @GetMapping("/list")
    public StatusResp getProductsFavourite() {
        StatusResp resp = new StatusResp();
        List<Category> list = categoryRepository.findAll();
        List<CategoryRespDTO> categoryRespDTOS = converter.toListResponse(list, CategoryConverter::toRespDTO);
        resp.setData(categoryRespDTOS);
        return resp;
    }


}
