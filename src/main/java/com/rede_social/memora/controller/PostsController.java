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

import com.rede_social.memora.dto.PostsDto;
import com.rede_social.memora.model.posts.Posts;
import com.rede_social.memora.model.posts.exceptions.DeletePostIsForbiddenException;
import com.rede_social.memora.model.posts.exceptions.PostNotFoundException;
import com.rede_social.memora.repository.PostsRepository;
import com.rede_social.memora.repository.SubjectRepository;
import com.rede_social.memora.service.PostsService;

import jakarta.transaction.Transactional;
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
    public ResponseEntity<PostsDto> getById(@PathVariable Long id){
        return postsService.findById(id).map(postsDto -> ResponseEntity.ok(postsDto))
        .orElseThrow(()-> new PostNotFoundException("Post não encontrado."));
    }

    @GetMapping
    public ResponseEntity<List<PostsDto>> getAll(){
        return ResponseEntity.ok(postsService.findAll());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<PostsDto>> getByTitle(@PathVariable String title) {
    List<PostsDto> postsDtoList = postsService.findAllByTitleContainingIgnoreCase(title);

    if (!postsDtoList.isEmpty()) {
        return ResponseEntity.ok(postsDtoList);
    } else {
        throw new PostNotFoundException("Post não encontrado.");
    }
    } 

    @PostMapping
    @Transactional
    public ResponseEntity<Posts> post(@Valid @RequestBody Posts posts){
        if (posts.getSubject() == null) 
            return ResponseEntity.status(HttpStatus.CREATED).body(postsRepository.save(posts));
        
        if(posts.getSubject() != null && subjectRepository.existsById(posts.getSubject().getId()))
        return ResponseEntity.status(HttpStatus.CREATED).body(postsRepository.save(posts));

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!", null);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Posts> put(@Valid @RequestBody Posts posts){

        if (postsRepository.existsById(posts.getId())) {
            return ResponseEntity.status(HttpStatus.OK).body(postsRepository.save(posts));
        } else {
            throw new PostNotFoundException("Post não encontrado.");
        }
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Long id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        Optional <Posts> postsOptional = postsRepository.findById(id);

        if(postsOptional.isEmpty()){
            throw new PostNotFoundException("Post não encontrado.");
        }

        Posts post = postsOptional.get();
        String postOwnerUsername = post.getUser().getUser();

        if (!currentUsername.equals(postOwnerUsername)) {
            throw new DeletePostIsForbiddenException ("Você não tem permissão para excluir este post.");
        }

        postsRepository.deleteById(id);
    }
}
