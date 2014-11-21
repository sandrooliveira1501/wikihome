package br.ufc.quixada.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DescricaoTabela {

	private String nome;
	private String nomeTabela;
	private String colunaChave;
	private List<String> colunas;
	private List<ChaveEstrangeira> chavesEstrangeiras;

	public DescricaoTabela(String nome, String nomeTabela, String colunaChave,
			String... colunas) {
		this.nome = nome;
		this.nomeTabela = nomeTabela;
		this.colunaChave = colunaChave;
		this.colunas = Arrays.asList(colunas);
		this.chavesEstrangeiras = new ArrayList<ChaveEstrangeira>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<String> getColunas() {
		return colunas;
	}

	public void setColunas(List<String> colunas) {
		this.colunas = colunas;
	}

	public String getNomeTabela() {
		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}

	public String getColunaChave() {
		return colunaChave;
	}

	public void setColunaChave(String colunaChave) {
		this.colunaChave = colunaChave;
	}

	public List<ChaveEstrangeira> getChavesEstrangeiras() {
		return chavesEstrangeiras;
	}

	public void setChavesEstrangeiras(List<ChaveEstrangeira> chavesEstrangeiras) {
		this.chavesEstrangeiras = chavesEstrangeiras;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		DescricaoTabela other = (DescricaoTabela) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
