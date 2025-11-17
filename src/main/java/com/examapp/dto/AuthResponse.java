package com.examapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String userId;
    private String name;
    private String email;
    private String role;
    private String message;
    private Boolean isNewUser;
    private Boolean isVerified;

    // Custom constructor (without message and isNewUser)
    public AuthResponse(String token, String userId, String name, String email, String role, Boolean isVerified) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.isVerified = isVerified;
    }
}
