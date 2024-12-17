package com.api.stackoverflow_backend.services.questions;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.stackoverflow_backend.dtos.QuestionsDTO;
import com.api.stackoverflow_backend.entities.Questions;
import com.api.stackoverflow_backend.entities.User;
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

    public QuestionsDTO addQuestion(QuestionsDTO questionsDTO) {
        Optional<User> optionalUser = userRepository.findById(questionsDTO.getUserId());
        if (optionalUser.isPresent()) {
            Questions question = new Questions();
            question.setTitle(questionsDTO.getTitle());
            question.setBody(questionsDTO.getBody());
            question.setTags(questionsDTO.getTags());
            question.setCreatedDate(new Date());

            Questions createdQuestions = questionsRepository.save(question);

            QuestionsDTO createdQuestionsDto = new QuestionsDTO();
            createdQuestionsDto.setId(createdQuestions.getId());
            createdQuestionsDto.setTitle(createdQuestions.getTitle());
            createdQuestionsDto.setBody(createdQuestions.getBody());
            
            return createdQuestionsDto;

        }
        return null;
    }

    
    
}
