package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.codejava.Model.User;
import net.codejava.enums.UserRole;
import net.codejava.repository.UserRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepo;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		String username = "cuongqn2023@gmail.com";
		if (userRepo.existsByEmail(username)) {
			user.setEmail("");
			user.setPassword(passwordEncoder.encode("admin"));
			user.setRole(UserRole.ADMIN);
			user.setEnabled(true);
			userRepo.save(user);
		}
	}
}
