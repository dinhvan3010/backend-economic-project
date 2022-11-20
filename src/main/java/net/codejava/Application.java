package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.codejava.repository.UserRepository;

@SpringBootApplication
public class Application  {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		User user = new User();
//		user.setEmail("admin");
//		user.setPassword(passwordEncoder.encode("admin"));
//		user.setRole(UserRole.ADMIN);
//		user.setEnabled(true);
//		userRepo.save(user);
//		
//	}

}
