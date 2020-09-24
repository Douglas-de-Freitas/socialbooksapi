package com.algaworks.socialbooks.exceptionhandler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.algaworks.socialbooks.exceptions.AutorExistenteException;
import com.algaworks.socialbooks.exceptions.AutorNaoEncontradoException;
import com.algaworks.socialbooks.exceptions.LivroNaoEncontradoException;
import com.algaworks.socialbooks.model.DetalhesErro;

@ControllerAdvice
public class ControleExceptionHandler {
	
	@ExceptionHandler(LivroNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro> handleLivroNaoEncontradoException(LivroNaoEncontradoException e, HttpServletRequest request) {
		
		DetalhesErro erro = new DetalhesErro(
				"O Livro não foi encontrado", 
				404L, 
				System.currentTimeMillis(), 
				"http://erros.socialbooks.com/404"
		);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(AutorExistenteException.class)
	public ResponseEntity<DetalhesErro> handleAutorExistenteException(AutorExistenteException e, HttpServletRequest request) {
		
		DetalhesErro erro = new DetalhesErro(
				"Autor já existe", 
				409L, 
				System.currentTimeMillis(), 
				"http://erros.socialbooks.com/409"
		);
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
	
	@ExceptionHandler(AutorNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro> handleAutorNaoEncontradoException(AutorNaoEncontradoException e, HttpServletRequest request) {
		
		DetalhesErro erro = new DetalhesErro(
				"O Autor não foi encontrado", 
				404L, 
				System.currentTimeMillis(), 
				"http://erros.socialbooks.com/404"
		);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<DetalhesErro> handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
		
		DetalhesErro erro = new DetalhesErro(
				"Requisição Inválida", 
				400L, 
				System.currentTimeMillis(), 
				"http://erros.socialbooks.com/400"
		);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

}
