package net.codejava.services.iml;

import net.codejava.exceptions.MyAppException;
import net.codejava.model.Category;
import net.codejava.repository.CategoryRepository;
import net.codejava.request.AddCateRequest;
import net.codejava.request.UpdateCateRequest;
import net.codejava.services.IManageCategoryService;
import net.codejava.utils.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageCategoryServiceImp implements IManageCategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getListCategory() {
        List<Category> list = categoryRepository.findAll();
        return list;
    }

    @Override
    public void deleteCategory(int categoryId) {
        Category category = categoryRepository.findOneById(categoryId);
        if (category == null) {
            throw new MyAppException(StaticData.ERROR_CODE.BRAND_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.BRAND_NOT_FOUND.getCode());
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Category getDetail(int categoryId) {
        return categoryRepository.findOneById(categoryId);
    }

    @Override
    public Category findById(int categoryId) {
        return categoryRepository.findOneById(categoryId);
    }

    @Override
    public void addCategory(AddCateRequest request) {
        Category category = new Category();
        if (!request.getName().equals("")) {
            category.setName(request.getName());
        }
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(UpdateCateRequest request) {
        Category category = categoryRepository.findOneById(request.getId());
        if (category == null) {
            throw new MyAppException(StaticData.ERROR_CODE.CATEGORY_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.CATEGORY_NOT_FOUND.getCode());
        }
        if (categoryRepository.existsByName(request.getName())) {
            throw new MyAppException(StaticData.ERROR_CODE.CATEGORY_NAME_EXISTED.getMessage(), StaticData.ERROR_CODE.CATEGORY_NAME_EXISTED.getCode());
        }
        if (!request.getName().equals("")) {
            category.setName(request.getName());
        }
        categoryRepository.save(category);
    }

    @Override
    public boolean existsByCateName(String name) {
        return categoryRepository.existsByName(name);
    }
}
