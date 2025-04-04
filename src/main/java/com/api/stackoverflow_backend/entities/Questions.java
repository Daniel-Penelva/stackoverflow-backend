package com.api.stackoverflow_backend.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.api.stackoverflow_backend.dtos.QuestionsDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "questions")
@Data
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O título é obrigatório")
    @Size(min = 3, max = 50, message = "O título deve ter entre 3 e 50 caracteres")
    private String title;

    @Lob
    @Column(name = "body", length = 512)
    @NotNull(message = "O corpo de texto é obrigatório")
    private String body;
    
    @NotNull(message = "A data de criação da resposta não pode estar vazia")
    private Date createdDate;

    @NotNull(message = "As tags não podem estar vazias")
    private List<String> tags;

    // Muitas perguntas são feitas por um único usuário
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer voteCount = 0;

    // Uma pergunta pode ter vários votos
    @OneToMany(mappedBy = "questions", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<QuestionVote> questionVoteList;

    public QuestionsDTO getQuestionDto() {
        QuestionsDTO questionsDTO = new QuestionsDTO();
        questionsDTO.setId(id);
        questionsDTO.setTitle(title);
        questionsDTO.setBody(body);
        questionsDTO.setTags(tags);
        questionsDTO.setCreatedDate(createdDate);
        questionsDTO.setUserId(user.getId());
        questionsDTO.setUsername(user.getName());
        questionsDTO.setVoteCount(voteCount);

        return questionsDTO;
    }
}
