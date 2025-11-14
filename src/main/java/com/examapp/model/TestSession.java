package com.examapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "test_sessions")
@Data
public class TestSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String mode; // SOLO, CHALLENGE
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer score;
    private Boolean completed = false;

    
    @PrePersist
    protected void onCreate() {
        startTime = LocalDateTime.now();
    }
    

}