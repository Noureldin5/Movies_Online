package org.example.midterm.service;

import org.example.midterm.entities.RefreshToken;
import org.example.midterm.entities.User;

public interface  RefreshTokenService {


    RefreshToken createRefreshToken(User user);

    RefreshToken verifyExpiration(RefreshToken token);
}