package br.ufc.quixada.model;

public class Comentario {

	private long idComentario;
	private String texto;
	private Local local;
	private Usuario usuario;

	public long getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(long id) {
		this.idComentario = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
