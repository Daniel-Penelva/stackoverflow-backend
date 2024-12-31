package com.api.stackoverflow_backend.services.questions;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.stackoverflow_backend.dtos.AllQuestionResponseDto;
import com.api.stackoverflow_backend.dtos.QuestionsDTO;
import com.api.stackoverflow_backend.dtos.SingleQuestionDto;
import com.api.stackoverflow_backend.entities.Questions;
import com.api.stackoverflow_backend.entities.User;
import com.api.stackoverflow_backend.exceptions.QuestionNotFoundException;
import com.api.stackoverflow_backend.exceptions.UserNotFoundException;
import com.api.stackoverflow_backend.repository.QuestionsRepository;
import com.api.stackoverflow_backend.repository.UserRepository;

@Service
public class QuestionsServiceImpl implements QuestionsService{

    private final UserRepository userRepository;
    private final QuestionsRepository questionsRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 5;

    //Construtor com injeção de dependência
    public QuestionsServiceImpl(UserRepository userRepository, QuestionsRepository questionsRepository) {
        this.userRepository = userRepository;
        this.questionsRepository = questionsRepository;
    }

    @Override
    public QuestionsDTO addQuestion(QuestionsDTO questionsDTO) {

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

    // Mapper - método para converter em DTO 
    private QuestionsDTO mapToDTO(Questions question) {
        QuestionsDTO questionsDTO = new QuestionsDTO();
        questionsDTO.setId(question.getId());
        questionsDTO.setTitle(question.getTitle());
        questionsDTO.setBody(question.getBody());
        questionsDTO.setTags(question.getTags());
        questionsDTO.setCreatedDate(question.getCreatedDate());
        questionsDTO.setUserId(question.getUser().getId());
        questionsDTO.setUsername(question.getUser().getName()); // Adiciona o nome do usuário
        return questionsDTO;
    }

    @Override
    public AllQuestionResponseDto getAllQuestions(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Questions> questionsPage = questionsRepository.findAll(paging);
        
        AllQuestionResponseDto allQuestionResponseDto = new AllQuestionResponseDto();
        allQuestionResponseDto.setQuestionsDtoList(questionsPage.getContent().stream().map(Questions::getQuestionDto).collect(Collectors.toList()));
        allQuestionResponseDto.setPageNumber(questionsPage.getPageable().getPageNumber());
        allQuestionResponseDto.setTotalPages(questionsPage.getTotalPages());
        
        return allQuestionResponseDto;
    }

    @Override
    public SingleQuestionDto getQuestionById(Long questionId) {

    /* O exemplo abaixo é uma forma de fazer a busca por ID, mas não é a melhor forma.
        Optional<Questions> optionalQuestion = questionsRepository.findById(questionId);
        
        SingleQuestionDto singleQuestionDto = new SingleQuestionDto();
        optionalQuestion.ifPresent(question -> singleQuestionDto.setQuestionsDTO(question.getQuestionDto()));

        return singleQuestionDto;
    */ 

        Questions question = questionsRepository.findById(questionId).orElseThrow(
            () -> new QuestionNotFoundException("Pergunta não encontrada para o ID: " + questionId));

        return mapToSingleQuestionDto(question);
    }

    private SingleQuestionDto mapToSingleQuestionDto(Questions question) {
        SingleQuestionDto singleQuestionDto = new SingleQuestionDto();
        singleQuestionDto.setQuestionsDTO(mapToDTO(question));           // Aqui, posso avaliar o uso direto de question.getQuestionDto() no lugar de mapToDTO, caso o método getQuestionDto na entidade Questions já cubra todas as necessidades.
        return singleQuestionDto;
    }

}