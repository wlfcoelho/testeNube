package com.wlcoelho.testeNube.domain.dto;


import org.modelmapper.ModelMapper;

import com.wlcoelho.testeNube.domain.User;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;
	private String nome;
	private String email;
	private Long telefone;
	
	//copia os atributos de um objeto para o outro model mapper
	public static UserDTO create(User u) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(u, UserDTO.class);
	}
}
