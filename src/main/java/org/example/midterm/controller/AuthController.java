package org.example.midterm.controller;

import lombok.RequiredArgsConstructor;
import org.example.midterm.config.JwtService;
import org.example.midterm.dto.auth.AuthLoginRequest;
import org.example.midterm.dto.auth.AuthResponse;
import org.example.midterm.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(AuthLoginRequest authLoginRequest){
        return authService.login(authLoginRequest);
    }

}

