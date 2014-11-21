package br.ufc.quixada.model;


public class Local {

	private long idLocal;
	private Usuario usuario;
	private DescricaoLocal descricao;
	private String endereco;
	private String latitude;
	private String longitude;

	public long getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(long id) {
		this.idLocal = id;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public DescricaoLocal getDescricao() {
		return descricao;
	}

	public void setDescricao(DescricaoLocal descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.endereco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idLocal ^ (idLocal >>> 32));
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
		Local other = (Local) obj;
		if (idLocal != other.idLocal)
			return false;
		return true;
	}

}
