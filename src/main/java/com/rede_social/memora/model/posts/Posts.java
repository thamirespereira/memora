package com.rede_social.memora.model.posts;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rede_social.memora.model.subject.Subject;
import com.rede_social.memora.model.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "tb_posts")
@Data
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size (min = 5, max = 140, message = "O título deve conter no mínimo 5 e no máximo 140 caracteres.")
    private String title;

    @NotBlank (message = "O texto é obrigatório!")
    @Size (min = 10, message = "O texto deve conter no mínimo 10 caracteres.")
    private String body;

    @UpdateTimestamp
    private LocalDateTime postDate;

    @ManyToOne
    @JsonIgnoreProperties("posts")
    private Subject subject;

    @ManyToOne
	@JsonIgnoreProperties("posts")
    private User user;
}
