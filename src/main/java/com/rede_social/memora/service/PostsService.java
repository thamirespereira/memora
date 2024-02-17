package com.rede_social.memora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rede_social.memora.dto.PostsDto;
import com.rede_social.memora.model.Posts;
import com.rede_social.memora.repository.PostsRepository;

@Service
public class PostsService {

    @Autowired
    private PostsRepository repository;

    public PostsDto findById(Long id) {
        Posts entity = repository.findById(id).get(); //busca post pela id
        PostsDto postsDto = new PostsDto(entity);
        return postsDto;
    }
}
