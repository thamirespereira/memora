package com.rede_social.memora.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rede_social.memora.dto.SubjectDto;
import com.rede_social.memora.model.posts.Posts;
import com.rede_social.memora.model.subject.Subject;
import com.rede_social.memora.model.subject.exceptions.DeleteSubjectIsForbiddenException;
import com.rede_social.memora.model.subject.exceptions.SubjectNotFoundException;
import com.rede_social.memora.repository.SubjectRepository;
import com.rede_social.memora.service.SubjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subjects")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectService subjectService;
    
    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAll(){
        return ResponseEntity.ok(subjectService.findAll());
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<SubjectDto> getById(@PathVariable Long id){
        return subjectService.findById(id).map(subjectDto -> ResponseEntity.ok(subjectDto))
        .orElseThrow(()-> new SubjectNotFoundException("Tema não encontrado."));
    }
    
    @GetMapping("/description/{description}")
    public ResponseEntity<List<Subject>> getByTitle(@PathVariable 
    String description){
        return ResponseEntity.ok(subjectRepository
            .findAllByDescriptionContainingIgnoreCase(description));
    }
    
    @PostMapping
    public ResponseEntity<Subject> post(@Valid @RequestBody Subject subject){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subjectRepository.save(subject));
    }
    
    @PutMapping
    public ResponseEntity<Subject> put(@Valid @RequestBody Subject subject){
        return subjectRepository.findById(subject.getId())
            .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
            .body(subjectRepository.save(subject)))
            .orElseThrow(()-> new SubjectNotFoundException("Tema não encontrado."));
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        
        if(subjectOptional.isEmpty())
            throw new SubjectNotFoundException("Tema não encontrado.");

        Subject subject  = subjectOptional.get();
        String subjectOwnerUsername = subject.getUser().getUser();

        if (!currentUsername.equals(subjectOwnerUsername)) {
            throw new DeleteSubjectIsForbiddenException( "Você não tem permissão para excluir este tema.");
        }

            subjectRepository.deleteById(id);              
    }

}
