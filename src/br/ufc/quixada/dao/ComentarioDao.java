package br.ufc.quixada.dao;

import java.util.List;

import br.ufc.quixada.model.Comentario;
import br.ufc.quixada.model.Local;

public interface ComentarioDao extends GenericDao<Comentario>{

	public List<Comentario> getComentariosLocal(Local local);
	
}
