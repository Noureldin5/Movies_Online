package org.example.midterm.controller;

import lombok.RequiredArgsConstructor;
import org.example.midterm.dto.User.UserRegisterRequest;
import org.example.midterm.dto.auth.AuthLoginRequest;
import org.example.midterm.dto.auth.AuthResponse;
import org.example.midterm.dto.auth.TokenRefreshRequest;
import org.example.midterm.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthLoginRequest authLoginRequest) {
        return authService.login(authLoginRequest);
    }
    
    @PostMapping("/refreshtoken")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        authService.register(userRegisterRequest);
        return ResponseEntity.ok("User registered successfully");
    }
}