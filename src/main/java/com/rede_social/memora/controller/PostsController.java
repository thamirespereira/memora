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

import com.rede_social.memora.dto.PostsDto;
import com.rede_social.memora.model.Posts;
import com.rede_social.memora.repository.PostsRepository;
import com.rede_social.memora.repository.SubjectRepository;
import com.rede_social.memora.service.PostsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostsController {
    
    @Autowired
    private PostsService postsService;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping(value = "/{id}")
    public PostsDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @GetMapping
    public ResponseEntity<List<Posts>> getAll(){
        return ResponseEntity.ok(postsRepository.findAll());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Posts>> getByTitle(@PathVariable String title){
        return ResponseEntity.ok(postsRepository.findAllByTitleContainingIgnoreCase(title));
    }

    @PostMapping
    public ResponseEntity<Posts> post(@Valid @RequestBody Posts posts){
        if(subjectRepository.existsById(posts.getSubject().getId()))
        return ResponseEntity.status(HttpStatus.CREATED).body(postsRepository.save(posts));

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!", null);
    }

    @PutMapping
    public ResponseEntity<Posts> put(@Valid @RequestBody Posts posts){
        if (postsRepository.existsById(posts.getId())){
            if(subjectRepository.existsById(posts.getSubject().getId()))
            return ResponseEntity.status(HttpStatus.OK).body(postsRepository.save(posts));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!", null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Long id){
        Optional <Posts> posts = postsRepository.findById(id);

        if(posts.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        postsRepository.deleteById(id);
    }
}
