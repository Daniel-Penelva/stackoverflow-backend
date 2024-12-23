package com.api.stackoverflow_backend.services.questions;

import com.api.stackoverflow_backend.dtos.AllQuestionResponseDto;
import com.api.stackoverflow_backend.dtos.QuestionsDTO;

public interface QuestionsService {

    QuestionsDTO addQuestion(QuestionsDTO questionsDTO);
    AllQuestionResponseDto getAllQuestions(int pageNumber);

}
