package net.codejava.controller.admin;

import net.codejava.controller.AbstractRestController;
import net.codejava.converter.CategoryConverter;
import net.codejava.dto.CategoryRespDTO;
import net.codejava.exceptions.MyAppException;
import net.codejava.model.Category;
import net.codejava.request.AddCateRequest;
import net.codejava.request.UpdateCateRequest;
import net.codejava.response.StatusResp;
import net.codejava.services.IListConverter;
import net.codejava.services.IManageCategoryService;
import net.codejava.utils.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
public class AdminCategoryController extends AbstractRestController {

    @Autowired
    IManageCategoryService categoryService;

    @Autowired
    IListConverter converter;


    @GetMapping("/list")
    public StatusResp getListCategory() {
        StatusResp resp = new StatusResp();
        List<Category> list = categoryService.getListCategory();
        List<CategoryRespDTO> categoryRespDTOS = converter.toListResponse(list, CategoryConverter::toRespDTO);
        resp.setData(categoryRespDTOS);
        return resp;
    }


    @PostMapping("/add")
    public StatusResp addBrand(@RequestBody AddCateRequest request) {
        StatusResp resp = new StatusResp();
        if (categoryService.existsByCateName(request.getName())) {
            throw new MyAppException(StaticData.ERROR_CODE.CATEGORY_NAME_EXISTED.getMessage(), StaticData.ERROR_CODE.CATEGORY_NAME_EXISTED.getCode());
        }
        categoryService.addCategory(request);
        return resp;
    }

    @DeleteMapping("/delete")
    public StatusResp deleteBrand(@RequestParam int categoryId) {
        StatusResp resp = new StatusResp();
        categoryService.deleteCategory(categoryId);
        return resp;
    }

    @GetMapping("/detail")
    public StatusResp brandDetail(@RequestParam int categoryId) {
        StatusResp resp = new StatusResp();
        Category category = categoryService.getDetail(categoryId);
        if (category == null) {
            throw new MyAppException(StaticData.ERROR_CODE.CATEGORY_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.CATEGORY_NOT_FOUND.getCode());
        }
        CategoryRespDTO categoryRespDTO = CategoryConverter.toRespDTO(category);
        resp.setData(categoryRespDTO);
        return resp;
    }

    @PutMapping("/update")
    public StatusResp updateBrand(@RequestBody UpdateCateRequest request) {
        StatusResp resp = new StatusResp();
        categoryService.updateCategory(request);
        return resp;
    }
}
