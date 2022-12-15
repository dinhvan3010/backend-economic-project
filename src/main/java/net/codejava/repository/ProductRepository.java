package net.codejava.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.codejava.Model.Brand;
import net.codejava.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Page<Product> findByNameContaining(String name, Pageable pageable);

	Product findOneById(int productId);

	List<Product> findAllByBrand(Brand brand);

	Page<Product>  findAllByBrand_Id(Pageable page , int brandId);

	Page<Product> findAllByCategory_Id(Pageable page, int categoryId);

	List<Product> findAllByBrand_IdAndCategory_Id(int brandId, int categoryId);


}
