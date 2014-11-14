package br.ufc.quixada.dao;

import java.util.Random;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.ufc.quixada.model.Usuario;

public class UsuarioJPADao extends GenericJPADao<Usuario> implements UsuarioDao {

	public UsuarioJPADao() {
		this.persistenceClass = Usuario.class;
	}

	@Override
	public Usuario autenticaAdm(Usuario usuario) {

		String hql = "select u from Usuario u where u.adm = true and u.email = :email and u.senha = :senha";

		Query query = em.createQuery(hql);
		query.setParameter("email", usuario.getEmail());
		query.setParameter("senha", usuario.getSenha());

		Usuario user = null;

		try {
			if ((user = (Usuario) query.getSingleResult()) != null) {
				return user;
			} else {
				return null;
			}
		} catch (PersistenceException ex) {
			throw new RuntimeException("Erro no login");
		}

	}

	private static final String USER = "userWikiAp";
	private static final String PASS = "userWikiAp";

	@Override
	public Usuario userDefault() {

		Usuario usuario = new Usuario();
		usuario.setEmail(USER);
		usuario.setSenha(PASS);

		try {
			return autenticaAdm(usuario);
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Usuario autenticaUser(Usuario usuario) {

		String hql = "select u from Usuario u where u.email = :email and (u.senha = :senha or u.senhaTemporaria = :senha)";

		Query query = em.createQuery(hql);
		query.setParameter("email", usuario.getEmail());
		query.setParameter("senha", usuario.getSenha());

		Usuario user = null;

		try {
			if ((user = (Usuario) query.getSingleResult()) != null) {
				return user;
			} else {
				return null;
			}
		} catch (PersistenceException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	@Override
	public String mudarSenha(Usuario usuario) {

		try {
			String hql = "select u from Usuario u where u.email = :email";
			Query query = em.createQuery(hql);
			query.setParameter("email", usuario.getEmail());

			Usuario user = (Usuario) query.getSingleResult();
			String senhaTemporaria = gerarSenha();
			user.setSenhaTemporaria(senhaTemporaria);
			user.setContaCriada(false);
			
			this.update(user);

			return senhaTemporaria;
		} catch (PersistenceException ex) {
			ex.printStackTrace();
			return null;
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
