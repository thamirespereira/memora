package com.rede_social.memora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.rede_social.memora.model.Posts;

public interface PostsRepository extends JpaRepository <Posts, Long>{
    List<Posts> findAllByTitleContainingIgnoreCase(@Param("title") String title);
}
