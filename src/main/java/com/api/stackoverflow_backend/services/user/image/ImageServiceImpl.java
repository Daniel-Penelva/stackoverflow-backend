package com.api.stackoverflow_backend.services.user.image;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.api.stackoverflow_backend.entities.Answers;
import com.api.stackoverflow_backend.entities.Image;
import com.api.stackoverflow_backend.repository.AnswersRepository;
import com.api.stackoverflow_backend.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final AnswersRepository answerRepository;

    //Construtor com injeção de dependência
    public ImageServiceImpl(ImageRepository imageRepository, AnswersRepository answerRepository) {
        this.imageRepository = imageRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public void storeFile(MultipartFile multipartFile, Long answerId) throws IOException {
        
        Optional<Answers> optionalAnswer = answerRepository.findById(answerId);

        if (optionalAnswer.isPresent()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            Image image = new Image();
            image.setName(fileName);
            image.setAnswer(optionalAnswer.get());
            image.setType(multipartFile.getContentType());
            image.setData(multipartFile.getBytes());

            imageRepository.save(image);
            
        } else {
            throw new IOException("Resposta não encontrada para o ID: " + answerId);
        }
    }

}
