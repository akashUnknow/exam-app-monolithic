package com.examapp.dto;

import lombok.Data;

@Data
public class StartTestRequest {
    private Long userId;
    private String examName;
    private String mode;
}
