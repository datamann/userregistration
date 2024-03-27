package no.sivertsensoftware.userregistration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import no.sivertsensoftware.userregistration.model.User;
import no.sivertsensoftware.userregistration.repository.UserRepository;

@SpringBootApplication
@EnableFeignClients
//@EnableMethodSecurity
public class UserregistrationApplication implements ApplicationRunner{

	//private static final Logger logger = LogManager.getLogger(UserregistrationApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UserregistrationApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository) {
		return args -> {
			User user1 = new User(null, null, null, null);
			user1.setId(null);
			user1.setFirst_name("Ola");
			user1.setLast_name("Normann");
			user1.setEmail("ola.normann@gmail.com");
			userRepository.save(user1);
		};
	}

	// Used by Keycloak
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

	@Override
	public void run(ApplicationArguments args) throws Exception {
	}

}