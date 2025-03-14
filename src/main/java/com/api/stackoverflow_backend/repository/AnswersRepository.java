package com.api.stackoverflow_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.stackoverflow_backend.entities.Answers;

@Repository
public interface AnswersRepository extends JpaRepository<Answers, Long> {
    
    List<Answers> findAllByQuestions_Id(Long questionsId);
}
