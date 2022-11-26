package net.codejava.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.codejava.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

//	@Query(value = "Select u From User u Where u.email = ?1")
//	User findUserByEmail(String email);

	User findOneByEmail(String email);

	Boolean existsByEmail(String email);

	@Query(value = "CALL `economicproject`.insert_user(:email, :password, :firstName, :lastName, :image, :gender);", nativeQuery = true)
	void CreaterAccount(@Param("email") String email,
						@Param("password") String password,
						@Param("firstName")String firstName, 
						@Param("lastName") String lastName,
						@Param("image")String image, 
						@Param("gender")String gender);

}
