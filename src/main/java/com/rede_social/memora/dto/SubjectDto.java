package com.rede_social.memora.dto;

import java.util.List;

import com.rede_social.memora.model.posts.Posts;
import com.rede_social.memora.model.subject.Subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {
    
    private Long id;
    private String description;
    private List<Posts> posts;

    public SubjectDto(Subject subject){
        id = subject.getId();
        description = subject.getDescription();
        posts = subject.getPosts();
    }
}
