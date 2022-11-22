package net.codejava.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import net.codejava.Model.User;
import net.codejava.enums.Gender;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	@Query(value = "Select u From User u Where u.email = ?1")
	User findUserByEmail(String email);

	Boolean existsByEmail(String email);

//	@Query(value = "CALL `economicproject`.insert_user(:email , :password, :firstName, :lastName , :image  ,:gender);", nativeQuery = true)
	@Procedure(value = "insert_user")
	void CreaterAccount(String email, String password, String firstName, String lastName, String image, Gender gender);

}
