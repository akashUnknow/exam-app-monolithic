package com.examapp.dto;

import lombok.Getter;
import lombok.Setter;

public class AuthResponse {
    // Getters and Setters
    @Getter
    @Setter
    private String token;
    @Getter
    @Setter
    private String userId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String role;
    @Getter
    @Setter
    private String message;  // Added for OAuth feedback
    @Getter
    @Setter
    private Boolean isNewUser;  // Added to distinguish login vs registration
    @Getter
    @Setter
    private Boolean isVerified; // Added to indicate if user is verified

    // Constructor without message (for backward compatibility)
    public AuthResponse(String token, String userId, String name, String email, String role,Boolean isVerified ) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.isVerified = isVerified;
    }

    // Constructor with message and isNewUser
    public AuthResponse(String token, String userId, String name, String email, String role,
                        String message, Boolean isNewUser , Boolean isVerified) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.message = message;
        this.isNewUser = isNewUser;
        this.isVerified = isVerified;

    }

}