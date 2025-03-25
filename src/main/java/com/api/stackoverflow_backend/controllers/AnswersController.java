package com.api.stackoverflow_backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.stackoverflow_backend.dtos.AnswersDTO;
import com.api.stackoverflow_backend.services.answers.AnswersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/answer")
public class AnswersController {

    private final AnswersService answersService;

    // Construtor
    public AnswersController(AnswersService answersService) {
        this.answersService = answersService;
    }

    @PostMapping
    public ResponseEntity<AnswersDTO> addAnswer(@Valid @RequestBody AnswersDTO answersDTO) {
        AnswersDTO createAnswersDTO = answersService.postAnswer(answersDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAnswersDTO);
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<AnswersDTO> approveAnswer(@PathVariable Long answerId) {
        AnswersDTO approvedAnswerDto = answersService.approveAnswer(answerId);
        if (approvedAnswerDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(approvedAnswerDto);
    }
}
