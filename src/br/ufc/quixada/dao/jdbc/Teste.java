package br.ufc.quixada.dao.jdbc;

import br.ufc.quixada.dao.UsuarioDao;
import br.ufc.quixada.dao.jdbc.daoimpl.GenericJDBCDao;
import br.ufc.quixada.dao.jdbc.daoimpl.UsuarioJDBCDAO;
import br.ufc.quixada.model.Local;
import br.ufc.quixada.model.Usuario;

public class Teste {
	
	public static void main(String[] args) {
		Usuario usuario;
		
		UsuarioDao udao = new UsuarioJDBCDAO();
		//usuario = udao.find(1);
		
		//System.out.println(dao.autenticaUser(usuario));
		
		//dao.mudarSenha(usuario);
		
		Local local = new Local();
		//local.setUsuario(usuario);
		local.setId(1000);
		local.setLongitude("-39.014280540197774");
		local.setLatitude("-4.969165319167851");
		
		GenericJDBCDao<Local> dao = new GenericJDBCDao<Local>(Local.class);
		//dao.save(local);
		
		local= dao.find(1000);
		System.out.println(local.getUsuario());
	}

}
