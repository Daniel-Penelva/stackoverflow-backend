package com.api.stackoverflow_backend.services.answers;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.stackoverflow_backend.dtos.AnswersDTO;
import com.api.stackoverflow_backend.entities.Answers;
import com.api.stackoverflow_backend.entities.Questions;
import com.api.stackoverflow_backend.entities.User;
import com.api.stackoverflow_backend.exceptions.QuestionNotFoundException;
import com.api.stackoverflow_backend.exceptions.UserNotFoundException;
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

        // Buscam o usuário e a pergunta (question) ou lança exceção
        User user = userRepository.findById(answersDTO.getUserId()).orElseThrow(() -> new UserNotFoundException(
                "Usuário não encontrado para o ID: " + answersDTO.getUserId()));

        Questions question = questionsRepository.findById(answersDTO.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException("Pergunta não encontrada para o ID: " + answersDTO.getQuestionId()));

        // Cria a entidade
        Answers answer = new Answers();
        answer.setBody(answersDTO.getBody());
        answer.setCreatedDate(new Date());
        answer.setUser(user);
        answer.setQuestions(question);

        // Salva e converte em DTO
        Answers createdAnswer = answersRepository.save(answer);
        return mapToDTO(createdAnswer);
    }

    // Mapper - método para converter em DTO
    private AnswersDTO mapToDTO(Answers answer) {
        AnswersDTO answersDTO = new AnswersDTO();
        answersDTO.setId(answer.getId());
        answersDTO.setBody(answer.getBody());
        answersDTO.setCreatedDate(answer.getCreatedDate());
        answersDTO.setUserId(answer.getUser().getId());
        answersDTO.setQuestionId(answer.getQuestions().getId());
        return answersDTO;
    }

    @Override
    public AnswersDTO approveAnswer(Long answerId) {
        Optional<Answers> optionalAnswer = answersRepository.findById(answerId);
        if (optionalAnswer.isPresent()) {
            Answers answers = optionalAnswer.get();
            answers.setApproved(true);
            Answers updateAnswer = answersRepository.save(answers);
            AnswersDTO updatedAnswerDto = new AnswersDTO();
            updatedAnswerDto.setId(updateAnswer.getId());
            return updatedAnswerDto;
        }
        return null;
    }

}
