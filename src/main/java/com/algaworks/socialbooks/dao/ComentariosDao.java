package com.algaworks.socialbooks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.algaworks.socialbooks.model.Comentario;

public interface ComentariosDao extends JpaRepository<Comentario, Long>{

}
