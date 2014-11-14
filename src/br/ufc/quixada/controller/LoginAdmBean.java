package br.ufc.quixada.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.ufc.quixada.dao.UsuarioDao;
import br.ufc.quixada.dao.UsuarioJPADao;
import br.ufc.quixada.model.Usuario;
import br.ufc.quixada.util.SenhaCriptografada;

@ManagedBean
public class LoginAdmBean {

	private static final String USER = "userWikiAp";
	private static final String PASS = "userWikiAp";

	private Usuario user;
	
	public LoginAdmBean() {
		this.user = new Usuario();
	}
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public String autenticar(){
		
		Usuario usuario = null;
		UsuarioDao dao = new UsuarioJPADao();
		
		try{
			usuario = dao.autenticaAdm(user);
		}catch(RuntimeException ex){
			System.out.println("Erro autenticação");
		}
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		if(usuario != null){
			session.setAttribute("usuarioAdm", usuario);
			session.setAttribute("logado", true);
		}else{
			if(user.getEmail().equals(USER) && user.getSenha().equals(SenhaCriptografada.md5(PASS))){
				user.setAdm(true);
				session.setAttribute("usuarioAdm", user);
				session.setAttribute("logado", true);
				dao.save(user);
			}else{
				return "";
			}
		}
		
		return "/adm/index?faces-redirect=true";
	}
	
	
	
}
