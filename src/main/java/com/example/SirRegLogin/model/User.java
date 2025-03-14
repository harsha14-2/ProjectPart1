package com.example.SirRegLogin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * User Entity
 * Represents a user in the system with their authentication and profile information.
 * This class is mapped to the 'users' table in the database and includes
 * validation constraints for data integrity.
 */
@Entity
@Table(name = "users")
@Data // Lombok annotation to generate getters, setters, equals, hashCode, and toString
public class User {

    /** Unique identifier for the user (auto-generated) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** User's username (must be unique and between 3-50 characters) */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(unique = true, nullable = false)
    private String username;

    /** User's email address (must be unique and valid) */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;

    /** User's password (must be at least 6 characters long) */
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(nullable = false)
    private String password; 

    /** User's date of birth (cannot be null) */
    @NotNull(message = "Date of Birth is required")
    private LocalDate dob;

    /** User's phone number (must be exactly 10 digits) */
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    @Column(unique = true, nullable = false)
    private String phone;

    /** User's gender (Allowed values: Male, Female, Other) */
    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String gender;

    /** Default user role */
    @Column(nullable = false)
    private String role = "USER";
}
