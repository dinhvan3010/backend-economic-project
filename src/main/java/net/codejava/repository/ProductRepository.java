package net.codejava.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.codejava.model.Brand;
import net.codejava.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByNameContaining(String name, Pageable pageable);

    Product findOneById(int productId);

    List<Product> findTop5ByOrderByDiscountDesc();

    Page<Product> findAllByBrandIdAndNameContaining(int brandId, String name, Pageable pageable);

    Page<Product> findAllByCategoryIdAndNameContaining(int categoryId, String name, Pageable pageable);

    Page<Product> findAllByCategoryIdAndBrandIdAndNameContaining(int brandId, int categoryId, String name, Pageable pageable);
}
