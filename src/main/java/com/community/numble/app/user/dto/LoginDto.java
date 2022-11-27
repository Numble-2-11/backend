package com.community.numble.app.user.dto;

import lombok.Data;
import org.springframework.security.authentication.*;

@Data
public class LoginDto {

    private String username;

    private String password;
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
