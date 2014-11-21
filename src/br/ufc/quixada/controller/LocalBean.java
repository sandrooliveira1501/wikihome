package br.ufc.quixada.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.ufc.quixada.dao.LocalDao;
import br.ufc.quixada.dao.jdbc.daoimpl.LocalJDBCDAO;
import br.ufc.quixada.model.Local;
import br.ufc.quixada.model.Usuario;

@ManagedBean
public class LocalBean {

	private List<Local> locais;
	@ManagedProperty(value = "#{repositorioBean}")
	private RepositorioBean repositorio;

	public LocalBean() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		LocalDao dao = new LocalJDBCDAO();
		locais = dao.getLocalPorUsuario(usuario);
	}

	public List<Local> getLocais() {
		return locais;
	}

	public void setLocais(List<Local> locais) {
		this.locais = locais;
	}

	public RepositorioBean getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(RepositorioBean repositorio) {
		this.repositorio = repositorio;
	}

	public String removerPonto() {

		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();

			long id = Long.parseLong(request.getParameter("idLocal"));

			LocalDao dao = new LocalJDBCDAO();
			Local local = dao.find(id);
			dao.delete(local);
			locais.remove(local);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage("Confirmação",
							"Local removido com sucesso!"));
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage("Aviso",
									"Erro ao enviar solicitação de exclusão, tente novamente!"));

		}
		return "?faces-redirect=true";

	}

}
