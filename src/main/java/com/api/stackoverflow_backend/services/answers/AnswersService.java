package com.api.stackoverflow_backend.services.answers;

import com.api.stackoverflow_backend.dtos.AnswersDTO;

public interface AnswersService {

    AnswersDTO postAnswer(AnswersDTO answersDTO);
    AnswersDTO approveAnswer(Long answerId);
}
