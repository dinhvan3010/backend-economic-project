package net.codejava.services;

import net.codejava.model.Category;
import net.codejava.request.AddCateRequest;
import net.codejava.request.UpdateBrandRequest;
import net.codejava.request.UpdateCateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IManageCategoryService {

    List<Category> getListCategory();

    void deleteCategory(int categoryId);

    Category getDetail(int categoryId);

    Category findById(int categoryId);

    void addCategory(AddCateRequest request);

    void updateCategory(UpdateCateRequest request);

    boolean existsByCateName(String name);

}
