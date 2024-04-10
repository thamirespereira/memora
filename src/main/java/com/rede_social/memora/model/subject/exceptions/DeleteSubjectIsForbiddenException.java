package com.rede_social.memora.model.subject.exceptions;

public class DeleteSubjectIsForbiddenException extends RuntimeException{
    public DeleteSubjectIsForbiddenException(String message){
        super(message);
    }
}
