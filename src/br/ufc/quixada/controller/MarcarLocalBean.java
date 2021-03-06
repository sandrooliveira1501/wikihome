package br.ufc.quixada.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpSession;

import br.ufc.quixada.dao.LocalDao;
import br.ufc.quixada.dao.UsuarioDao;
import br.ufc.quixada.dao.jdbc.LocalJDBCDAO;
import br.ufc.quixada.dao.jdbc.UsuarioJDBCDAO;
import br.ufc.quixada.model.DescricaoLocal;
import br.ufc.quixada.model.Local;
import br.ufc.quixada.model.Usuario;

@ManagedBean
public class MarcarLocalBean {

	private Local local;
	@ManagedProperty(value = "#{repositorioBean}")
	private RepositorioBean repositorio;

	
	public MarcarLocalBean() {
		this.local = new Local();
		this.local.setDescricao(new DescricaoLocal());
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public RepositorioBean getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(RepositorioBean repositorio) {
		this.repositorio = repositorio;
	}

	public String adicionarLocal() {

		LocalDao localDao = new LocalJDBCDAO();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		if (usuario != null) {
			local.setUsuario(repositorio.getUsuario());
			localDao.save(local);
			Flash flash = FacesContext.getCurrentInstance()
					.getExternalContext().getFlash();
			flash.setKeepMessages(true);
			FacesContext.getCurrentInstance().addMessage("marcarLocal",
					new FacesMessage("Local", "Local adicionado com sucesso!"));
		} else {
			Usuario user = new Usuario();
			user.setEmail("wikiape@gmail.com");
			user.setSenha("qwe123");
			UsuarioDao dao = new UsuarioJDBCDAO();
			usuario = dao.autenticaUser(user);
			if (usuario == null) {
				dao.save(user);
				usuario = dao.autenticaUser(user);
			}
			local.setUsuario(usuario);
			localDao.save(local);
			Flash flash = FacesContext.getCurrentInstance()
					.getExternalContext().getFlash();
			flash.setKeepMessages(true);
			FacesContext.getCurrentInstance().addMessage("marcarLocal",
					new FacesMessage("Local", "Local adicionado com sucesso!"));
		}

		return "/index?faces-redirect=true";
	}

}
