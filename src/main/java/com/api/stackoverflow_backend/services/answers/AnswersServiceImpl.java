package com.api.stackoverflow_backend.services.answers;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.api.stackoverflow_backend.dtos.AnswersDTO;
import com.api.stackoverflow_backend.dtos.CommentDto;
import com.api.stackoverflow_backend.entities.Answers;
import com.api.stackoverflow_backend.entities.Comment;
import com.api.stackoverflow_backend.entities.Questions;
import com.api.stackoverflow_backend.entities.User;
import com.api.stackoverflow_backend.exceptions.AnswersNotFoundException;
import com.api.stackoverflow_backend.exceptions.QuestionNotFoundException;
import com.api.stackoverflow_backend.exceptions.UserNotFoundException;
import com.api.stackoverflow_backend.repository.AnswersRepository;
import com.api.stackoverflow_backend.repository.CommentRepository;
import com.api.stackoverflow_backend.repository.QuestionsRepository;
import com.api.stackoverflow_backend.repository.UserRepository;

@Service
public class AnswersServiceImpl implements AnswersService {

    private final UserRepository userRepository;
    private final QuestionsRepository questionsRepository;
    private final AnswersRepository answersRepository;
    private final CommentRepository commentRepository;

    // Construtor
    public AnswersServiceImpl(UserRepository userRepository, QuestionsRepository questionsRepository,
            AnswersRepository answersRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.questionsRepository = questionsRepository;
        this.answersRepository = answersRepository;
        this.commentRepository = commentRepository;
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
        answersDTO.setApproved(answer.getApproved());
        return answersDTO;
    }

    @Override
    public AnswersDTO approveAnswer(Long answerId) {

        Answers answers = answersRepository.findById(answerId).orElseThrow(
            () -> new AnswersNotFoundException("Resposta não encontrada para o ID: " + answerId));
            
        answers.setApproved(true);
        Answers updateAnswer = answersRepository.save(answers);
        AnswersDTO updatedAnswerDto = new AnswersDTO();
        updatedAnswerDto.setId(updateAnswer.getId());
        return updatedAnswerDto;
    }

    @Override
    public CommentDto postCommentToAnswer(CommentDto commentDto) {

        // Busca a resposta (answer) ou lança exceção
        Answers answer = answersRepository.findById(commentDto.getAnswersId()).orElseThrow(() -> new AnswersNotFoundException(
                "Resposta não encontrada para o ID: " + commentDto.getAnswersId()));

        // Busca o usuário ou lança exceção
        User user = userRepository.findById(commentDto.getUserId()).orElseThrow(() -> new UserNotFoundException(
                "Usuário não encontrado para o ID: " + commentDto.getUserId()));

        // Cria a entidade de comentário
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setBody(commentDto.getBody());
        comment.setCreatedDate(new Date());
        comment.setAnswers(answer);
        comment.setUser(user);

        // Salva o comentário
        Comment createdComment = commentRepository.save(comment);

        // Converte para DTO
        CommentDto createdCommentDto = new CommentDto();
        createdCommentDto.setId(createdComment.getId());
        createdCommentDto.setBody(createdComment.getBody());
        createdCommentDto.setCreatedDate(createdComment.getCreatedDate());
        createdCommentDto.setAnswersId(createdComment.getAnswers().getId());
        createdCommentDto.setUserId(createdComment.getUser().getId());

        return createdCommentDto;
    }

}
