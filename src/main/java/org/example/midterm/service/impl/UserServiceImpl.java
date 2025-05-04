package org.example.midterm.service.impl;

import lombok.AllArgsConstructor;
import org.example.midterm.entities.User;
import org.example.midterm.repositories.UserRepository;
import org.example.midterm.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public User registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        return user;
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public User findOrCreateOAuth2User(String email, String name, String provider) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User user = new User();
                    user.setEmail(email);
                    user.setName(name);
                    user.setUsername(email);
                    user.setEmailVerified(true); // Social login emails are verified

                    // Generate a random secure password
                    String randomPassword = UUID.randomUUID().toString();
                    user.setPassword(encoder.encode(randomPassword));

                    return userRepository.save(user);
                });

    }
}

