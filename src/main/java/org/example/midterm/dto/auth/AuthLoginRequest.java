package org.example.midterm.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthLoginRequest {
    private String username;
    private String email;
    private String password;
}