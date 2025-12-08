package com.example.safepass_api;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SafepassApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafepassApiApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return "Hello, World!";
	}

	@GetMapping("/health")
	public String healthCheck() {
		return Map.of("status", "UP").toString();
	}

}
