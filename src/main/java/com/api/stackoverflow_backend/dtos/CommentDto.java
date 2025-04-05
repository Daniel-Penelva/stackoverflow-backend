package com.api.stackoverflow_backend.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    private String body;
    private Date createdDate;
    private Long answersId;
    private Long userId;

}
