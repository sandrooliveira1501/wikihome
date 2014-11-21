package br.ufc.quixada.model;


public class DescricaoLocal {

	private long idDescricao;

	private String tipo;
	private double preco;
	private int qtdQuartos;
	private int qtdApartamentos;
	private boolean garagem;
	private boolean quintal;
	private int banheiros;
	private String emailContato;
	private String telefoneContato;

	public long getIdDescricao() {
		return idDescricao;
	}

	public void setIdDescricao(long id) {
		this.idDescricao = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQtdQuartos() {
		return qtdQuartos;
	}

	public void setQtdQuartos(int qtdQuartos) {
		this.qtdQuartos = qtdQuartos;
	}

	public int getQtdApartamentos() {
		return qtdApartamentos;
	}

	public void setQtdApartamentos(int qtdApartamentos) {
		this.qtdApartamentos = qtdApartamentos;
	}

	public boolean isGaragem() {
		return garagem;
	}

	public void setGaragem(boolean garagem) {
		this.garagem = garagem;
	}

	public boolean isQuintal() {
		return quintal;
	}

	public void setQuintal(boolean quintal) {
		this.quintal = quintal;
	}

	public int getBanheiros() {
		return banheiros;
	}

	public void setBanheiros(int banheiros) {
		this.banheiros = banheiros;
	}

	public String getEmailContato() {
		return emailContato;
	}

	public void setEmailContato(String emailContato) {
		this.emailContato = emailContato;
	}

	public String getTelefoneContato() {
		return telefoneContato;
	}

	public void setTelefoneContato(String telefoneContato) {
		this.telefoneContato = telefoneContato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idDescricao ^ (idDescricao >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DescricaoLocal other = (DescricaoLocal) obj;
		if (idDescricao != other.idDescricao)
			return false;
		return true;
	}

}
