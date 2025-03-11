package com.api.stackoverflow_backend.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.stackoverflow_backend.services.user.image.ImageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/image/{answerId}")
    public ResponseEntity<Map<String, String>> uploadImage(@PathVariable Long answerId, @RequestParam("multipartFile") MultipartFile multipartFile) {
    Map<String, String> response = new HashMap<>();
    try {
        String message = imageService.saveImage(answerId, multipartFile);
        response.put("message", message);
        return ResponseEntity.ok(response);
    } catch (IOException e) {
        response.put("error", "Erro ao salvar a imagem: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
}
