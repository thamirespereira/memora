package com.rede_social.memora.model.posts.exceptions;

public class DeletePostIsForbiddenException extends RuntimeException{
    public DeletePostIsForbiddenException (String message){
        super(message);
    }

}
