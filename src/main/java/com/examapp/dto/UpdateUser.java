package com.examapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUser {
    private String email;
    private Boolean isVerified;
    private String name;
    private String password;
    private String phoneNumber;
}
