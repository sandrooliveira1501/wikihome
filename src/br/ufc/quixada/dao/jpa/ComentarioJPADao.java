package br.ufc.quixada.dao.jpa;

import br.ufc.quixada.dao.ComentarioDao;
import br.ufc.quixada.model.Comentario;

public class ComentarioJPADao extends GenericJPADao<Comentario> implements ComentarioDao{

	public ComentarioJPADao() {
		this.persistenceClass = Comentario.class;
	}
	
}

