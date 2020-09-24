package com.algaworks.socialbooks.controle;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.socialbooks.model.Autor;
import com.algaworks.socialbooks.service.AutoresService;

@RestController
@RequestMapping("/autores")
public class AutoresControle {

	@Autowired
	private AutoresService autoresService;

	/* Usando Media Type para obter respostas em vários formatos
	 * Deve se observar que é necessário adicionar uma dependência no POM.xml:
	 * <groupId>com.fasterxml.jackson.dataformat</groupId>
	 * <artifactId>jackson-dataformat-xml</artifactId>
	 * Também é necessário quem estiver chamando (postman...) passar nos headers:
	 * Accept application/xml
	 * para que o retorno venha em XML e não no padrão json.
	 * */
	@GetMapping(produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE
	})
	public ResponseEntity<List<Autor>> listar() {
		List<Autor> autores = autoresService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(autores);
	}
	
	/* 
	 * O @Valid no metodo salvar garante que seja validado na hora de salvar e não 
	 * caso contrario, os validadores inseridos no bean (@notEmpty, size, notnull...) 
	 * só serão validados quando chegar na camada de persistência.
	 * */
	@PostMapping
	public ResponseEntity<Autor> salvar(@Valid @RequestBody Autor autor){
		autor = autoresService.salvar(autor);
		
		URI uri = ServletUriComponentsBuilder.
				fromCurrentRequest().
				path("/{id}").
				buildAndExpand(autor.getId()).
				toUri();
		
		return ResponseEntity.created(uri).body(autor);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Autor> buscar(@PathVariable Long id){
		Autor autor = autoresService.buscar(id);
		return ResponseEntity.status(HttpStatus.OK).body(autor);
	}
}
