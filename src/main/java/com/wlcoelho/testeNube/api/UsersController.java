package com.wlcoelho.testeNube.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wlcoelho.testeNube.domain.User;
import com.wlcoelho.testeNube.domain.dto.UserDTO;
import com.wlcoelho.testeNube.domain.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity get() {

		return ResponseEntity.ok(service.getUsers());
		
	}
	//pesquisa por ID
	@GetMapping("/{id}")
	public ResponseEntity get(@PathVariable("id") Long id) {
		//faz a pesquisa na lista de usuários
		Optional<UserDTO> user = service.getUserById(id);
		//se ele exitir retorna http 200 OK caso contrário http notfound 400.
		return user.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	//pesquisa por nome 204 no content 
	@GetMapping("/nome/{nome}")
	public ResponseEntity getUsersByNome(@PathVariable("nome") String nome) {
		//faz a pesquisa na lista de usuário
		List<UserDTO> user = service.getUsersByNome(nome);	
		//se o item pesquisa não existir retorna 204no content se existir 200ok
		return user.isEmpty() ?
				ResponseEntity.noContent().build() :
				ResponseEntity.ok(user);
	}
	//pesquisa por nome 204 no content 
	@GetMapping("/telefone/{telefone}")
	public ResponseEntity  getUsersByTelefone(@PathVariable("telefone") Long telefone) {
		//faz a pesquisa na lista de usuário
		List<UserDTO> user = service.getUsersByTelefone(telefone);	
		//se o item pesquisa não existir retorna 204no content se existir 200ok
		return user.isEmpty() ?
				ResponseEntity.noContent().build() :
				ResponseEntity.ok(user);
	}
	
	@PostMapping
	public ResponseEntity post(@RequestBody User user) {
		
		UserDTO u = service.insert(user);
		
		URI location = getUri(u.getId());
		return ResponseEntity.created(location).build();
	}
	
	private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
	}
	@PutMapping("/{id}")
	public ResponseEntity put(@PathVariable("id")Long id, @RequestBody User user) {
		
		user.setId(id);
		
		UserDTO u = service.update(user, id);
		
		return u != null?
				ResponseEntity.ok(u) :
				ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable("id")Long id) {
		
		service.delete(id);
		
		return ResponseEntity.ok().build();
	}	
}
