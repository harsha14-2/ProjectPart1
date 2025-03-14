package com.example.SirRegLogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.example.SirRegLogin.model.User;
import com.example.SirRegLogin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        logger.info("📌 Login page accessed");
        return "login"; 
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        logger.info("📌 Registration page accessed");
        return "register"; 
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model) {
        logger.info("📩 Registration attempt - Username: {}", user.getUsername());

        if (result.hasErrors()) {
            logger.error("❌ Validation errors: {}", result.getAllErrors());
            return "register"; 
        }

        try {
            userService.registerUser(user);
            logger.info("✅ Registration successful - Username: {}, Email: {}, Phone: {}, Gender: {}",
                    user.getUsername(), user.getEmail(), user.getPhone(), user.getGender());

            return "redirect:/login?success=Registration successful! Please login.";
        } catch (RuntimeException e) {
            logger.error("❌ Registration failed - Reason: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "register"; // Stay on register if registration fails
        }
    }

    @GetMapping("/login-success")
    public String loginSuccess(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails user = (UserDetails) authentication.getPrincipal();

            String username = user.getUsername();
            String ipAddress = getClientIpAddress(request); // Extract real IP
            String roles = authentication.getAuthorities().toString();

            logger.info("\n==============================\n" +
                        "🎉 SUCCESSFUL LOGIN\n" +
                        "👤 User: {}\n" +
                        "🌍 IP Address: {}\n" +
                        "🔑 Roles: {}\n" +
                        "==============================", username, ipAddress, roles);
        } else {
            logger.warn("\n==============================\n" +
                        "⚠️ LOGIN ATTEMPT FAILED\n" +
                        "❌ No valid authentication detected\n" +
                        "==============================");
        }
        return "redirect:/home"; // Redirect to homepage after login
    }

    //  Utility method to get the real client IP address
    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
