package com.wlcoelho.testeNube.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.wlcoelho.testeNube.domain.User;
import com.wlcoelho.testeNube.domain.dto.UserDTO;
import com.wlcoelho.testeNube.domain.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository rep;
	//traz a lista de usuários
	public List<UserDTO> getUsers() {
		
		return rep.findAll().stream().map(UserDTO::create).collect(Collectors.toList());
		
	}
	//pesquisa por ID
	public Optional<UserDTO> getUserById(Long id) {
		return rep.findById(id).map(UserDTO::create);
	}
	//pesquisa por nome
	public List<UserDTO> getUsersByNome(String nome) {
		return rep.findByNome(nome).stream().map(UserDTO::create).collect(Collectors.toList());
	}	
	//pesquisa por telefone
	public List<UserDTO> getUsersByTelefone(Long telefone) {
		return rep.findByTelefone(telefone).stream().map(UserDTO::create).collect(Collectors.toList());
	}	
	//inserir um novo usuário
	public UserDTO insert(User user) {
		Assert.isNull(user.getId(), "Não foi possível o registro");
		
		return UserDTO.create(rep.save(user));
	}
	//atualiza o usuário
	public UserDTO update(User user, Long id) {
		//Verifica se o id informado não é nulo
		Assert.notNull(id, "Não foi possível atualizar o registro");
		
		//Busca o carro no banco de dados
		Optional<User> optional = rep.findById(id);
		//Verifica se o id existe
		if(optional.isPresent()) {
			User db = optional.get();
			
			//Copiar as propriedades
			db.setNome(user.getNome());
			db.setEmail(user.getEmail());
			db.setTelefone(user.getTelefone());	
			System.out.println("User id" + db.getId());
			
			//Atualiza o usuário salvo
			rep.save(db);
			
			return UserDTO.create(db);
		} else {
			throw new RuntimeException("Não foi possível o registro");
		}	
	}	
	//deleta o usuário
	public void delete(Long id) {
		//Verifica se o id existe
		if(getUserById(id).isPresent()) {
			rep.deleteById(id);
		}
	}	
}
