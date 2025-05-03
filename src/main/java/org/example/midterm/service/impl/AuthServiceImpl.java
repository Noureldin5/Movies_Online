package org.example.midterm.service.impl;

import lombok.AllArgsConstructor;
import org.example.midterm.config.JwtService;
import org.example.midterm.dto.auth.AuthLoginRequest;
import org.example.midterm.dto.auth.AuthResponse;
import org.example.midterm.dto.User.UserRegisterRequest;
import org.example.midterm.entities.RefreshToken;
import org.example.midterm.entities.User;
import org.example.midterm.exception.TokenRefreshException;
import org.example.midterm.repositories.RefreshTokenRepository;
import org.example.midterm.repositories.UserRepository;
import org.example.midterm.service.AuthService;
import org.example.midterm.service.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void register(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(encoder.encode(userRegisterRequest.getPassword()));
        user.setEmail(userRegisterRequest.getEmail());
        user.setEmailVerified(false);
        userRepository.save(user);

    }

    @Override
    public AuthResponse login(AuthLoginRequest authLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authLoginRequest.getUsername(),
                authLoginRequest.getPassword()
            )
        );
        
        User user = userRepository.findByUsername(authLoginRequest.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            
        String accessToken = JwtService.generateAccessToken(user);
        
        // Create refresh token and store it in the database
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        
        return new AuthResponse(accessToken, refreshToken.getToken());
    }
    
    @Override
    public AuthResponse refreshToken(String requestRefreshToken) {
        return refreshTokenRepository.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                String accessToken = JwtService.generateAccessToken(user);
                return new AuthResponse(accessToken, requestRefreshToken);
            })
            .orElseThrow(() -> new TokenRefreshException("Refresh token not found in database"));
    }

    @Override
    public User getUsernameFromToken(String token) {
        // Existing implementation
        String username = jwtService.extractUsername(token);
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}