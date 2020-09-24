package com.algaworks.socialbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.socialbooks.dao.AutoresDao;
import com.algaworks.socialbooks.exceptions.AutorExistenteException;
import com.algaworks.socialbooks.exceptions.AutorNaoEncontradoException;
import com.algaworks.socialbooks.model.Autor;

@Service
public class AutoresService {
	
	@Autowired
	private AutoresDao autoresDao;
	
	public List<Autor> listar(){
		return autoresDao.findAll();
	}
	
	public Autor salvar(Autor autor) {
		if(autor.getId() != null) {
			Autor a = autoresDao.findById(autor.getId()).orElse(null);
			if(a != null) {
				throw new AutorExistenteException("O autor já existe");
			}
		}
		return autoresDao.save(autor);
	}
	
	public Autor buscar(Long id) {
		Autor autor = autoresDao.findById(id).orElse(null);
		if(autor == null) {
			throw new AutorNaoEncontradoException("O Autor não foi encontrado.");
		}
		return autor;
	}

}
