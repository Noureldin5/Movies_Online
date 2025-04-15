package org.example.midterm.service.impl;

import lombok.AllArgsConstructor;
import org.example.midterm.entities.User;
import org.example.midterm.repositories.UserRepository;
import org.example.midterm.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public void registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

    }
}
