package com.wlcoelho.testeNube.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wlcoelho.testeNube.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

	List<User> findByNome(String nome);
	
	List<User> findByTelefone(Long telefone);
}
