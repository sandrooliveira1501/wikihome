package br.ufc.quixada.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;

import br.ufc.quixada.dao.UsuarioDao;
import br.ufc.quixada.dao.jdbc.UsuarioJDBCDAO;
import br.ufc.quixada.model.Usuario;
import br.ufc.quixada.util.Email;

@ManagedBean
public class LoginBean {

	private final String EMAIL = "wikiape@gmail.com";

	private Usuario usuario;

	@ManagedProperty(value = "#{repositorioBean}")
	private RepositorioBean repositorio;

	public LoginBean() {
		this.usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public RepositorioBean getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(RepositorioBean repositorio) {
		this.repositorio = repositorio;
	}

	public String autentica() {

		UsuarioDao userDao = new UsuarioJDBCDAO();
		String retorno = "";

		Usuario user = userDao.autenticaUser(usuario);
		if (user == null) {
			FacesContext.getCurrentInstance().addMessage(
					"errologin",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso",
							"E-mail ou senha incorretos!"));
			return "";
		} else {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			
			retorno = "/user/index?faces-redirect=true";
			session.setAttribute("usuario", user);
			session.setAttribute("logado", true);

			repositorio.setUsuario(user);
		}

		return retorno;
	}

	public String criaUsuario() {

		try {

			UsuarioDao userDao = new UsuarioJDBCDAO();
			userDao.save(usuario);

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage("Usuário criado",
							"Faça login para editar suas informações!"));
		} catch (PersistenceException ex) {
			ex.printStackTrace();
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage("Aviso",
									"Erro ao enviar solicitação, e-mail já cadastrado no sistema!"));

		}

		return "?faces-redirect=true";
	}

	public String logout() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.setAttribute("logado", false);
		session.invalidate();

		return "/index?faces-redirect=true";

	}

	public String mudarSenha() {

		UsuarioDao userDao = new UsuarioJDBCDAO();
		String novaSenha = userDao.mudarSenha(usuario);
		String mensagem = "Sua nova senha é : " + novaSenha + "\n\n\n"
				+ ", Wiki Apê";

		Email.enviarEmail(EMAIL, usuario.getEmail(), "Nova Senha", mensagem);

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage("Recuperar Senha",
						"Sua nova senha foi mandada para seu e-mail"));

		return "?faces-redirect=true";
	}

}
