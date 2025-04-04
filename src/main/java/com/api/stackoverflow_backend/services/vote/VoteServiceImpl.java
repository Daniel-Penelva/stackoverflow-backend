package com.api.stackoverflow_backend.services.vote;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.stackoverflow_backend.dtos.AnswerVoteDto;
import com.api.stackoverflow_backend.dtos.QuestionVoteDto;
import com.api.stackoverflow_backend.entities.AnswerVote;
import com.api.stackoverflow_backend.entities.Answers;
import com.api.stackoverflow_backend.entities.QuestionVote;
import com.api.stackoverflow_backend.entities.Questions;
import com.api.stackoverflow_backend.entities.User;
import com.api.stackoverflow_backend.entities.VoteType;
import com.api.stackoverflow_backend.repository.AnswerVoteRepository;
import com.api.stackoverflow_backend.repository.AnswersRepository;
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
    private final AnswerVoteRepository answerVoteRepository;
    private final AnswersRepository answersRepository;

    @Override
    public QuestionVoteDto addVoteToQuestion(QuestionVoteDto questionVoteDto) {
        try {
            Optional<User> optionalUser = userRepository.findById(questionVoteDto.getUserId());
            Optional<Questions> optionalQuestions = questionsRepository.findById(questionVoteDto.getQuestionId());

            if (optionalUser.isPresent() && optionalQuestions.isPresent()) {
                QuestionVote questionVote = new QuestionVote();

                Questions existingQuestion = optionalQuestions.get();

                questionVote.setVoteType(questionVoteDto.getVoteType());

                if(questionVote.getVoteType() == VoteType.UPVOTE) {
                    existingQuestion.setVoteCount(existingQuestion.getVoteCount() + 1);
                } else {
                    existingQuestion.setVoteCount(existingQuestion.getVoteCount() - 1);
                }

                questionVote.setQuestions(optionalQuestions.get());
                questionVote.setUser(optionalUser.get());

                questionsRepository.save(existingQuestion);

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

    @Override
    public AnswerVoteDto addVoteToAnswer(AnswerVoteDto answerVoteDto) {
        Optional<User> optionalUser = userRepository.findById(answerVoteDto.getUserId());
        Optional<Answers> optionalAnswers = answersRepository.findById(answerVoteDto.getAnswersId());

        if (optionalUser.isPresent() && optionalAnswers.isPresent()) {
            Answers existingAnswer = optionalAnswers.get();
            User existingUser = optionalUser.get();

            AnswerVote answerVote = new AnswerVote();
            answerVote.setVoteType(answerVoteDto.getVoteType());
            answerVote.setAnswers(existingAnswer);
            answerVote.setUser(existingUser);

            if (answerVoteDto.getVoteType() == VoteType.UPVOTE) {
                existingAnswer.setVoteCount(existingAnswer.getVoteCount() + 1);
            } else {
                existingAnswer.setVoteCount(existingAnswer.getVoteCount() - 1);
            }

            answersRepository.save(existingAnswer);
            
            AnswerVote createdAnswerVote = answerVoteRepository.save(answerVote);
            AnswerVoteDto createdAnswerVotedDto = new AnswerVoteDto();
            createdAnswerVotedDto.setId(createdAnswerVote.getId());

            return createdAnswerVotedDto;
        }

        return null;
    }

}
