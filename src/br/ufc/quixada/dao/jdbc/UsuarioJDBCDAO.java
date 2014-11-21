package br.ufc.quixada.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.persistence.Query;

import br.ufc.quixada.config.MontadorObjeto;
import br.ufc.quixada.dao.UsuarioDao;
import br.ufc.quixada.model.Usuario;

public class UsuarioJDBCDAO extends GenericJDBCDao<Usuario> implements
		UsuarioDao {

	private MontadorObjeto<Usuario> montadorObjeto;

	public UsuarioJDBCDAO() {
		super(Usuario.class);
		montadorObjeto = new MontadorObjeto<Usuario>();
	}

	@Override
	public Usuario autenticaUser(Usuario usuario) {

		StringBuilder sql = new StringBuilder();

		sql.append("select * from Usuario u where u.email = ? and u.senha = ?");

		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = connection.prepareStatement(sql.toString());
			stmt.setObject(1, usuario.getEmail());
			stmt.setObject(2, usuario.getSenha());

			resultSet = stmt.executeQuery();
			if (resultSet.next()) {

				Usuario usuarioAutenticado = montadorObjeto.montarObjeto(
						descricaoTabela, resultSet);

				return usuarioAutenticado;

			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				resultSet.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

	}

	@Override
	public String mudarSenha(Usuario usuario) {

		/*
		 * String hql = "select u from Usuario u where u.email = :email"; Query
		 * query = em.createQuery(hql); query.setParameter("email",
		 * usuario.getEmail());
		 * 
		 * Usuario user = (Usuario) query.getSingleResult(); String
		 * senhaTemporaria = gerarSenha();
		 * user.setSenhaTemporaria(senhaTemporaria); user.setContaCriada(false);
		 * 
		 * this.update(user);
		 * 
		 * return senhaTemporaria;
		 */

		StringBuilder sql = new StringBuilder();
		sql.append("select * from Usuario u where u.email = ?");

		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		try {
			stmt = connection.prepareStatement(sql.toString());
			stmt.setObject(1, usuario.getEmail());
			resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				Usuario usuarioBusca = montadorObjeto.montarObjeto(
						descricaoTabela, resultSet);

				String novaSenha = gerarSenha();
				usuarioBusca.setSenhaTemporaria(novaSenha);
				update(usuarioBusca);
				return novaSenha;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				stmt.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

	}

	public static String gerarSenha() {

		String novaSenha = "";
		Random random = new Random();

		while (novaSenha.length() < 6) {
			novaSenha += random.nextInt(10);
		}

		return novaSenha;
	}

}
