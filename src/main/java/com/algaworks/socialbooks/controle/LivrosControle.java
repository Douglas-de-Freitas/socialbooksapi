package com.algaworks.socialbooks.controle;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.socialbooks.model.Comentario;
import com.algaworks.socialbooks.model.Livro;
import com.algaworks.socialbooks.service.LivrosService;

@RestController
@RequestMapping("/livros")
public class LivrosControle {

	@Autowired
	private LivrosService livrosService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@CrossOrigin
	public List<Livro> listar() {
		return livrosService.listar();
	}

	@PostMapping
	public ResponseEntity<Livro> salvar(@Valid @RequestBody Livro livro) {
		livro = livrosService.salvar(livro);

		// para retorno por location nos headers
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livro.getId()).toUri();

		return ResponseEntity.created(uri).body(livro); // retorna 201 created, location e o corpo json
	}

	@GetMapping("/{id}")
	public ResponseEntity<Livro> buscar(@PathVariable Long id) {
		Livro livro = livrosService.buscar(id);
		
		//Trabalhando com cache
		//TODO percebi que não esta funcionando legal
		CacheControl cacheControl = CacheControl.maxAge(20L, TimeUnit.SECONDS);
		
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(livro);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		livrosService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Livro> atualizar(@Valid @RequestBody Livro livro, @PathVariable Long id) {
		livro = livrosService.atualizar(livro, id);
		return ResponseEntity.ok(livro);
	}

	// -------------------------------------------------------------------------------

	// o nome da variável no @PathVariable (livroId) não correspondia ao do
	// @RequestMapping (id)
	@PostMapping("/{id}/comentarios")
	public ResponseEntity<Void> adicionarComentario(@PathVariable("id") Long livroId,
			@Valid @RequestBody Comentario comentario) {

		livrosService.salvarComentario(livroId, comentario);

		// para retorno por location nos headers
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping("/{id}/comentarios")
	public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable("id") Long livroId) {
		List<Comentario> comentarios = livrosService.listarComentarios(livroId);
		return ResponseEntity.status(HttpStatus.OK).body(comentarios);
	}

}
