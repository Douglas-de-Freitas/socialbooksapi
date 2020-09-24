package com.algaworks.socialbooks.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.socialbooks.model.Autor;

public interface AutoresDao extends JpaRepository<Autor, Long>{

}
