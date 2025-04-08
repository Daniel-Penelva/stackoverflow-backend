package com.api.stackoverflow_backend.dtos;

import java.util.Date;
import java.util.List;

import com.api.stackoverflow_backend.entities.Image;

public class AnswersDTO {
    
    private Long id;
    private String body;
    private Long userId;
    private Long questionId;
    private Date createdDate;
    private String username;   // para capturar o nome do usuário
    private Image file;
    private boolean approved;
    private Integer voteCount;
    private int voted;        // 0 = não votou, 1 = votou positivo, -1 = votou negativo
    private List<CommentDto> commentDtoList; // lista de comentários da resposta

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Image getFile() {
        return file;
    }
    public void setFile(Image file) {
        this.file = file;
    }
    public boolean getApproved() {
        return approved;
    }
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    
    public Integer getVoteCount() {
        return voteCount;
    }
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
    public int getVoted() {
        return voted;
    }
    public void setVoted(int voted) {
        this.voted = voted;
    }
    public List<CommentDto> getCommentDtoList() {
        return commentDtoList;
    }
    public void setCommentDtoList(List<CommentDto> commentDtoList) {
        this.commentDtoList = commentDtoList;
    }

}
