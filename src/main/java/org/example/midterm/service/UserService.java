package org.example.midterm.service;

import org.example.midterm.entities.User;

public interface UserService {
    User registerUser(User user);

    void saveUser(User user);

// Add to UserService interface
    User findOrCreateOAuth2User(String email, String name, String provider);
}