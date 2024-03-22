package com.rede_social.memora.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_users")
public class User {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "O Atributo Nome é Obrigatório!")
	private String name;

	@NotNull(message = "O Atributo Usuário é Obrigatório!")
	@Size(max = 100, message = "O Nome de Usuário deve ter até 100 caracteres")
	private String username;

	@NotBlank(message = "O Atributo Senha é Obrigatório!")
	@Size(min = 8, message = "A Senha deve ter no mínimo 8 caracteres")
	private String password;

	@NotNull(message = "O Atributo Email é Obrigatório!")
	@Email(message = "O Atributo Email deve ser um email válido!")
	private String email;

	@Size(max = 5000, message = "O link da foto não pode ser maior do que 5000 caracteres")
	private String image;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("user")
	private List<Posts> posts;
}
