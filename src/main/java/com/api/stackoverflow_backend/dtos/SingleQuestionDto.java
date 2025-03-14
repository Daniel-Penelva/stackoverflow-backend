package com.api.stackoverflow_backend.dtos;

import java.util.List;

import lombok.Data;

@Data
public class SingleQuestionDto {

    private QuestionsDTO questionsDTO;

    private List<AnswersDTO> answersDTOList;

}
