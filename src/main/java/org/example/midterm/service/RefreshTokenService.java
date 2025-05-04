package org.example.midterm.service;

import org.example.midterm.entities.RefreshToken;
import org.example.midterm.entities.User;

import java.util.Optional;

public interface  RefreshTokenService {


    RefreshToken createRefreshToken(User user);


    Optional<RefreshToken> findByToken(String token);

    boolean isTokenExpired(RefreshToken token);

    void deleteByToken(String token);
}