package com.rede_social.memora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rede_social.memora.dto.PostsDto;
import com.rede_social.memora.model.posts.Posts;
import com.rede_social.memora.repository.PostsRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostsService {

    @Autowired
    private PostsRepository repository; 

    public Optional<PostsDto> findById(Long id) {
        return repository.findById(id)
                                .map(PostsDto::new);
    }

    public PostsDto convertToDto(Posts posts) {
        PostsDto postsDto = new PostsDto();
        postsDto.setId(posts.getId());
        postsDto.setTitle(posts.getTitle());
        postsDto.setBody(posts.getBody());
        postsDto.setPostDate(posts.getPostDate());
        postsDto.setSubject(posts.getSubject());
        postsDto.setUser(posts.getUser());
     
        return postsDto;
    }

    public List<PostsDto> findAll(){
        List<Posts> posts = repository.findAll();
        return posts.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
    }

    public List<PostsDto> findAllByTitleContainingIgnoreCase(String title){
        List<Posts> posts = repository.findAllByTitleContainingIgnoreCase(title);
        return posts.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
    }
    
}
