package org.jyb.backend.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String username;
    private String password;
}
