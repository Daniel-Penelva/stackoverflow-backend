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
    private Integer voteCount;
    private int voted;         // 0 - não votou, 1 - votou positivo, -1 - votou negativo
    private boolean hasApprovedAnswer = false; // se a pergunta tem uma resposta aprovada

}
