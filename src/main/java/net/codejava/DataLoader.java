package net.codejava;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.codejava.model.*;
import net.codejava.enums.UserRole;
import net.codejava.repository.*;
import net.codejava.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuantityBySizeRepository quantityBySizeRepository;
    @Autowired
    PasswordEncoder pe;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==================== FAKE DATA GENERATOR ==================");

        User userAdmin = new User();
        String username = "cuongqn2023@gmail.com";
        String password = "admin";
        if (!userRepository.existsByEmail(username)) {
            userAdmin.setEmail(username);
            userAdmin.setPassword(pe.encode(password));
            userAdmin.setRole(UserRole.ADMIN);
            userAdmin.setEnabled(true);
            userAdmin.setCreatedDate(DateUtil.now());
            userRepository.save(userAdmin);


        if (prodRepo.findAll().size() > 0) return;

        Faker faker = new Faker(new Locale("vi-VN"));

        List<User> users = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Profile profile = Profile.builder().firstName(faker.name().firstName()).lastName(faker.name().lastName()).image(faker.avatar().image()).birthday(faker.date().birthday()).build();

            User user = User.builder().email(faker.internet().emailAddress()).password(pe.encode(faker.internet().password())).profile(profile).build();
            users.add(user);
        }
        List<User> savedUsers = userRepository.saveAll(users);
        log.info("==================== FAKE DATA User : DONE ==================");

        List<Brand> brands = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Brand brand = Brand.builder().name(faker.rockBand().name()).build();
            brands.add(brand);
        }
        List<Brand> savedBrands = brandRepo.saveAll(brands);
        log.info("==================== FAKE DATA Brand : DONE ==================");

        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Category category = Category.builder().name(faker.company().name()).build();
            categories.add(category);
        }
        List<Category> savedCategories = cateRepo.saveAll(categories);
        log.info("==================== FAKE DATA Category : DONE ==================");

        List<Product> prods = new ArrayList<>();
        for (int i = 0; i < 100; i++) {

            Brand b = savedBrands.get(faker.number().numberBetween(0, brands.size()));
            Category c = savedCategories.get(faker.number().numberBetween(0, savedCategories.size()));
            String name = faker.commerce().productName();
            int quantity = faker.number().numberBetween(5, 250);
            String desc = faker.lorem().paragraph();

            Product prod = Product.builder().name(name).createdDate(DateUtil.now()).unitPrice(faker.number().numberBetween(200, 1000)).description(desc).discount(faker.number().randomDigit()).category(c).brand(b).build();

            prods.add(prod);
        }

        List<Product> prosSaved = prodRepo.saveAll(prods);
        log.info("==================== FAKE DATA Product :DONE ==================");


        prosSaved.forEach(e -> {
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
        log.info("==================== FAKE DATA ProductImage : DONE ==================");

        List<QuantityBySize> quantityBySizes = new ArrayList<>();
        prosSaved.forEach(e -> {
            for (int i = 36; i < 45; i++) {
                QuantityBySize quantityBySize = QuantityBySize.builder().Size(i).quantity(faker.number().numberBetween(-1, 100)).product(e).build();
                quantityBySizes.add(quantityBySize);
            }
            List<QuantityBySize> savedQBSs = quantityBySizeRepository.saveAll(quantityBySizes);
        });
        log.info("==================== FAKE DATA QuantityBySize : DONE ==================");
        log.info("==================== FAKE DATA END ==================");
    }
    }
}
