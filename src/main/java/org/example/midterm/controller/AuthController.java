package org.example.midterm.controller;

import lombok.RequiredArgsConstructor;
import org.example.midterm.dto.User.UserRegisterRequest;
import org.example.midterm.dto.auth.AuthLoginRequest;
import org.example.midterm.dto.auth.AuthResponse;
import org.example.midterm.dto.auth.TokenRefreshRequest;
import org.example.midterm.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    
    // The endpoint was "/auth/login" but should be exactly "/auth/login" without any @RequestMapping in the class level
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest authLoginRequest) {
        try {
            AuthResponse response = authService.login(authLoginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Authentication failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> apiLogin(@RequestBody AuthLoginRequest authLoginRequest) {
        try {
            AuthResponse response = authService.login(authLoginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Authentication failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            authService.register(userRegisterRequest);
            return ResponseEntity.ok("User registered successfully. Please check your email for verification.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Registration failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/auth/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        try {
            AuthResponse response = authService.refreshToken(request.getRefreshToken());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Token refresh failed: " + e.getMessage());
        }
    }
}