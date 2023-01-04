package net.codejava.services.iml;

import net.codejava.model.Brand;
import net.codejava.repository.BrandRepository;
import net.codejava.services.IManageBrandService;
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
        brandRepository.deleteById(brandId);
    }
}
