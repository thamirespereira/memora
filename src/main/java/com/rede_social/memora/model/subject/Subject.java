package com.rede_social.memora.model.subject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rede_social.memora.model.posts.Posts;
import com.rede_social.memora.model.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_subject")
public class Subject {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "O Atributo Descrição é obrigatório")
	private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject", cascade = CascadeType.DETACH)
    @JsonIgnoreProperties("subject")
    private List<Posts> posts;

    @ManyToOne
	@JsonIgnoreProperties("subject")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

    public User getUser(User user){
        return user;
    }

    public void SetUser(User user){
        this.user = user;
    }
}
