package com.api.stackoverflow_backend.services.vote;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.stackoverflow_backend.dtos.QuestionVoteDto;
import com.api.stackoverflow_backend.entities.QuestionVote;
import com.api.stackoverflow_backend.entities.Questions;
import com.api.stackoverflow_backend.entities.User;
import com.api.stackoverflow_backend.repository.QuestionVoteRepository;
import com.api.stackoverflow_backend.repository.QuestionsRepository;
import com.api.stackoverflow_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final QuestionVoteRepository questionVoteRepository;
    private final UserRepository userRepository;
    private final QuestionsRepository questionsRepository;

    @Override
    public QuestionVoteDto addVoteToQuestion(QuestionVoteDto questionVoteDto) {
        try {
            Optional<User> optionalUser = userRepository.findById(questionVoteDto.getUserId());
            Optional<Questions> optionalQuestions = questionsRepository.findById(questionVoteDto.getQuestionId());

            if (optionalUser.isPresent() && optionalQuestions.isPresent()) {
                QuestionVote questionVote = new QuestionVote();
                questionVote.setVoteType(questionVoteDto.getVoteType());
                questionVote.setQuestions(optionalQuestions.get());
                questionVote.setUser(optionalUser.get());

                QuestionVote votedQuestion = questionVoteRepository.save(questionVote);

                QuestionVoteDto questionVotedDto = new QuestionVoteDto();
                questionVotedDto.setId(votedQuestion.getId());
                questionVotedDto.setVoteType(votedQuestion.getVoteType());
                questionVotedDto.setUserId(optionalUser.get().getId());
                questionVotedDto.setQuestionId(optionalQuestions.get().getId());
                return questionVotedDto;
            } else {
                throw new IllegalArgumentException("Usuário ou pergunta não encontrados");
            }
        } catch (Exception e) {
            // Log the exception (use a logging framework in production)
            System.err.println("Erro ao adicionar voto: " + e.getMessage());
            throw e; // Re-throw the exception to be handled by the controller
        }
    }

}
