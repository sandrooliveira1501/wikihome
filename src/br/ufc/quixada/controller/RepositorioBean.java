package br.ufc.quixada.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.ufc.quixada.dao.ComentarioDao;
import br.ufc.quixada.dao.LocalDao;
import br.ufc.quixada.dao.jdbc.ComentarioJDBCDAO;
import br.ufc.quixada.dao.jdbc.LocalJDBCDAO;
import br.ufc.quixada.model.Comentario;
import br.ufc.quixada.model.Local;
import br.ufc.quixada.model.Usuario;

@SessionScoped
@ManagedBean(name = "repositorioBean")
public class RepositorioBean {

	private Usuario usuario;
	private Local local;
	private boolean page = false;
	private Comentario comentario;
	private List<Comentario> comentarios;

	public RepositorioBean() {
		this.local = new Local();
		this.usuario = new Usuario();
		this.comentario = new Comentario();
		comentarios = new ArrayList<Comentario>();
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
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	public boolean emptyList(List lista) {
		return lista.size() == 0;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public String adicionarComentario() {
		ComentarioDao comentarioDao = new ComentarioJDBCDAO();
		comentario.setLocal(this.local);
		comentario.setUsuario(this.usuario);
		comentarioDao.save(comentario);

		comentario = new Comentario();
		
	    try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("coments.xhtml?id=" + local.getId());
			return "";
	    } catch (IOException e) {
			e.printStackTrace();
			return "?faces-redirect=true&includeViewParams=true&id=" + local.getId();
		}   
		
	}

	public void carregarLocal() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		long id;
		if (request.getParameter("id") != null) {
			id = Long.parseLong(request.getParameter("id"));
			LocalDao localDao = new LocalJDBCDAO();
			this.local = localDao.find(id);

			ComentarioDao comentarioDao = new ComentarioJDBCDAO();
			comentarios = comentarioDao.getComentariosLocal(this.local);

		}
	}

}