package com.vrealcompany.contacthub;

import com.vrealcompany.contacthub.enums.Role;
import com.vrealcompany.contacthub.model.User;
import com.vrealcompany.contacthub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableCaching
public class ContactHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactHubApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository userRepository) {
		return (args) -> {
			User user = User.builder().firstName("Rumesha").lastName("Ranathunga")
					.email("rumesha@mail.com").password(new BCryptPasswordEncoder().encode("password"))
					.role(Role.USER).build();
			userRepository.save(user);
		};
	}

}
