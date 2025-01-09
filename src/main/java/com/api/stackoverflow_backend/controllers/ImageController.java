package com.api.stackoverflow_backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.stackoverflow_backend.services.user.image.ImageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;

    // Construtor com injeção de dependência
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/image/{answerId}")
    public ResponseEntity<String> uploadFile(@Valid @RequestParam MultipartFile multipartFile, @PathVariable Long answerId) {

        try {
            imageService.storeFile(multipartFile, answerId);
            return ResponseEntity.ok("Imagem salva com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar imagem!" + e.getMessage());
        }

    }

}
