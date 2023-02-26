package net.codejava.services;

import net.codejava.model.Brand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IManageBrandService {

    List<Brand> getListBrand();

    void deleteBrand(int brandId);

    Brand getDetail(int brandId);

    Brand findById(int brandId);
}
