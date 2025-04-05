package com.api.stackoverflow_backend.exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message) {
        super(message);
    }

    public CommentNotFoundException(Long commentId) {
        super("Comentário não encontrado com id: " + commentId);
    }
    
}
