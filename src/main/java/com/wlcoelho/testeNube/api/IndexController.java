package com.wlcoelho.testeNube.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {
	
	
	//mapeamento da requisição abaixo para ser chamado
	@GetMapping
	public String get () {
		return "Api User";
	}
}
