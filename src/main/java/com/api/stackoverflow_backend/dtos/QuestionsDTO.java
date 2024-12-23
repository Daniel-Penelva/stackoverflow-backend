package com.api.stackoverflow_backend.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class QuestionsDTO {

    private Long id;
    private String title;
    private String body;
    private List<String> tags;
    private Long userId;
    private Date createdDate;
    private String username;   // para capturar o nome do usuário

}
