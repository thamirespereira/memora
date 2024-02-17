package com.rede_social.memora.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.rede_social.memora.model.Subject;
import com.rede_social.memora.repository.SubjectRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subjects")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;
    
    @GetMapping
    public ResponseEntity<List<Subject>> getAll(){
        return ResponseEntity.ok(subjectRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Subject> getById(@PathVariable Long id){
        return subjectRepository.findById(id)
            .map(resposta -> ResponseEntity.ok(resposta))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        
        if(subject.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        
            subjectRepository.deleteById(id);              
    }

}