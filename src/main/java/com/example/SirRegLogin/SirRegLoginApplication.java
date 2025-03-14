package com.example.SirRegLogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Main Application Class
 * Entry point for the Spring Boot application
 * Handles application startup and configuration loading
 * 
 * Features:
 * - User registration and authentication
 * - Protected pages (home, about, map)
 * - Database integration
 * - Security configuration
 * - Caching support
 */
@SpringBootApplication
public class SirRegLoginApplication {

	/**
	 * Main method to start the Spring Boot application
	 * Initializes the application context and starts the embedded server
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SirRegLoginApplication.class, args);
	}

}
