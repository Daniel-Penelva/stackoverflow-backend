package com.api.stackoverflow_backend.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.api.stackoverflow_backend.dtos.AnswersDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Answers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "body", length = 512)
    @NotNull(message = "O corpo de texto é obrigatório")
    private String body;

    @NotNull(message = "A data de criação da resposta não pode estar vazia")
    private Date createdDate;

    // Muitas respostas são feitas por um único usuário
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    // Muitas respostas são feitas por uma único pergunta
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Questions questions;

    private boolean approved = false;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer voteCount = 0;

    @OneToMany(mappedBy = "answers", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AnswerVote> answerVotes;


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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Questions getQuestions() {
        return questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
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

    public List<AnswerVote> getAnswerVotes() {
        return answerVotes;
    }

    public void setAnswerVotes(List<AnswerVote> answerVotes) {
        this.answerVotes = answerVotes;
    }

    public AnswersDTO getAnswersDto() {
        AnswersDTO answersDTO = new AnswersDTO();
        answersDTO.setId(id);
        answersDTO.setBody(body);
        answersDTO.setUserId(user.getId());
        answersDTO.setQuestionId(questions.getId());
        answersDTO.setApproved(approved);
        answersDTO.setCreatedDate(createdDate);
        answersDTO.setUsername(user.getName());

        return answersDTO;
    }

}
