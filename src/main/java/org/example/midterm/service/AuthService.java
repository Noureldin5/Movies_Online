package org.example.midterm.service;

import org.example.midterm.dto.auth.AuthLoginRequest;
import org.example.midterm.dto.auth.AuthResponse;
import org.example.midterm.dto.auth.TokenRefreshRequest;
import org.example.midterm.dto.User.UserRegisterRequest;
import org.example.midterm.entities.User;

public interface AuthService {
    void register(UserRegisterRequest userRegisterRequest);

    AuthResponse login(AuthLoginRequest authLoginRequest);
    
    AuthResponse refreshToken(String refreshToken);

    User getUsernameFromToken(String token);
}