package com.examapp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Data
public class SubmitTestRequest {
    // Getters and Setters
    private Long testSessionId;
    private LocalDateTime timeTaken= LocalDateTime.now();
    private Boolean completed = false;
    private Long score;
}