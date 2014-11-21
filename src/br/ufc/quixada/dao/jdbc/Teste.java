package br.ufc.quixada.dao.jdbc;

import br.ufc.quixada.dao.GenericDao;
import br.ufc.quixada.dao.UsuarioDao;
import br.ufc.quixada.dao.jdbc.daoimpl.GenericJDBCDao;
import br.ufc.quixada.dao.jdbc.daoimpl.UsuarioJDBCDAO;
import br.ufc.quixada.model.Usuario;

public class Teste {
	
	public static void main(String[] args) {
		
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(1000);
		usuario.setEmail("teste");
		usuario.setSenha("teste");
		
		UsuarioDao dao = new UsuarioJDBCDAO();
		dao.save(usuario);
		
	}

}
