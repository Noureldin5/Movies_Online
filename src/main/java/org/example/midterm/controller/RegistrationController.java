package org.example.midterm.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.midterm.config.EmailService;
import org.example.midterm.entities.User;
import org.example.midterm.entities.VerificationToken;
import org.example.midterm.repositories.VerificationTokenRepository;
import org.example.midterm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Set email as not verified
        user.setEmailVerified(false);
        
        // Register the user
        User savedUser = userService.registerUser(user);
        
        // Generate verification token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(savedUser);
        verificationToken.setExpiryDate(LocalDateTime.now().plusDays(1));
        verificationTokenRepository.save(verificationToken);
        
        try {
            // Send verification email
            emailService.sendVerificationEmail(user.getEmail(), token);
            return ResponseEntity.ok("User registered successfully. Please check your email for verification.");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to send verification email: " + e.getMessage());
        }
    }
    
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
            .orElse(null);
            
        if (verificationToken == null) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
        
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token expired");
        }
        
        User user = verificationToken.getUser();
        user.setEmailVerified(true);
        userService.saveUser(user);
        
        return ResponseEntity.ok("Email verified successfully");
    }
}