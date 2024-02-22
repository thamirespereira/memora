package com.rede_social.memora.dto;

import java.time.LocalDateTime;
import com.rede_social.memora.model.Posts;
import com.rede_social.memora.model.Subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsDto {

    private Long id;
    private String title;
    private String body;
    private LocalDateTime postDate;
    private Subject subject;

    public PostsDto(Posts posts){
        id = posts.getId();
        title = posts.getTitle();
        body = posts.getBody();
        postDate = posts.getPostDate();
        subject = posts.getSubject();
    }

}
