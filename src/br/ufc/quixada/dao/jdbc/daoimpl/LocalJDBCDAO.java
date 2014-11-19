package br.ufc.quixada.dao.jdbc.daoimpl;

import java.util.List;

import br.ufc.quixada.dao.LocalDao;
import br.ufc.quixada.dao.jdbc.descricao.MontadorObjeto;
import br.ufc.quixada.model.Local;
import br.ufc.quixada.model.Usuario;

public class LocalJDBCDAO extends GenericJDBCDao<Local> implements LocalDao{
	
	private MontadorObjeto<Local> montadorObjeto = new MontadorObjeto<Local>();
	
	public LocalJDBCDAO() {
		super(Local.class);
	}
	
	@Override
	public List<Local> getLocais() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Local> getLocalPorUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
