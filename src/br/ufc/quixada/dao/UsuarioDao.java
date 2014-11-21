package br.ufc.quixada.dao;

import br.ufc.quixada.model.Usuario;

public interface UsuarioDao extends GenericDao<Usuario>{

	public Usuario autenticaUser(Usuario usuario);
	public String mudarSenha(Usuario usuario);
	
}
