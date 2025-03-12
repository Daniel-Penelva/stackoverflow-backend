package com.api.stackoverflow_backend.services.user.image;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.api.stackoverflow_backend.entities.Answers;
import com.api.stackoverflow_backend.entities.Image;
import com.api.stackoverflow_backend.exceptions.ImageUploadException;
import com.api.stackoverflow_backend.repository.AnswersRepository;
import com.api.stackoverflow_backend.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final AnswersRepository answersRepository;

    public Map<String, String> saveImage(Long answerId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new ImageUploadException("O arquivo está vazio.");
        }

        Answers answer = answersRepository.findById(answerId)
                .orElseThrow(() -> new ImageUploadException("Resposta não encontrada para o ID: " + answerId));

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (filename.contains("..")) {
            throw new ImageUploadException("O nome do arquivo é inválido: " + filename);
        }

        try {
            Image image = new Image();
            image.setName(filename);
            image.setType(file.getContentType());
            image.setData(file.getBytes());
            image.setAnswer(answer);

            imageRepository.save(image);
        } catch (IOException e) {
            throw new ImageUploadException("Erro ao processar o arquivo.");
        }

        return Collections.singletonMap("message", "Imagem salva com sucesso!");
    }
}
