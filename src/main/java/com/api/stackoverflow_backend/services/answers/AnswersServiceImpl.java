package com.api.stackoverflow_backend.services.answers;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.stackoverflow_backend.dtos.AnswersDTO;
import com.api.stackoverflow_backend.entities.Answers;
import com.api.stackoverflow_backend.entities.Questions;
import com.api.stackoverflow_backend.entities.User;
import com.api.stackoverflow_backend.repository.AnswersRepository;
import com.api.stackoverflow_backend.repository.QuestionsRepository;
import com.api.stackoverflow_backend.repository.UserRepository;

@Service
public class AnswersServiceImpl implements AnswersService {

    private final UserRepository userRepository;
    private final QuestionsRepository questionsRepository;
    private final AnswersRepository answersRepository;

    // Construtor
    public AnswersServiceImpl(UserRepository userRepository, QuestionsRepository questionsRepository,
            AnswersRepository answersRepository) {
        this.userRepository = userRepository;
        this.questionsRepository = questionsRepository;
        this.answersRepository = answersRepository;
    }

    @Override
    public AnswersDTO postAnswer(AnswersDTO answersDTO) {

        Optional<User> optionalUser = userRepository.findById(answersDTO.getUserId());
        Optional<Questions> optionalQuestion = questionsRepository.findById(answersDTO.getQuestionId());
        
        if (optionalUser.isPresent() && optionalQuestion.isPresent()) {
            Answers answer = new Answers();
            answer.setBody(answersDTO.getBody());
            answer.setCreatedDate(new Date());
            answer.setUser(optionalUser.get());
            answer.setQuestions(optionalQuestion.get());

            Answers createdAnswer = answersRepository.save(answer);

            AnswersDTO createdAdAnswersDTO = new AnswersDTO();
            createdAdAnswersDTO.setId(createdAnswer.getId());

            return createdAdAnswersDTO;
        }
        return null;
    }
    
}
