package com.ravi.s3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/")
	public Map<String, String> home() {
		return Map.of(
			"project", "87-s3-file-service",
			"tier", "9 - DevOps + Cloud",
			"status", "Live on AWS EC2 - S3 Ready",
			"owner", "RaviTeja",
			"upload", "POST /upload",
			"files", "GET /files"
		);
	}

	@GetMapping("/health")
	public Map<String, String> health() {
		return Map.of("status", "UP");
	}
}