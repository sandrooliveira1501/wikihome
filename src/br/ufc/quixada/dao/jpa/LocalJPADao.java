package br.ufc.quixada.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import br.ufc.quixada.dao.LocalDao;
import br.ufc.quixada.model.Local;
import br.ufc.quixada.model.Usuario;

public class LocalJPADao extends GenericJPADao<Local> implements LocalDao {

	public LocalJPADao() {
		this.persistenceClass = Local.class;
	}

	@Override
	public List<Local> getLocais() {

		String hql = "select l from Local l";

		Query query = em.createQuery(hql);

		List<Local> lista;

		if ((lista = query.getResultList()) == null) {
			lista = new ArrayList<Local>();
		}

		return lista;
	}

	@Override
	public List<Local> getLocalPorUsuario(Usuario usuario) {
		String hql = "select l from Local l inner join l.usuario u where u.idUsuario = :usuario";
		
		Query query = em.createQuery(hql);
		query.setParameter("usuario", usuario.getIdUsuario());
		
		List<Local> lista;

		if ((lista = query.getResultList()) == null) {
			lista = new ArrayList<Local>();
		}

		return lista;
	}

	public static void main(String[] args) {
		
		Usuario user = new UsuarioJPADao().find(1L);
		LocalDao dao = new LocalJPADao();
		System.out.println(dao.getLocalPorUsuario(user).size());
		
	}
	
}
