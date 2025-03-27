package com.api.stackoverflow_backend.dtos;

import com.api.stackoverflow_backend.entities.VoteType;

import lombok.Data;

@Data
public class AnswerVoteDto {

    private Long id;
    private VoteType voteType;
    private Long userId;
    private Long answersId;
}
