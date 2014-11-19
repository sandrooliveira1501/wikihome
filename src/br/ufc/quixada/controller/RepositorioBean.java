package br.ufc.quixada.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.ufc.quixada.dao.LocalDao;
import br.ufc.quixada.dao.jpa.LocalJPADao;
import br.ufc.quixada.model.Local;
import br.ufc.quixada.model.Usuario;

@SessionScoped
@ManagedBean(name = "repositorioBean")
public class RepositorioBean {

	private Usuario usuario;
	private Local local;
	private boolean page = false;

	public RepositorioBean() {
		this.local = new Local();
		this.usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		if (this.usuario.getEndereco() == null)
			this.usuario.setEndereco("Clique para adicionar");
		if (this.usuario.getCidade() == null)
			this.usuario.setCidade("Clique para adicionar");
		if (this.usuario.getProfissao() == null)
			this.usuario.setProfissao("Clique para adicionar");
		if (this.usuario.getEmpresa() == null)
			this.usuario.setEmpresa("Clique para adicionar");
	}

	public boolean emptyList(List lista) {
		return lista.size() == 0;
	}

	public void carregarLocal() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		long id;
		if (request.getParameter("id") != null) {
			id = Long.parseLong(request.getParameter("id"));
			LocalDao localDao = new LocalJPADao();
			this.local = localDao.find(id);
		}
	}

}