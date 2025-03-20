package com.api.stackoverflow_backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.stackoverflow_backend.dtos.QuestionVoteDto;
import com.api.stackoverflow_backend.services.vote.VoteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/vote")
    public ResponseEntity<?> addVoteToQuestion(@RequestBody QuestionVoteDto questionVoteDto) {
        try {
            QuestionVoteDto questionVotedDto = voteService.addVoteToQuestion(questionVoteDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(questionVotedDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

}
