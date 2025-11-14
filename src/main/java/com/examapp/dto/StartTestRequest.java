package com.examapp.dto;

import lombok.Data;

@Data
public class StartTestRequest {
    private Long userId;
    private Long categoryId;
    private String mode;
}
