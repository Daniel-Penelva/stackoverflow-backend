package com.api.stackoverflow_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.stackoverflow_backend.entities.Answers;
import com.api.stackoverflow_backend.entities.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByAnswer(Answers answer);
    
}
