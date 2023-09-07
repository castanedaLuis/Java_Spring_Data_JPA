package com.joseluis.pizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class JoseluisPizzeriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoseluisPizzeriaApplication.class, args);
	}

}
