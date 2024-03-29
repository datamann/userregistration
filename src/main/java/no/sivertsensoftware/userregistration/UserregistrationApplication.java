package no.sivertsensoftware.userregistration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.client.RestTemplate;

import no.sivertsensoftware.userregistration.model.User;
import no.sivertsensoftware.userregistration.repository.UserRepository;

@SpringBootApplication
@EnableFeignClients
@EnableMethodSecurity
public class UserregistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserregistrationApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository) {
		return args -> {
			User user1 = new User(null, null, null, null);
			user1.setId(null);
			user1.setFirst_name("Stig");
			user1.setLast_name("Sivertsen");
			user1.setEmail("sbsivertsen@gmail.com");
			userRepository.save(user1);
		};
	}

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}