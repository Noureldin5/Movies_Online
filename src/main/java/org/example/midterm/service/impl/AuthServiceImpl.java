package org.example.midterm.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.midterm.config.JwtService;
import org.example.midterm.dto.User.UserRegisterRequest;
import org.example.midterm.dto.auth.AuthLoginRequest;
import org.example.midterm.dto.auth.AuthResponse;
import org.example.midterm.entities.RefreshToken;
import org.example.midterm.entities.User;
import org.example.midterm.repositories.UserRepository;
import org.example.midterm.service.AuthService;
import org.example.midterm.service.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @Override
    public void register(UserRegisterRequest userRegisterRequest) {
        // Check if username or email already exists
        if (userRepository.findByUsername(userRegisterRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(userRegisterRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setName(userRegisterRequest.getName());
        user.setEmailVerified(false); // Default to false until verified - make it true for testing if needed

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthLoginRequest authLoginRequest) {
        try {
            // This will throw an exception if authentication fails
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authLoginRequest.getUsername(),
                            authLoginRequest.getPassword()
                    )
            );

            User user = userRepository.findByUsername(authLoginRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Optional: Skip email verification for now (for testing purposes)
            // if (!user.isEmailVerified()) {
            //     throw new RuntimeException("Email not verified. Please verify your email first.");
            // }

            // Generate tokens
            String accessToken = JwtService.generateAccessToken(user);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

            return new AuthResponse(accessToken, refreshToken.getToken());

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        // Validate the refresh token
        RefreshToken token = refreshTokenService.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // Check if token is expired
        if (refreshTokenService.isTokenExpired(token)) {
            refreshTokenService.deleteByToken(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        // Get the user associated with the token
        User user = token.getUser();

        // Generate new access token
        String accessToken = JwtService.generateAccessToken(user);

        // Return the new access token with the same refresh token
        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public User getUsernameFromToken(String token) {
        String username = jwtService.extractUsername(token.replace("Bearer ", ""));
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}