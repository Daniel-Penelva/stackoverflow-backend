package com.api.stackoverflow_backend.services.vote;

import com.api.stackoverflow_backend.dtos.QuestionVoteDto;

public interface VoteService {

    public QuestionVoteDto addVoteToQuestion(QuestionVoteDto questionVoteDto);
    
}
