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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final AnswersRepository answersRepository;

    public String saveImage(Long answerId, MultipartFile file) throws IOException {
        
        // Verifica se o arquivo está vazio
        if (file.isEmpty()) {
            throw new IOException("O arquivo está vazio.");
        }

        // Busca a resposta associada
        Optional<Answers> answerOptional = answersRepository.findById(answerId);
        if (answerOptional.isEmpty()) {
            throw new IOException("Resposta não encontrada para o ID: " + answerId);
        }

        Answers answer = answerOptional.get();

        // Normalizar o nome do arquivo
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));  // remove possíveis caracteres inválidos do nome do arquivo.
        if (filename.contains("..")) {
            throw new IOException("O nome do arquivo é inválido: " + filename);  // Nessa verificação impede ataques de path traversal (tentativas de acessar diretórios indevidos no servidor).
        }

        // Criar entidade Image e salvar no banco de dados
        Image image = new Image();
        image.setName(filename);
        image.setType(file.getContentType());
        image.setData(file.getBytes()); // Converte MultipartFile para byte[]
        image.setAnswer(answer);

        imageRepository.save(image);

        return "Imagem salva com sucesso!";
    }
}
