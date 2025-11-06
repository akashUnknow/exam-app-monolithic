package com.examapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examapp.model.Challenge;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByChallengerIdOrChallengedId(Long challengerId, Long challengedId);
    List<Challenge> findByChallengedIdAndStatus(Long challengedId, String status);
}