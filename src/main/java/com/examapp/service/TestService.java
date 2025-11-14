package com.examapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examapp.dto.StartTestRequest;
import com.examapp.dto.SubmitTestRequest;
import com.examapp.model.Challenge;
import com.examapp.model.TestSession;
import com.examapp.repository.ChallengeRepository;
import com.examapp.repository.TestSessionRepository;
import com.examapp.repository.UserAnswerRepository;

@Service
public class TestService {
    
    @Autowired
    private TestSessionRepository testSessionRepository;
    
    @Autowired
    private UserAnswerRepository userAnswerRepository;
    
    @Autowired
    private ChallengeRepository challengeRepository;
    
    public TestSession startTest(StartTestRequest request) {
        TestSession session = new TestSession();
        session.setUserId(request.getUserId());
        session.setMode(request.getMode());
        return testSessionRepository.save(session);
    }
    
    public String submitTest(SubmitTestRequest request) {
        TestSession session = testSessionRepository.findById(request.getTestSessionId())
            .orElseThrow(() -> new RuntimeException("Test session not found"));

        // Update session
        session.setEndTime(LocalDateTime.now());
        session.setCompleted(true);
        session.setScore(request.getScore());
        
        testSessionRepository.save(session);
        return "score has been updated";
    }
    
    public Challenge createChallenge(Long challengerId, Long challengedId, Long categoryId) {
        Challenge challenge = new Challenge();
        challenge.setChallengerId(challengerId);
        challenge.setChallengedId(challengedId);
        challenge.setCategoryId(categoryId);
        
        return challengeRepository.save(challenge);
    }
    
    public List<TestSession> getUserTestHistory(Long userId) {
        return testSessionRepository.findByUserIdOrderByStartTimeDesc(userId);
    }
    
    public List<Challenge> getUserChallenges(Long userId) {
        return challengeRepository.findByChallengerIdOrChallengedId(userId, userId);
    }
}