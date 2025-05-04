package org.example.midterm.repositories;

import org.example.midterm.entities.RefreshToken;
import org.example.midterm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);

    void deleteByToken(String token);
}