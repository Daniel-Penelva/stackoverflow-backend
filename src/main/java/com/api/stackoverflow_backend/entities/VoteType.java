package com.api.stackoverflow_backend.entities;

public enum VoteType {

    DOWNVOTE(-1),
    UPVOTE(1);

    private final int direction; // Atributo para armazenar o valor

    VoteType(int direction) {
        this.direction = direction; // O valor é armazenado
    }

    public int getDirection() {
        return direction; // Permite acessar o valor numérico
    }
    
}
