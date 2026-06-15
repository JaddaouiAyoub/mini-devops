package com.example.mini_devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MiniDevopsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniDevopsApplication.class, args);
	}

}
