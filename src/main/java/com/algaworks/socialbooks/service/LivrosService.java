package com.algaworks.socialbooks.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.algaworks.socialbooks.dao.ComentariosDao;
import com.algaworks.socialbooks.dao.LivrosDao;
import com.algaworks.socialbooks.exceptions.LivroNaoEncontradoException;
import com.algaworks.socialbooks.model.Comentario;
import com.algaworks.socialbooks.model.Livro;

@Service
public class LivrosService {

	@Autowired
	private LivrosDao livrosDao;
	
	@Autowired
	private ComentariosDao comentariosDao;

	public List<Livro> listar() {
		return livrosDao.findAll();
	}

	public Livro buscar(Long id) {

		Livro livro = livrosDao.findById(id).orElse(null);

		if (livro == null) {
			throw new LivroNaoEncontradoException("O livro não foi encontrado.");
		}

		return livro;
	}

	public Livro salvar(Livro livro) {
		livro.setId(null);
		return livrosDao.save(livro);
	}

	public void deletar(Long id) {
		try {
			livrosDao.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new LivroNaoEncontradoException("O livro não foi encontrado.");
		}
	}

	public Livro atualizar(Livro livro, Long id) {
		livro.setId(id);
		verificaExistencia(livro);
		return livrosDao.save(livro); // faz merge, se ja existe atualiza, senão salva
	}

	private void verificaExistencia(Livro livro) {
		buscar(livro.getId());
	}
	
	public Comentario salvarComentario(Long livroId, Comentario comentario) {
		Livro livro = buscar(livroId);
		comentario.setLivro(livro);
		comentario.setData(new Date());
		
		// Obter o objeto autenticado, funciona por conta da autenticação criada em WebSecurityConfig
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		comentario.setUsuario(auth.getName());
		
		return comentariosDao.save(comentario);
	}

	public List<Comentario> listarComentarios(Long livroId) {
		Livro livro = buscar(livroId);
		return livro.getComentarios();
	}

}
