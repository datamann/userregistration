package no.sivertsensoftware.userregistration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import no.sivertsensoftware.userregistration.model.User;
import no.sivertsensoftware.userregistration.repository.UserRepository;

@SpringBootApplication
public class UserregistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserregistrationApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository) {
		return args -> {
			User user1 = new User();
			user1.setId(null);
			user1.setFirst_name("Stig");
			user1.setLast_name("Sivertsen");
			user1.setEmail("sbsivertsen@gmail.com");
			userRepository.createUser(user1);
		};
	}

}