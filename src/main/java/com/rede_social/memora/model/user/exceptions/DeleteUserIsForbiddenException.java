package com.rede_social.memora.model.user.exceptions;

public class DeleteUserIsForbiddenException extends RuntimeException {
    public DeleteUserIsForbiddenException(String message){
        super(message);
    }
}
