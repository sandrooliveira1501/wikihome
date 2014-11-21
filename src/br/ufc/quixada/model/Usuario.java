package br.ufc.quixada.model;

import br.ufc.quixada.util.SenhaCriptografada;

public class Usuario {

	private long idUsuario;
	private String email;
	private String senha;
	private String senhaTemporaria;
	private String nome;
	private String endereco = "Sem endereço";
	private String cidade = "Sem cidade";

	public Usuario() {
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = SenhaCriptografada.md5(senha);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getSenhaTemporaria() {
		return senhaTemporaria;
	}

	public void setSenhaTemporaria(String senhaTemporaria) {
		this.senhaTemporaria = SenhaCriptografada.md5(senhaTemporaria);
		;
	}

	@Override
	public String toString() {
		return this.email + " - " + this.idUsuario;
	}

}
