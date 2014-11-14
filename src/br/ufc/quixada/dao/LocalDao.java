package br.ufc.quixada.dao;

import java.util.List;

import br.ufc.quixada.model.Local;
import br.ufc.quixada.model.Usuario;

public interface LocalDao extends GenericDao<Local>{
	
	public List<Local> getLocais();
	public List<Local> getLocalPorUsuario(Usuario usuario);

}
