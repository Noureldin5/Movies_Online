package org.example.midterm.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.midterm.entities.RefreshToken;
import org.example.midterm.entities.User;
import org.example.midterm.exception.TokenRefreshException;
import org.example.midterm.repositories.RefreshTokenRepository;
import org.example.midterm.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token-expiration:86400000}")
    private Long refreshTokenDurationMs;

    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException("Refresh token was expired. Please make a new login request");
        }

        return token;
    }
}