package net.codejava.repository;

import net.codejava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	User findOneByEmail(String email);

	Boolean existsByEmail(String email);


}
