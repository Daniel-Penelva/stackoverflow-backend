package com.api.stackoverflow_backend.dtos;

import java.util.List;

import lombok.Data;

@Data
public class QuestionSearchResponseDto {

    private List<QuestionsDTO> questionsDtoList;
    private Integer totalPages;
    private Integer pageNumber;
    
}
