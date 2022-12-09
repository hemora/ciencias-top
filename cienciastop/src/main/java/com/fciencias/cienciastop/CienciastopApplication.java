package com.fciencias.cienciastop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CienciastopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CienciastopApplication.class, args);

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// Aylin
		String endocedPassword = passwordEncoder.encode("Abc_8123");
		System.out.println(endocedPassword);
		// Daniel
		endocedPassword = passwordEncoder.encode("Abc8_123");
		System.out.println(endocedPassword);
		// Gretel
		endocedPassword = passwordEncoder.encode("Abc_8123");
		System.out.println(endocedPassword);
		// América Abc_1283
		endocedPassword = passwordEncoder.encode("Abc_1283");
		System.out.println(endocedPassword);

	}

}
