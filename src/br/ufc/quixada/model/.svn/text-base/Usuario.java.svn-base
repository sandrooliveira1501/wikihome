package br.ufc.quixada.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.ufc.quixada.util.SenhaCriptografada;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idUsuario;
	@Column(unique = true)
	private String email;
	private String senha;
	private String senhaTemporaria;
	private String nome;
	private String endereco;
	private String cidade;
	private String empresa;
	private String profissao;
	private boolean adm = false;
	private boolean contaCriada = false;

	public Usuario() {
	}

	public Usuario(long idUsuario, String email, String senha) {
		this.idUsuario = idUsuario;
		this.email = email;
		this.senha = senha;
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

	public boolean isAdm() {
		return adm;
	}

	public void setAdm(boolean adm) {
		this.adm = adm;
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

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public boolean isContaCriada() {
		return contaCriada;
	}

	public void setContaCriada(boolean contaCriada) {
		this.contaCriada = contaCriada;
	}

	public String getSenhaTemporaria() {
		return senhaTemporaria;
	}

	public void setSenhaTemporaria(String senhaTemporaria) {
		this.senhaTemporaria = SenhaCriptografada.md5(senhaTemporaria);;
	}

	@Override
	public String toString() {
		return this.email + " - " + this.idUsuario;
	}

}
