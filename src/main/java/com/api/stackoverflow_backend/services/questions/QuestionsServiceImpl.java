package com.api.stackoverflow_backend.services.questions;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.api.stackoverflow_backend.dtos.QuestionsDTO;
import com.api.stackoverflow_backend.entities.Questions;
import com.api.stackoverflow_backend.entities.User;
import com.api.stackoverflow_backend.exceptions.UserNotFoundException;
import com.api.stackoverflow_backend.repository.QuestionsRepository;
import com.api.stackoverflow_backend.repository.UserRepository;

@Service
public class QuestionsServiceImpl implements QuestionsService{

    private final UserRepository userRepository;
    private final QuestionsRepository questionsRepository;

    //Construtor com injeção de dependência
    public QuestionsServiceImpl(UserRepository userRepository, QuestionsRepository questionsRepository) {
        this.userRepository = userRepository;
        this.questionsRepository = questionsRepository;
    }

    @Override
    public QuestionsDTO addQuestion(QuestionsDTO questionsDTO) {

        // Validação de entrada
        validateQuestionDTO(questionsDTO);

        // Buscar o usuário ou lança exceção
        User user = userRepository.findById(questionsDTO.getUserId()).orElseThrow(
            () -> new UserNotFoundException("Usuário não encontrado para o ID: " + questionsDTO.getUserId()));

        // Cria a entidade
        Questions question = new Questions();
        question.setTitle(questionsDTO.getTitle());
        question.setBody(questionsDTO.getBody());
        question.setTags(questionsDTO.getTags());
        question.setCreatedDate(new Date());
        question.setUser(user);

        // Salva e converte em DTO
        Questions createdQuestions = questionsRepository.save(question);
        return mapToDTO(createdQuestions);
    }

    // Método de validações ao criar a pergunta
    private void validateQuestionDTO(QuestionsDTO questionsDTO) {
        if (questionsDTO.getTitle() == null || questionsDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("O título não pode estar vazio");
        } if (questionsDTO.getBody() == null || questionsDTO.getBody().isEmpty()) {
            throw new IllegalArgumentException("O corpo da pergunta não pode estar vazio");
        } if (questionsDTO.getTags() == null || questionsDTO.getTags().isEmpty()) {
            throw new IllegalArgumentException("As tags não podem estar vazias");
        } if (questionsDTO.getUserId() == null) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo.");
        }
    }

    // Mapper - método para converter em DTO 
    private QuestionsDTO mapToDTO(Questions question) {
        QuestionsDTO questionsDTO = new QuestionsDTO();
        questionsDTO.setId(question.getId());
        questionsDTO.setTitle(question.getTitle());
        questionsDTO.setBody(question.getBody());
        questionsDTO.setTags(question.getTags());
        questionsDTO.setCreatedDate(question.getCreatedDate());
        questionsDTO.setUserId(question.getUser().getId());
        return questionsDTO;
    }

}