package com.api.stackoverflow_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.stackoverflow_backend.entities.QuestionVote;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long>{
    
}
