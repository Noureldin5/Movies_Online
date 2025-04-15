package org.example.midterm.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {

    private String email;
    private String username;
    private String password;
    private boolean emailVerified;

}
