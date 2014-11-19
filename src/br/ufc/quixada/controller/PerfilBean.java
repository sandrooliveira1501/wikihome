package br.ufc.quixada.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;

import br.ufc.quixada.dao.UsuarioDao;
import br.ufc.quixada.dao.jpa.UsuarioJPADao;
import br.ufc.quixada.model.Usuario;

@ManagedBean
public class PerfilBean {

	private Usuario usuario;
	@ManagedProperty(value = "#{repositorioBean}")
	private RepositorioBean repositorio;
	private String novaSenha;

	public PerfilBean(){
		this.usuario = new Usuario();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public RepositorioBean getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(RepositorioBean repositorio) {
		this.repositorio = repositorio;
	}

	public String alterarPerfil() {

		UsuarioDao dao = new UsuarioJPADao();

		try {
			dao.update(repositorio.getUsuario());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Perfil editado com sucesso"));
		} catch (PersistenceException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro ao editar perfil!", ""));
		}

		return "?faces-redirect=true";
	}

	public String alterarSenha() {

		UsuarioDao dao = new UsuarioJPADao();

		try {
			usuario.setEmail(repositorio.getUsuario().getEmail());
			usuario = dao.autenticaUser(usuario);
			usuario.setSenha(novaSenha);
			dao.update(usuario);
			repositorio.setUsuario(usuario);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Perfil editado com sucesso"));
		} catch (PersistenceException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro ao editar perfil!", ""));
		}

		return "?faces-redirect=true";
		
	}
}
