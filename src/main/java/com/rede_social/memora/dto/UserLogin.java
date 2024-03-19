package com.rede_social.memora.dto;

import lombok.Data;

@Data
public class UserLogin {
    private Long id;
	private String name;
	private String user;
	private String password;
	private String image;
	private String token;
}
