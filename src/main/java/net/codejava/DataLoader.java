package net.codejava;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.codejava.Model.Brand;
import net.codejava.Model.Category;
import net.codejava.Model.Product;
import net.codejava.Model.ProductImage;
import net.codejava.repository.BrandRepository;
import net.codejava.repository.CategoryRepository;
import net.codejava.repository.ProductImageRepository;
import net.codejava.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@AllArgsConstructor
@Slf4j
public class DataLoader implements ApplicationRunner {


    @Autowired
    private ProductRepository prodRepo;

    @Autowired
    private BrandRepository brandRepo;

    @Autowired
    private CategoryRepository cateRepo;

    @Autowired
    private ProductImageRepository piRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==================== fake data generator ==================");

        if (prodRepo.findAll().size() > 0)
            return;

        Faker faker = new Faker(new Locale("vi-VN"));

        List<Brand> brands = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Brand brand = Brand.builder().name(faker.rockBand().name()).build();

            brands.add(brand);
        }

        List<Brand> savedBrands = brandRepo.saveAll(brands);

        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Category category = Category.builder().name(faker.company().name()).build();

            categories.add(category);
        }

        List<Category> savedCategorys = cateRepo.saveAll(categories);

        List<Product> prods = new ArrayList<>();



        for (int i = 0; i < 100; i++) {



            Brand b = savedBrands.get(faker.number().numberBetween(0, brands.size()));
            Category c = savedCategorys.get(faker.number().numberBetween(0, savedCategorys.size()));

            String name = faker.lorem().sentence();

            int quantity = faker.number().numberBetween(5, 250);

            String desc = faker.lorem().paragraph();
            Product prod = Product.builder().name(name).quantity(quantity)
                    .unitPrice(faker.number().numberBetween(200, 1000)).description(desc)
                    .discount(faker.number().randomDigit()).category(c).brand(b).build();

            prods.add(prod);
        }

        List<Product> prosSaved = prodRepo.saveAll(prods);

        prosSaved.forEach(e->{
            List<String> imgUrl = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                imgUrl.add(faker.avatar().image());
            }
            List<ProductImage> pis = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                ProductImage pi = ProductImage.builder().url(imgUrl.get(j)).product(e).build();
                pis.add(pi);
            }
            List<ProductImage> saveAll = piRepo.saveAll(pis);
        });
    }
}
