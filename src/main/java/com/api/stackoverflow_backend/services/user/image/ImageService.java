package com.api.stackoverflow_backend.services.user.image;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void storeFile(MultipartFile multipartFile, Long answerId) throws IOException;
    
}
