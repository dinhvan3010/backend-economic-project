package net.codejava.repository;

import java.util.List;

import net.codejava.Model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.codejava.Model.Product;
import net.codejava.Model.User;

import javax.transaction.Transactional;


@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {

	List<WishList> findByUser_Id(Integer id);

	Boolean existsByProductAndUser(Product product, User user);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM WishList w WHERE w.id = ?1" )
	 void deleteItemWishList(int id);

	WishList findAllByProduct_IdAndUser_Id(int productId, Integer id);
}
