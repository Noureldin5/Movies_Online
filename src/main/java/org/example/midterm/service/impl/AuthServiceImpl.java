package org.example.midterm.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.midterm.config.JwtService;
import org.example.midterm.dto.User.UserRegisterRequest;
import org.example.midterm.dto.auth.AuthLoginRequest;
import org.example.midterm.dto.auth.AuthResponse;
import org.example.midterm.entities.Customer;
import org.example.midterm.entities.User;
import org.example.midterm.repositories.UserRepository;
import org.example.midterm.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public void register(UserRegisterRequest userRegisterRequest) {
        if (userRepository.findByEmail(userRegisterRequest.getEmail()).isPresent()) {
            throw new BadCredentialsException("User with email: " + userRegisterRequest.getEmail() + " already exists!");
        }

        User user = new User();
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(encoder.encode(userRegisterRequest.getPassword()));

        Customer customer = new Customer();
        customer.setUser(user);
        customer.setName(userRegisterRequest.getUsername());
        user.setCustomer(customer);

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthLoginRequest authLoginRequest) {
        Optional<User> user = userRepository.findByEmail(authLoginRequest.getEmail());
        if (user.isEmpty()) {
            throw new BadCredentialsException("User not found");
        }

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authLoginRequest.getEmail(), authLoginRequest.getPassword())
            );
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String accessToken = jwtService.generateAccessToken(user.get());
        String refreshToken = jwtService.generateRefreshToken(user.get());

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public User getUsernameFromToken(String token) {
        String email = jwtService.extractUsername(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User not found"));
    }
}