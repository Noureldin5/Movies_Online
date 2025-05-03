package org.example.midterm.service;

import org.example.midterm.dto.auth.AuthLoginRequest;
import org.example.midterm.dto.auth.AuthResponse;
import org.example.midterm.entities.User;
import org.example.midterm.repositories.UserRepository;
import org.example.midterm.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @BeforeEach
    void setUp() {
        // Clear all users
        userRepository.deleteAll();
        
        // Create a test user
        User user = new User();
        user.setUsername("testuser");
        user.setPassword(passwordEncoder.encode("password"));
        user.setEmail("test@example.com");
        user.setEmailVerified(true);
        
        userRepository.save(user);
    }
    
    @Test
    void testLoginSuccess() {
        // Prepare login request
        AuthLoginRequest request = new AuthLoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");
        
        // Login
        AuthResponse response = authService.login(request);
        
        // Verify response
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
    }
    
    @Test
    void testRefreshToken() {
        // Login first to get tokens
        AuthLoginRequest request = new AuthLoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");
        
        AuthResponse loginResponse = authService.login(request);
        
        // Use refresh token to get new access token
        AuthResponse refreshResponse = authService.refreshToken(loginResponse.getRefreshToken());
        
        // Verify response
        assertNotNull(refreshResponse.getAccessToken());
        assertEquals(loginResponse.getRefreshToken(), refreshResponse.getRefreshToken());
    }
}