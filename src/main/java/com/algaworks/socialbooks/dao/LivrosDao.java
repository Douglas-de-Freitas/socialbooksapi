package com.algaworks.socialbooks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.algaworks.socialbooks.model.Livro;

public interface LivrosDao extends JpaRepository<Livro, Long> {

}
