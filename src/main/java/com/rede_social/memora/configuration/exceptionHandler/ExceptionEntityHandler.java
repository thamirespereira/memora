package com.rede_social.memora.configuration.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rede_social.memora.model.posts.exceptions.DeletePostIsForbiddenException;
import com.rede_social.memora.model.posts.exceptions.PostNotFoundException;
import com.rede_social.memora.model.subject.exceptions.DeleteSubjectIsForbiddenException;
import com.rede_social.memora.model.subject.exceptions.SubjectNotFoundException;
import com.rede_social.memora.model.user.exceptions.DeleteUserIsForbiddenException;
import com.rede_social.memora.model.user.exceptions.UserNotFoundException;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(DeleteUserIsForbiddenException.class)
    public ResponseEntity handleDeleteUserIsForbiddenUser(DeleteUserIsForbiddenException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFound(UserNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(DeletePostIsForbiddenException.class)
    public ResponseEntity handleDeletePostIsForbidden(DeletePostIsForbiddenException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity handlePostNotFound(PostNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(DeleteSubjectIsForbiddenException.class)
    public ResponseEntity handleDeleteSubjectIsForbidden(DeleteSubjectIsForbiddenException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity handleSubjectNotFound(SubjectNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
