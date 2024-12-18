package com.api.stackoverflow_backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.stackoverflow_backend.dtos.QuestionsDTO;
import com.api.stackoverflow_backend.services.questions.QuestionsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/question")
public class QuestionsController {

    private final QuestionsService questionsService;

    // Construtor
    public QuestionsController(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @PostMapping
    public ResponseEntity<?> postQuestion(@Valid @RequestBody QuestionsDTO questionsDTO) {
        QuestionsDTO createdQuestionDto = questionsService.addQuestion(questionsDTO);
        if (createdQuestionDto == null) {
            return new ResponseEntity<>("Algo deu errado", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDto);
    }

}
