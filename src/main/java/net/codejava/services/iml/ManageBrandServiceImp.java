package net.codejava.services.iml;

import net.codejava.exceptions.MyAppException;
import net.codejava.model.Brand;
import net.codejava.repository.BrandRepository;
import net.codejava.services.IManageBrandService;
import net.codejava.utils.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageBrandServiceImp implements IManageBrandService {

    @Autowired
    BrandRepository brandRepository;

    @Override
    public List<Brand> getListBrand() {
        List<Brand> list = brandRepository.findAll();
        return list;
    }

    @Override
    public void deleteBrand(int brandId) {
        Brand brand = brandRepository.findOneById(brandId);
        if (brand == null) {
            throw new MyAppException(StaticData.ERROR_CODE.BRAND_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.BRAND_NOT_FOUND.getCode());
        }
        brandRepository.deleteById(brandId);
    }

    @Override
    public Brand getDetail(int brandId) {
        return brandRepository.findOneById(brandId);
    }

    @Override
    public Brand findById(int brandId) {
        return brandRepository.findOneById(brandId);
    }
}
