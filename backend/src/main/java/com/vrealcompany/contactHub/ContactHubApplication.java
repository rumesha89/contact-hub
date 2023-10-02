package com.vrealcompany.contactHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ContactHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactHubApplication.class, args);
	}

}
