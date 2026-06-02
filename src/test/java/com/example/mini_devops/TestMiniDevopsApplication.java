package com.example.mini_devops;

import org.springframework.boot.SpringApplication;

public class TestMiniDevopsApplication {

	public static void main(String[] args) {
		SpringApplication.from(MiniDevopsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
