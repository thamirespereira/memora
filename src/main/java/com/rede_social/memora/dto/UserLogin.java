package com.rede_social.memora.dto;

import lombok.Data;

@Data
public class UserLogin {
    private Long id;
	private String name;
	private String username;
	private String password;
	private String image;
	private String token;
	private String email;
}
