package com.api.stackoverflow_backend.services.questions;

import com.api.stackoverflow_backend.dtos.AllQuestionResponseDto;
import com.api.stackoverflow_backend.dtos.QuestionsDTO;
import com.api.stackoverflow_backend.dtos.SingleQuestionDto;

public interface QuestionsService {

    QuestionsDTO addQuestion(QuestionsDTO questionsDTO);
    AllQuestionResponseDto getAllQuestions(int pageNumber);
    SingleQuestionDto getQuestionById(Long questionId, Long userId);
    AllQuestionResponseDto getQuestionsByUserId(Long userId, int pageNumber);
}
