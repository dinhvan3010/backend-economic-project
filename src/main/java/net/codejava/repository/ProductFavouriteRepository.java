package net.codejava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.codejava.Model.Product;
import net.codejava.Model.ProductFavoutite;
import net.codejava.Model.User;

@Repository
public interface ProductFavouriteRepository extends JpaRepository<ProductFavoutite, Integer> {

	List<ProductFavoutite> findByUser_Id(Integer id);

	Boolean existsByProductAndUser(Product product, User byId);

}
